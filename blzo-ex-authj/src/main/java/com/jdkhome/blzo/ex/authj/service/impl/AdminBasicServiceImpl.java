package com.jdkhome.blzo.ex.authj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.ex.authj.core.AuthjConstants;
import com.jdkhome.blzo.ex.authj.enums.AdminStatusEnum;
import com.jdkhome.blzo.ex.authj.enums.AuthjResponseError;
import com.jdkhome.blzo.ex.authj.generator.dao.AdminMapper;
import com.jdkhome.blzo.ex.authj.generator.model.Admin;
import com.jdkhome.blzo.ex.authj.generator.model.AdminExample;
import com.jdkhome.blzo.ex.authj.pojo.dto.LayerDTO;
import com.jdkhome.blzo.ex.authj.service.AdminBasicService;
import com.jdkhome.blzo.ex.authj.service.GroupBasicService;
import com.jdkhome.blzo.ex.basic.constants.SqlTemplate;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.utils.coder.PasswordEncoder;
import com.jdkhome.blzo.ex.utils.generator.SaltGenerator;
import com.jdkhome.blzo.ex.utils.tools.gson.PerfectGson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * Created by jdk on 17/11/16.
 * 管理员管理业务实现类
 */
@Slf4j
@Service
public class AdminBasicServiceImpl implements AdminBasicService {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    GroupBasicService groupBasicService;

    /**
     * 添加管理员
     *
     * @param username 登录名
     * @param password 密码
     * @param nickName
     * @param phone
     * @param remark
     * @return
     */
    @Override
    public Integer addAdmin(Integer organizeId, String username, String password, String nickName, String phone, String email, String remark) {

        //入参验证
        if (organizeId == null || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(nickName) || StringUtils.isEmpty(phone)) {
            log.error("添加管理员 -> 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        Admin admin = new Admin();
        admin.setOrganizeId(organizeId);
        admin.setUsername(username);
        admin.setSalt(SaltGenerator.get());
        admin.setPassword(PasswordEncoder.toMD5(password, admin.getSalt()));
        admin.setNickName(nickName);
        admin.setPhone(phone);
        admin.setEmail(email);
        admin.setRemark(remark);
        admin.setStatus(AdminStatusEnum.INIT.getCode());

        return adminMapper.insertSelective(admin);
    }

    /**
     * 修改管理员
     *
     * @param adminId
     * @param username 登录名
     * @param password 密码
     * @param nickName
     * @param phone
     * @param remark
     * @return
     */
    @Override
    public Integer editAdmin(Integer adminId, String username, String password,
                             String nickName, String phone, String email,
                             AdminStatusEnum status, String remark, List<LayerDTO> layer) {

        //入参验证
        if (adminId == null) {
            log.error("修改管理员->参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        //获取管理员
        Admin admin = this.getAdminById(adminId);

        if (admin == null) {
            log.error("修改管理员->管理员 adminId={} 不存在", adminId);
            throw new ServiceException(AuthjResponseError.ADMIN_NOT_EXIST);
        }


        Admin updateAdmin = new Admin();

        updateAdmin.setId(adminId);

        if (StringUtils.isNotEmpty(username)) {
            updateAdmin.setUsername(username);
        }

        if (StringUtils.isNotEmpty(password)) {
            String salt = admin.getSalt();
            updateAdmin.setPassword(PasswordEncoder.toMD5(password, salt));
        }

        if (StringUtils.isNotEmpty(nickName)) {
            updateAdmin.setNickName(nickName);
        }

        if (StringUtils.isNotEmpty(phone)) {
            updateAdmin.setPhone(phone);
        }

        if (StringUtils.isNotEmpty(email)) {
            updateAdmin.setEmail(email);
        }

        if (status != null) {
            updateAdmin.setStatus(status.getCode());
        }

        if (StringUtils.isNotEmpty(remark)) {
            updateAdmin.setRemark(remark);
        }

        if (layer != null) {
            // size=0 = > []
            updateAdmin.setLayer(PerfectGson.getGson().toJson(layer));
        }

        return adminMapper.updateByPrimaryKeySelective(updateAdmin);
    }

    /**
     * 删除管理员
     *
     * @param adminId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer delAdmin(Integer adminId) {

        if (adminId == null) {
            log.error("删除管理员->参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        if (adminId.equals(AuthjConstants.ROOT_ID)) {
            log.error("删除管理员->禁止删除root用户");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        // 被删除的管理员 退出所有加入的组
        groupBasicService.delGroupAdminByAdminId(adminId);

        // 删除所有该管理员创建的组
        groupBasicService.delGroupByCreateAdminId(adminId);

        return adminMapper.deleteByPrimaryKey(adminId);
    }

    /**
     * 获取管理员通过Id
     *
     * @param adminId
     * @return
     */
    @Override
    public Admin getAdminById(Integer adminId) {

        //入参验证
        if (adminId == null) {
            log.error("获取管理员通过Id->参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        return adminMapper.selectByPrimaryKey(adminId);
    }

    /**
     * 获取管理员通过username
     *
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUsername(String username) {

        if (StringUtils.isEmpty(username)) {
            log.error("获取管理员通过username->参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        AdminExample example = new AdminExample();
        example.createCriteria().andUsernameEqualTo(username);

        List<Admin> objs = adminMapper.selectByExample(example);
        if (objs != null && !objs.isEmpty()) {
            return objs.get(0);
        }
        return null;
    }

    /**
     * 获取通过phone
     *
     * @param phone
     * @return
     */
    @Override
    public Admin getAdminByPhone(String phone) {

        if (StringUtils.isEmpty(phone)) {
            log.error("获取管理员获取通过phone->参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        List<Admin> list = this.getAllAdmin(null, null, null, phone, null);

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 获取通过email
     *
     * @param email
     * @return
     */
    @Override
    public Admin getAdminByEmail(String email) {

        if (StringUtils.isEmpty(email)) {
            log.error("获取管理员获取通过email->参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        List<Admin> list = this.getAllAdmin(null, null, null, null, email);

        return CollectionUtils.isEmpty(list) ? null : list.get(0);
    }

    /**
     * 分页查询管理员
     *
     * @param username
     * @param nickName
     * @param phone
     * @param page
     * @param size
     * @return
     */
    @Override
    public PageInfo<Admin> getAdminsWithPage(Integer organizeId, String username, String nickName, String phone, String email, Integer page, Integer size) {

        //入参验证
        if (page == null || size == null) {
            log.error("分页查询管理员 -> 分页参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        PageHelper.startPage(page, size);
        return new PageInfo<>(this.getAllAdmin(organizeId, username, nickName, phone, email));
    }

    /**
     * 获取所有管理员
     *
     * @param username
     * @param nickName
     * @param phone
     * @return
     */
    @Override
    public List<Admin> getAllAdmin(Integer organizeId, String username, String nickName, String phone, String email) {

        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();

        if (organizeId != null) {
            criteria.andOrganizeIdEqualTo(organizeId);
        }

        if (StringUtils.isNotEmpty(username)) {
            criteria.andUsernameEqualTo(username);
        }

        if (StringUtils.isNotEmpty(nickName)) {
            criteria.andNickNameLike("%" + nickName + "%");
        }

        if (StringUtils.isNotEmpty(phone)) {
            criteria.andPhoneEqualTo(phone);
        }

        if (StringUtils.isNoneEmpty(email)) {
            criteria.andEmailEqualTo(email);
        }

        example.setOrderByClause(SqlTemplate.ORDER_BY_ID_DESC);

        return adminMapper.selectByExample(example);
    }
}

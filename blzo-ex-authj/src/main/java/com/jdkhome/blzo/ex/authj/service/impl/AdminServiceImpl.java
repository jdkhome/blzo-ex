package com.jdkhome.blzo.ex.authj.service.impl;

import com.jdkhome.blzo.ex.authj.core.AuthjConstants;
import com.jdkhome.blzo.ex.authj.enums.AdminStatusEnum;
import com.jdkhome.blzo.ex.authj.enums.AuthjResponseError;
import com.jdkhome.blzo.ex.authj.generator.dao.AdminMapper;
import com.jdkhome.blzo.ex.authj.generator.model.Admin;
import com.jdkhome.blzo.ex.authj.generator.model.AdminExample;
import com.jdkhome.blzo.ex.authj.generator.model.Organize;
import com.jdkhome.blzo.ex.authj.service.*;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import com.jdkhome.blzo.ex.google_auth.GoogleAuth;
import com.jdkhome.blzo.ex.utils.coder.PasswordEncoder;
import com.jdkhome.blzo.ex.utils.generator.SaltGenerator;
import com.jdkhome.blzo.ex.utils.generator.UUIDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by jdk on 17/12/6.
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminBasicService adminBasicService;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    GroupBasicService groupBasicService;

    @Autowired
    OrganizeBasicService organizeBasicService;

    @Autowired
    GroupService groupService;

    /**
     * 管理员登录
     *
     * @param username
     * @param password
     * @param ip
     * @return
     */
    @Override
    public Admin login(String username, String password, String ip) {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(ip)) {
            log.error("管理员登录->登录必要参数为空");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        // 获取对应管理员
        Admin admin = adminBasicService.getAdminByUsername(username);
        if (admin == null) {
            log.error("管理员登录->管理员不存在");
            throw new ServiceException(AuthjResponseError.ADMIN_NOT_EXIST);
        }

        if (StringUtils.isNotEmpty(admin.getGoogleSecret())) {
            // 如果设置了Google验证码 则使用google验证码登陆
            if (!GoogleAuth.validCode(admin.getGoogleSecret(), password)) {
                log.error("管理员登录->谷歌验证码校验失败");
                throw new ServiceException(AuthjResponseError.PASSWORD_ERROR);
            }
        } else if (!admin.getPassword().equals(PasswordEncoder.toMD5(password, admin.getSalt()))) {
            // 没设置Google验证码则使用密码验证
            log.error("管理员登录->密码错误");
            throw new ServiceException(AuthjResponseError.PASSWORD_ERROR);
        }

        //更新ip
        admin.setLastIp(ip);
        admin.setLastTime(new Date());

        adminMapper.updateByPrimaryKeySelective(admin);

        return admin;
    }

    /**
     * 设置google验证码 已设置则覆盖
     *
     * @param adminId
     * @param secret  秘钥
     * @param code    code
     * @return
     */
    @Override
    public void setGoogleAuth(Integer adminId, String secret, String code) {

        if (adminId == null || StringUtils.isEmpty(secret) || StringUtils.isEmpty(code)) {
            log.error("设置google验证码 -> 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        if (!GoogleAuth.validCode(secret, code)) {
            log.error("设置google验证码 -> 验证失败");
            throw new ServiceException(AuthjResponseError.GOOGLE_AUTH_CODE_ERROR);
        }

        Admin admin = new Admin();

        admin.setId(adminId);
        admin.setGoogleSecret(secret);

        adminMapper.updateByPrimaryKeySelective(admin);
    }

    /**
     * 移除google验证码
     *
     * @param adminId
     */
    @Override
    public void removeGoogleAuth(Integer adminId) {
        if (adminId == null) {
            log.error("移除google验证码 -> 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        Admin admin = new Admin();
        admin.setId(adminId);
        admin.setGoogleSecret("");

        adminMapper.updateByPrimaryKeySelective(admin);
    }

    /**
     * 激活管理员账户(第一次登录需重置密码)
     *
     * @param adminId     管理员Id
     * @param newPassword 新密码
     * @return
     */
    @Override
    public Integer activeAdmin(Integer adminId, String newPassword) {

        // 入参验证
        if (adminId == null || StringUtils.isEmpty(newPassword)) {
            log.error("激活管理员账户 -> 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        //获取管理员
        Admin admin = adminBasicService.getAdminById(adminId);

        if (admin == null) {
            log.error("激活管理员账户 -> 管理员 adminId={} 不存在", adminId);
            throw new ServiceException(AuthjResponseError.ADMIN_NOT_EXIST);
        }


        Admin updateAdmin = new Admin();
        updateAdmin.setId(adminId);
        String salt = admin.getSalt();
        updateAdmin.setPassword(PasswordEncoder.toMD5(newPassword, salt));
        updateAdmin.setStatus(AdminStatusEnum.NORMAL.getCode());

        // 校验
        if (!AdminStatusEnum.INIT.getCode().equals(admin.getStatus())) {
            log.error("激活管理员账户 -> 管理员 adminId={} 状态错误", adminId, admin.getStatus());
            throw new ServiceException(AuthjResponseError.ADMIN_STATUS_ERROR);
        }

        if (admin.getPassword().equals(updateAdmin.getPassword())) {
            log.error("激活管理员账户 -> 新密码不能和老密码一样", adminId);
            throw new ServiceException(AuthjResponseError.ACTIVE_LIKE_ORIGIN);
        }

        adminMapper.updateByPrimaryKeySelective(updateAdmin);

        return null;
    }

    /**
     * 修改管理员组织
     *
     * @param adminId
     * @param orgId
     * @return
     */
    @Override
    public Integer changeAdminOrg(Integer adminId, Integer orgId) {

        Admin admin = adminBasicService.getAdminById(adminId);

        if (admin == null) {
            log.error("修改管理员组织 -> 管理员不存在 adminId={}", adminId);
            throw new ServiceException(AuthjResponseError.ADMIN_NOT_EXIST);
        }

        Organize organize = organizeBasicService.getOrganizeById(orgId);
        if (organize == null) {
            log.error("修改管理员组织 -> 管理员不存在 adminId={}", adminId);
            throw new ServiceException(AuthjResponseError.ADMIN_NOT_EXIST);
        }

        // 移除管理员创建的所有组
        groupService.delGroupByCreateAdminId(adminId);

        // 管理员退出所有非0号组织创建的组
        groupService.delGroupAdminByAdminId(adminId);

        Admin updateAdmin = new Admin();
        updateAdmin.setId(adminId);
        updateAdmin.setOrganizeId(orgId);

        return adminMapper.updateByPrimaryKeySelective(updateAdmin);
    }

    /**
     * 如果没有root用户，则自动初始化一个
     */
    @Override
    public void initRoot() {

        Admin admin = adminBasicService.getAdminById(AuthjConstants.ROOT_ID);

        if (admin == null) {

            admin = new Admin();

            String pwd = UUIDGenerator.get();

            admin.setId(AuthjConstants.ROOT_ID);
            admin.setUsername(AuthjConstants.ROOT_INIT_USERNAME);
            admin.setSalt(SaltGenerator.get());
            admin.setPassword(PasswordEncoder.toMD5(pwd, admin.getSalt()));
            admin.setNickName(AuthjConstants.ROOT_INIT_NICKNAME);
            admin.setStatus(AdminStatusEnum.NORMAL.getCode());
            admin.setPhone(null);
            admin.setRemark(null);

            adminMapper.insertSelective(admin);

            AdminExample example = new AdminExample();
            example.createCriteria().andUsernameEqualTo(AuthjConstants.ROOT_INIT_USERNAME);
            Admin upId = new Admin();
            upId.setId(AuthjConstants.ROOT_ID);
            adminMapper.updateByExampleSelective(upId, example);

            log.info("自动初始化root用户 -> pwd: {}", pwd);
        }


    }
}

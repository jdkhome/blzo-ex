package com.jdkhome.blzo.ex.authj.service.impl;

import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.generator.dao.GroupAdminMapper;
import com.jdkhome.blzo.ex.authj.generator.dao.GroupAuthMapper;
import com.jdkhome.blzo.ex.authj.generator.dao.GroupMapper;
import com.jdkhome.blzo.ex.authj.generator.model.Group;
import com.jdkhome.blzo.ex.authj.generator.model.GroupAdminExample;
import com.jdkhome.blzo.ex.authj.generator.model.GroupAuth;
import com.jdkhome.blzo.ex.authj.generator.model.GroupAuthExample;
import com.jdkhome.blzo.ex.authj.service.GroupBasicService;
import com.jdkhome.blzo.ex.authj.service.GroupService;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author linkji
 * @date 2019-03-12 10:45
 */
@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupMapper groupMapper;

    @Autowired
    GroupAdminMapper groupAdminMapper;

    @Autowired
    GroupAuthMapper groupAuthMapper;

    @Autowired
    GroupBasicService groupBasicService;

    @Autowired
    AuthjManager authjManager;

    /**
     * 设置组权限
     *
     * @param groupId
     * @param uris
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void setAuth(Integer groupId, List<String> uris) {

        // 首先移除组内所有权限
        this.delGroupAuthByGroupId(groupId);

        uris.forEach(uri -> {
            GroupAuth groupAuth = new GroupAuth();
            groupAuth.setGroupId(groupId);
            groupAuth.setUri(uri);
            groupAuth.setCreateAdminId(authjManager.getUserId());
            groupAuthMapper.insertSelective(groupAuth);
        });
    }


    /**
     * 删除组
     *
     * @param groupId
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Integer delGroup(Integer groupId) {
        if (groupId == null) {
            log.error("删除组->参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        // 删除这个组的所有关联！
        this.delGroupAuthByGroupId(groupId);
        this.delGroupAdminByGroupId(groupId);

        return groupMapper.deleteByPrimaryKey(groupId);
    }

    /**
     * 删除某管理员创建的所有组
     *
     * @param adminId
     * @return
     */
    @Override
    public Integer delGroupByCreateAdminId(Integer adminId) {

        if (adminId == null) {
            log.error("删除某管理员创建的所有组->参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        List<Group> list = groupBasicService.getAllGroup(null, adminId);

        list.forEach(group -> this.delGroup(group.getId()));

        return 0;
    }

    /**
     * 删除某组的所有管理员关联
     *
     * @param groupId
     * @return
     */
    @Override
    public Integer delGroupAdminByGroupId(Integer groupId) {

        if (groupId == null) {
            log.error("删除某组的所有管理员关联->参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        GroupAdminExample example = new GroupAdminExample();
        example.createCriteria().andGroupIdEqualTo(groupId);

        return groupAdminMapper.deleteByExample(example);
    }

    /**
     * 删除某管理员的所有组关联
     *
     * @param adminId
     * @return
     */
    @Override
    public Integer delGroupAdminByAdminId(Integer adminId) {
        if (adminId == null) {
            log.error("删除某管理员的所有组关联->参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        GroupAdminExample example = new GroupAdminExample();
        example.createCriteria().andAdminIdEqualTo(adminId);

        return groupAdminMapper.deleteByExample(example);
    }

    /**
     * 删除组所有权限关联
     *
     * @param groupId
     * @return
     */
    @Override
    public Integer delGroupAuthByGroupId(Integer groupId) {

        //入参验证
        if (groupId == null) {
            log.error("删除组所有权限关联->参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        GroupAuthExample example = new GroupAuthExample();
        example.createCriteria().andGroupIdEqualTo(groupId);

        return groupAuthMapper.deleteByExample(example);
    }
}

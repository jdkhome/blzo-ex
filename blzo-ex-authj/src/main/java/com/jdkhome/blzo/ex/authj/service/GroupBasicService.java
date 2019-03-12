package com.jdkhome.blzo.ex.authj.service;

import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.ex.authj.generator.model.Group;
import com.jdkhome.blzo.ex.authj.generator.model.GroupAdmin;
import com.jdkhome.blzo.ex.authj.generator.model.GroupAuth;

import java.util.List;

/**
 * Created by jdk on 17/11/16.
 * 组管理业务接口
 * 包括 组 组-管理员 组-权限 组-菜单 的管理维护
 */
public interface GroupBasicService {

    // ==== 组相关 ==== //

    /**
     * 分页查询组
     *
     * @param name
     * @param page
     * @param size
     * @return
     */
    PageInfo<Group> getGroupsWithPage(String name, Integer createId, Integer page, Integer size);

    /**
     * 获取所有组
     *
     * @param name
     * @return
     */
    List<Group> getAllGroup(String name, Integer createId);

    /**
     * 获取组通过Id
     *
     * @param groupId
     * @return
     */
    Group getGroupById(Integer groupId);

    /**
     * 添加组
     *
     * @param name
     * @param comment
     * @return
     */
    Integer addGroup(String name, String comment);

    /**
     * 修改组
     *
     * @param groupId
     * @param name
     * @param comment
     * @return
     */
    Integer editGroups(Integer groupId, String name, String comment);




    // ==== 组管理员相关 ==== //

    /**
     * 增加组管理员关联
     *
     * @param groupId
     * @param adminId
     * @return
     */
    Integer addGroupAdmin(Integer groupId, Integer adminId);

    /**
     * 删除组管理员关联
     *
     * @param groupId
     * @param adminId
     * @return
     */
    Integer delGroupAdmin(Integer groupId, Integer adminId);


    //============== 查询接口 ==============//

    /**
     * 获取GroupAdmin 通过管理员Id
     *
     * @param adminId
     * @return
     */
    List<GroupAdmin> getGroupAdminByAdminId(Integer adminId);

    /**
     * 获取GroupAuth 通过组Id
     *
     * @param groupId
     * @return
     */
    List<GroupAuth> getGroupAuthByGroupId(Integer groupId);

    /**
     * 获取GroupAdmin 通过组Id
     *
     * @param groupId
     * @return
     */
    List<GroupAdmin> getGroupAdminByGroupId(Integer groupId);

}

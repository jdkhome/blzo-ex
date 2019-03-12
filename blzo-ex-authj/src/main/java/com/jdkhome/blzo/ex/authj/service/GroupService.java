package com.jdkhome.blzo.ex.authj.service;

import java.util.List;

/**
 * @author linkji
 * @date 2019-03-12 10:45
 */
public interface GroupService {

    /**
     * 设置组权限
     * @param groupId
     * @param uris
     * @return
     */
    void setAuth(Integer groupId, List<String> uris);

    /**
     * 删除组
     *
     * @param groupId
     * @return
     */
    Integer delGroup(Integer groupId);

    /**
     * 删除某管理员创建的所有组
     *
     * @param adminId
     * @return
     */
    Integer delGroupByCreateAdminId(Integer adminId);

    /**
     * 删除某组的所有管理员关联
     *
     * @param groupId
     * @return
     */
    Integer delGroupAdminByGroupId(Integer groupId);

    /**
     * 删除某管理员的所有组关联
     *
     * @param adminId
     * @return
     */
    Integer delGroupAdminByAdminId(Integer adminId);

    /**
     * 删除组所有权限关联
     *
     * @param groupId
     * @return
     */
    Integer delGroupAuthByGroupId(Integer groupId);
}

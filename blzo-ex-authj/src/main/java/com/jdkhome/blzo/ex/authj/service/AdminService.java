package com.jdkhome.blzo.ex.authj.service;


import com.jdkhome.blzo.ex.authj.generator.model.Admin;

/**
 * Created by jdk on 17/12/6.
 * 管理员业务接口
 */
public interface AdminService {

    /**
     * 管理员登录
     *
     * @param username
     * @param password
     * @param ip
     * @return
     */
    Admin login(String username, String password, String ip);

    /**
     * 激活管理员账户(第一次登录需重置密码)
     *
     * @param adminId     管理员Id
     * @param newPassword 新密码
     * @return
     */
    Integer activeAdmin(Integer adminId, String newPassword);

    /**
     * 修改管理员组织
     *
     * @param adminId
     * @param orgId
     * @return
     */
    Integer changeAdminOrg(Integer adminId, Integer orgId);


    /**
     * 如果没有root用户，则自动初始化一个
     */
    void initRoot();
}

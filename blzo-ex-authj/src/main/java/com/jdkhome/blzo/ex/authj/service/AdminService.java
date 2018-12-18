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
     * 如果没有root用户，则自动初始化一个
     */
    void initRoot();
}

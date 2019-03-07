package com.jdkhome.blzo.ex.authj.service;


import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.ex.authj.enums.AdminStatusEnum;
import com.jdkhome.blzo.ex.authj.generator.model.Admin;
import com.jdkhome.blzo.ex.authj.pojo.dto.LayerDTO;

import java.util.List;

/**
 * Created by jdk on 17/11/16.
 * 管理员管理业务接口
 */
public interface AdminBasicService {

    //============== 添加 ==============//


    /**
     * 添加管理员
     *
     * @param organizeId 组织Id(0=总组织)
     * @param username   登录名
     * @param password   密码
     * @param nickName   昵称
     * @param phone      手机号
     * @param email      邮箱
     * @param remark     备注
     * @return
     */
    Integer addAdmin(Integer organizeId, String username, String password, String nickName, String phone, String email, String remark);

    //============== 修改 ==============//

    /**
     * 通用修改功能(不能修改组织)
     *
     * @param adminId  管理员Id
     * @param username 登录名
     * @param password 密码
     * @param nickName 昵称
     * @param phone    手机号
     * @param email    邮箱
     * @param remark   备注
     * @param layer    自定义菜单
     * @return
     */
    Integer editAdmin(Integer adminId, String username, String password, String nickName, String phone, String email, AdminStatusEnum status, String remark, List<LayerDTO> layer);


    //============== 删除 ==============//

    /**
     * 删除管理员
     *
     * @param adminId 管理员Id
     * @return
     */
    Integer delAdmin(Integer adminId);

    //============== 查询接口 ==============//

    /**
     * 获取管理员通过Id
     *
     * @param adminId 管理员Id
     * @return
     */
    Admin getAdminById(Integer adminId);

    /**
     * 获取管理员通过username
     *
     * @param username
     * @return
     */
    Admin getAdminByUsername(String username);

    /**
     * 获取通过phone
     *
     * @param phone
     * @return
     */
    Admin getAdminByPhone(String phone);

    /**
     * 获取通过email
     *
     * @param email
     * @return
     */
    Admin getAdminByEmail(String email);

    /**
     * 分页查询管理员
     *
     * @param page
     * @param size
     * @return
     */
    PageInfo getAdminsWithPage(Integer organizeId, String username, String nickName, String phone, String email, Integer page, Integer size);

    /**
     * 获取所有管理员
     *
     * @return
     */
    List<Admin> getAllAdmin(Integer organizeId, String username, String nickName, String phone, String email);


}

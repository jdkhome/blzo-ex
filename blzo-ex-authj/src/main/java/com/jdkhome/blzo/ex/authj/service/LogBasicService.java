package com.jdkhome.blzo.ex.authj.service;


import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.ex.authj.generator.model.Log;

import java.util.Date;

/**
 * author linkji.
 * 日志服务
 */
public interface LogBasicService {

    //============== 添加 ==============//


    /**
     * 添加日志
     *
     * @param organizeId 组织Id
     * @param adminId    管理员Id
     * @param adminName  管理员名称
     * @param authjUri   authj地址
     * @param authjName  authjName
     * @param paramers   参数(json)
     * @param ip         ip
     * @return
     */
    Integer addLog(Integer organizeId, Integer adminId, String adminName, String authjUri, String authjName, String paramers, String ip);

    //============== 修改 ==============//

    //============== 删除 ==============//

    //============== 查询接口 ==============//

    /**
     * 获取日志通过Id
     *
     * @param logId
     * @return
     */
    Log getLog(Integer logId);


    /**
     * 分页查询日志
     *
     * @param organizeId 组织Id
     * @param nickName   用户昵称
     * @param authjUri   权限Uri
     * @param authjName  权限Name
     * @param timeBegan
     * @param timeEnd
     * @param page
     * @param size
     * @return
     */
    PageInfo<Log> getLogWithPage(Integer organizeId, String nickName, String authjUri, String authjName,
                                 Date timeBegan, Date timeEnd, Integer page, Integer size);

}

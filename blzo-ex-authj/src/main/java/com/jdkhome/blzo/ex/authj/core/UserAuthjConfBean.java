package com.jdkhome.blzo.ex.authj.core;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * Created by jdk on 18/1/6.
 * 管理员权限配置实体
 */
@Data
public class UserAuthjConfBean {

    /**
     * 管理员id
     */
    Integer id;

    /**
     * 管理员组织Id
     */
    Integer organizeId;

    /**
     * 管理员昵称
     */
    String name;

    /**
     * 管理员所有权限集合
     */
    Set<String> authUriSet;

    /**
     * 自定义菜单列表
     */
    List<MenuVO> layerList;

    /**
     * 未分组菜单
     */
    MenuVO moreMenu;

}

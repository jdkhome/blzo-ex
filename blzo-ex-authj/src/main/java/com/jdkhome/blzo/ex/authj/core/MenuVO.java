package com.jdkhome.blzo.ex.authj.core;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by jdk on 18/1/8.
 * 菜单VO
 */
@Data
public class MenuVO implements Serializable {
    String name;
    List<AuthjBean> menus;


}

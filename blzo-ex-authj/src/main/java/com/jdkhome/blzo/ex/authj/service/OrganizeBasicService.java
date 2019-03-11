package com.jdkhome.blzo.ex.authj.service;

import com.github.pagehelper.PageInfo;
import com.jdkhome.blzo.ex.authj.enums.OrganizeStatusEnum;
import com.jdkhome.blzo.ex.authj.generator.model.Organize;

import java.util.List;

/**
 * Created by jdk on 2019/3/6.
 * 组织基本业务接口
 */
public interface OrganizeBasicService {

    /**
     * 添加组织
     *
     * @param name   组织名称
     * @param remark 组织备注
     * @return
     */
    Integer addOrganize(String name, String remark);

    /**
     * 编辑组织
     *
     * @param organizeId
     * @param name
     * @param remark
     * @param statusEnum
     * @return
     */
    Integer editOrganize(Integer organizeId, String name, String remark, OrganizeStatusEnum statusEnum);

    /**
     * 获取组织通过Id
     *
     * @param organizeId
     * @return
     */
    Organize getOrganizeById(Integer organizeId);

    List<Organize> getAllOrganize(Integer organizeId, String name, OrganizeStatusEnum statusEnum, String remark);

    PageInfo<Organize> getOrganizeWithPage(Integer organizeId, String name,
                                           OrganizeStatusEnum statusEnum, String remark, Integer page, Integer size);
}

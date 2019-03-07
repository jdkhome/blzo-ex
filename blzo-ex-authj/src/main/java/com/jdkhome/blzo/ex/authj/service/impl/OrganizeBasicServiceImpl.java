package com.jdkhome.blzo.ex.authj.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.jdkhome.blzo.ex.authj.enums.OrganizeStatusEnum;
import com.jdkhome.blzo.ex.authj.generator.dao.OrganizeMapper;
import com.jdkhome.blzo.ex.authj.generator.model.Organize;
import com.jdkhome.blzo.ex.authj.generator.model.OrganizeExample;
import com.jdkhome.blzo.ex.authj.service.OrganizeBasicService;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by jdk on 2019/3/6.
 */
@Slf4j
@Service
public class OrganizeBasicServiceImpl implements OrganizeBasicService {

    @Autowired
    OrganizeMapper organizeMapper;

    /**
     * 添加组织
     *
     * @param name   组织名称
     * @param remark 组织备注
     * @return
     */
    @Override
    public Integer addOrganize(String name, String remark) {

        if (StringUtil.isNotEmpty(name) || StringUtil.isNotEmpty(remark)) {
            log.error("添加组织 -> 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        Organize organize = new Organize();
        organize.setName(name);
        organize.setRemark(remark);
        organize.setStatus(OrganizeStatusEnum.NORMAL.getCode());

        return organizeMapper.insertSelective(organize);
    }

    /**
     * 编辑组织
     *
     * @param organizeId
     * @param name
     * @param remark
     * @param statusEnum
     * @return
     */
    @Override
    public Integer editOrganize(Integer organizeId, String name, String remark, OrganizeStatusEnum statusEnum) {

        if (organizeId == null) {
            log.error("编辑组织 -> 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        Organize updateOrganize = new Organize();
        updateOrganize.setId(organizeId);
        updateOrganize.setName(name);
        updateOrganize.setRemark(remark);
        updateOrganize.setStatus(statusEnum.getCode());

        return organizeMapper.updateByPrimaryKeySelective(updateOrganize);
    }

    /**
     * 获取组织通过Id
     *
     * @param organizeId
     * @return
     */
    @Override
    public Organize getOrganizeById(Integer organizeId) {

        if (organizeId == null) {
            log.error("获取组织通过Id -> 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        return organizeMapper.selectByPrimaryKey(organizeId);
    }

    @Override
    public List<Organize> getAllOrganize(String name, String remark) {

        OrganizeExample example = new OrganizeExample();
        OrganizeExample.Criteria criteria = example.createCriteria();

        if (StringUtil.isNotEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }

        if (StringUtil.isNotEmpty(remark)) {
            criteria.andRemarkEqualTo("%" + remark + "%");
        }

        return organizeMapper.selectByExample(example);
    }

    @Override
    public PageInfo<Organize> getOrganizeWithPage(String name, String remark, Integer page, Integer size) {

        if (page == null || size == null) {
            log.error("分页获取组织 -> 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        PageHelper.startPage(page, size);
        return new PageInfo<>(this.getAllOrganize(name, remark));
    }
}

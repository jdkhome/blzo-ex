package com.jdkhome.blzo.ex.authj.service.impl;

import com.jdkhome.blzo.ex.authj.enums.AuthjResponseError;
import com.jdkhome.blzo.ex.authj.generator.dao.OrganizeMapper;
import com.jdkhome.blzo.ex.authj.generator.model.Admin;
import com.jdkhome.blzo.ex.authj.generator.model.Organize;
import com.jdkhome.blzo.ex.authj.service.AdminBasicService;
import com.jdkhome.blzo.ex.authj.service.OrganizeService;
import com.jdkhome.blzo.ex.basic.enums.BasicResponseError;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author linkji
 * @date 2019-03-11 15:38
 */
@Slf4j
@Service
public class OrganizeServiceImpl implements OrganizeService {

    @Autowired
    OrganizeMapper organizeMapper;

    @Autowired
    AdminBasicService adminBasicService;

    /**
     * 删除组织
     *
     * @param id
     * @return
     */
    @Override
    public Integer delOrganize(Integer id) {

        if (id == null) {
            log.error("删除组织 -> 参数错误");
            throw new ServiceException(BasicResponseError.PARAMETER_ERROR);
        }

        if (id.equals(0)) {
            log.error("删除组织 -> 不可删除0号组织");
            throw new ServiceException(BasicResponseError.NO_PERMISSION);
        }

        // 删除组织前，该组织必须没有管理员
        List<Admin> list = adminBasicService.getAllAdmin(id, null, null, null, null);
        if (!CollectionUtils.isEmpty(list)) {
            log.error("删除组织 -> 删除组织前需先清空组织内成员");
            throw new ServiceException(AuthjResponseError.DEL_ORG_WILL_DEL_ADMIN);
        }

        return organizeMapper.deleteByPrimaryKey(id);
    }
}

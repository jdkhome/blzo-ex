package com.jdkhome.blzo.ex.authj.validator;

import com.jdkhome.blzo.ex.authj.core.AuthjManager;
import com.jdkhome.blzo.ex.authj.enums.AuthjResponseError;
import com.jdkhome.blzo.ex.authj.generator.dao.AdminMapper;
import com.jdkhome.blzo.ex.authj.generator.model.Admin;
import com.jdkhome.blzo.ex.basic.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by jdk on 2019/3/7.
 * 组织校验器
 */
@Slf4j
@Component
public class OrganizeValidator {

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    AuthjManager authjManager;

    /**
     * 校验组织内管理员操作权限
     * 校验当前登录用户组织是否和admin一个组织或者为总组织
     *
     * @param adminId
     * @return
     */
    public boolean validAdmin(Integer adminId) {
        Admin admin = adminMapper.selectByPrimaryKey(adminId);
        if (admin == null) {
            log.error("校验组织内管理员操作权限 -> 管理员不存在 Id={}", adminId);
            throw new ServiceException(AuthjResponseError.ADMIN_NOT_EXIST);
        }

        if (authjManager.getOrganizeId() != null &&
                (authjManager.getOrganizeId() == 0 || authjManager.getOrganizeId().equals(admin.getOrganizeId()))) {
            return true;
        }

        return false;

    }

}

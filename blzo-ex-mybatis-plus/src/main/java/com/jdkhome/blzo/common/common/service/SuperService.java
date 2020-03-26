package com.jdkhome.blzo.common.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdkhome.blzo.common.common.entity.SuperEntity;


public interface SuperService <T extends SuperEntity> extends IService<T> {
}

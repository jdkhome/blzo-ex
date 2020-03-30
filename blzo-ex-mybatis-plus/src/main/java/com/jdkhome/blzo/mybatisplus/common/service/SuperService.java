package com.jdkhome.blzo.mybatisplus.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jdkhome.blzo.mybatisplus.common.entity.SuperEntity;


public interface SuperService <T extends SuperEntity> extends IService<T> {
}

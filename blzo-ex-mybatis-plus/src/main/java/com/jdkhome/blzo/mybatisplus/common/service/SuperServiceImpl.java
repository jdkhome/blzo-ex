package com.jdkhome.blzo.mybatisplus.common.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jdkhome.blzo.mybatisplus.common.entity.SuperEntity;
import com.jdkhome.blzo.mybatisplus.common.mapper.SuperMapper;


public abstract class SuperServiceImpl <P extends SuperMapper<T>, T extends SuperEntity> extends ServiceImpl <P  , T > {

}

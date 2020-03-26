package com.jdkhome.blzo.ex.utils.tools;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

//import lombok.extern.slf4j.Slf4j;

//@Slf4j
public class AssemblerUtil {

    public static <T, D> List<D> listTo(List<T> origList, Class<D> c) throws Exception{
        List<D> descList = new ArrayList<D>();
        for(T origObj : origList){
            descList.add(to(origObj, c));
        }
        return descList;

    }

    public static <T, D> D to(T origObj, Class<D> c) throws Exception{
        ModelMapper modelMapper = new ModelMapper();
        D descObj = null;
        try {
             descObj = modelMapper.map(origObj, c);
        }catch (Exception e){
           // log.debug("对象转换时异常，原对象数据  origObj :{}", JSON.toJSONString(origObj));
            throw e;
        }
        return descObj;
    }





}

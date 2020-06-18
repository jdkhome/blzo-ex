package com.jdkhome.blzo.ex.basic.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by jdk on 2019/1/16.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {

    // list
    List<T> list;

    // 总大小
    Long totalSize;


}
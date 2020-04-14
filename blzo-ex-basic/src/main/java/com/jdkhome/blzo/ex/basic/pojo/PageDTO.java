package com.jdkhome.blzo.ex.basic.pojo;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Created by jdk on 2019/1/16.
 */
@Data
//@Builder
public class PageDTO<T> {

    Long totalSize;

    List<T> list;

    public PageDTO() {
    }

    public PageDTO(List<T> list, Long totalSize) {
        this.list = list;
        this.totalSize = totalSize;
    }

}
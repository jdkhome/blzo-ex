package com.jdkhome.blzo.ex.basic.pojo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by jdk on 2017/9/4.
 */
@Data
public class PageRequest {

    @Min(value = 1, message = "页码错误")
    @NotNull
    public Integer page;

    @Min(value = 1, message = "页大小错误")
    @NotNull
    public Integer size;

    public PageRequest() {
        // 默认获取第一页的20个数据
        this.page = 1;
        this.size = 20;
    }

    public PageRequest(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }
}

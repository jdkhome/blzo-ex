package com.jdkhome.blzo.ex.basic.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created by jdk on 2017/9/4.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

    @Min(value = 1, message = "页码错误")
    @NotNull
    @Builder.Default
    public Integer page = 1;

    @Min(value = 1, message = "页大小错误")
    @NotNull
    @Builder.Default
    public Integer size = 20;

}

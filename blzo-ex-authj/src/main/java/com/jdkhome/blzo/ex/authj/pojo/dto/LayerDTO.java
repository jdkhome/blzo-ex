package com.jdkhome.blzo.ex.authj.pojo.dto;

import com.jdkhome.blzo.ex.authj.constants.ErrorMsg;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * author link.ji
 * createTime 下午2:40 2018/12/5
 */
@Data
public class LayerDTO {

    @NotBlank(message = ErrorMsg.NULL)
    String name;

    @NotEmpty(message = ErrorMsg.NULL)
    List<String> uris;
}

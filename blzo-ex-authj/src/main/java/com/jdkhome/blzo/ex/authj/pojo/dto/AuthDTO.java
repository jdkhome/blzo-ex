package com.jdkhome.blzo.ex.authj.pojo.dto;

import com.jdkhome.blzo.ex.authj.constants.ErrorMsg;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author linkji
 * @date 2019-03-12 10:38
 */
@Data
public class AuthDTO {

    /**
     * 权限组Id
     */
    @NotNull
    Integer groupId;

    @NotEmpty(message = ErrorMsg.NULL)
    List<String> uris;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    Date day;
}

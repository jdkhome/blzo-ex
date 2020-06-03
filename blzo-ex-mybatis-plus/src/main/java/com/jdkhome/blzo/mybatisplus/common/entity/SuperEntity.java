package com.jdkhome.blzo.mybatisplus.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public abstract class SuperEntity  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键序号
     */
    @TableId(value = "f_id", type = IdType.ID_WORKER)
    private Long id;

    /**
     * 逻辑删除
     */
    @TableLogic
    @TableField("f_deleted")
    private Integer deleted;

    /**
     * 版本号
     */
    @Version
    @TableField("f_version")
    private Integer version;

    /**
     * 创建时间
     */
    @TableField("f_created_at")
    private LocalDateTime createdAt;

    /**
     * 创建人id
     */
    @TableField("f_created_by_id")
    private String createdById;

    /**
     * 创建人名称
     */
    @TableField("f_created_by_name")
    private String createdByName;

    /**
     * 最后更新时间
     */
    @TableField("f_last_updated_at")
    private LocalDateTime lastUpdatedAt;

    /**
     * 最后更新人id
     */
    @TableField("f_last_updated_by_id")
    private String lastUpdatedById;

    /**
     * 最后更新人名称
     */
    @TableField("f_last_updated_by_name")
    private String lastUpdatedByName;


    public static final String ID = "f_id";

    public static final String DELETED = "f_deleted";

    public static final String VERSION = "f_version";

    public static final String CREATED_AT = "f_created_at";

    public static final String CREATED_BY_ID = "f_created_by_id";

    public static final String CREATED_BY_NAME = "f_created_by_name";

    public static final String LAST_UPDATED_AT = "f_last_updated_at";

    public static final String LAST_UPDATED_BY_ID = "f_last_updated_by_id";

    public static final String LAST_UPDATED_BY_NAME = "f_last_updated_by_name";

}

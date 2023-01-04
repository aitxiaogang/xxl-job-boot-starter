package com.xiaogang.xxljobadminsdk.constants;

import lombok.Getter;

/**
 * @author: xiaogang
 */
@Getter
public enum TriggerStatusEnum {
    ALL(-1),
    START(1),
    STOP(0),
    ;
    private Integer status;

    TriggerStatusEnum(Integer status) {
        this.status = status;
    }
}

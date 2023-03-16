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

    public static TriggerStatusEnum getByStatus(int status){
        if (status == -1) {
            return ALL;
        }else if (status == 1){
            return START;
        }else if (status == 0){
            return STOP;
        }
        return null;
    }
}

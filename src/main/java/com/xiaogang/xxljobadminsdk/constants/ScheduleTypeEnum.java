package com.xiaogang.xxljobadminsdk.constants;

import lombok.Getter;

/**
 * @author: xiaogang
 */
@Getter
public enum ScheduleTypeEnum {
    NONE("无"),
    CRON("CRON"),
    FIX_RATE("固定速度")
    ;
    private String remark;

    ScheduleTypeEnum(String remark) {
        this.remark = remark;
    }
}

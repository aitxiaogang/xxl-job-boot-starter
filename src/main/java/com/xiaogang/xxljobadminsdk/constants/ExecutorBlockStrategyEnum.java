package com.xiaogang.xxljobadminsdk.constants;

import lombok.Getter;

/**
 * @author: xiaogang
 */
@Getter
public enum ExecutorBlockStrategyEnum {
    SERIAL_EXECUTION("单机串行"),
    DISCARD_LATER("丢弃后续调度"),
    COVER_EARLY("覆盖之前调度")
    ;
    private String remark;

    ExecutorBlockStrategyEnum(String remark) {
        this.remark = remark;
    }
}

package com.xiaogang.xxljobadminsdk.constants;

import lombok.Getter;

/**
 * @author: xiaogang
 */
@Getter
public enum MisfireStrategyEnum {
    DO_NOTHING("忽略"),
    FIRE_ONCE_NOW("立即执行一次")
    ;
    private String remark;

    MisfireStrategyEnum(String remark) {
        this.remark = remark;
    }
}

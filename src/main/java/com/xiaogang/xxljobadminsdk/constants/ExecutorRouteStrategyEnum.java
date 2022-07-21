package com.xiaogang.xxljobadminsdk.constants;

import lombok.Getter;

/**
 * @author : xiaogang
 */
@Getter
public enum ExecutorRouteStrategyEnum {
    FIRST("第一个"),
    LAST("最后一个"),
    ROUND("轮询"),
    RANDOM("随机"),
    CONSISTENT_HASH("一致性HASH"),
    LEAST_FREQUENTLY_USED("最不经常使用"),
    LEAST_RECENTLY_USED("最近最久未使用"),
    FAILOVER("故障转移"),
    BUSYOVER("忙碌转移"),
    SHARDING_BROADCAST("分片广播")
    ;
    private String remark;

    ExecutorRouteStrategyEnum(String remark) {
        this.remark = remark;
    }
}

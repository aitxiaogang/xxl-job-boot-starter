package com.xiaogang.xxljobadminsdk.model;

import com.xiaogang.xxljobadminsdk.constants.ExecutorBlockStrategyEnum;
import com.xiaogang.xxljobadminsdk.constants.ExecutorRouteStrategyEnum;
import com.xiaogang.xxljobadminsdk.constants.MisfireStrategyEnum;
import com.xiaogang.xxljobadminsdk.constants.ScheduleTypeEnum;
import lombok.Data;

@Data
public class JobUpdateParam {
    /**
     * 执行器
     */
    private int jobGroup;

    /**
     * 任务描述
     */
    private String jobDesc;

    /**
     * 负责人
     */
    private String author;

    /**
     * 报警邮件
     */
    private String alarmEmail;

    /**
     * 调度类型
     */
    private ScheduleTypeEnum scheduleType;

    /**
     * 调度类型对应配置
     */
    private String scheduleConf;

    /**
     * JobHandler
     */
    private String executorHandler;

    /**
     * 任务参数
     */
    private String executorParam;

    /**
     * 路由策略
     */
    private ExecutorRouteStrategyEnum executorRouteStrategy;

    /**
     * 子任务ID
     */
    private Integer childJobId;

    /**
     * 调度过期策略
     */
    private MisfireStrategyEnum misfireStrategy;

    /**
     * 阻塞处理策略
     */
    private ExecutorBlockStrategyEnum executorBlockStrategy;

    /**
     * 任务超时时间
     */
    private Integer executorTimeout;

    /**
     * 失败重试次数
     */
    private Integer executorFailRetryCount;

    /**
     * 任务id
     */
    private Integer id;

}

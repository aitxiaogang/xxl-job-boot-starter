package com.xiaogang.xxljobadminsdk.model;

import com.xiaogang.xxljobadminsdk.constants.*;
import lombok.Data;

/**
 * 添加job的必传参数，其它默认。但是可以修改默认参数
 * @author: xiaogang
 */
@Data
public class DefaultXxlJobAddParam{

    protected int jobGroup;		// 执行器主键ID
    protected String jobDesc;
    protected String author;		// 负责人
    protected String alarmEmail;	// 报警邮件

    protected ScheduleTypeEnum scheduleType = ScheduleTypeEnum.CRON;			// 调度类型
    protected String scheduleConf;			// 调度配置，值含义取决于调度类型

    protected String executorHandler;		    // 执行器，任务Handler名称
    protected String executorParam;		    // 执行器，任务参数

    protected GlueTypeEnum glueType = GlueTypeEnum.BEAN;		// GLUE类型	#com.xxl.job.core.glue.GlueTypeEnum
    protected ExecutorRouteStrategyEnum executorRouteStrategy = ExecutorRouteStrategyEnum.FIRST;	// 执行器路由策略
    protected String childJobId;		// 子任务ID，多个逗号分隔
    protected MisfireStrategyEnum misfireStrategy = MisfireStrategyEnum.DO_NOTHING;			// 调度过期策略
    protected ExecutorBlockStrategyEnum executorBlockStrategy = ExecutorBlockStrategyEnum.SERIAL_EXECUTION;	// 阻塞处理策略
    protected int executorTimeout = 0;     		// 任务执行超时时间，单位秒
    protected int executorFailRetryCount = 0;		// 失败重试次数
    protected String glueRemark = "glueRemark";		// GLUE备注

}

package com.xiaogang.xxljobadminsdk.annotation;

import com.xiaogang.xxljobadminsdk.constants.ExecutorRouteStrategyEnum;
import com.xiaogang.xxljobadminsdk.constants.MisfireStrategyEnum;
import com.xiaogang.xxljobadminsdk.constants.TriggerStatusEnum;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;

import java.lang.annotation.*;

/**
 * @program: xxl-job-boot-starter
 * @description: xxljob自动注入
 * @author: tuyulong
 * @create: 2023-10-12 09:46
 **/
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface XxlRegistry {

    /**
     * cron表达式
     */
    String cron();

    /**
     * 任务的默认调度状态，0：停止，1：运行
     * 填0后需要在控制台手动启动任务才能运行
     * @see TriggerStatusEnum
     */
    int triggerStatus() default 0;

    /**
     * 任务描述
     */
    String jobDesc() default "default jobDesc";

    /**
     * 任务负责人
     */
    String author() default "default Author";

    /**
     * 报警邮件,多个用逗号分隔
     */
    String alarmEmail() default "";

    /**
     * 任务参数
     */
    String executorParam() default "";

    /**
     * 子任务ID，多个逗号分隔
     */
    String childJobId() default "";


    /**
     * 默认为 ROUND 轮询方式
     * 可选： FIRST 第一个
     */
    ExecutorRouteStrategyEnum executorRouteStrategy() default ExecutorRouteStrategyEnum.ROUND;

    /**
     * 阻塞处理策略
     * 默认单机串行
     */
    ExecutorBlockStrategyEnum executorBlockStrategy() default ExecutorBlockStrategyEnum.SERIAL_EXECUTION;

    /**
     * 调度过期策略
     */
    MisfireStrategyEnum misfireStrategy() default MisfireStrategyEnum.DO_NOTHING;

    /**
     * 任务执行超时时间，单位秒
     */
    int executorTimeout() default 0;

    /**
     * 失败重试次数
     */
    int executorFailRetryCount() default 0;
}

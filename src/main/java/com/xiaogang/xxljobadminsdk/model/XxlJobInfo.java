package com.xiaogang.xxljobadminsdk.model;

import com.xiaogang.xxljobadminsdk.constants.*;
import lombok.Data;

import java.util.Date;

/**
 *	原始添加添加job的所有参数
 */
@Data
public class XxlJobInfo {
	
	protected int id;				// 主键ID
	
	protected int jobGroup;		// 执行器主键ID
	protected String jobDesc;
	
	protected Date addTime;
	protected Date updateTime;
	
	protected String author;		// 负责人
	protected String alarmEmail;	// 报警邮件

	protected ScheduleTypeEnum scheduleType;			// 调度类型
	protected String scheduleConf;			// 调度配置，值含义取决于调度类型
	protected MisfireStrategyEnum misfireStrategy;			// 调度过期策略

	protected ExecutorRouteStrategyEnum executorRouteStrategy;	// 执行器路由策略
	protected String executorHandler;		    // 执行器，任务Handler名称
	protected String executorParam;		    // 执行器，任务参数
	protected String executorBlockStrategy;	// 阻塞处理策略
	protected int executorTimeout;     		// 任务执行超时时间，单位秒
	protected int executorFailRetryCount;		// 失败重试次数
	
	protected GlueTypeEnum glueType;		// GLUE类型	#com.xxl.job.core.glue.GlueTypeEnum
	protected String glueSource;		// GLUE源代码
	protected String glueRemark;		// GLUE备注
	protected Date glueUpdatetime;	// GLUE更新时间

	protected String childJobId;		// 子任务ID，多个逗号分隔

	protected int triggerStatus;		// 调度状态：0-停止，1-运行
	protected long triggerLastTime;	// 上次调度时间
	protected long triggerNextTime;	// 下次调度时间

}

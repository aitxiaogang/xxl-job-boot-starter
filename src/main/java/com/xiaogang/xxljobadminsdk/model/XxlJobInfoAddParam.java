package com.xiaogang.xxljobadminsdk.model;

import com.xiaogang.xxljobadminsdk.constants.ScheduleTypeEnum;
import lombok.Data;

/**
 * 添加job的必传参数，其它默认
 * @author : xiaogang
 */
@Data
public class XxlJobInfoAddParam {

    protected int jobGroup;		// 执行器主键ID
    protected String jobDesc;
    protected String author;		// 负责人
    protected String alarmEmail;	// 报警邮件

    protected ScheduleTypeEnum scheduleType = ScheduleTypeEnum.CRON;			// 调度类型
    protected String scheduleConf;			// 调度配置，值含义取决于调度类型

    protected String executorHandler;		    // 执行器，任务Handler名称
    protected String executorParam;		    // 执行器，任务参数

}

package com.xiaogang.xxljobadminsdk.vo;

import com.xiaogang.xxljobadminsdk.constants.ExecutorBlockStrategyEnum;
import com.xiaogang.xxljobadminsdk.constants.ExecutorRouteStrategyEnum;
import com.xiaogang.xxljobadminsdk.constants.MisfireStrategyEnum;
import com.xiaogang.xxljobadminsdk.constants.ScheduleTypeEnum;
import lombok.Data;

import java.io.Serializable;

public @Data class JobInfoPageItem implements Serializable {
	private static final long serialVersionUID = -1842102864350831173L;

	private Long triggerLastTime;
	private String alarmEmail;
	private String glueUpdatetime;
	private String executorParam;
	private String addTime;
	private ExecutorBlockStrategyEnum executorBlockStrategy;
	private String author;
	private String scheduleConf;
	private ExecutorRouteStrategyEnum executorRouteStrategy;
	private Long triggerStatus;
	private Integer childJobId;
	private long triggerNextTime;
	private String updateTime;
	private int jobGroup;
	private String glueRemark;
	private String jobDesc;
	private String glueSource;
	private MisfireStrategyEnum misfireStrategy;
	private ScheduleTypeEnum scheduleType;
	private String glueType;
	private String executorHandler;
	private int executorFailRetryCount;
	private int id;
	private int executorTimeout;
}
package com.xiaogang.xxljobadminsdk.vo;

import lombok.Data;

import java.io.Serializable;

public @Data class JobInfoPageItem implements Serializable {
	private static final long serialVersionUID = -1842102864350831173L;

	private int triggerLastTime;
	private String alarmEmail;
	private String glueUpdatetime;
	private String executorParam;
	private String addTime;
	private String executorBlockStrategy;
	private String author;
	private String scheduleConf;
	private String executorRouteStrategy;
	private int triggerStatus;
	private String childJobId;
	private long triggerNextTime;
	private String updateTime;
	private int jobGroup;
	private String glueRemark;
	private String jobDesc;
	private String glueSource;
	private String misfireStrategy;
	private String scheduleType;
	private String glueType;
	private String executorHandler;
	private int executorFailRetryCount;
	private int id;
	private int executorTimeout;
}
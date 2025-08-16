package com.xiaogang.xxljobadminsdk.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

public @Data class JobInfoPageResult implements Serializable {
	private static final long serialVersionUID = 1316450425048690593L;

	private int recordsFiltered;
	private List<JobInfoPageItem> data;
	private int recordsTotal;
}
package com.xiaogang.xxljobadminsdk.vo;

import java.util.List;
import lombok.Data;

public @Data class JobInfoPageResult {
	private int recordsFiltered;
	private List<JobInfoPageItem> data;
	private int recordsTotal;
}
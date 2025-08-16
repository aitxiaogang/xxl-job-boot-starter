package com.xiaogang.xxljobadminsdk.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class JobGroupPageResult implements Serializable {

	private int recordsFiltered;

	private List<DataItem> data;

	private int recordsTotal;
}
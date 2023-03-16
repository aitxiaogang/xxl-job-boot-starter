package com.xiaogang.xxljobadminsdk.vo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class DataItem implements Serializable {

	private int id;

	private String appname;

	private String title;

	private int addressType;

	private String addressList;

	private String updateTime;

	private List<String> registryList;
}
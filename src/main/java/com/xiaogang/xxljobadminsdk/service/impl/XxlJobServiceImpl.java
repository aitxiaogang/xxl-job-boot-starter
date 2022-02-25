package com.xiaogang.xxljobadminsdk.service.impl;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.xiaogang.xxljobadminsdk.config.XxlJobAdminProperties;
import com.xiaogang.xxljobadminsdk.dto.HttpHeader;
import com.xiaogang.xxljobadminsdk.dto.ReturnT;
import com.xiaogang.xxljobadminsdk.model.XxlJobInfo;
import com.xiaogang.xxljobadminsdk.service.XxlJobService;
import com.xiaogang.xxljobadminsdk.vo.JobInfoPageResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XxlJobServiceImpl implements XxlJobService {

    private HttpHeader loginHeader;

    private XxlJobAdminProperties xxlJobAdminProperties;

    private int timeout;

    public XxlJobServiceImpl(HttpHeader loginHeader, XxlJobAdminProperties xxlJobAdminProperties) {
        this.loginHeader = loginHeader;
        this.xxlJobAdminProperties = xxlJobAdminProperties;
        this.timeout = this.xxlJobAdminProperties.getConnectionTimeOut();
    }

    @Override
    public JobInfoPageResult pageList(int start, int length, int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author) {
        HttpRequest httpRequest = this.getHttpRequest(jobPageListPath);
        Map<String, Object> paramMap = new HashMap();
        paramMap.put("start",start);
        paramMap.put("length",length);
        paramMap.put("jobGroup",jobGroup);
        paramMap.put("triggerStatus",triggerStatus);
        paramMap.put("jobDesc",jobDesc);
        paramMap.put("executorHandler",executorHandler);
        paramMap.put("author",author);

        HttpResponse response = httpRequest.form(paramMap).timeout(timeout).execute();
        int status = response.getStatus();
        String body = response.body();
        Assert.isTrue(status == 200);
        JobInfoPageResult jobInfoPageResult = JSON.parseObject(body, JobInfoPageResult.class);

        return jobInfoPageResult;
    }

    @Override
    public String add(XxlJobInfo jobInfo) {
        HttpRequest httpRequest = postHttpRequest(jobAddPath);
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(jobInfo));
        ReturnT<String> returnT = requestXxlJobAdmin(httpRequest, jsonObject, new TypeReference<ReturnT<String>>() {
        });
        return returnT.getContent();
    }

    @Override
    public void update(XxlJobInfo jobInfo) {
        HttpRequest httpRequest = postHttpRequest(jobUpdatePath);

        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(jobInfo));
        requestXxlJobAdmin(httpRequest, jsonObject,new TypeReference<ReturnT<String>>(){});
    }

    @Override
    public void remove(int id) {
        HttpRequest httpRequest = postHttpRequest(jobDeletePath);

        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        requestXxlJobAdmin(httpRequest, map,new TypeReference<ReturnT<String>>(){});
    }

    @Override
    public void start(int id) {
        HttpRequest httpRequest = postHttpRequest(jobStartPath);

        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        requestXxlJobAdmin(httpRequest, map,new TypeReference<ReturnT<String>>(){});
    }

    @Override
    public void stop(int id) {
        HttpRequest httpRequest = postHttpRequest(jobStopPath);

        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        requestXxlJobAdmin(httpRequest, map,new TypeReference<ReturnT<String>>(){});
    }

    @Override
    public void triggerJob(int id, String executorParam, String addressList) {
        HttpRequest httpRequest = postHttpRequest(jobTriggerPath);

        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("executorParam",executorParam);
        map.put("addressList",addressList);
        requestXxlJobAdmin(httpRequest, map,new TypeReference<ReturnT<String>>(){});
    }

    @Override
    public List<String> nextTriggerTime(String scheduleType, String scheduleConf) {
        HttpRequest httpRequest = getHttpRequest(jobNextTriggerTimePath);

        Map<String,Object> map = new HashMap<>();
        map.put("scheduleType",scheduleType);
        map.put("scheduleConf",scheduleConf);
        ReturnT<List<String>> returnT = requestXxlJobAdmin(httpRequest, map,new TypeReference<ReturnT<List<String>>>(){});
        return returnT.getContent();
    }

    private HttpRequest postHttpRequest(String path) {
        String url = new StringBuilder(xxlJobAdminProperties.getAdminUrl()).append(path).toString();
        HttpRequest httpRequest = HttpRequest.post(url);
        httpRequest.header(loginHeader.getHeaderName(),loginHeader.getHeaderValue());
        return httpRequest;
    }

    private HttpRequest getHttpRequest(String path) {
        String url = new StringBuilder(xxlJobAdminProperties.getAdminUrl()).append(path).toString();
        HttpRequest httpRequest = HttpRequest.get(url);
        httpRequest.header(loginHeader.getHeaderName(),loginHeader.getHeaderValue());
        return httpRequest;
    }

    private <T extends ReturnT> T requestXxlJobAdmin(HttpRequest httpRequest, Map<String, Object> paramMap,TypeReference<T> type) {
        HttpResponse response = httpRequest.form(paramMap).timeout(timeout).execute();
        int status = response.getStatus();
        String body = response.body();
        T returnT = JSON.parseObject(body, type);
        Assert.isTrue(status == 200, returnT.getMsg());
        return returnT;
    }

}

package com.xiaogang.xxljobadminsdk.service;

import com.xiaogang.xxljobadminsdk.model.DefaultXxlJobAddParam;
import com.xiaogang.xxljobadminsdk.model.XxlJobInfo;
import com.xiaogang.xxljobadminsdk.model.XxlJobInfoAddParam;
import com.xiaogang.xxljobadminsdk.vo.JobInfoPageResult;

import java.util.List;

public interface XxlJobService {

    String jobAddPath = "/jobinfo/add";
    String jobDeletePath = "/jobinfo/remove";
    String jobUpdatePath = "/jobinfo/update";
    String jobStartPath = "/jobinfo/start";
    String jobStopPath = "/jobinfo/stop";
    String jobTriggerPath = "/jobinfo/trigger";
    String jobPageListPath = "/jobinfo/pageList";
    String jobNextTriggerTimePath = "/jobinfo/nextTriggerTime";

    JobInfoPageResult pageList(int start, int length, int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author);

    /**
     * 包含添加job的所有参数
     *
     * @param jobInfo 任务信息
     * @return 任务id
     */
    Integer add(XxlJobInfo jobInfo);

    /**
     * 通过必要参数添加job，其它参数和通过网页添加job的默认参数一样
     * @param addParam
     * @return
     */
    Integer add(XxlJobInfoAddParam addParam);

    /**
     * 通过必要参数添加job，其它参数和通过网页添加job的默认参数一样。但是可以修改默认参数
     * @param defaultXxlJobAddParam
     * @return
     */
    Integer add(DefaultXxlJobAddParam defaultXxlJobAddParam);

    /**
     * update job
     *
     * @param jobInfo 任务信息
     */
    void update(XxlJobInfo jobInfo);

    /**
     * remove job
     * 	 *
     * @param id 任务id
     */
    void remove(int id);

    void remove(int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author);

    void removeAll(int jobGroup, int triggerStatus, String jobDesc, String executorHandler, String author);

    /**
     * 停止运行中的任务并删除
     */
    void cancel(int jobGroup, String jobDesc, String executorHandler, String author);

    void cancelAll(int jobGroup, String jobDesc, String executorHandler, String author);
    /**
     * start job
     *
     * @param id 任务id
     */
    void start(int id);

    /**
     * stop job
     *
     * @param id 任务id
     */
    void stop(int id);

    void triggerJob(int id, String executorParam, String addressList);

    List<String> nextTriggerTime(String scheduleType, String scheduleConf);

}

package com.xiaogang.xxljobadminsdk.service;

import com.xiaogang.xxljobadminsdk.dto.ReturnT;
import com.xiaogang.xxljobadminsdk.model.XxlJobInfo;
import com.xiaogang.xxljobadminsdk.vo.JobInfoPageResult;

import java.util.List;
import java.util.Map;

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
     * add job
     *
     * @param jobInfo
     * @return
     */
    String add(XxlJobInfo jobInfo);

    /**
     * update job
     *
     * @param jobInfo
     * @return
     */
    void update(XxlJobInfo jobInfo);

    /**
     * remove job
     * 	 *
     * @param id
     * @return
     */
    void remove(int id);

    /**
     * start job
     *
     * @param id
     * @return
     */
    void start(int id);

    /**
     * stop job
     *
     * @param id
     * @return
     */
    void stop(int id);

    void triggerJob(int id, String executorParam, String addressList);

    List<String> nextTriggerTime(String scheduleType, String scheduleConf);

}

package com.xiaogang.xxljobadminsdk.service;

import com.xiaogang.xxljobadminsdk.constants.TriggerStatusEnum;
import com.xiaogang.xxljobadminsdk.dto.JobGroupQuery;
import com.xiaogang.xxljobadminsdk.dto.JobQuery;
import com.xiaogang.xxljobadminsdk.model.DefaultXxlJobAddParam;
import com.xiaogang.xxljobadminsdk.model.JobUpdateParam;
import com.xiaogang.xxljobadminsdk.model.XxlJobInfo;
import com.xiaogang.xxljobadminsdk.model.XxlJobInfoAddParam;
import com.xiaogang.xxljobadminsdk.vo.JobGroupPageResult;
import com.xiaogang.xxljobadminsdk.vo.JobInfoPageItem;
import com.xiaogang.xxljobadminsdk.vo.JobInfoPageResult;

import java.util.Date;
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
    String jobGroupListPath = "/jobgroup/pageList";

    /**
     * 分页查询任务数据
     * @param jobQuery
     * @return
     */
    JobInfoPageResult pageList(JobQuery jobQuery);

    /**
     * 分页查询执行器数据
     * @param jobGroupQuery
     * @return
     */
    JobGroupPageResult pageList(JobGroupQuery jobGroupQuery);

    /**
     * 通过执行器名称查询执行器id
     * @param appName
     * @return
     */
    int getFirstJobGroupIdByAppName(String appName);

    /**
     * 通过配置的appname查询执行器id
     * @return
     */
    int getDefaultJobGroupId();

    /**
     * 包含添加job的所有参数
     *
     * @param jobInfo 任务信息
     * @return 任务id
     */
    Integer add(XxlJobInfo jobInfo);

    /**
     * 通过必要参数添加job，其它参数和通过网页添加job的默认参数一样
     * @param  addParam
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
     * 添加只在将来执行一次的任务
     * @param customId 自定义的唯一的业务id，此id在所有任务中保持唯一
     * @param triggerTime 任务执行时间，必须大于当前时间
     * @param executorParam 任务执行参数
     * @param executorHandler 任务处理器，关联@XxlJob的value
     * @return
     */
    Integer addJustExecuteOnceJob(String customId, Date triggerTime, String executorParam, String executorHandler);

    /**
     * 通过自定义的唯一业务id查询任务id
     * @param customId
     * @return
     */
    Integer getJobIdByCustomId(String customId);

    /**
     * 通过自定义的唯一业务id查询任务
     * @param customId
     * @return
     */
    JobInfoPageItem getJobByCustomId(String customId);

    /**
     * update job
     *
     * @param jobUpdateParam 任务信息
     */
    void update(JobUpdateParam jobUpdateParam);

    /**
     * 修改任务
     * @param jobInfoPageItem
     */
    void update(JobInfoPageItem jobInfoPageItem);

    /**
     * 数据类型转换
     * @param jobInfoPageItem
     * @return
     */
    JobUpdateParam transform(JobInfoPageItem jobInfoPageItem);

    /**
     * remove job
     * 	 *
     * @param id 任务id
     */
    void remove(int id);


    /**
     * 删除符合条件的所有任务---默认的执行器
     * @param triggerStatus
     * @param jobDesc
     * @param executorHandler
     * @param author
     */
    void remove(TriggerStatusEnum triggerStatus, String jobDesc, String executorHandler, String author);


    /**
     * start job
     *
     * @param id 任务id
     */
    void start(int id);

    /**
     * 开始所有符合条件的任务
     * @param triggerStatus
     * @param jobDesc
     * @param executorHandler
     * @param author
     */
    void start(TriggerStatusEnum triggerStatus, String jobDesc, String executorHandler, String author);

    /**
     * stop job
     *
     * @param id 任务id
     */
    void stop(int id);

    /**
     * 停止所有符合条件的任务
     * @param triggerStatus
     * @param jobDesc
     * @param executorHandler
     * @param author
     */
    void stop(TriggerStatusEnum triggerStatus, String jobDesc, String executorHandler, String author);

    void triggerJob(int id, String executorParam, String addressList);

    List<String> nextTriggerTime(String scheduleType, String scheduleConf);

}

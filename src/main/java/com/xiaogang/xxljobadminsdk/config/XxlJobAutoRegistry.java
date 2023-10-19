package com.xiaogang.xxljobadminsdk.config;
import com.xiaogang.xxljobadminsdk.constants.GlueTypeEnum;
import com.xiaogang.xxljobadminsdk.constants.MisfireStrategyEnum;
import com.xiaogang.xxljobadminsdk.constants.ScheduleTypeEnum;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import com.xiaogang.xxljobadminsdk.annotation.XxlRegistry;
import com.xiaogang.xxljobadminsdk.constants.TriggerStatusEnum;
import com.xiaogang.xxljobadminsdk.dto.JobGroupQuery;
import com.xiaogang.xxljobadminsdk.dto.JobQuery;
import com.xiaogang.xxljobadminsdk.model.XxlJobInfo;
import com.xiaogang.xxljobadminsdk.model.XxlJobInfoAddParam;
import com.xiaogang.xxljobadminsdk.service.XxlJobService;
import com.xiaogang.xxljobadminsdk.vo.DataItem;
import com.xiaogang.xxljobadminsdk.vo.JobGroupPageResult;
import com.xiaogang.xxljobadminsdk.vo.JobInfoPageResult;
import com.xxl.job.core.enums.ExecutorBlockStrategyEnum;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.core.MethodIntrospector;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @program: xxl-job-boot-starter
 * @description: xxlJob自动注册
 * @author: tuyulong
 * @create: 2023-10-12 10:00
 **/
@Component
public class XxlJobAutoRegistry implements ApplicationListener<ApplicationReadyEvent>, ApplicationContextAware {

    private Logger logger = LoggerFactory.getLogger(XxlJobAutoRegistry.class);

    private XxlJobService xxlJobService;

    private XxlJobAdminProperties xxlJobAdminProperties;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        this.xxlJobService = applicationContext.getBean("xxlJobService", XxlJobService.class);
        this.xxlJobAdminProperties = applicationContext.getBean(XxlJobAdminProperties.class);
        if (StrUtil.isBlank(xxlJobAdminProperties.getAppname())
                || StrUtil.isBlank(xxlJobAdminProperties.getGroupTitle())) {
            logger.info("xxl-job appName or groupTitle is blank! Please configure appName and groupTitle in the properties file or add job by hands");
            return;
        }
        addJobGroup();
        addJobInfo();
    }

    /**
     * 注册执行器
     */
    private void addJobGroup() {
        if (xxlJobService.jobGroupPreciselyCheck()) {
            return ;
        }
        Integer integer = xxlJobService.addJobGroup();
        logger.info("auto register xxl-job group success!");
    }

    /**
     * 注册job
     */
    private void addJobInfo() {
        DataItem jobGroup = this.getJobGroup();

        String[] beanDefinitionNames = applicationContext.getBeanNamesForType(Object.class, false, true);
        for (String beanDefinitionName : beanDefinitionNames) {
            Object bean = applicationContext.getBean(beanDefinitionName);
            Map<Method, XxlJob> annotatedMethods = MethodIntrospector.selectMethods(bean.getClass(),
                    (MethodIntrospector.MetadataLookup<XxlJob>) method -> AnnotatedElementUtils.findMergedAnnotation(method, XxlJob.class));
            if (CollUtil.isEmpty(annotatedMethods)) {
                continue;
            }
            for (Map.Entry<Method, XxlJob> methodXxlJobEntry : annotatedMethods.entrySet()) {
                try {
                    Method executeMethod = methodXxlJobEntry.getKey();
                    XxlJob xxlJob = methodXxlJobEntry.getValue();
                    if (xxlJob == null) {
                        continue;
                    }
                    Assert.notBlank(xxlJob.value());
                    if (!executeMethod.isAnnotationPresent(XxlRegistry.class)) {
                        continue;
                    }

                    //检查job是否已注册
                    if (jobInfoCheck(jobGroup, xxlJob)) {
                        continue;
                    }

                    XxlRegistry xxlRegistry = executeMethod.getAnnotation(XxlRegistry.class);
                    //添加job
                    createJobInfo(xxlRegistry, xxlJob, jobGroup);
                } catch (Exception e) {
                    logger.error("XxlJobAutoRegistry addJobInfo exception", e);
                }
            }
        }
    }

    /**
     * 校验job是否已经注册
     * @param jobGroup
     * @param xxlJob
     * @return true:已经注册或查询异常，false:未注册
     */
    private boolean jobInfoCheck(DataItem jobGroup, XxlJob xxlJob){
        JobQuery jobQuery = new JobQuery();
        jobQuery.setJobGroup(jobGroup.getId());
        jobQuery.setExecutorHandler(xxlJob.value());
        JobInfoPageResult jobInfoPageResult = xxlJobService.pageList(jobQuery);
        if (Objects.isNull(jobInfoPageResult)) {
            logger.error("jobInfoCheck query jobInfo result is null! jobGroup AppName:{},JobHandler:{}", jobGroup.getAppname(), xxlJob.value());
            return true;
        }

        if (CollUtil.isEmpty(jobInfoPageResult.getData())) {
            return false;
        }

        return jobInfoPageResult.getData().stream().anyMatch(jobInfo -> StrUtil.equals(jobInfo.getExecutorHandler(), xxlJob.value()));
    }

    private void createJobInfo(XxlRegistry xxlRegistry, XxlJob xxlJob, DataItem jobGroup) {
        Assert.checkBetween(xxlRegistry.triggerStatus(), TriggerStatusEnum.STOP.getStatus(),
                TriggerStatusEnum.START.getStatus(), "调度状态仅支持启动和停止，请检查");
        XxlJobInfo xxlJobInfo = new XxlJobInfo();
        xxlJobInfo.setJobGroup(jobGroup.getId());
        xxlJobInfo.setJobDesc(xxlRegistry.jobDesc());
        xxlJobInfo.setAuthor(xxlRegistry.author());
        xxlJobInfo.setAlarmEmail(xxlRegistry.alarmEmail());
        xxlJobInfo.setScheduleConf(xxlRegistry.cron());
        xxlJobInfo.setMisfireStrategy(xxlRegistry.misfireStrategy());
        xxlJobInfo.setExecutorRouteStrategy(xxlRegistry.executorRouteStrategy());
        xxlJobInfo.setExecutorParam(xxlRegistry.executorParam());
        xxlJobInfo.setExecutorBlockStrategy(xxlRegistry.executorBlockStrategy().name());
        xxlJobInfo.setExecutorTimeout(xxlRegistry.executorTimeout());
        xxlJobInfo.setExecutorFailRetryCount(xxlRegistry.executorFailRetryCount());
        xxlJobInfo.setChildJobId(xxlRegistry.childJobId());
        xxlJobInfo.setTriggerStatus(xxlRegistry.triggerStatus());
        xxlJobInfo.setExecutorHandler(xxlJob.value());
        xxlJobInfo.setScheduleType(ScheduleTypeEnum.CRON);
        xxlJobInfo.setGlueType(GlueTypeEnum.BEAN);
        xxlJobInfo.setGlueRemark("GLUE代码初始化");
        Integer jobInfoId = xxlJobService.add(xxlJobInfo);
        logger.info("JobHandler:{},jobInfoId:{} auto register success!",xxlJob.value(),jobInfoId);
    }

    private DataItem getJobGroup() {
        JobGroupQuery jobGroupQuery = new JobGroupQuery();
        jobGroupQuery.setAppname(xxlJobAdminProperties.getAppname());
        jobGroupQuery.setTitle(xxlJobAdminProperties.getGroupTitle());
        JobGroupPageResult jobGroupPageResult = xxlJobService.pageList(jobGroupQuery);
        String errorMsgTemplate = "查询结果为空，请检查是否创建对应名称的执行器";
        Assert.notNull(jobGroupPageResult, errorMsgTemplate);
        List<DataItem> data = jobGroupPageResult.getData();
        Assert.isTrue(CollUtil.isNotEmpty(data), errorMsgTemplate);
        DataItem jobGroup = data.stream().filter(ele -> StrUtil.equals(xxlJobAdminProperties.getAppname(), ele.getAppname())
                && StrUtil.equals(xxlJobAdminProperties.getGroupTitle(), ele.getTitle())).findFirst().orElseGet(null);
        Assert.notNull(jobGroupPageResult, errorMsgTemplate);
        return jobGroup;
    }
}

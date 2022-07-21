package com.xiaogang.xxljobadminsdk.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xxl-job config
 *
 * @author xuxueli 2017-04-28
 */
@Configuration
@Data
public class XxlJobConfig {
    private Logger logger = LoggerFactory.getLogger(XxlJobConfig.class);

    @Bean
    @ConditionalOnProperty(prefix = "xxl.job.sdk",value = "enable",havingValue = "true")
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobAdminProperties xxlJobAdminProperties) {
        logger.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobAdminProperties.getAdminUrl());
        xxlJobSpringExecutor.setAppname(xxlJobAdminProperties.getAppname());
        xxlJobSpringExecutor.setAddress(xxlJobAdminProperties.getAddress());
        xxlJobSpringExecutor.setIp(xxlJobAdminProperties.getIp());
        xxlJobSpringExecutor.setPort(xxlJobAdminProperties.getPort());
        xxlJobSpringExecutor.setAccessToken(xxlJobAdminProperties.getAccessToken());
        xxlJobSpringExecutor.setLogPath(xxlJobAdminProperties.getLogPath());
        xxlJobSpringExecutor.setLogRetentionDays(xxlJobAdminProperties.getLogRetentionDays());

        return xxlJobSpringExecutor;
    }

}
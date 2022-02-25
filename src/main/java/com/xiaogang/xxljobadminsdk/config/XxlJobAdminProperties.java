package com.xiaogang.xxljobadminsdk.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "xxl.job.sdk")
public class XxlJobAdminProperties {
    private String adminUrl = "http://localhost:8080/xxl-job-admin";
    private String userName = "admin";
    private String password = "123456";
    private int connectionTimeOut = 5000;

    private boolean enable = false;
}

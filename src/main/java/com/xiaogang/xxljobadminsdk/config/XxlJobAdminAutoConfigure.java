package com.xiaogang.xxljobadminsdk.config;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.xiaogang.xxljobadminsdk.dto.HttpHeader;
import com.xiaogang.xxljobadminsdk.dto.ReturnT;
import com.xiaogang.xxljobadminsdk.service.XxlJobService;
import com.xiaogang.xxljobadminsdk.service.impl.XxlJobServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ConditionalOnClass(XxlJobService.class)
@EnableConfigurationProperties(XxlJobAdminProperties.class)
public class XxlJobAdminAutoConfigure {

    @Bean
    @ConditionalOnProperty(prefix = "xxl.job.sdk",value = "enable",havingValue = "true")
    public XxlJobService xxlJobService(HttpHeader loginHeader, XxlJobAdminProperties xxlJobAdminProperties) {
        XxlJobService xxlJobService = new XxlJobServiceImpl(loginHeader, xxlJobAdminProperties);
        return xxlJobService;
    }

    @Bean("loginHeader")
    @ConditionalOnProperty(prefix = "xxl.job.sdk",value = "enable",havingValue = "true")
    public HttpHeader httpRequest(XxlJobAdminProperties xxlJobAdminProperties){
        String adminUrl = xxlJobAdminProperties.getAdminUrl();
        String userName = xxlJobAdminProperties.getUserName();
        String password = xxlJobAdminProperties.getPassword();
        int connectionTimeOut = xxlJobAdminProperties.getConnectionTimeOut();

        Map<String, Object > paramMap = new HashMap<>();
        paramMap.put("userName",userName);
        paramMap.put("password",password);

        HttpResponse httpResponse = HttpRequest.post(adminUrl+"/login").form(paramMap).timeout(connectionTimeOut).execute();
        int status = httpResponse.getStatus();

        if (200 != status) {
            throw new RuntimeException("登录失败");
        }

        String body = httpResponse.body();
        ReturnT returnT = JSONUtil.toBean(body, ReturnT.class);
        if (200 != returnT.getCode()) {
            throw new RuntimeException("登录失败:"+returnT.getMsg());
        }

        String cookieName = "XXL_JOB_LOGIN_IDENTITY";
        HttpCookie cookie = httpResponse.getCookie(cookieName);
        if (cookie == null) {
            throw new RuntimeException("没有获取到登录成功的cookie，请检查登录连接或者参数是否正确");
        }
        String headerValue = new StringBuilder(cookieName).append("=").append(cookie.getValue()).toString();
        HttpHeader loginHeader = new HttpHeader("Cookie",headerValue);
        return loginHeader;
    }

}

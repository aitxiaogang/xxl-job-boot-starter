package com.xiaogang.xxljobadminsdk.config;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.xiaogang.xxljobadminsdk.dto.HttpHeader;
import com.xiaogang.xxljobadminsdk.dto.ReturnT;
import com.xiaogang.xxljobadminsdk.service.XxlJobService;
import com.xiaogang.xxljobadminsdk.service.impl.XxlJobServiceImpl;
import com.xxl.job.core.biz.AdminBiz;
import com.xxl.job.core.biz.model.RegistryParam;
import com.xxl.job.core.enums.RegistryConfig;
import com.xxl.job.core.executor.XxlJobExecutor;
import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.HttpCookie;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties(XxlJobAdminProperties.class)
public class XxlJobAdminAutoConfigure {

    private Logger logger = LoggerFactory.getLogger(XxlJobAdminAutoConfigure.class);

    @Bean("loginHeader")
    public HttpHeader httpRequest(XxlJobAdminProperties xxlJobAdminProperties) {
        logger.info(">>>>>>>>>>> xxl-job config init. httpRequest");
        String adminUrl = xxlJobAdminProperties.getAdminUrl();
        Assert.notBlank(adminUrl, "请配置adminUrl");

        Integer jobGroupId = xxlJobAdminProperties.getJobGroupId();
        Assert.notNull(jobGroupId, "请配置jobGroupId");

        String userName = xxlJobAdminProperties.getUserName();
        String password = xxlJobAdminProperties.getPassword();
        int connectionTimeOut = xxlJobAdminProperties.getConnectionTimeOut();

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userName", userName);
        paramMap.put("password", password);

        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpRequest.post(adminUrl + "/login").form(paramMap).timeout(connectionTimeOut).execute();
        } catch (Exception e) {
            Assert.isTrue(false, "链接xxl-job服务失败，请检查xxl-job服务是否运行以及到xxl-job的网络是否通畅。当前链接地址：{}", adminUrl);
        }
        int status = httpResponse.getStatus();
        Assert.isTrue(200 == status, "登录失败,请检查用户名密码是否正确");

        String body = httpResponse.body();
        ReturnT returnT = JSONUtil.toBean(body, ReturnT.class);

        Assert.isTrue(200 == returnT.getCode(), "登录失败,请检查用户名密码是否正确");

        String cookieName = "XXL_JOB_LOGIN_IDENTITY";
        HttpCookie cookie = httpResponse.getCookie(cookieName);
        Assert.notNull(cookie, "没有获取到登录成功的cookie，请检查登录连接或者参数是否正确");

        String headerValue = new StringBuilder(cookieName).append("=").append(cookie.getValue()).toString();
        HttpHeader loginHeader = new HttpHeader("Cookie", headerValue);
        return loginHeader;
    }


    @Bean
    public XxlJobService xxlJobService(HttpHeader loginHeader, XxlJobAdminProperties xxlJobAdminProperties) {
        logger.info(">>>>>>>>>>> xxl-job config init. xxlJobService");
        XxlJobService xxlJobService = new XxlJobServiceImpl(loginHeader, xxlJobAdminProperties);
        return xxlJobService;
    }

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor(XxlJobAdminProperties xxlJobAdminProperties) throws UnknownHostException {
        logger.info(">>>>>>>>>>> xxl-job config init. XxlJobSpringExecutor");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        String adminUrl = xxlJobAdminProperties.getAdminUrl();
        Assert.notBlank(adminUrl, "xxl-job服务地址不能为空");
        xxlJobSpringExecutor.setAdminAddresses(adminUrl);

        String appname = xxlJobAdminProperties.getAppname();
        Assert.notBlank(appname,"请配置执行器参数appname");
        xxlJobSpringExecutor.setAppname(appname);

        String address = xxlJobAdminProperties.getAddress();
        if (StrUtil.isBlank(address)) {
            address = InetAddress.getLocalHost().getHostAddress();
        }
        xxlJobSpringExecutor.setAddress(address);

        String ip = xxlJobAdminProperties.getIp();
        if (StrUtil.isNotBlank(ip)) {
            xxlJobSpringExecutor.setIp(ip);
        }

        Integer port = xxlJobAdminProperties.getPort();
        if (port == null) {
            port = 9999;
        }
        xxlJobSpringExecutor.setPort(port);

        String accessToken = xxlJobAdminProperties.getAccessToken();
        if (StrUtil.isNotBlank(accessToken)) {
            xxlJobSpringExecutor.setAccessToken(accessToken);
        }

        String logPath = xxlJobAdminProperties.getLogPath();
        if (StrUtil.isNotBlank(logPath)) {
            xxlJobSpringExecutor.setLogPath(logPath);
        }

        Integer logRetentionDays = xxlJobAdminProperties.getLogRetentionDays();
        if (logRetentionDays != null) {
            xxlJobSpringExecutor.setLogRetentionDays(logRetentionDays);
        }


        RegistryParam registryParam = new RegistryParam(RegistryConfig.RegistType.EXECUTOR.name(), appname, address);
        for (AdminBiz adminBiz: XxlJobExecutor.getAdminBizList()) {
            try {
                com.xxl.job.core.biz.model.ReturnT<String> registryResult = adminBiz.registry(registryParam);
                if (registryResult!=null && com.xxl.job.core.biz.model.ReturnT.SUCCESS_CODE == registryResult.getCode()) {
                    registryResult = com.xxl.job.core.biz.model.ReturnT.SUCCESS;
                    logger.debug(">>>>>>>>>>> xxl-job registry success, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                    break;
                } else {
                    logger.info(">>>>>>>>>>> xxl-job registry fail, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                    Assert.isTrue(false,">>>>>>>>>>> xxl-job registry fail, registryParam:{}, registryResult:{}", new Object[]{registryParam, registryResult});
                }
            } catch (Exception e) {
                logger.info(">>>>>>>>>>> xxl-job registry error, registryParam:{}", registryParam, e);
                Assert.isTrue(false,">>>>>>>>>>> xxl-job registry error, registryParam:{}", registryParam, e);
            }
        }

        return xxlJobSpringExecutor;
    }
}

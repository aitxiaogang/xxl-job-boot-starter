# 更新说明-2023-01-04（已上传maven中央仓库）
1. 2.3.0-jobinfo-executor-1是最新版并合并到了master
2. 修复了任务执行器注册失败的bug
3. 封装了任务查询参数到类里面，添加必要参数默认值
4. 任务相关方法添加参数校验，如果校验没有通过会抛异常
5. 请求xxl-job-admin添加返回结果校验，如果不是成功，错误信息抛异常提示
6. 请求xxl-job-admin添加返回结果添加debug日志，显示响应结果信息
7. 合并重复的任务操作方法，简化调用步骤
8. 部分代码优化，修改部分任务字段类型
# 更新说明（已上传maven中央仓库）
1. 2.3.0-jobinfo-executor版本集成了xxl-job-core，并且默认配置XxlJobSpringExecutor交给Spring管理，省去了xxl-job的配置，实现了更加快速的集成xxl-job到项目中。使用只需要两步，1.加本项目依赖，2.application.yml配置文件中加入自定义配置就完成了集成。
2. 提供更多添加job的方法，使用更少参数添加job，降低方法的调用复杂度
3. 添加成功job之后返回int类型的任务id数据，省去转换的过程


# 项目介绍
1. 使用java代码控制xxl-job-admin。使用代码添加job的CRUD等功能
2. 项目使用的xxl-job-admin版本为2.3.0
3. 此项目版本号对应xxl-job-admin版本号
4. 配置了用户名密码之后，此模块会模拟登录请求admin来实现功能
5. 想要通过代码往admin里面添加job等功能，只需要在springboot项目中引入此项目，配置关键参数，引入sdk类就可以了，其它什么都不用关心了
6. 默认配置了XxlJobSpringExecutor，所以不用再额外配置此类交给Spring管理了

下面是自动配置类，默认配置如下
```
@Data
@ConfigurationProperties(prefix = "xxl.job.sdk")
public class XxlJobAdminProperties {
private String adminUrl = "http://localhost:8080/xxl-job-admin";
    private String userName = "admin";
    private String password = "123456";
    private Integer connectionTimeOut = 5000;

    //执行器AppName [选填]：执行器心跳注册分组依据；为空则关闭自动注册
    private String appname;
    //执行器通讯TOKEN [选填]：非空时启用；
    private String accessToken;
    //执行器注册 [选填]：优先使用该配置作为注册地址，为空时使用内嵌服务 ”IP:PORT“ 作为注册地址。从而更灵活的支持容器类型执行器动态IP和动态映射端口问题。
    private String address;
    //执行器IP [选填]：默认为空表示自动获取IP，多网卡时可手动设置指定IP，该IP不会绑定Host仅作为通讯实用；地址信息用于 "执行器注册" 和 "调度中心请求并触发任务"；
    private String ip;
    //执行器端口号 [选填]：小于等于0则自动获取；默认端口为9999，单机部署多个执行器时，注意要配置不同执行器端口；
    private Integer port;
    //执行器运行日志文件存储磁盘路径 [选填] ：需要对该路径拥有读写权限；为空则使用默认路径；
    private String logPath;
    //执行器日志文件保存天数 [选填] ： 过期日志自动清理, 限制值大于等于3时生效; 否则, 如-1, 关闭自动清理功能；
    private Integer logRetentionDays;
    private Integer jobGroupId;

    private boolean enable = false;
}
```
# 使用方法
```
<dependency>
    <groupId>com.lxgnb</groupId>
    <artifactId>xxl-job-boot-starter</artifactId>
    <version>2.3.0-jobinfo-executor</version>
</dependency>
```
1. pom文件中添加上面的依赖坐标（已上传到maven中央仓库）
2. 添加配置项，配置参数如下
3. ![image](https://user-images.githubusercontent.com/18614347/180215271-7979d1aa-61dc-4dbe-91dc-0530f0da497f.png)
4. 接下来注入关键的类，使用XxlJobService来控制job的crud
5. <img width="337" alt="image" src="https://user-images.githubusercontent.com/18614347/155742249-49778cf5-b6e8-4317-9020-78df46b023fc.png">

## 添加job
1. 添加任务有三个方法，对应的参数个数不一样，截图如下
2. ![image](https://user-images.githubusercontent.com/18614347/180215693-a94d1d03-b960-46ee-9196-a52f4b5cad64.png)
3. XxlJobInfo 这是参数最全的类，官方有哪些参数，这个类就有哪些字段
4. XxlJobInfoAddParam 这个是必填参数，其它参数和在网页上添加任务的默认参数一样
5. DefaultXxlJobAddParam 这个和 XxlJobInfoAddParam差不多，区别在于默认参数这个类也有字段，可以修改，XxlJobInfoAddParam就只有必填参数，不能修改默认参数
6. 之所以提供三个方法来添加任务是为了方便添加任务，不用每个参数都去设置


使用方法就是这样，现在只接入了JOB的crud，后续看项目情况也许会接入其它接口。
如果大家有什么问题，欢迎大家提出来。这个项目写的很粗糙，大家有什么意见和建议，非常欢迎大家来交流。谢谢


## QQ群
点击链接加入群聊【xxl-job-boot-starter】：https://jq.qq.com/?_wv=1027&k=7T9xX7XY
群号：709425770


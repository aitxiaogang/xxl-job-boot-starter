# 项目介绍
1. 使用java代码控制xxl-job-admin。使用代码添加job的CRUD等功能
2. 项目使用的xxl-job-admin版本为2.3.0
3. 此项目版本号对应xxl-job-admin版本号
4. 配置了用户名密码之后，此模块会模拟登录请求admin来实现功能
5. 想要通过代码往admin里面添加job等功能，只需要在springboot项目中引入此项目，配置关键参数，引入sdk类就可以了，其它什么都不用关心了

下面是自动配置类，默认配置如下
```
@Data
@ConfigurationProperties(prefix = "xxl.job.sdk")
public class XxlJobAdminProperties {
    private String adminUrl = "http://localhost:8080/xxl-job-admin";
    private String userName = "admin";
    private String password = "123456";
    private int connectionTimeOut = 5000;

    private boolean enable = false;
}
```
# 使用方法
1. 下载本项目install到本地maven仓库
2. 其它需要通过代码添加job的spring boot项目加入本项目的依赖
3. 配置参数如下
4. <img width="496" alt="image" src="https://user-images.githubusercontent.com/18614347/155742668-e078698c-efa7-48f9-b834-0effa19de44e.png">
5. 接下来注入关键的类，使用XxlJobService来控制job的crud
6. <img width="337" alt="image" src="https://user-images.githubusercontent.com/18614347/155742249-49778cf5-b6e8-4317-9020-78df46b023fc.png">

使用方法就是这样，现在只接入了JOB的crud，后续看项目情况也许会接入其它接口。
如果大家有什么问题，欢迎大家提出来。这个项目写的很粗糙，大家有什么意见和建议，非常欢迎大家来交流。谢谢

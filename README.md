# 更新说明：2023-03-17
1. 项目启动之前校验每一个配置参数，如果参数错误会终止项目启动，避免配置错误却不知道执行器启动失败
2. 将accessToken校验提前到项目启动完成之前，避免accessToken配置错误但是项目还是启动成功影响业务
3. 每个配置参数添加对应错误提示
4. 减少不必要的配置参数，降低集成成本，更好使用
5. 不用再配置jobGroupId，添加任务的执行器默认为appname对应的执行器id
6. 新增数个操作任务的方法，现在任务相关CRUD方法更充足，多样
7. 多个方法重载实现相同功能，降低方法调用成本。删除不必要参数
8. 新增查询执行器功能
9. 新增只在某个时间执行一次的任务方法，参数更少，添加任务更方便
10. 新增通过自定义字符串id作为唯一条件查询任务信息或者任务id，操作某个任务更方便（使用的任务负责人（author）字段实现，xxl-job的负责人字段查询是模糊查询，所以自定义id需要差别尽量大）
11. 删除，停止，启动都提供了两种方法，分别操作一个和操作符合条件的所有任务
12. 2.3.0-jobinfo-executor-2是最新版并合并到了master
13. bug修复

# 项目介绍
1. 使用java代码控制xxl-job-admin。使用代码添加job的CRUD等功能
2. 项目使用的xxl-job-admin版本为2.3.0
3. 此项目版本号对应xxl-job-admin版本号
4. 配置了用户名密码之后，此模块会模拟登录请求admin来实现功能
5. 想要通过代码往admin里面操作job等功能，只需要在springboot项目中引入此项目，配置关键参数，引入sdk类就可以了，其它什么都不用关心了
6. 默认配置了XxlJobSpringExecutor，所以不用再额外配置此类交给Spring管理了

下面是自动配置类，默认配置如下
```
@Data
@ConfigurationProperties(prefix = "xxl.job.sdk")
@Configuration
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
    private Integer logRetentionDays = 30;

}
```
# 使用方法
```
<dependency>
    <groupId>com.lxgnb</groupId>
    <artifactId>xxl-job-boot-starter</artifactId>
    <version>2.3.0-jobinfo-executor-2</version>
</dependency>
```
1. pom文件中添加上面的依赖坐标（已上传到maven中央仓库）
2. 添加配置项，配置参数如下
3. <img width="510" alt="image" src="https://user-images.githubusercontent.com/18614347/225945079-beab4c11-3c83-4cba-b676-43c8d82b5f30.png">
4. 接下来注入关键的类，使用XxlJobService来控制job的crud
5. <img width="337" alt="image" src="https://user-images.githubusercontent.com/18614347/155742249-49778cf5-b6e8-4317-9020-78df46b023fc.png">

## 添加job
1. 添加任务有四个方法，对应的参数个数不一样，截图如下
3. ![image](https://user-images.githubusercontent.com/18614347/226236154-0cff3bfa-c997-4d3a-b5c0-3aa898556547.png)
4. XxlJobInfo 这是参数最全的类，官方有哪些参数，这个类就有哪些字段
5. XxlJobInfoAddParam 这个是必填参数，其它参数和在网页上添加任务的默认参数一样
6. DefaultXxlJobAddParam 这个和 XxlJobInfoAddParam差不多，区别在于默认参数这个类也有字段，可以修改，XxlJobInfoAddParam就只有必填参数，不能修改默认参数
7. addJustExecuteOnceJob是2.3.0-jobinfo-executor-2新增的方法，因为很多业务只需要执行一次，为了方便添加此类业务，新增了此方法，并且还有和此配套的getJobIdByCustomId方法，方便对此任务进行修改或者删除
8. 之所以提供三个方法来添加任务是为了方便添加任务，不用每个参数都去设置


使用方法就是这样，后续看项目情况也许会接入其它接口。
如果大家有什么问题，欢迎大家提出来。这个项目写的很粗糙，大家有什么意见和建议，非常欢迎大家来交流。谢谢

## 微信群
![image](https://user-images.githubusercontent.com/18614347/226236677-24f4fe3d-cfd8-49cf-9015-4f4d636113e6.png)




## QQ群
点击链接加入群聊【xxl-job-boot-starter】：https://jq.qq.com/?_wv=1027&k=7T9xX7XY
群号：709425770


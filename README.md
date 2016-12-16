
BPStrutsO project .
项目要是想启动，那就必须要使用如下地址的 jar 文件包,文件下载的地址是 :
http://download.csdn.net/detail/supingemail/9713396

一 . 本项目集合框架特色：

1. 使用了：common-pool-1.6.jar ; common-dbcp-1.4.jar ; 使用dbcp来连接数据库，而不是使用jdbc来连接,在spring-application.xml 中有体现...
2. 添加了request ，respone ，session 的获取方式来取值,已经验证，request是可以使用的,在 界面上有展示..
3. 可以控制到按钮的操作,对于没有使用权限的用户，是不可以操作按钮的.
4.分页的特色. 本框架所支持的分页为最全分页：实现了分页显示；去某一特定页以及页面显示特性.
5. 自动生成代码功能完成.
6.导入,导出excel功能.
7.动态修改配置文件并让其使用起效.... 动态修改了预警设置...
8.全国联动的设置:在js area.js province.js 文件中有展示。
9.图片上传到七牛服务器功能.

二 . 使用代码生成工具注意点：

1.使用 com.javabase.codeutil.CodeProductorUtil 实现代码生成的操作。

2.在 configXml 下的 mybatis.xml 加入对应的实体别名。

3.在 configXml 下的 spring-application.xml 的 mapperLocations 下加入对应的 *-mapper.xml 文件,120 行左右添加上context:component-scan 内容.

4.在 configXml 下的 spring-servlet.xml 中加入对应的 <context:component-scan base-package="com.*.controller"/> 扫描类.

5.资源列表的配置工作：

a.查找,添加,修改,删除和查看详情 的资源KEY依次是：tablename_find,tablename_addUI,tablename_edit,tablename_delete,tablename_info;

b.资源的url是：/background/${lowerNmae}/*.html ,所有的访问都是以 html 结尾的.

c.资源的类型主要是由目录,菜单和按钮组成.

d.资源的优先级别一般都是和资源的sourceId是同步的,保证资源的有顺序的显示.

使用这套代码，在此基础上可以很方便开发项目，进行延展和扩充.


希望对学习者有易，如果有什么问题的,可以QQ我: 1577620678 

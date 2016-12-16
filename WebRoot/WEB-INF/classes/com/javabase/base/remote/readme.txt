远程http访问实现

传参数：
m : 所在类的名字.类中的方法名字调用;
p : 传递的参数,参数是json的格式传入;

RemoteController.java 远程访问的控制器,里面的remote 方法接受的的是一个 HttpServletRequest 对象,在运用java 的反射机制找到调用的 Service.

eg:
http://localhost:8080/JavaBase/remote.html?m=RemoteTest.test&p=jsonStr.

m=RemoteTest.test : 标示调用的是 RemoteTestService类的 test 方法.

p=jsonStr : 标示的是接受的是一个 json 字符串.

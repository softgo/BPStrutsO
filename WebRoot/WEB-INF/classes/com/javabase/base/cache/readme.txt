这个cache框架的使用步骤：
1. 使用前,先配置cache.properties 文件中的信息
2. MCacheManager 中提供了memoryCache,dataCache和创建任意的一个cache的方法。

memoryCache：主要是内存中的cache,主要用来把缓存的数据放入到内存中的，不够的，就直接丢掉了，不会再管理.

dataCache：主要是在满足了内存的需要之后，会把多余的数据写入到对应磁盘的文件夹中，但是要使用dataCache，
                                必须实现WriteableObject 接口.
                                
getCache：主要用来提供一个任意的cache对象，主要需要cacheName 和 maxMemory这两个参数.


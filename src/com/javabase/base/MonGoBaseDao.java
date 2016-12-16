package com.javabase.base;

import java.util.List;
import java.util.Map;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;

import com.javabase.pulgin.mybatis.plugin.PageView;

/**
 * <b>function:</b> 集合持久层的公用的增，删，改，查接口
 * @author bruce
 * @createDate 1970-1-1
 * @package com.javabase.base
 * @project JavaBase
 * <T> 表示传入实体类
 * @version 1.0
 */
public interface MonGoBaseDao<T,K> {
	
	/**
	 * 通过主键查询
	 * @param pk
	 * @return
	 */
	public T findByMongoId(K pk);
	
	/**
	 * 通过名字查询
	 * @param proKey,proValue
	 * @return
	 */
	public T findByMongoName(String proKey,String proValue);
	
	/**
	 * 通过对象查找对象
	 * @param params
	 * @return
	 */
	public T findByMongoProps(Map<String, ?> params);
	
	/**
	 * 返回分页后的数据
	 * @param pageView
	 * @param params
	 * @return
	 */
	public List<T> findMongoList(PageView pageView,Map<String, ?> params);
	
	/**
	 * 返回分页后的数据
	 * @param pageView
	 * @param params
	 * @return
	 */
	public PageView find(PageView pageView,Map<String, ?> params);
	
	/**
	 * 返回所有数据
	 * @return
	 */
	public List<T> findMongoAll();
	
	/**
	 * 返回所有数据
	 * @param params
	 * @return
	 */
	public List<T> findMongoAllByPros(Map<String, ?> params);
	
	/**
	 * 添加
	 * @param t
	 */
	public int addMongoOne(T t);
	
	/**
	 * 添加所有
	 * @param ts
	 */
	public boolean addMongoAll(List<T> ts);
	
	/**
	 * 修改
	 * @param t
	 */
	public int updateMongoOne(K pk,Map<String, ?> params);  
	
	/**
	 * 修改
	 * @param t
	 */
	public UpdateResults updateMongoOne(Query<T> query, UpdateOperations<T> ops);
	
	/**
	 * 修改所有属性.
	 * @param ts
	 */
	public boolean updateMongoAll(List<K> pks,List<Map<String, ?>> ts);
	
	/**
	 * 删除
	 * @param pk
	 */
	public int deleteByMongoId(K pk);

	/**
	 * 删除所有
	 * @param pks
	 */
	public boolean deleteMongoAll(List<K> pks);	

	/**
	 * 获得总条数.
	 * @return
	 */
	public long getObjsMongoCount();	
	
	/**
	 * 通过条件获得总条数.
	 * @return
	 */
	public long getObjsByMongoProsCount(Map<String, ?> params);	
	
}

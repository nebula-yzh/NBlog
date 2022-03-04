package top.naccl.service;

import top.naccl.model.vo.BlogDetail;
import top.naccl.model.vo.BlogInfo;
import top.naccl.model.vo.PageResult;

import java.util.List;
import java.util.Map;

public interface RedisService {
	PageResult<BlogInfo> getBlogInfoPageResultByHash(String hash, Integer pageNum);

	/**
	 * 根据hash和博客id获取redis中博客信息
	 * @param hash 哈希key
	 * @param blogId 博客id
	 * @return 博客信息
	 */
	BlogDetail getBlogDetailByHash(String hash,Long blogId);

	void saveKVToHash(String hash, Object key, Object value);

	void saveMapToHash(String hash, Map map);

	Map getMapByHash(String hash);

	Object getValueByHashKey(String hash, Object key);

	/**
	 * 根据增加因子，增加键(key)的值(value)
	 * @param hash redis中的hash名
	 * @param key redis中hash中的键
	 * @param increment 增加因子
	 */
	void incrementByHashKey(String hash, Object key, int increment);

	void deleteByHashKey(String hash, Object key);

	/**
	 * 根据redis中的键获取其中的数据
	 * @param key 键值
	 * @param <T> 数据类型
	 * @return 返回T类型集合
	 */
	<T> List<T> getListByValue(String key);

	/**
	 * 将数据保存到redis某个键中
	 * @param key 被保存在redis中的键
	 * @param list 被保存的集合
	 * @param <T> 类型
	 */
	<T> void saveListToValue(String key, List<T> list);

	<T> Map<String, T> getMapByValue(String key);

	<T> void saveMapToValue(String key, Map<String, T> map);

	<T> T getObjectByValue(String key, Class t);

	void incrementByKey(String key, int increment);

	void saveObjectToValue(String key, Object object);

	void saveValueToSet(String key, Object value);

	int countBySet(String key);

	void deleteValueBySet(String key, Object value);

	boolean hasValueInSet(String key, Object value);

	void deleteCacheByKey(String key);

	boolean hasKey(String key);

	void expire(String key, long time);
}

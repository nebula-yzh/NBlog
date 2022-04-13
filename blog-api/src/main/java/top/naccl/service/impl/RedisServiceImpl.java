package top.naccl.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import top.naccl.model.vo.BlogDetail;
import top.naccl.model.vo.BlogInfo;
import top.naccl.model.vo.PageResult;
import top.naccl.service.RedisService;
import top.naccl.util.JacksonUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 读写Redis相关操作
 * @Author: Naccl
 * @Date: 2020-09-27
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate jsonRedisTemplate;

    @Override
    public PageResult<BlogInfo> getBlogInfoPageResultByHash(String hash, Integer pageNum) {
        if (jsonRedisTemplate.opsForHash().hasKey(hash, pageNum)) {
            Object redisResult = jsonRedisTemplate.opsForHash().get(hash, pageNum);
            PageResult<BlogInfo> pageResult = JacksonUtils.convertValue(redisResult, PageResult.class);
            return pageResult;
        } else {
            return null;
        }
    }

    @Override
    public BlogDetail getBlogDetailByHash(String hash, Long blogId) {
        if (jsonRedisTemplate.opsForHash().hasKey(hash, blogId)) {
            Object redisResult = jsonRedisTemplate.opsForHash().get(hash, blogId);
            BlogDetail blogDetail = JacksonUtils.convertValue(redisResult, BlogDetail.class);
            return blogDetail;
        } else {
            return null;
        }
    }


    @Override
    public void saveKVToHash(String hash, Object key, Object value) {
        jsonRedisTemplate.opsForHash().put(hash, key, value);
    }

    @Override
    public void saveMapToHash(String hash, Map map) {
        jsonRedisTemplate.opsForHash().putAll(hash, map);
    }

    @Override
    public Map getMapByHash(String hash) {
        return jsonRedisTemplate.opsForHash().entries(hash);
    }

    @Override
    public Object getValueByHashKey(String hash, Object key) {
        return jsonRedisTemplate.opsForHash().get(hash, key);
    }

    @Override
    public void incrementByHashKey(String hash, Object key, int increment) {
        if (increment < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        jsonRedisTemplate.opsForHash().increment(hash, key, increment);
    }

    @Override
    public void deleteByHashKey(String hash, Object key) {
        jsonRedisTemplate.opsForHash().delete(hash, key);
    }

    @Override
    public <T> List<T> getListByValue(String key) {
        List<T> redisResult = (List<T>) jsonRedisTemplate.opsForValue().get(key);
        return redisResult;
    }

    @Override
    public <T> void saveListToValue(String key, List<T> list) {
        jsonRedisTemplate.opsForValue().set(key, list);
    }

    @Override
    public <T> Map<String, T> getMapByValue(String key) {
        Map<String, T> redisResult = (Map<String, T>) jsonRedisTemplate.opsForValue().get(key);
        return redisResult;
    }

    @Override
    public <T> void saveMapToValue(String key, Map<String, T> map) {
        jsonRedisTemplate.opsForValue().set(key, map);
    }

    @Override
    public <T> T getObjectByValue(String key, Class<T> t) {
        Object redisResult = jsonRedisTemplate.opsForValue().get(key);
        return JacksonUtils.convertValue(redisResult, t);
    }

    @Override
    public void incrementByKey(String key, int increment) {
        if (increment < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        jsonRedisTemplate.opsForValue().increment(key, increment);
    }

    @Override
    public void saveObjectToValue(String key, Object object) {
        jsonRedisTemplate.opsForValue().set(key, object);
    }

    @Override
    public void saveObjectToValueWithExpireTime(String key, Object object, long expireTime, TimeUnit timeUnit) {
        jsonRedisTemplate.opsForValue().set(key, object, expireTime, timeUnit);
    }


    @Override
    public void saveValueToSet(String key, Object value) {
        jsonRedisTemplate.opsForSet().add(key, value);
    }

    @Override
    public int countBySet(String key) {
        return jsonRedisTemplate.opsForSet().size(key).intValue();
    }

    @Override
    public void deleteValueBySet(String key, Object value) {
        jsonRedisTemplate.opsForSet().remove(key, value);
    }

    @Override
    public boolean hasValueInSet(String key, Object value) {
        return jsonRedisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public void saveValueToZSet(String key, double score, String member) {
        jsonRedisTemplate.opsForZSet().add(key, member, score);
    }

    @Override
    public void saveValuesToZSet(String key, Set<ZSetOperations.TypedTuple<String>> scoreMemberSet) {
        jsonRedisTemplate.opsForZSet().add(key, scoreMemberSet);
    }

    @Override
    public Set<String> getRangeFromZSet(String key, boolean isReverse, long start, long end) {
        if (isReverse) {
            return jsonRedisTemplate.opsForZSet().reverseRange(key, start, end);
        } else {
            return jsonRedisTemplate.opsForZSet().range(key, start, end);
        }
    }

    @Override
    public Set<String> getRangeWithScoreFromZSet(String key, boolean isReverse, long start, long end) {
        if (isReverse) {
            return jsonRedisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        } else {
            return jsonRedisTemplate.opsForZSet().rangeWithScores(key, start, end);
        }
    }

    @Override
    public Long countZSet(String key) {
        return jsonRedisTemplate.opsForZSet().zCard(key);
    }

    @Override
    public void deleteCacheByKey(String key) {
        jsonRedisTemplate.delete(key);
    }

    @Override
    public boolean hasKey(String key) {
        return jsonRedisTemplate.hasKey(key);
    }

    @Override
    public void expire(String key, long time) {
        jsonRedisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    @Override
    public void expire(String key, long time, TimeUnit timeUnit) {
        jsonRedisTemplate.expire(key, time, timeUnit);
    }
}

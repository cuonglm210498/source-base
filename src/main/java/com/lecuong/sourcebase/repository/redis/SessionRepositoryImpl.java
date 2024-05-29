package com.lecuong.sourcebase.repository.redis;

import com.lecuong.sourcebase.util.JsonConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

@Repository
public class SessionRepositoryImpl implements SessionRepository {

    private static final String TOPIC = "SESSION_CHECK";

    @Resource(name = "redisTemplateCustom")
    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public SessionRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void add(String key, String sessionId) {
        hashOperations.put(TOPIC, key, sessionId);
    }

    @Override
    public void addList(String key, List<String> listSessionId) {
        String listSessionIdJson = JsonConvertUtils.convertObjectToJsonWithGson(listSessionId);
        hashOperations.put(TOPIC, key, listSessionIdJson);
    }

    @Override
    public String find(String key) {
        return (String) hashOperations.get(TOPIC, key);
    }

    @Override
    public List<String> findList(String key) {
        String listSessionIdJson = (String) hashOperations.get(TOPIC, key);
        return JsonConvertUtils.convertJsonArrayToListWithGson(listSessionIdJson, String.class);
    }

    @Override
    public void delete(String key) {
        hashOperations.delete(TOPIC, key);
    }
}

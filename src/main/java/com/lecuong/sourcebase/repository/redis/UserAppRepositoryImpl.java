package com.lecuong.sourcebase.repository.redis;

import com.lecuong.sourcebase.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

@Repository
public class UserAppRepositoryImpl implements UserAppRepository {

    private static final String KEY = "USER_APP_KEY";

    @Resource(name = "redisTemplateCustom")
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations hashOperations;

    @Autowired
    public UserAppRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
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
    public void add(String id, User user) {
        hashOperations.put(KEY, id, user);
    }

    @Override
    public void addBlacklist(String id, Long time) {
        hashOperations.put(KEY, id, time);
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }

    @Override
    public User find(String id) {
        return (User) hashOperations.get(KEY, id);
    }

    @Override
    public Long findBlacklist(String id) {
        return (Long) hashOperations.get(KEY, id);
    }

    @Override
    public Map<Object, Object> findAll() {
        return hashOperations.entries(KEY);
    }
}

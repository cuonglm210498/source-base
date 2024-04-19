package com.lecuong.sourcebase.repository.redis;

import com.lecuong.sourcebase.entity.User;

import java.util.Map;

public interface UserAppRepository {

    void add(String id, User user);

    void addBlacklist(String id, Long time);

    void delete(String id);

    User find(String id);

    Long findBlacklist(String id);

    Map<Object, Object> findAll();
}

package com.lecuong.sourcebase.repository.redis;

import java.util.List;

public interface SessionRepository {

    void add(String key, String sessionId);

    void addList(String key, List<String> listSessionId);

    String find(String key);

    List<String> findList(String key);

    void delete(String key);
}

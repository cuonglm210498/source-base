package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.ApiKey;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author CuongLM
 * @created 20/05/2024 - 20:21
 * @project source-base
 */
@Repository
public interface ApiKeyRepository extends BaseRepository<ApiKey, Long> {

    @Query(value = "SELECT * FROM api_key a WHERE a.code = :code AND a.is_active = true AND a.is_deleted = false", nativeQuery = true)
    Optional<ApiKey> findByCode(@Param("code") String code);
}

package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.Client;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author CuongLM
 * @created 20/05/2024 - 21:29
 * @project source-base
 */
@Repository
public interface ClientRepository extends BaseRepository<Client, Long> {

    @Query(value = "SELECT * FROM client c WHERE c.client_id = :clientId AND c.client_pass = :clientPass AND c.is_active = true AND c.is_deleted = false", nativeQuery = true)
    Optional<Client> findByClientIdAndClientPass(@Param("clientId") String clientId, @Param("clientPass") String clientPass);
}

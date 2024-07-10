package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.RefreshToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author CuongLM
 * @created 11/07/2024 - 00:43
 * @project source-base
 */
@Repository
public interface RefreshTokenRepository extends BaseRepository<RefreshToken, Long> {

    @Query(value = "select rf from RefreshToken rf where rf.userName = :userName and rf.refreshToken = :refreshToken")
    Optional<RefreshToken> findByUserNameAndRefreshToken(String userName, String refreshToken);

    @Query(value = "select rf from RefreshToken rf where rf.userName = :userName")
    List<RefreshToken> findAllByUserName(String userName);
}

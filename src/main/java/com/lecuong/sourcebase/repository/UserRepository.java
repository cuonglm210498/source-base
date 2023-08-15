package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.User;
import com.lecuong.sourcebase.repository.custom.UserCustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long>, UserCustomRepository {

    Optional<User> findByUserNameAndPassword(String userName, String password);

    @Modifying
    @Query(value = "DELETE FROM user u WHERE u.user_name = :userName", nativeQuery = true)
    void deleteUserByUserName(String userName);

    long countAllByUserName(String userName);

    long countAllByEmail(String email);

    long countAllByPhone(String phone);
}

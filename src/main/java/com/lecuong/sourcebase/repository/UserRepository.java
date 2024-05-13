package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.User;
import com.lecuong.sourcebase.repository.custom.UserCustomRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    @Query(value = "CALL sp_get_user_by_id(:id)", nativeQuery = true)
    Optional<User> findUserByIdAndCallProc(Long id);
//    DELIMITER $$
//    CREATE PROCEDURE `sp_get_user_by_id`(IN user_id BIGINT(20))
//    BEGIN
//            SELECT
//		*
//    FROM `user` u
//    WHERE u.id = user_id;
//    END$$
//            DELIMITER ;
//
//    call sp_get_user_by_id(1);
}

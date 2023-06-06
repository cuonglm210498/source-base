package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.User;
import com.lecuong.sourcebase.repository.custom.UserCustomRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long>, UserCustomRepository {

    Optional<User> findByUserNameAndPassword(String userName, String password);

    long countAllByUserName(String userName);

    long countAllByEmail(String email);

    long countAllByPhone(String phone);
}

package com.lecuong.sourcebase.repository.custom.impl;

import com.lecuong.sourcebase.modal.request.user.UserFilterRequest;
import com.lecuong.sourcebase.modal.response.UserResponse;
import com.lecuong.sourcebase.repository.custom.UserCustomRepository;
import com.lecuong.sourcebase.util.DataUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * @author CuongLM18
 * @created 06/06/2023 - 3:16 PM
 * @project source-base
 */
@Repository
public class UserCustomRepositoryImpl implements UserCustomRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<UserResponse> getAllUser(Pageable pageable) {

        List<UserResponse> userResponses = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("u.id, ");
        sql.append("u.address, ");
        sql.append("u.avatar, ");
        sql.append("u.date_of_birth, ");
        sql.append("u.email, ");
        sql.append("u.full_name, ");
        sql.append("u.phone, ");
        sql.append("u.user_name ");
        sql.append("FROM user u ");

        if (pageable != null) {
            sql.append(" limit :size OFFSET :page ");
        }

        Query query = entityManager.createNativeQuery(sql.toString());

        if (pageable != null) {
            query.setParameter("page", DataUtil.pageSize(pageable));
            query.setParameter("size", pageable.getPageSize());
        }

        List<Object[]> result = query.getResultList();

        for (Object[] o : result) {
            int i = 0;
            UserResponse userResponse = new UserResponse();
            userResponse.setId(DataUtil.safeToLong(o[i++]));
            userResponse.setAddress(DataUtil.safeToString(o[i++]));
            userResponse.setAvatar(DataUtil.safeToString(o[i++]));
            userResponse.setDateOfBirth(DataUtil.safeToLocalDate(o[i++]));
            userResponse.setEmail(DataUtil.safeToString(o[i++]));
            userResponse.setFullName(DataUtil.safeToString(o[i++]));
            userResponse.setPhone(DataUtil.safeToString(o[i++]));
            userResponse.setUserName(DataUtil.safeToString(o[i++]));

            userResponses.add(userResponse);
        }

        return new PageImpl<>(userResponses);
    }

    @Override
    public Page<UserResponse> filterUser(UserFilterRequest filter, Pageable pageable) {
        List<UserResponse> userResponses = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        sql.append("u.id, ");
        sql.append("u.address, ");
        sql.append("u.avatar, ");
        sql.append("u.date_of_birth, ");
        sql.append("u.email, ");
        sql.append("u.full_name, ");
        sql.append("u.phone, ");
        sql.append("u.user_name ");
        sql.append("FROM user u ");
        sql.append("WHERE 1 = 1 ");

        if (!DataUtil.isNullOrEmpty(filter.getUserName())) {
            sql.append(" AND lower(u.user_name) LIKE lower(:userName) ");
        }
        if (!DataUtil.isNullOrEmpty(filter.getAddress())) {
            sql.append(" AND lower(u.address) LIKE lower(:address) ");
        }
        if (!DataUtil.isNullOrEmpty(filter.getEmail())) {
            sql.append(" AND lower(u.email) LIKE lower(:email) ");
        }
        if (!DataUtil.isNullOrEmpty(filter.getDateOfBirthFrom())) {
            sql.append(" AND q.date_of_birth >= :dateOfBirthFrom ");
        }
        if (!DataUtil.isNullOrEmpty(filter.getDateOfBirthTo())) {
            sql.append(" AND u.date_of_birth < DATE_ADD(:dateOfBirthTo, INTERVAL 1 DAY) ");
        }

        if (pageable != null) {
            sql.append(" limit :size OFFSET :page ");
        }

        Query query = entityManager.createNativeQuery(sql.toString());

        if (!DataUtil.isNullOrEmpty(filter.getUserName())) {
            query.setParameter("userName", "%" + filter.getUserName() + "%");
        }

        if (!DataUtil.isNullOrEmpty(filter.getAddress())) {
            query.setParameter("address", "%" + filter.getAddress() + "%");
        }

        if (!DataUtil.isNullOrEmpty(filter.getEmail())) {
            query.setParameter("email", "%" + filter.getEmail() + "%");
        }

        if (!DataUtil.isNullOrEmpty(filter.getDateOfBirthFrom())) {
            query.setParameter("dateOfBirthFrom", filter.getDateOfBirthFrom());
        }
        if (!DataUtil.isNullOrEmpty(filter.getDateOfBirthTo())) {
            query.setParameter("dateOfBirthTo", filter.getDateOfBirthTo());
        }

        if (pageable != null) {
            query.setParameter("page", DataUtil.pageSize(pageable));
            query.setParameter("size", pageable.getPageSize());
        }

        List<Object[]> result = query.getResultList();

        for (Object[] o : result) {
            int i = 0;
            UserResponse userResponse = new UserResponse();
            userResponse.setId(DataUtil.safeToLong(o[i++]));
            userResponse.setAddress(DataUtil.safeToString(o[i++]));
            userResponse.setAvatar(DataUtil.safeToString(o[i++]));
            userResponse.setDateOfBirth(DataUtil.safeToLocalDate(o[i++]));
            userResponse.setEmail(DataUtil.safeToString(o[i++]));
            userResponse.setFullName(DataUtil.safeToString(o[i++]));
            userResponse.setPhone(DataUtil.safeToString(o[i++]));
            userResponse.setUserName(DataUtil.safeToString(o[i++]));

            userResponses.add(userResponse);
        }

        return new PageImpl<>(userResponses);
    }
}

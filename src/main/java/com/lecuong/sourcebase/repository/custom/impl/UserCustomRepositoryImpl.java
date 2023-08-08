package com.lecuong.sourcebase.repository.custom.impl;

import com.lecuong.sourcebase.modal.request.user.UserFilterRequest;
import com.lecuong.sourcebase.modal.response.user.UserResponse;
import com.lecuong.sourcebase.repository.custom.UserCustomRepository;
import com.lecuong.sourcebase.util.DataUtils;
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

    private static final String SELECT_USER =
            "SELECT u.id, u.address, u.avatar, u.date_of_birth, u.email, u.full_name, u.phone, u.user_name, " +
            "COUNT(1) over(PARTITION BY 1) totalCount " +
            "FROM user u " +
            "WHERE 1 = 1 ";

    private static final String SELECT_USER_WITH_ROLE =
            "SELECT u.id, u.address, u.avatar, u.date_of_birth, u.email, u.full_name, u.phone, u.user_name, group_concat(' ', r.name), " +
            "COUNT(u.id) over(PARTITION BY u.id) totalCount " +
            "FROM user u " +
            "INNER JOIN permission p ON u.id = p.user_id " +
            "INNER JOIN role r ON p.role_id = r.id " +
            "WHERE 1 = 1 ";

    private static final String GROUP_BY_USER_ID = "GROUP BY u.id";

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<UserResponse> getAllUser(Pageable pageable) {

        List<UserResponse> userResponses = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_USER_WITH_ROLE);
        sql.append(GROUP_BY_USER_ID);

        if (pageable != null) {
            sql.append(" limit :size OFFSET :page ");
        }

        Query query = entityManager.createNativeQuery(sql.toString());

        if (pageable != null) {
            query.setParameter("page", DataUtils.pageSize(pageable));
            query.setParameter("size", pageable.getPageSize());
        }

        List<Object[]> result = query.getResultList();

        for (Object[] o : result) {
            int i = 0;
            UserResponse userResponse = new UserResponse();
            userResponse.setId(DataUtils.safeToLong(o[i++]));
            userResponse.setAddress(DataUtils.safeToString(o[i++]));
            userResponse.setAvatar(DataUtils.safeToString(o[i++]));
            userResponse.setDateOfBirth(DataUtils.safeToLocalDate(o[i++]));
            userResponse.setEmail(DataUtils.safeToString(o[i++]));
            userResponse.setFullName(DataUtils.safeToString(o[i++]));
            userResponse.setPhone(DataUtils.safeToString(o[i++]));
            userResponse.setUserName(DataUtils.safeToString(o[i++]));
            userResponse.setRoleName(DataUtils.safeToListString(o[i++]));
//            userResponse.setTotalCount(DataUtil.safeToInt(o[i]));

            userResponses.add(userResponse);
        }

        return new PageImpl<>(userResponses);
    }

    @Override
    public Page<UserResponse> filterUser(UserFilterRequest filter, Pageable pageable) {
        List<UserResponse> userResponses = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_USER_WITH_ROLE);

        if (!DataUtils.isNullOrEmpty(filter.getUserName())) {
            sql.append(" AND lower(u.user_name) LIKE lower(:userName) ");
        }
        if (!DataUtils.isNullOrEmpty(filter.getAddress())) {
            sql.append(" AND lower(u.address) LIKE lower(:address) ");
        }
        if (!DataUtils.isNullOrEmpty(filter.getEmail())) {
            sql.append(" AND lower(u.email) LIKE lower(:email) ");
        }
        if (!DataUtils.isNullOrEmpty(filter.getDateOfBirthFrom())) {
            sql.append(" AND q.date_of_birth >= :dateOfBirthFrom ");
        }
        if (!DataUtils.isNullOrEmpty(filter.getDateOfBirthTo())) {
            sql.append(" AND u.date_of_birth < DATE_ADD(:dateOfBirthTo, INTERVAL 1 DAY) ");
        }

        if (!DataUtils.isNullOrEmpty(filter.getRoleId())) {
            sql.append(" AND r.id in (:roleId) ");
        }

        sql.append(GROUP_BY_USER_ID);

        if (pageable != null) {
            sql.append(" limit :size OFFSET :page ");
        }

        Query query = entityManager.createNativeQuery(sql.toString());

        if (!DataUtils.isNullOrEmpty(filter.getUserName())) {
            query.setParameter("userName", "%" + filter.getUserName() + "%");
        }

        if (!DataUtils.isNullOrEmpty(filter.getAddress())) {
            query.setParameter("address", "%" + filter.getAddress() + "%");
        }

        if (!DataUtils.isNullOrEmpty(filter.getEmail())) {
            query.setParameter("email", "%" + filter.getEmail() + "%");
        }

        if (!DataUtils.isNullOrEmpty(filter.getDateOfBirthFrom())) {
            query.setParameter("dateOfBirthFrom", filter.getDateOfBirthFrom());
        }
        if (!DataUtils.isNullOrEmpty(filter.getDateOfBirthTo())) {
            query.setParameter("dateOfBirthTo", filter.getDateOfBirthTo());
        }
        if (!DataUtils.isNullOrEmpty(filter.getRoleId())) {
            query.setParameter("roleId", filter.getRoleId());
        }

        if (pageable != null) {
            query.setParameter("page", DataUtils.pageSize(pageable));
            query.setParameter("size", pageable.getPageSize());
        }

        List<Object[]> result = query.getResultList();

        for (Object[] o : result) {
            int i = 0;
            UserResponse userResponse = new UserResponse();
            userResponse.setId(DataUtils.safeToLong(o[i++]));
            userResponse.setAddress(DataUtils.safeToString(o[i++]));
            userResponse.setAvatar(DataUtils.safeToString(o[i++]));
            userResponse.setDateOfBirth(DataUtils.safeToLocalDate(o[i++]));
            userResponse.setEmail(DataUtils.safeToString(o[i++]));
            userResponse.setFullName(DataUtils.safeToString(o[i++]));
            userResponse.setPhone(DataUtils.safeToString(o[i++]));
            userResponse.setUserName(DataUtils.safeToString(o[i++]));
            userResponse.setRoleName(DataUtils.safeToListString(o[i++]));
            userResponse.setTotalCount(DataUtils.safeToInt(o[i]));

            userResponses.add(userResponse);
        }

        return new PageImpl<>(userResponses);
    }
}

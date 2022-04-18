package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {

    List<Role> findByIdIn(List<Long> ids);
}

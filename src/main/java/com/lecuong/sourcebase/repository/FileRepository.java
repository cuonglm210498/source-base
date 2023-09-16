package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.File;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author CuongLM
 * @created 16/09/2023 - 10:56 PM
 * @project source-base
 */
@Repository
public interface FileRepository extends BaseRepository<File, Long> {

    Optional<File> findByFileName(String name);
}

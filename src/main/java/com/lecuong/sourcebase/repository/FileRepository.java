package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.File;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author CuongLM
 * @created 16/07/2024 - 21:25
 * @project source-base
 */
@Repository
public interface FileRepository extends BaseRepository<File, Long> {
}

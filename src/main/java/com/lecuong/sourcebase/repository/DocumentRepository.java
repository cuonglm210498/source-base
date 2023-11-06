package com.lecuong.sourcebase.repository;

import com.lecuong.sourcebase.entity.Document;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author CuongLM
 * @created 06/11/2023 - 7:30 PM
 * @project source-base
 */
@Repository
public interface DocumentRepository extends BaseRepository<Document, Long> {

    Optional<Document> findByRefId(String refId);
}

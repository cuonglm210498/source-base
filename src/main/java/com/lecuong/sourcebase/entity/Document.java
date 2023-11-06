package com.lecuong.sourcebase.entity;

import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CuongLM
 * @created 06/11/2023 - 7:25 PM
 * @project source-base
 */
@Entity
@Table(name = "document")
@Where(clause = "is_deleted = 0")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document extends BaseEntity {

    @Column(length = 100)
    private String refId;
    @Column(length = 250)
    private String fileName;
    @Column(length = 500)
    private String filePath;
    @Column(length = 100)
    private String fileType;
    @Column
    private long fileSize;
}

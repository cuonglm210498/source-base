package com.lecuong.sourcebase.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author CuongLM18
 * @created 30/03/2023 - 11:02 AM
 * @project source-base
 */
@Entity
@Table(name = "attach_file")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttachFile extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "file_name", length = 200)
    private String fileName;

    @Column(name = "url", length = 200)
    private String uri;

    @Column(name = "size")
    private Long size;
}

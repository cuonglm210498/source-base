package com.lecuong.sourcebase.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author CuongLM
 * @created 20/05/2024 - 20:17
 * @project source-base
 */

@Entity
@Table(name = "api_key")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiKey extends BaseEntity {

    @Column(unique = true, nullable = false)
    private String code;

    @Column()
    private String description;
}

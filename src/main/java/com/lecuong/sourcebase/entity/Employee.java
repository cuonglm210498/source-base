package com.lecuong.sourcebase.entity;

import com.lecuong.sourcebase.constant.PositionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author CuongLM18
 * @created 28/03/2023 - 10:25 AM
 * @project source-base
 */
@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "position")
    @Enumerated(EnumType.ORDINAL)
    private PositionEnum positionEnum;

}

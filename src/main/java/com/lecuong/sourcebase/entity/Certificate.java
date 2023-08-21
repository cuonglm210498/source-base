package com.lecuong.sourcebase.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cuong.lemanh10
 * @created 21/08/2023
 * @project demo
 */
@Entity
@Table(name = "certificate")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "employee_id") // Mã nhân viên
    private long employeeId;

    @Column(name = "type") // Phân loại chứng nhận, chứng chỉ
    private String type;

    @Column(name = "field") // lĩnh vực
    private String field;

    @Column(name = "full_name_of_certificate") // tên đầy đủ của chứng chỉ
    private String fullNameOfCertificate;

    @Column(name = "acronym") // Viết tắt
    private String acronym;

    @Column(name = "issuer") // hãng cấp
    private String issuer;

    @Column(name = "date_range") // ngày cấp
    private Date dateRange;

    @Column(name = "expiration_date") // ngày hết hạn
    private Date expirationDate;
}

package com.lecuong.sourcebase.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;

/**
 * @author CuongLM
 * @created 11/07/2024 - 00:35
 * @project source-base
 */
@Entity
@Table(name = "refresh_token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Where(clause = "is_deleted = 0 AND is_active = 1")
public class RefreshToken extends BaseEntity {

    @Column
    private String userName;

    @Column
    private Long userId;

    @Lob
    private String refreshToken;

    // Sử dụng @Temporal(TemporalType.TIMESTAMP) để lưu giờ phút giây vào database
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresDate;
}

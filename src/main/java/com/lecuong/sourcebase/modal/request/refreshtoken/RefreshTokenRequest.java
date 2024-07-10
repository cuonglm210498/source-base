package com.lecuong.sourcebase.modal.request.refreshtoken;

import lombok.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * @author CuongLM
 * @created 11/07/2024 - 00:48
 * @project source-base
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {

    private Long userId;
    private String userName;
    private String refreshToken;
    @Temporal(TemporalType.TIMESTAMP)
    private Date expiresDate;
}

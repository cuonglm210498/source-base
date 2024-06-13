package com.lecuong.sourcebase.modal.response.user;

import lombok.*;

/**
 * @author CuongLM
 * @created 12/06/2024 - 20:55
 * @project source-base
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthResponse {

    private String accessToken;
    private String refreshToken;
}

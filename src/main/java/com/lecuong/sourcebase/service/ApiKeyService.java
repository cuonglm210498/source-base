package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.modal.response.apikey.ApiKeyResponse;

/**
 * @author CuongLM
 * @created 20/05/2024 - 20:29
 * @project source-base
 */
public interface ApiKeyService {

    ApiKeyResponse findByKey(String code);
}

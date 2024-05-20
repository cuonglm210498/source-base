package com.lecuong.sourcebase.service;

import com.lecuong.sourcebase.modal.response.client.ClientResponse;

/**
 * @author CuongLM
 * @created 20/05/2024 - 21:29
 * @project source-base
 */
public interface ClientService {

    ClientResponse findByClientIdAndClientPass(String clientId, String clientPass);
}

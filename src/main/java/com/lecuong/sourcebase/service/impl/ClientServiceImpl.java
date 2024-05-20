package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.entity.Client;
import com.lecuong.sourcebase.exceptionv2.BusinessCodeResponse;
import com.lecuong.sourcebase.exceptionv2.BusinessExceptionV2;
import com.lecuong.sourcebase.mapper.ClientMapper;
import com.lecuong.sourcebase.modal.response.client.ClientResponse;
import com.lecuong.sourcebase.repository.ClientRepository;
import com.lecuong.sourcebase.service.ClientService;
import com.lecuong.sourcebase.util.AlgorithmUtils;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author CuongLM
 * @created 20/05/2024 - 21:31
 * @project source-base
 */
@Data
@Service
public class ClientServiceImpl implements ClientService {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    @Override
    public ClientResponse findByClientIdAndClientPass(String clientId, String clientPass) {
        Optional<Client> client = clientRepository.findByClientIdAndClientPass(clientId, AlgorithmUtils.hashSha256(clientPass));
        return checkClientExist(client);
    }

    private ClientResponse checkClientExist(Optional<Client> client) {
        if (client.isPresent()) {
            return clientMapper.to(client.get());
        } else {
            throw new BusinessExceptionV2(BusinessCodeResponse.CLIENT_NOT_FOUND);
        }
    }
}

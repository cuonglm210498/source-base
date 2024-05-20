package com.lecuong.sourcebase.mapper;

import com.lecuong.sourcebase.entity.Client;
import com.lecuong.sourcebase.modal.response.client.ClientResponse;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * @author CuongLM
 * @created 20/05/2024 - 21:32
 * @project source-base
 */
@Component
@AllArgsConstructor
public class ClientMapper {

    private ModelMapper modelMapper;

    public ClientResponse to(Client client) {
        return modelMapper.map(client, ClientResponse.class);
    }
}

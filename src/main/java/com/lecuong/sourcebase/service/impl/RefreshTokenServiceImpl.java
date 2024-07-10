package com.lecuong.sourcebase.service.impl;

import com.lecuong.sourcebase.entity.RefreshToken;
import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.mapper.RefreshTokenMapper;
import com.lecuong.sourcebase.modal.request.refreshtoken.RefreshTokenRequest;
import com.lecuong.sourcebase.modal.response.refreshtoken.RefreshTokenResponse;
import com.lecuong.sourcebase.repository.RefreshTokenRepository;
import com.lecuong.sourcebase.service.RefreshTokenService;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author CuongLM
 * @created 11/07/2024 - 00:56
 * @project source-base
 */
@Data
@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenMapper mapper;
    private final RefreshTokenRepository repository;

    @Override
    @Transactional
    public void save(RefreshTokenRequest request) {
        repository.save(mapper.to(request));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<RefreshToken> refreshToken = repository.findById(id);
        if (refreshToken.isPresent()) {
            refreshToken.get().setIsActive(false);
            refreshToken.get().setIsDeleted(true);
            repository.save(refreshToken.get());
        } else {
            throw new BusinessException(StatusTemplate.REFRESH_TOKEN_NOT_FOUND);
        }
    }

    @Override
    @Transactional
    public void deleteAllByUserName(String userName) {
        List<RefreshToken> refreshTokens = repository.findAllByUserName(userName);
        repository.saveAll(refreshTokens.stream().peek(refreshToken -> {
            refreshToken.setIsDeleted(true);
            refreshToken.setIsActive(false);
        }).collect(Collectors.toList()));
    }

    @Override
    @Transactional
    public List<RefreshTokenResponse> findAll() {
        return repository.findAll().stream().map(mapper::to).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public RefreshTokenResponse findByUserNameAndRefreshToken(String userName, String refreshToken) {
        Optional<RefreshToken> refreshTokenOptional = repository.findByUserNameAndRefreshToken(userName, refreshToken);
        if (refreshTokenOptional.isPresent()) {
            return mapper.to(refreshTokenOptional.get());
        } else {
            throw new BusinessException(StatusTemplate.REFRESH_TOKEN_NOT_FOUND);
        }
    }
}

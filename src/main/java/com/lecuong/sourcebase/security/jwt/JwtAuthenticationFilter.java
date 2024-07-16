package com.lecuong.sourcebase.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecuong.sourcebase.exception.BusinessException;
import com.lecuong.sourcebase.exception.StatusTemplate;
import com.lecuong.sourcebase.modal.response.BaseResponse;
import com.lecuong.sourcebase.security.UserDetailsServiceImpl;
import com.lecuong.sourcebase.util.DataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final List<String> apiBase = List.of(
                "/api/v1/user/register",
                "/api/v1/user/login",
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars",
                "/swagger-ui");

        Predicate<HttpServletRequest> isApiBase = r -> apiBase.stream()
                .anyMatch(uri -> r.getRequestURI().toLowerCase().contains(uri.toLowerCase()));

        if (!isApiBase.test(request)) {
            String jwt = getJwtFromRequest(request);
            // Kiểm tra sự tồn tại của token trong mỗi request
            if (Boolean.FALSE.equals(DataUtils.isTrue(jwt))) {
                HttpServletResponse resp = response;
                resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                BusinessException error = new BusinessException(StatusTemplate.TOKEN_MISSING);
                this.objectMapper.writeValue(resp.getOutputStream(), BaseResponse.ofFail(error));
                return;
            }

            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) && "refresh".equals(tokenProvider.getTypeOfToken(jwt))) {
                // Kiểm tra nếu là refresh_token và không phải endpoint làm mới token thì trả về lỗi
                // Trường hợp truyền refresh_token để truy cập các API (không được phép truy cập)
                // Ngược lại thì cho đi qua
                if (!"/api/v1/token/refresh-token".equalsIgnoreCase(request.getRequestURI())) {
                    HttpServletResponse resp = response;
                    resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                    BusinessException error;
                    error = new BusinessException(StatusTemplate.REFRESH_TOKEN_IS_NOT_ALLOWED);
                    this.objectMapper.writeValue(resp.getOutputStream(), BaseResponse.ofFail(error));
                    return;
                }
            } else if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt) && "access".equals(tokenProvider.getTypeOfToken(jwt))) {
                String userName = tokenProvider.getUsername(jwt);
                UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(userName);
                if (userDetails != null) {
                    UsernamePasswordAuthenticationToken
                            authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } else {
                HttpServletResponse resp = response;
                resp.setStatus(HttpStatus.UNAUTHORIZED.value());
                BusinessException error;
                if ("refresh".equals(tokenProvider.getTypeOfToken(jwt))) {
                    error = new BusinessException(StatusTemplate.REFRESH_TOKEN);
                } else {
                    error = new BusinessException(StatusTemplate.TOKEN);
                }
                this.objectMapper.writeValue(resp.getOutputStream(), BaseResponse.ofFail(error));
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Kiểm tra xem header Authorization có chứa thông tin jwt không
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}

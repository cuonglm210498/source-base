package com.lecuong.sourcebase.security;

import com.lecuong.sourcebase.entity.Role;
import com.lecuong.sourcebase.entity.User;
import com.lecuong.sourcebase.repository.UserRepository;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Kiểm tra xem user có tồn tại trong database không?
        User user = userRepository.findByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        UserToken userToken = new UserToken();
        userToken.setId(user.getId());
        userToken.setUserName(user.getUserName());
        userToken.setPassword(user.getPassword());
        userToken.setEmail(user.getEmail());
        userToken.setPhone(user.getPhone());
        userToken.setAddress(user.getAddress());
        userToken.setFullName(user.getFullName());
        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        userToken.setRoles(roles);
        return UserDetailsImpl.build(userToken);
    }
}

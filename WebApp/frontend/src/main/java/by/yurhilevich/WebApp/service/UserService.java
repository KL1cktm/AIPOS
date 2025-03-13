package by.yurhilevich.WebApp.service;


import by.yurhilevich.WebApp.dto.UserDTO;
import by.yurhilevich.WebApp.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Slf4j
@Service
public class UserService implements UserDetailsService {
//    Logger logger = LoggerFactory.getLogger(DeviceComponentService.class);
    @Autowired
    private RestTemplate restTemplate;
    @Value("${backend.url}")
    private String backendUrl;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String url = backendUrl + "/api/load_user";
        UserDTO userDTO1 = new UserDTO();
        userDTO1.setUsername(username);

        ResponseEntity<UserDTO> response = restTemplate.exchange(url, HttpMethod.POST,
                new HttpEntity<>(userDTO1), UserDTO.class);

        if (response.getStatusCode()== HttpStatus.NOT_FOUND) {
            throw new UsernameNotFoundException("User not found");
        }
        User user = new User();
        UserDTO userDTO = response.getBody();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setFullname(userDTO.getFullname());
        user.setPhone(userDTO.getPhone());
        user.setId(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setRole(User.Role.valueOf(userDTO.getRole()));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }

}

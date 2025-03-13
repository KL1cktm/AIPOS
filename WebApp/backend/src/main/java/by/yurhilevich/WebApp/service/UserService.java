package by.yurhilevich.WebApp.service;


import by.yurhilevich.WebApp.models.User;
import by.yurhilevich.WebApp.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
//    Logger logger = LoggerFactory.getLogger(DeviceComponentService.class);
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);

        User user = optionalUser.orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username)
        );

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))
        );
    }

    public void saveUser(User user) {
        userRepository.save(user);
//        logger.info("User saved successfully: {}", user.getUsername());
    }

    public boolean isUserExists(String username, String phone) {
        return (userRepository.findByUsername(username).isPresent() || userRepository.findByPhone(phone).isPresent());
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public void changeUserRole( Long userId, User.Role role){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            User currentUser = user.get();
            currentUser.setRole( role );
            userRepository.save( currentUser );
        }
    }
}

package jan.jakubowski.noteme.services;

import jan.jakubowski.noteme.database.entities.User;
import jan.jakubowski.noteme.database.repositories.UserRepository;
import jan.jakubowski.noteme.services.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    public Optional<UserDTO> fetchUserByLogin(String login) {
        Optional<User> userOptional = userRepository.getOneByLogin(login);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return Optional.of(
                    new UserDTO(
                            user.getId(),
                            user.getLogin(),
                            user.getEmail(),
                            "",
                            user.isAccountNonExpired(),
                            user.isAccountNonLocked(),
                            user.isCredentialsNonExpired(),
                            user.isEnabled()

                    ));
        }

        return Optional.empty();
    }


}

package jan.jakubowski.noteme.services;

import jan.jakubowski.noteme.database.entities.User;
import jan.jakubowski.noteme.database.repositories.UserRepository;
import jan.jakubowski.noteme.exceptions.UserAlreadyExistException;
import jan.jakubowski.noteme.exceptions.UserDoesNotExistException;
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

    public UserDTO addUser(UserDTO dto) {
        Optional<User> userOptional = userRepository.getOneByLogin(dto.login);
        if (userOptional.isEmpty()) {
            User result = userRepository.save(new User(
                    dto.login,
                    dto.email,
                    encoder.encode(dto.password),
                    true,
                    true,
                    true,
                    true
            ));
            return mapToDTO(result);
        }
        throw new UserAlreadyExistException();
    }

    public User fetchUserByLogin(String login) {
        return userRepository
                .getOneByLogin(login)
                .orElseThrow(UserDoesNotExistException::new);
    }

    private UserDTO mapToDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                "",
                user.isAccountNonExpired(),
                user.isAccountNonLocked(),
                user.isCredentialsNonExpired(),
                user.isEnabled()
        );
    }


}

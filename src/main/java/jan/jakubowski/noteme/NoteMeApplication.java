package jan.jakubowski.noteme;

import jan.jakubowski.noteme.database.entities.Privilege;
import jan.jakubowski.noteme.database.entities.Role;
import jan.jakubowski.noteme.database.entities.User;
import jan.jakubowski.noteme.database.repositories.PrivilegeRepository;
import jan.jakubowski.noteme.database.repositories.RoleRepository;
import jan.jakubowski.noteme.database.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@SpringBootApplication
public class NoteMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteMeApplication.class, args);
	}

	@Autowired
	PasswordEncoder passwordEncoder;

	@Bean
	@Transactional
	CommandLineRunner demo(UserRepository userRepository, RoleRepository roleRepository) {
		return (args) -> {
			Optional<User> hipoteticalUser = userRepository.getOneByLogin("test");
			if (hipoteticalUser.isPresent()) {
				return;
			}
		    Role role = roleRepository.getOneByName("USER").orElseThrow(() -> new NullPointerException("Role == null"));
		    User user = new User("test", "test@test.test", passwordEncoder.encode("test"), true, true, true, true);
			List<Role> roles = new ArrayList<>();
			roles.add(role);
		    user.setRoles(roles);
		    userRepository.save(user);
		};
	}
}

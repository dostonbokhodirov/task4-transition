package intern.task4.authorization.service;

import intern.task4.authorization.config.security.UserDetails;
import intern.task4.authorization.dto.AuthUserUpdateDto;
import intern.task4.authorization.entity.AuthUser;
import intern.task4.authorization.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthUserService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthUser user = authUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Bad Credentials"));
        if (Objects.isNull(user)) {
            throw new RuntimeException("USER_NOT_FOUND");
        }
        return new UserDetails(user);

    }

    public void create(AuthUser authUser) {
        authUserRepository.findByEmail(authUser.getEmail())
                .orElseThrow(() -> new RuntimeException("Email is already taken"));
        String encodedPassword = encoder.encode(authUser.getPassword());
        authUser.setPassword(encodedPassword);
        authUserRepository.save(authUser);
    }

    public Object get(Long id) {
        return null;
    }

    public Object getAll(long l) {
        return null;
    }

    public void update(AuthUserUpdateDto dto) {

    }
}

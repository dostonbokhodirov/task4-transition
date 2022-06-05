package intern.task4.authorization.service;

import intern.task4.authorization.config.security.UserDetails;
import intern.task4.authorization.entity.AuthUser;
import intern.task4.authorization.enums.Status;
import intern.task4.authorization.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
        Optional<AuthUser> optional = authUserRepository.findByEmail(authUser.getEmail());
        if (optional.isPresent()) return;
        String encodedPassword = encoder.encode(authUser.getPassword());
        authUser.setPassword(encodedPassword);
        authUserRepository.save(authUser);
    }

    public List<AuthUser> getAll() {
        return authUserRepository.findAll();
    }

    public void delete(Long id) {
        authUserRepository.deleteById(id);
    }

    public void blockOrUnblock(Long id) {
        Optional<AuthUser> optional = authUserRepository.findById(id);
        if (optional.isPresent()) {
            AuthUser authUser = optional.get();
            authUser.setStatus(authUser.getStatus().equals(Status.ACTIVE) ? Status.BLOCKED : Status.ACTIVE);
            authUserRepository.save(authUser);
        }
    }

}

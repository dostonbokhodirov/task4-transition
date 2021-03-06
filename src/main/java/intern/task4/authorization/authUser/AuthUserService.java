package intern.task4.authorization.authUser;

import intern.task4.authorization.config.security.UserDetails;
import intern.task4.authorization.enums.Status;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AuthUserService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder encoder;

    public AuthUserService(AuthUserRepository authUserRepository,
                           PasswordEncoder encoder) {
        this.authUserRepository = authUserRepository;
        this.encoder = encoder;
    }

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

    public void blockOrUnblock(Long id) {
        Optional<AuthUser> optional = getAuthUserByIdAndCheckExistence(id);
        if (optional.isPresent()) {
            AuthUser authUser = optional.get();
            authUser.setStatus(authUser.getStatus().equals(Status.ACTIVE) ? Status.BLOCKED : Status.ACTIVE);
            authUserRepository.save(authUser);
        }
    }

    private Optional<AuthUser> getAuthUserByIdAndCheckExistence(Long id) {
        return Optional.ofNullable(authUserRepository.findById(id).orElseThrow(() -> new RuntimeException("Bad Credentials")));
    }

    public void blockOrUnblock(List<Long> ids) {
        for (Long id : ids) {
            getAuthUserByIdAndCheckExistence(id);
            blockOrUnblock(id);
        }
    }

    public void delete(List<Long> ids) {
        for (Long id : ids) {
            getAuthUserByIdAndCheckExistence(id);
            authUserRepository.deleteById(id);
        }
    }

    public Long getIdByName(String name) {
        return authUserRepository.findByName(name).getId();
    }
}

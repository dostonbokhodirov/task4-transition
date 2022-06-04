package intern.task4.authorization.service;

import intern.task4.authorization.config.security.UserDetails;
import intern.task4.authorization.dto.LoginDto;
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
        if (optional.isPresent()) {
            return;
        }
        String encodedPassword = encoder.encode(authUser.getPassword());
        authUser.setPassword(encodedPassword);
        authUserRepository.save(authUser);
    }

    public void blockOrUnblock(Long id) {
        AuthUser authUser = authUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        authUser.setStatus(authUser.getStatus().equals(Status.ACTIVE) ? Status.BLOCKED : Status.ACTIVE);
        authUserRepository.save(authUser);
    }

    public AuthUser get(Long id) {
        return authUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<AuthUser> getAll() {
        return authUserRepository.findAll();
    }

    public boolean login(LoginDto dto) {
        Optional<AuthUser> optional = authUserRepository.findByEmail(dto.email);
        return optional.isPresent() && encoder.matches(dto.password, optional.get().getPassword());
    }

    public void delete(Long id) {
        authUserRepository.deleteById(id);
    }
}

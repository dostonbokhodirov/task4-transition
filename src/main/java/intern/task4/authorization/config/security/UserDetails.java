package intern.task4.authorization.config.security;

import intern.task4.authorization.authUser.AuthUser;
import intern.task4.authorization.enums.Status;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private final AuthUser authUser;


    public UserDetails(AuthUser user) {
        this.authUser = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.authUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.authUser.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return authUser.getStatus().equals(Status.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return authUser.getStatus().equals(Status.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return authUser.getStatus().equals(Status.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return this.authUser.getStatus() == Status.ACTIVE;
    }
}

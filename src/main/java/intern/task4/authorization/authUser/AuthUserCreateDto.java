package intern.task4.authorization.authUser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUserCreateDto {
    public String name;
    public String email;
    public String password;
}

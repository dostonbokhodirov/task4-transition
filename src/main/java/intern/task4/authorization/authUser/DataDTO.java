package intern.task4.authorization.authUser;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DataDTO {
    private List<Long> ids;
    private String name;
}

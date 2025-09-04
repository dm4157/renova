package party.msdg.renova.user.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PLoginUser {

    @NotBlank(message = "账号不能为空")
    private String account;

    @NotBlank(message = "密码不能为空")
    private String password;
}

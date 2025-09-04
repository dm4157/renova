package party.msdg.renova.user;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import party.msdg.renova.base.Re;
import party.msdg.renova.toolkit.Beans;
import party.msdg.renova.user.model.PLoginUser;
import party.msdg.renova.user.model.User;

/**
 * Wow! Sweet moon.
 */
@RestController
@RequestMapping("/api/uc")
public class UserController {
  
    @Autowired
    private UserService accountService;
    
    @PostMapping("/v1/register")
    void register(
        @RequestParam("account") String account,
        @RequestParam("password") String password
    ) {
        accountService.add(account, password);
    }
    
    @PostMapping("/v1/login")
    Re<String> login(@Validated @RequestBody PLoginUser loginUser) {
        User user = Beans.copy(loginUser, User.class);
        accountService.doLogin(user);
        return Re.ok(StpUtil.getTokenValue());
    }
}

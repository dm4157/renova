package party.msdg.renova.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    void login(
        @RequestParam("account") String account,
        @RequestParam("password") String password
    ) {
        accountService.doLogin(account, password);
    }
}

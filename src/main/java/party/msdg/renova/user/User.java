package party.msdg.renova.user;

import lombok.Data;

/**
 * 登录账户
 * Wow! Sweet moon.
 */
@Data
public class User {
    
    private int id;
    private String account;
    private String password;
    
    public User() {
    }
    
    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }
}

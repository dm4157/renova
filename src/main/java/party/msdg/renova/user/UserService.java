package party.msdg.renova.user;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.msdg.renova.base.work.WorkAssert;

import static party.msdg.renova.base.work.WorkCode.*;

/**
 * Wow! Sweet moon.
 */
@Service
public class UserService {
    
    @Autowired
    private UserDao userDao;
    
    public void add(String account, String password) {
        // 判断是否已经注册
        User alreadyRegister = userDao.one(account);
        WorkAssert.isNull(alreadyRegister).just(ALREADY_REGISTER);

        // 注册用户
        User user = new User(account, password);
        int res = userDao.add(user);
        WorkAssert.equalBasic(res, 1).just(REGISTER_FAIL);
    }
    
    public void doLogin(String account, String password) {
        User user = userDao.one(account);
        WorkAssert.notNull(user).just(UNREGISTER);
        WorkAssert.equal(user.getPassword(), password).just(LOGIN_NOT_MATCH);
        
        StpUtil.login(user.getId());
    }
}

package party.msdg.renova.user;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import party.msdg.renova.user.model.User;

@Repository
public interface UserDao {
    User one(@Param("account") String account);
    
    int add(User account);
}

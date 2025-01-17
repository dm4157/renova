package party.msdg.renova.user;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User one(@Param("account") String account);
    
    int add(User account);
}

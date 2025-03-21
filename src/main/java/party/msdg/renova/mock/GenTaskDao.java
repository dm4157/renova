package party.msdg.renova.mock;

import org.springframework.stereotype.Repository;

@Repository
public interface GenTaskDao {

    GenTask one(int id);
}

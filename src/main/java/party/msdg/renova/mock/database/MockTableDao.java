package party.msdg.renova.mock.database;

import org.springframework.stereotype.Repository;

@Repository
public interface MockTableDao {

    MockTable one(int id);
}

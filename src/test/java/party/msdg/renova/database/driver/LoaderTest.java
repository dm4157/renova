package party.msdg.renova.database.driver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
class LoaderTest {

    @Autowired
    private Loader loader;

    @Test
    void loadAll() {
        loader.loadAll();
    }
}
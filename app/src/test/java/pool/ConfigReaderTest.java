package pool;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigReaderTest {
    @Test void appHasAGreeting() {
        ConfigReader r = new ConfigReader();

        r.parse("../resources/config.json");
    }
}

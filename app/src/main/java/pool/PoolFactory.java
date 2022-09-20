package pool;

import org.json.simple.JSONObject;

public interface PoolFactory {
    PoolObject createPoolObj(JSONObject configJson);
}

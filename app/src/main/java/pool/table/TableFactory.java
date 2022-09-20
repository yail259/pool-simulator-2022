package pool.table;

import org.json.simple.JSONObject;
import pool.PoolFactory;
import pool.PoolObject;

public class TableFactory implements PoolFactory {
    /**
     * The creator for pool table.
     *
     * Reads in a JSONObject file and creates a Pool table object based on the JSON file.
     * @param configJson
     * @return Table as PoolObj
     */
    @Override
    public PoolObject createPoolObj(JSONObject configJson) {
        // reading the Table section:
        JSONObject jsonTable = (JSONObject) configJson.get("Table");

        // reading a value from the table section
        String tableColour = (String) jsonTable.get("colour");

        // reading a coordinate from the nested section within the table
        // note that the table x and y are of type Long (i.e. they are integers)
        Long tableX = (Long) ((JSONObject) jsonTable.get("size")).get("x");
        Long tableY = (Long) ((JSONObject) jsonTable.get("size")).get("y");

        // getting the friction level.
        // This is a double which should affect the rate at which the balls slow down
        Double tableFriction = (Double) jsonTable.get("friction");

        return new Table(tableColour, tableX, tableY, tableFriction);
    }
}

package pool;

import javafx.application.Application;
import javafx.stage.Stage;

import pool.table.Table;

public class App extends Application{
    @Override
    public void start(Stage primaryStage) {
        Table newTable = readResourcesTable();

        GameManager game = new GameManager(newTable);

        // show game, enjoy!
        primaryStage.setTitle("Pool is cool");
        primaryStage.setScene(game.getGameScene());
        primaryStage.show();

        game.run();
    }

    public static Table readResourcesTable() {
        ConfigReader cr = new ConfigReader();
        // ensure the correct project directory is used
        String projDir = System.getProperty("user.dir");
        projDir = projDir.replace("\\", "/");
        System.out.println(projDir);

        // creates new table from JSON config
        Table newTable = cr.parse(projDir + "/src/main/resources/config1.json");
        return newTable;
    }

    public static void main(String[] args) {
        launch(args);
    }
}



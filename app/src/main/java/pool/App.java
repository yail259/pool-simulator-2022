package pool;

import javafx.application.Application;
import javafx.stage.Stage;

import pool.table.Table;

public class App extends Application{
    @Override
    public void start(Stage primaryStage) {
        ConfigReader cr = new ConfigReader();

        String projDir = System.getProperty("user.dir");
        projDir = projDir.replace("\\", "/");
        System.out.println(projDir);

        Table newTable = cr.parse(projDir + "/src/main/resources/config.json");

        GameManager game = new GameManager(newTable);

        primaryStage.setTitle("Pool is cool");
        primaryStage.setScene(game.getGameScene());
        primaryStage.show();

        game.run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

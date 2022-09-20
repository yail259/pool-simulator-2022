package pool;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.event.*;

import pool.table.Table;
import pool.ball.Ball;

public class GameManager {
    private final GraphicsContext gc;
    private Table gameTable;
    private Scene gameScene;

    private Pane gamePane;

    public GameManager(Table gameTable) {
        this.gameTable = gameTable;

        Pane aPane = new Pane();
        this.gamePane = aPane;
        this.gameScene = new Scene(aPane, gameTable.getX(), gameTable.getY());
        gameScene.setFill(Color.GREEN);

        Canvas gameCanvas = new Canvas(gameTable.getX(), gameTable.getY());
        this.gc = gameCanvas.getGraphicsContext2D();

        aPane.getChildren().add(gameCanvas);
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public void run() {
        Timeline gameTime = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));
        gameTime.setCycleCount(Timeline.INDEFINITE);
        gameTime.play();
    }

    private void draw() {
        // increments the game at 60fps and clears the table to redraw
        gameTable.tick();
        gc.clearRect(0, 0, gameTable.getX(), gameTable.getY());

        boolean tableAtRest = true;
        Ball cueBall = null;

        for (Ball aBall: gameTable.getBalls()) {
            if (aBall.getVelocity().magnitude() != 0) {
                tableAtRest = false;
            }

            if (aBall.getColour().equals("white")) {
                cueBall = aBall;
            }
        }

        for (Ball aBall: gameTable.getBalls()) {
            gc.setFill(Color.BLACK);
            gc.fillOval(aBall.getPosition().getX() - aBall.getRadius(),
                    aBall.getPosition().getY() - aBall.getRadius(),
                    aBall.getRadius() * 2,
                    aBall.getRadius() * 2);
        }

        Circle circle = new Circle();
        circle.setFill(Color.WHITE);

        circle.setCenterX(cueBall.getPosition().getX());
        circle.setCenterX(cueBall.getPosition().getY());
        circle.setRadius(cueBall.getRadius());

        if (tableAtRest) {
            EventHandler<MouseEvent> eventHandler = new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println("Hello World");


                    circle.setFill(Color.DARKSLATEBLUE);
                }
            };

            circle.addEventFilter(MouseEvent.MOUSE_CLICKED, eventHandler);

            this.gamePane.getChildren().add(circle);
        }

    }
}

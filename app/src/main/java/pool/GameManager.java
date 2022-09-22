package pool;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.event.*;

import pool.hole.Hole;
import pool.table.Table;
import pool.ball.Ball;

public class GameManager {
    // determines how sensitive the stick input is
    public static final double INPUT_SENSITIVITY = 0.1;
    private final GraphicsContext gc;
    private Table gameTable;
    private Scene gameScene;
    private Pane gamePane;
    private Circle cueNode;

    public GameManager(Table gameTable) {
        this.gameTable = gameTable;

        Pane aPane = new Pane();
        this.gamePane = aPane;
        this.gameScene = new Scene(aPane, gameTable.getX(), gameTable.getY());

        Canvas gameCanvas = new Canvas(gameTable.getX(), gameTable.getY());
        this.gc = gameCanvas.getGraphicsContext2D();

        aPane.getChildren().add(gameCanvas);
    }

    public Scene getGameScene() {
        return gameScene;
    }

    public void run() {
        // set up the cue ball node to take in input
        initialiseCueBall();

        // run the game at 60 fps, incrementing it and drawing it at every frame. This is set to run indefinitely.
        Timeline gameTime = new Timeline(new KeyFrame(Duration.millis(17),
                t -> this.draw()));
        gameTime.setCycleCount(Timeline.INDEFINITE);
        gameTime.play();
    }

    private void initialiseCueBall() {
        // create a new circle node to accept input
        Circle circle = new Circle();
        circle.setFill(Color.WHITE);
        this.cueNode = circle;
        // update the position of the node to reflect the cueBall position
        updateCueNode();

        // add line node to represent stick, this shows user the magnitude and direction of input
        Line stick = new Line();
        stick.setFill(Color.ALICEBLUE);

        // this eventhandler is responsible for handling the drag event by displaying the stick to show the magnitude
        EventHandler<MouseEvent> dragHit = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                stick.setVisible(true);

                // draw stick to reflect inout magnitude and direction
                stick.setStartX(circle.getCenterX());
                stick.setStartY(circle.getCenterY());

                stick.setEndX(e.getX());
                stick.setEndY(e.getY());

                circle.setFill(Color.DARKSLATEBLUE);
            }
        };

        // this eventhandler is responsible for dealing with the logic of applying the moment to the ball and setting
        // stick to be invisible
        Ball cueBall = this.gameTable.getCueBall();
        EventHandler<MouseEvent> release = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                // hide the stick which cues the drag magnitude
                stick.setVisible(false);

                Point2D newHitVel = new Point2D(circle.getCenterX() - e.getX(), circle.getCenterY() - e.getY());
                newHitVel = newHitVel.multiply(INPUT_SENSITIVITY);

                // apply new moment to ball
                cueBall.setVelocity(newHitVel);
                circle.setFill(Color.WHITE);
            }
        };

        // the events are attached to the circle node
        circle.addEventFilter(MouseEvent.MOUSE_DRAGGED, dragHit);
        circle.addEventFilter(MouseEvent.MOUSE_RELEASED, release);

        this.gamePane.getChildren().add(circle);
        this.gamePane.getChildren().add(stick);
    }

    private void updateCueNode() {
        Ball cueBall = this.gameTable.getCueBall();

        // update the position of the circle node to accurately reflect the position of the cue ball
        this.cueNode.setCenterX(cueBall.getPosition().getX());
        this.cueNode.setCenterY(cueBall.getPosition().getY());
        this.cueNode.setRadius(cueBall.getRadius());
    }

    private void draw() {

        // if the game is won, show the win screen and stop running the game.
        if (gameTable.isHasWon()) {
            gc.setFill(gameTable.getPaintColour().invert());
            gc.fillRect(0, 0, gameTable.getX(), gameTable.getY());

            gc.setFill(gameTable.getPaintColour());
            gc.setFont(Font.font("Consolas", 50));
            gc.fillText("YOU WIN!!!", gameTable.getX() / 2, gameTable.getY() / 2);
            return;
        }

        // increments the game at 60fps and clears the table to redraw
        gameTable.tick();
        // circle is a separate node that accepts input, this is draw as well as the canvas oval
        updateCueNode();

        gc.setFill(gameTable.getPaintColour());
        gc.fillRect(0, 0, gameTable.getX(), gameTable.getY());

        gc.setFill(gameTable.getPaintColour().invert());
        gc.setFont(Font.font("Consolas", 20));
        gc.fillText(gameTable.getTickCountSeconds() + "S", gameTable.getX() / 2, gameTable.getY() / 2);

        boolean tableAtRest = this.gameTable.isTableAtRest();

        // player can't make input until table is completely at rest
        if (tableAtRest) {
            gamePane.setDisable(false);
        } else {
            gamePane.setDisable(true);
        }

        // draw the holes of the table
        for (Hole aHole: gameTable.getHoles()) {
            gc.setFill(aHole.getPaintColour());
            gc.fillOval(aHole.getPosition().getX() - aHole.getRadius(),
                    aHole.getPosition().getY() - aHole.getRadius(),
                    aHole.getRadius() * 2,
                    aHole.getRadius() * 2);
        }

        // draw the updated coordinates of the balls as ovals on the canvas
        for (Ball aBall: gameTable.getBalls()) {
            gc.setFill(aBall.getPaintColour());
            gc.fillOval(aBall.getPosition().getX() - aBall.getRadius(),
                    aBall.getPosition().getY() - aBall.getRadius(),
                    aBall.getRadius() * 2,
                    aBall.getRadius() * 2);
        }
    }
}

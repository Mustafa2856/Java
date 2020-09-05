/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package astar;
 

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class Astar extends Application {

	@Override
	public void start(Stage primaryStage) {
            Rectangle rect = new Rectangle();
            rect.setX(100);
            rect.setY(100);
            rect.setWidth(400);
            rect.setHeight(300);
            rect.setFill(Color.BLUE);
            Canvas drawer = new Canvas();
            drawer.setLayoutX(0);
            drawer.setLayoutY(0);
            drawer.setWidth(400);
            drawer.setHeight(300);
            Image image= new Image("dirt.png");
            drawer.getGraphicsContext2D().drawImage(image, 0,0,image.getWidth(),image.getHeight(),100,100,200,200);
            drawer.getGraphicsContext2D().
            GridPane root = new GridPane();
            root.add(drawer, 0, 0);
            Scene scene=new Scene(root,600,400);      
            primaryStage.setTitle("First JavaFX Application");  
            primaryStage.setScene(scene);  
            primaryStage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}
}

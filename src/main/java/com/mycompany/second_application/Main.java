/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.second_application;

/**
 *
 * @author alqarnia
 */
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

  @Override
  public void start(Stage primaryStage) {
    // javafx.util.Duration
    PauseTransition timer = new PauseTransition(Duration.seconds(5));
    // timer.setOnFinished(e -> /* play sound */);

    Button startBtn = new Button("Start");
    startBtn.setOnAction(e -> timer.play());

    Button pauseBtn = new Button("Pause");
    pauseBtn.setOnAction(e -> timer.pause());

    Button resetBtn = new Button("Reset");
    resetBtn.setOnAction(e -> timer.stop());

    Label label = new Label();
    label.setFont(Font.font("Monospaced", 20));
    label.textProperty().bind(timeLeftAsString(timer));
   
    
    HBox hbox = new HBox(10, startBtn, pauseBtn, resetBtn);
    hbox.setAlignment(Pos.CENTER);

    VBox root = new VBox(25, label, hbox);
    root.setPadding(new Insets(25));
    root.setAlignment(Pos.CENTER);

    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  private StringBinding timeLeftAsString(Animation animation) {
    return Bindings.createStringBinding(
        () -> {
          double currentTime = animation.getCurrentTime().toMillis();
          double totalTime = animation.getCycleDuration().toMillis();
          long remainingTime = Math.round(totalTime - currentTime);
          // java.time.Duration
          java.time.Duration dur = java.time.Duration.ofMillis(remainingTime);
          return String.format(
              "%02d:%03d", dur.toSecondsPart(), dur.toMillisPart());
        },
        animation.currentTimeProperty(),
        animation.cycleDurationProperty());
  }
   public static void main(String[] args) {
        launch();
    }
}
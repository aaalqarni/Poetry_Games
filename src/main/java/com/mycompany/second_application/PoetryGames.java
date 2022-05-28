/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.second_application;
import java.io.FileInputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;


/**
 *
 * @author abdulrahman
 */
public class PoetryGames extends Application {
    public static int numOfFiles=12;
    public static int maxLines=200;
    
    public String  user_name="عالي"; 
    public int Score=0;

    public int getMy_level() {
        return my_level-1;
    }

    public void setMy_level(int my_level) {
        this.my_level = my_level;
    }
    
    int my_level=1;

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }

    

    public void setScore(int Score) {
        this.Score = Score;
    }
    
    public int getScore() {
        return Score;
    }
    

    public int get_index(String text)
    {
        int index=1;
        System.out.println("Text"+text+"Text");
        switch (text) {
            case "أطفال":
                index=1;
                break;
            case "مبتدئ":
                index=2;
                break;
            case "متقدم":
                index=3;
                break;
            default:
                break;
        }
        
        return index;
    }
  
    

    
    public void start(Stage primaryStage) throws Exception{
        
        String level="";
        BorderStroke borderStroke = new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, null,
                new BorderWidths(2));
        Border border=new Border(borderStroke);
                        
        //primaryStage.setOnCloseRequest(event -> event.consume());
        primaryStage.setTitle("ألعاب شعرية");
        GridPane grid = new GridPane();
        
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5);
        grid.setVgap(5);
        
        Label label1 = new Label("اللاعب:");
        label1.setId("player_name");
        Text textField = new Text(getUser_name());
        textField.setId("player_name_bold");
        
        Label labe_score = new Label("النقاط:");
        labe_score.setId("player_name");
        
        Text textScore;
        textScore = new Text(String.valueOf(getScore()));
        textScore.setId("player_name_bold");
        
        HBox hb = new HBox();
       
        
        
        
        HBox hb_socre = new HBox();
        //hb_socre.getChildren().addAll(textScore,labe_score);
        hb_socre.setSpacing(15);
        //hb_socre.setAlignment(Pos.BASELINE_RIGHT);
        
        
       Button exit_game = new Button("خروج");  
       exit_game.setId("return_button");
       Image imageDecline = new Image(getClass().getResourceAsStream("/logout.png"));
       
       //Button button5 = new Button();
       ImageView imageView = new ImageView(imageDecline);
       imageView.setFitWidth(20);

       imageView.setFitHeight(20);
       exit_game.setGraphic(imageView); 
       
       hb.getChildren().add(exit_game);
        
        hb.getChildren().addAll(textScore,labe_score);
        hb.setSpacing(10);
        hb.getChildren().addAll(textField,label1);
        
        hb.setSpacing(15);
       
       
       hb.setAlignment(Pos.BASELINE_RIGHT);
       //HBox exit_button = new HBox();
       //exit_button.getChildren().add(exit_game);
       //exit_button.setSpacing(10);
       //exit_button.setAlignment(Pos.BASELINE_RIGHT);
       
        
        
        Text scenetitle = new Text("اختر لعبة ");
        scenetitle.setId("welcome-text");

        System.out.println("New Player "+getUser_name() +" End");
        
        Button b1 = new Button("لعبة ترتيب الشطر الثاني");
        Button b2 = new Button("لعبة ترتيب كلمة القافية");
        Button b3 = new Button("لعبة تحدي الوقت"); 
        Button b4 = new Button("لعبة البيت السليم"); 
        Button b5 = new Button("لعبة ترتيب بيتين من قصيدتين"); 
        Button b6 = new Button("لعبة الشطر السليم والمبعثر"); 
        
        b1.setId("select-button");b2.setId("select-button");b3.setId("select-button");
        b4.setId("select-button");b5.setId("select-button");b6.setId("select-button");
        
       
        //---------------------------------------------------------------------
       // Create a tile pane
        TilePane pane = new TilePane();
        //Create a label
        Label description_label =
                         new Label("تحديد المستوى");
        description_label.setId("player_name");
        // levels
        String levels[] =
                   { "أطفال","مبتدئ", "متقدم"};
        // Create a combo box
        ComboBox combo_box =
                    new ComboBox(FXCollections
                              .observableArrayList(levels));
        
        combo_box.getSelectionModel().select(getMy_level());
        
        // Label to display the selected menuitem
        Label selected = new Label("تحديد المستوى");
        
        // Create a tile pane
        
         //HBox level_box = new HBox();
         //level_box.getChildren().addAll(combo_box,description_label);
         //level_box.setSpacing(20);
         
        
        TilePane tile_pane = new TilePane(combo_box,description_label);
        tile_pane.setAlignment(Pos.CENTER);
        //----------------------------------------------------------------------
        VBox vbBtnLevel = new VBox(10); vbBtnLevel.setAlignment(Pos.CENTER);  
        
        //vbBtnLevel.setAlignment(Pos.CENTER);
        vbBtnLevel.getChildren().add(tile_pane);
        vbBtnLevel.setBorder(border);
        
        VBox vbBtn1 = new VBox(1); vbBtn1.setAlignment(Pos.CENTER); 
        vbBtn1.setBorder(border);
        VBox vbBtn2 = new VBox(20); vbBtn2.setAlignment(Pos.CENTER);
        //vbBtn2.setStyle("-fx-background-color: #996633;");
        vbBtn2.setBorder(border);
        
        vbBtn2.getChildren().add(hb); 
        vbBtn2.getChildren().add(hb_socre); 
        
        //vbBtn2.getChildren().add(exit_button);
        
        vbBtn1.getChildren().add(scenetitle); 
        vbBtn1.getChildren().add(b1); vbBtn1.getChildren().add(b2);
        vbBtn1.getChildren().add(b3); vbBtn1.getChildren().add(b4);
        vbBtn1.getChildren().add(b5);  vbBtn1.getChildren().add(b6);

        b1.setMinWidth(330);
        b2.setMinWidth(330);
        b3.setMinWidth(330);
        b4.setMinWidth(330);
        b5.setMinWidth(330);
        b6.setMinWidth(330);
        
        grid.add(vbBtnLevel,0,0);
        grid.add(vbBtn1, 0, 1);
        grid.add(vbBtn2,0,2);
        
        
         
        //-------------------------------------
        
        ////////////////////
        FileInputStream inputstream = new FileInputStream("./resources/images/back3.jpg"); 
        Image img = new Image(inputstream);
        BackgroundImage bImg = new BackgroundImage(img,
                                                   BackgroundRepeat.REPEAT,
                                                   BackgroundRepeat.REPEAT,
                                                   BackgroundPosition.DEFAULT,
                                                   BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        grid.setBackground(bGround);
        ////////////////////
        //-------------------------------------
        
        BorderPane root = new BorderPane();
        root.setCenter(grid);
                
        root.setStyle("-fx-padding: 25;");
        root.setId("border-pane");
        
        Scene scene = new Scene(root, 500, 700);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(PoetryGames.class.getResource("/PoetryGames.css").toExternalForm());        
        primaryStage.show();
        primaryStage.setResizable(false);
        Stage SeconderyStage = new Stage();
        
        exit_game.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                main_interface game = new main_interface ();
                try {
                    
                       primaryStage.hide();
                       //game.setUser_name(user_name); 
                       game.start(SeconderyStage); 
                } catch (Exception ee) {
                    //
                }
            }
        });
 
        
        
                
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                OrderSecondHalf game = new OrderSecondHalf ();
                try {
                    
                       primaryStage.hide();
                       game.setLevel(get_index(combo_box.getValue().toString()));
                       game.setTotal_Score(Score);
                       game.setUser_name(user_name); 
                       game.start(SeconderyStage); 
                } catch (Exception ee) {
                    //
                }
            }
        });
        
                
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                OrderLastWord game = new OrderLastWord();
                try {
                        primaryStage.hide();
                        
                        System.out.println(get_index(selected.getText()));
                        game.setLevel(get_index(combo_box.getValue().toString()));
                        game.setTotal_Score(Score);
                        game.setUser_name(user_name);
                        game.start(SeconderyStage); 
                } catch (Exception ee) {
                    //
                }
            }
        });
        
        
        b3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                TimeChallenge game = new TimeChallenge();
                try {
                     primaryStage.hide();
                     game.setLevel(get_index(combo_box.getValue().toString()));
                     game.setTotal_Score(Score);
                     game.setUser_name(user_name);
                     game.start(SeconderyStage); 
                } catch (Exception ee) {
                    //
                }
            }
        });
        
        b4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                ValidVerse game = new ValidVerse();
                try {
                     primaryStage.hide();
                     game.setLevel(get_index(combo_box.getValue().toString()));
                     game.setTotal_Score(Score);
                     
                     game.setUser_name(user_name);
                     game.start(SeconderyStage); 
                } catch (Exception ee) {
                    //
                }
            }
        });
        
        b5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                OrderTwoPoems game = new OrderTwoPoems();
                try {
                     primaryStage.hide();
                     game.setLevel(get_index(combo_box.getValue().toString()));
                     game.setTotal_Score(Score);
                     
                     game.setUser_name(user_name);
                     game.start(SeconderyStage);
                   
                } catch (Exception ee) {
                    //
                }
            }
        });
        
        b6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                OrderSingleVerse game = new OrderSingleVerse();
                try {
                     primaryStage.hide();
                     game.setLevel(get_index(combo_box.getValue().toString()));
                     game.setTotal_Score(Score);
                     
                     game.setUser_name(user_name);
                     game.start(SeconderyStage);
                } catch (Exception ee) {
                    //
                }
            }
        });
        
        // Create action event
        EventHandler<ActionEvent> event =
                  new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println(combo_box.getValue());
                
            }
        };
 
        // Set on action
        combo_box.setOnAction(event);
    }
    
    
    
    public static void main(String[] args) {
        launch();
    }
}
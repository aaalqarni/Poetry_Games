/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.second_application;

 
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.util.List;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Application;  
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;  
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;  
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;  
import javafx.scene.control.TextField;  
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;  
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;  





public class main_interface extends Application {  
    
    
    
    
    public class Foo {

    @CsvBindByName(column = "Game", required = true)
    private String first;

    @CsvBindByName(column = "Score", required = true)
    private String second;

    @CsvBindByName(column = "time", required = true)
    private String third;
    }
    
    public List<Foo> readCsvFromPath(final String filePath) throws IOException {
        try (Reader reader = Files.newBufferedReader(Paths.get(filePath));) {
            CsvToBean<Foo> csvToBean = new CsvToBeanBuilder<Foo>(reader)
                    .withSeparator(',')
                    .withType(Foo.class)
                    .build();
            return csvToBean.parse();
        }
    }
    
    
    
     List<String[]> list;
    
    private static List<String[]> createCsvDataSimple() {
        String[] header = {"Game", "score"};
        String[] record1 = {"1","0"};
        String[] record2 = {"2","0"};
        String[] record3 = {"3","0"};
        String[] record4 = {"4","0"};
        String[] record5 = {"5","0"};
        String[] record6 = {"6","0"};
        
        List<String[]> list = new ArrayList<>();
        list.add(header);
        list.add(record1);
        list.add(record2);
        list.add(record3);
        list.add(record4);
        list.add(record5);
        list.add(record6);
                

        return list;
    }

    public main_interface() {
        this.list = null;
    }
    
    public List<String[]> oneByOne(Reader reader) throws Exception {
    List<String[]> list = new ArrayList<>();
    CSVReader csvReader = new CSVReader(reader);
    String[] line;
    while ((line = csvReader.readNext()) != null) {
        System.out.println(Arrays.toString(line));
        list.add(line);
    }
    reader.close();
    csvReader.close();
    return list;
    }
    
   
    
    
  
      
public static void main(String[] args) {  
launch(args);     
}  
  
@Override  
public void start(Stage primaryStage) throws Exception {  
    // TODO Auto-generated method stub  
    
    primaryStage.setTitle("ألعاب شعرية");
    primaryStage.setResizable(false);
    
    
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    Text scenetitle = new Text("مرحبا بك في الألعاب الشعرية");
    scenetitle.setId("welcome-text-2");
    
    //ٍString name="  "+"إبدأ"+"  ";
    Button submit = new Button("  "+"إبدأ"+"  ");  
    submit.setId("start-button");
    
    //grid.addRow(0,  tf1,user_id);  
    //grid.addRow(2, b);  
    
    Label label1 = new Label("اسم اللاعب");
    label1.setId("player_name");
    TextField textField = new TextField ();
    HBox hb = new HBox();
    hb.getChildren().addAll(textField,label1);
    hb.setSpacing(10);
    
    VBox vbBtn1 = new VBox(1); vbBtn1.setAlignment(Pos.CENTER); 
    vbBtn1.getChildren().add(scenetitle); 
    vbBtn1.getChildren().addAll(hb); 
    vbBtn1.getChildren().add(submit);
    
    grid.add(vbBtn1, 0, 1);
    
    
    //-----------------------------------
    //Scene scene = new Scene(grid, 300, 200);
    
     //////////
    BorderPane root = new BorderPane();
    
    // Store the Input Area in the Center Region 
    root.setCenter(grid);
        ///////
        // Set the alignment of the Header Label to bottom center
    BorderPane.setAlignment(scenetitle,Pos.CENTER);
    // Set the alignment of the Input Label to center left
        
        
    root.setStyle("-fx-padding: 25;");
    root.setId("border-pane");
    ///////////

    //////BackgroundImage//////////////
    //System.out.println("------"+main_interface.class.getResource("/back3.jpg")+"------------");
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
        
    root.setStyle("-fx-padding: 25;");
    root.setId("border-pane");    
        
    Scene scene = new Scene(root,300, 300);
    primaryStage.setScene(scene);
    scene.getStylesheets().add(main_interface.class.getResource("/PoetryGames.css").toExternalForm()); 
    
    
    primaryStage.show();
    
     
        
    //--------------------------------------------------------------------------
    //buttons
    
    
    //-------------------------------------------------------------------------
    
    //buttons
    
    Stage SeconderyStage = new Stage();
    Alert a = new Alert(AlertType.NONE);
    
    submit.setOnAction(new EventHandler<ActionEvent>() {

    @Override
        public void handle(ActionEvent e) {
            if ((textField.getText() != null && !textField.getText().isEmpty()))
            {
               
                 List<String[]> csvData = createCsvDataSimple();

                 // default all fields are enclosed in double quotes
                 // default separator is a comma
                String file_name=textField.getText();
                File user_file=new File(file_name+".csv");
                if (!user_file.exists())
                {
                    try (CSVWriter writer = new CSVWriter(new FileWriter(user_file))) {
                    writer.writeAll(csvData);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    PoetryGames game= new PoetryGames();
                    game.setUser_name(file_name);
                   
                     try {
                         primaryStage.hide();
                         game.start(SeconderyStage);
                         
                         //
                     } catch (Exception ex) {
                         ex.printStackTrace();
                     }
                }
                else
                {
                    int Score=0;
                    FileReader read_file = null;
                     try {
                         read_file = new FileReader(user_file);
                     } catch (FileNotFoundException ex) {
                         ex.printStackTrace();
                     }
                     try {
                          List<String[]> List = oneByOne(read_file);
                          int counter=0;
                          for (String[] strings : List)
                          {
                              
                            if (counter!=0){  
                             String temp_text=Arrays.toString(strings);
                             String[] array = temp_text.split(",");
                             array[1] = array[1].replaceAll("[\\[\\](){}]","");
                             System.out.print("----"+array[1]);
                             int temp_score = Integer.parseInt(array[1].trim());
                             Score=Score+temp_score;
                            }
                            counter++;
                          }
                          //readCsvFromPath(file_name+".csv");
                          
                     } catch (Exception ex) {
                         ex.printStackTrace();
                     }
                    PoetryGames game= new PoetryGames();
                    game.setUser_name(file_name);
                    game.setScore(Score);
                     try {
                         primaryStage.hide();
                         game.start(SeconderyStage);
                     } catch (Exception ex) {
                         ex.printStackTrace();
                     }
                }
            } else {
                    a.setAlertType(AlertType.WARNING);
                    // set content text
                    a.setContentText("الرجاء إدخال إسم اللاعب");
                    // show the dialog
                    a.show();
                 
            }
         }
     });
    
}  
}  
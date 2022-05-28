package com.mycompany.second_application;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class TimeChallenge extends Application {
    
   PauseTransition timer = new PauseTransition(Duration.seconds(5)); 
   Label Timerlabel = new Label();
   int game_id=3;
   public String  user_name="عالي"; 
   public int Score=0;
   private int level=1;
   Button b1, b2, b3;

   
   int temp_totalScore=0;
   
    public int getTotal_Score() {
        return Total_Score;
    }

    public void setTotal_Score(int Total_Score) {
        this.Total_Score = Total_Score;
    }
   public int Total_Score=0;
    
   

   
   
   public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }
    
    //Read user file info
    //--------------------------------------------------------------------------
    private StringBinding timeLeftAsString(Animation animation) {
    return Bindings.createStringBinding(
        () -> {
          double currentTime = animation.getCurrentTime().toMillis();
          double totalTime = animation.getCycleDuration().toMillis();
          long remainingTime = Math.round(totalTime - currentTime);
          // java.time.Duration
          java.time.Duration dur = java.time.Duration.ofMillis(remainingTime);
          return String.format(
              "%02d:%02d:%03d", dur.toMinutes(), dur.toSecondsPart(), dur.toMillisPart());
        },
        animation.currentTimeProperty(),
        animation.cycleDurationProperty());
  }
    
    //-------------------------------------------------------------------------
     public String get_Level(int level)
    {
        String my_level="أطفال";
        
        switch (level) {
            case 1:
                    my_level="أطفال";
                break;
            case 2:
                my_level="مبتدئ";
                break;
            case 3:
                my_level="متقدم";
                break;
            default:
                break;
        }
        
        return my_level;
    }
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    
    
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
    //------------------------------------------------------------------------
    
    public void readfile(String username,int score,int game_id)throws Exception
    {
        String file_name=username;
        File user_file=new File(file_name+".csv");
        FileReader read_file = null;
        read_file = new FileReader(user_file);
        List<String[]> List = oneByOne(read_file);
        
       int counter=0;
       List<String[]> list = new ArrayList<>();
       String[] record1={"",""};
       for (String[] strings : List)
       {
            
            int total=0;
            if (counter==game_id)
            {  
                
               
                String temp_text=Arrays.toString(strings);
                String[] array = temp_text.split(",");
                array[1] = array[1].replaceAll("[\\[\\](){}]","");
                System.out.println("----"+Integer.parseInt(array[1].trim()+score)+"-----------------------");
                int temp_score = Integer.parseInt(array[1].trim());
                total=score+temp_score;
                record1[0]=Integer.toString(game_id);
                record1[1]=Integer.toString(total);
                list.add(record1);
            }
            else{
            list.add(strings);
            }
            counter++;
       }
       
        for (String[] strings : list)
        {
            System.out.println(Arrays.toString(strings));               
        
        }
         
        //File user_file=new File(file_name+".csv"); 
        try (CSVWriter writer = new CSVWriter(new FileWriter(user_file))) 
        {
           writer.writeAll(list);
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        
        
        
    }
    
    public int compute_total_score(String user_name) throws Exception
    {
        int Score=0;
        File user_file=new File(user_name+".csv");
        FileReader read_file = null;
        read_file = new FileReader(user_file);
        List<String[]> List = oneByOne(read_file);
        int counter=0;
        for (String[] strings : List)
        {
            if (counter!=0)
            {  
                String temp_text=Arrays.toString(strings);
                String[] array = temp_text.split(",");
                array[1] = array[1].replaceAll("[\\[\\](){}]","");
                System.out.println("----"+array[1]);
                int temp_score = Integer.parseInt(array[1].trim());
                Score=Score+temp_score;
                System.out.println(Score);
            }
            counter++;
        }
        
        return Score;
        
    }
    
    //------------------------------------------------------------------------
    
    int abyatLength=6;
    int currentBayt=0;
    String[] poem=null;
    String[] buzzleText=null;
    int [] buzzleIndex=null;
    String answerWord=null;
    Random rand = new Random();
    int score=0;
    
    private boolean checkAnswer(String s){
        if (! s.equals(answerWord))
                return(false);
        
        return(true);
    }   
    
    private void createBuzzle(){
        String word1=buzzleText[2-1].substring(buzzleText[2-1].lastIndexOf(" ")).trim();
        String word2=buzzleText[4-1].substring(buzzleText[4-1].lastIndexOf(" ")).trim();
        String word3=buzzleText[6-1].substring(buzzleText[6-1].lastIndexOf(" ")).trim();
        answerWord=word1;
        
        b1.setText(word3); 
        b2.setText(word2); 
        b3.setText(word1); 
        
        ///////////////////////////////
        buzzleIndex = new int[abyatLength];
        for (int i=0; i<abyatLength;i++){
           buzzleIndex[i]=i;
        }
        
        String temp;
        for (int ii=0; ii<3;ii++){
            int r = rand.nextInt(3);
            switch(r) {
                case 0:
                    temp=b1.getText(); b1.setText(b2.getText());b2.setText(temp);
                    break;
                case 1:
                    temp=b1.getText(); b1.setText(b3.getText());b3.setText(temp);
                    break;
                case 2:
                    temp=b3.getText(); b3.setText(b2.getText());b2.setText(temp);
                    break;    
            }

        }
    }
    
    private void readAbyat() throws Exception{
        buzzleText = new String[abyatLength];
	for (int i=0;i<abyatLength;i++){
            if ((poem !=null)&&(currentBayt<poem.length)&&(poem[currentBayt] != null)){
		buzzleText[i]= poem[currentBayt].trim();
		currentBayt++;
            } else {
		poem = readRandomPoem(getLevel());
		currentBayt=0;
		i=-1;
            }
	}
    }
    
    private String[] readRandomPoem(int my_level)throws Exception {
        String s;
        String [] poem = new String[PoetryGames.maxLines];
        FileInputStream f;
        BufferedReader br;

        
        System.out.println(my_level);
        
        File folder = new File("./resources/poets/content/kidslevel");
        File[] listOfFiles = folder.listFiles();
        if (my_level==2)
        {
             folder = new File("./resources/poets/content/startinglevel");
             listOfFiles = folder.listFiles();
        }
        else if(my_level==3)
        {
            folder = new File("./resources/poets/content/expertlevel");
            listOfFiles = folder.listFiles();
            
        }
        
        
        
        
        Random rand = new Random();

        File file = listOfFiles[rand.nextInt(listOfFiles.length)];
        
        
        f =new FileInputStream (file);
        br = new BufferedReader(new InputStreamReader(f,"UTF-8"));
        // read file line by line
        int i = 0;
        while (((s=br.readLine()) != null) && (i<PoetryGames.maxLines))  {
            s =s.trim();
            poem[i]=s;
            i++;
        }
	
	
        f.close();
	br.close();
		
    return (poem);
    
}
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        
        readAbyat();
        PauseTransition timer = new PauseTransition(Duration.seconds(10));
        timer.playFromStart();
        //createBuzzle();
        temp_totalScore=getTotal_Score();
        //----------------------------------------------------------------------
        Stage popup = new Stage();
        
        BorderStroke borderStroke = new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, null,
                new BorderWidths(2));
        Border border=new Border(borderStroke);
        //---------------------------------------------------------------------
        popup.setTitle("إنتباه!");
        
        Text errortitle = new Text("اجابة خاطئة");
        errortitle.setStyle("-fx-font-size: 30px;" +
                            "  -fx-font-family: \"Sakkal Majalla\";\n" +
                            "  -fx-fill:red;\n" +
                            "  -fx-font-weight: bold;\n" +
                            "  -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );  ");
        BorderPane error_root = new BorderPane();
        
        
        ///////
        // Set the alignment of the Header Label to bottom center
        
        // Set the alignment of the Input Label to center left
       
        
        error_root.setStyle("-fx-padding: 25;");
        error_root.setId("border-pane");
        
        popup.initStyle(StageStyle.UTILITY);
        //Scene scene = new Scene(new Group(new Text(25, 25, "Hello World!")));
        VBox errorBox = new VBox(10);
        errorBox.setBorder(border);
        errorBox.setAlignment(Pos.CENTER);
        errorBox.getChildren().add(errortitle);
        error_root.setCenter(errorBox);
        
      
        //----------------------------------------------------------------------
        Stage Timepopup = new Stage();
        
        BorderStroke borderStrokeTime = new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, null,
                new BorderWidths(2));
        Border borderTime=new Border(borderStroke);
        //---------------------------------------------------------------------
        popup.setTitle("إنتباه!");
        
        Text Timetitle = new Text("أنتهى الوقت !");
        Timetitle.setStyle("-fx-font-size: 30px;" +
                            "  -fx-font-family: \"Sakkal Majalla\";\n" +
                            "  -fx-fill:red;\n" +
                            "  -fx-font-weight: bold;\n" +
                            "  -fx-effect: dropshadow( gaussian , rgba(255,255,255,0.5) , 0,0,0,1 );  ");
        BorderPane Time_root = new BorderPane();
        
        
        ///////
        // Set the alignment of the Header Label to bottom center
        
        // Set the alignment of the Input Label to center left
       
        
        Time_root.setStyle("-fx-padding: 25;");
        Time_root.setId("border-pane");
        
        Timepopup.initStyle(StageStyle.UTILITY);
        //Scene scene = new Scene(new Group(new Text(25, 25, "Hello World!")));
        VBox TimeBox = new VBox(10);
        TimeBox.setBorder(border);
        TimeBox.setAlignment(Pos.CENTER);
        TimeBox.getChildren().add(Timetitle);
        Time_root.setCenter(TimeBox);
        
        //----------------------------------------------------------------------
        
     
        
       
        double width = primaryStage.getWidth() / 1.6;
        double height = primaryStage.getHeight() / 1.6;
        popup.setScene(new Scene(error_root,width,height));
        Timepopup.setScene(new Scene(Time_root,width,height));
        //----------------------------------------------------------------------
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        //----------------------------------------------------------------------
        primaryStage.setTitle("ألعاب شعرية");
        
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> event.consume());
        
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        //grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("لعبة تحدي الوقت");
        scenetitle.setId("welcome-text");
        //grid.add(scenetitle, 0, 0, 2, 1);

        Label l2 = new Label((buzzleText[2-1]).substring(0,(buzzleText[2-1].lastIndexOf(" ")))); grid.add(l2, 2, 1);
        Label l1 = new Label(buzzleText[1-1]); grid.add(l1, 4, 1);
        Label x1 = new Label("***"); grid.add(x1, 3, 1);
        
        b1 = new Button(""); //grid.add(b1, 1, 1);
        b2 = new Button(""); //grid.add(b1, 1, 1);
        b3 = new Button(""); //grid.add(b1, 1, 1);
        createBuzzle();
        
        HBox hbBtn1 = new HBox(1); hbBtn1.setAlignment(Pos.CENTER);
        
        hbBtn1.getChildren().add(new Text("["));
        hbBtn1.getChildren().add(b1); 
        hbBtn1.getChildren().add(new Text("|"));
        hbBtn1.getChildren().add(b2); 
        hbBtn1.getChildren().add(new Text("|"));
        hbBtn1.getChildren().add(b3);
        hbBtn1.getChildren().add(new Text("]"));
        b1.setId("word-button");
        b2.setId("word-button");
        b3.setId("word-button");
        
        grid.add(hbBtn1, 1, 1);
              
      //----------------------------------------------------------------------
       Button exit_game = new Button("عودة");  
       exit_game.setId("return_button");
       Image imageDecline = new Image(getClass().getResourceAsStream("/return.png"));
       
       //Button button5 = new Button();
       ImageView imageView = new ImageView(imageDecline);
       imageView.setFitWidth(20);

       imageView.setFitHeight(20);
       exit_game.setGraphic(imageView); 
       exit_game.setAlignment(Pos.TOP_LEFT);
       
       //-----------------------------------------------------------------------
       //----------------------------------------------------------------------
       
       //Button btn = new Button("     التالي     ");
       HBox hbBtn = new HBox(10);
       hbBtn.setAlignment(Pos.CENTER);
       hbBtn.getChildren().add(exit_game);
       hbBtn.setSpacing(20);
       //hbBtn.getChildren().add(btn);
     //--------------------------------------------------------------------
      
       
     
       
        
        
        //grid.add(hbBtn, 2, 4);
        final Text actiontarget = new Text();
        final Text actiontLevel = new Text();
        final Text actiontTotalScore = new Text();
        actiontTotalScore.setId("actiontarget");
        //grid.add(actiontarget, 3, 0);
        actiontarget.setId("actiontarget");
        actiontarget.setText("آختر آخر كلمة في البيت التالي:");
        
      actiontTotalScore.setText("مجموع النقاط "+" : "+getTotal_Score());
        
      actiontLevel.setText("المستوى "+" : "+get_Level(getLevel()));
        
       switch (getLevel()) {
           case 1:
               actiontLevel.setFill(Color.GREEN);
               break;
           case 2:
               actiontLevel.setFill(Color.ORANGE);
               break;
           case 3:
               actiontLevel.setFill(Color.RED);
               break;
           default:
               break;
       }
        //label.getStylesheets().add("sample/styles/send.css");
        actiontLevel.setId("Level");
        HBox hBox=new HBox(20);
        hBox.setStyle("-fx-padding:4;" +
                     " -fx-border-width: 2;" +
                     " -fx-border-color: black;" +
                     "-fx-border-radius: 4;" +
                     "-fx-background-color: f1f1f1;" +
                     "-fx-border-insets: 5;");
        hBox.getChildren().add(actiontLevel);
        
        
         //hBox.
        hBox.setSpacing(100);
        hBox.getChildren().add(actiontTotalScore);
        
        hBox.setAlignment(Pos.BASELINE_CENTER);
        
        
        Timerlabel.setId("Timerlabel");
        Timerlabel.textProperty()
        .bind(
            Bindings.createStringBinding(
                () -> {
                  Duration currentTime = timer.getCurrentTime();
                  Duration duration = timer.getDuration();
                  double timeRemaining = duration.subtract(currentTime).toSeconds();
                  return String.format(" الوقت المتبقي  :%04.1f ", timeRemaining);
                },
                timer.currentTimeProperty(),
                timer.durationProperty()));
        
        VBox vBox = new VBox(10);
        vBox.getChildren().add(hBox);
        vBox.setSpacing(10);
        
        vBox.setBorder(border);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(scenetitle);
        vBox.getChildren().add(actiontarget);
        vBox.getChildren().add(Timerlabel);
               
        VBox vBox_bottom = new VBox(20);
        //vBox_bottom.setBorder(border);
        vBox_bottom.setAlignment(Pos.CENTER);
        vBox_bottom.getChildren().add(hbBtn);
        //----------------------------------------------------------------------
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                 timer.stop();
                if (checkAnswer(b1.getText())){
                    score=score+10;
                    
                    temp_totalScore =temp_totalScore+10;
                    
                    actiontTotalScore.setText("مجموع النقاط "+" : "+temp_totalScore+"  ▲  ");
 
                    actiontTotalScore.setFill(Color.GREEN);
                } else {
                    actiontTotalScore.setText("مجموع النقاط "+" : "+temp_totalScore) ;

                    actiontTotalScore.setFill(Color.RED);
                                        
                    PauseTransition delay = new PauseTransition(Duration.millis(600));
                    delay.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            popup.hide();
                        }
                    });

                    popup.show();
                    delay.play();
                }
                
                try {
                    
                    timer.play();
                    readAbyat();
                    createBuzzle();
                    
                    l1.setText(buzzleText[1-1]);
                    l2.setText((buzzleText[2-1]).substring(0,(buzzleText[2-1].lastIndexOf(" "))));

                } catch(Exception ee) {
                    //
                }    
            }
        });
//----------------------------------------------------------------------
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                 timer.stop();
                if (checkAnswer(b2.getText())){
                    score=score+10;
                   
                    temp_totalScore =temp_totalScore+10;
                     
                    actiontTotalScore.setText("مجموع النقاط "+" : "+temp_totalScore+"  ▲  ");

                    actiontTotalScore.setFill(Color.GREEN);
                } else {
                    actiontTotalScore.setText("مجموع النقاط "+" : "+temp_totalScore) ;

                    actiontTotalScore.setFill(Color.RED);
                                        
                    PauseTransition delay = new PauseTransition(Duration.millis(600));
                    delay.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            popup.hide();
                        }
                    });

                    popup.show();
                    delay.play();
                }
                
                try {
                    timer.play();
                    readAbyat();
                    createBuzzle();
                    
                    l1.setText(buzzleText[1-1]);
                    l2.setText((buzzleText[2-1]).substring(0,(buzzleText[2-1].lastIndexOf(" "))));

                } catch(Exception ee) {
                    //
                }    
            }
        });//----------------------------------------------------------------------
        b3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                timer.stop();
                if (checkAnswer(b3.getText())){
                    score=score+10;
                    
                    temp_totalScore =temp_totalScore+10;
                    actiontTotalScore.setText("مجموع النقاط "+" : "+temp_totalScore+"  ▲  ");

                    actiontTotalScore.setFill(Color.GREEN);
                } else {
                    actiontTotalScore.setText("مجموع النقاط "+" : "+temp_totalScore) ;

                    actiontTotalScore.setFill(Color.RED);
                                        
                    PauseTransition delay = new PauseTransition(Duration.millis(600));
                    delay.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                            popup.hide();
                        }
                    });

                    popup.show();
                    delay.play();
                }
                
                try {
                    timer.play();
                    readAbyat();
                    createBuzzle();
                    
                    l1.setText(buzzleText[1-1]);
                    l2.setText((buzzleText[2-1]).substring(0,(buzzleText[2-1].lastIndexOf(" "))));

                } catch(Exception ee) {
                    //
                }    
            }
        });
        //----------------------------------------------------------------------
        
        timer.setOnFinished(
        e -> {
            
             
               PauseTransition delay = new PauseTransition(Duration.millis(600));
                    delay.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent e) {
                           Timepopup.hide();
                        }
                    });

                    Timepopup.show();
                    delay.play();
            
           
          try {
                //alert.hide();
                timer.play();
                readAbyat();
                createBuzzle();
                readAbyat();
                createBuzzle();
                actiontTotalScore.setText("مجموع النقاط "+" : "+temp_totalScore) ;
                actiontTotalScore.setFill(Color.RED);
                l1.setText(buzzleText[1-1]);
                l2.setText((buzzleText[2-1]).substring(0,(buzzleText[2-1].lastIndexOf(" "))));
                } catch(Exception ee) {
                    //
                }
          
        });
        
        //----------------------------------------------------------------------
        
        Stage SeconderyStage = new Stage();
        Alert a = new Alert(AlertType.NONE);
        exit_game.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
                timer.stop();
                
                PoetryGames game= new PoetryGames();
                game.setUser_name(user_name);   
                game.setMy_level(level);
                
                game.setUser_name(user_name);   
                if(score==0)
                {
                  primaryStage.hide();
                    try {
                        game.setUser_name(user_name);
                        game.setScore(compute_total_score(getUser_name()));
                        
                        primaryStage.hide();
                        game.start(SeconderyStage);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                else
                {
                    try {
                        System.out.println("------------------"+score+"---------------");
                        readfile(getUser_name(),score,game_id);
                        game.setScore(compute_total_score(getUser_name()));
                        game.setUser_name(user_name);
                        primaryStage.hide();
                        game.start(SeconderyStage);
                        
                        
                        
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                
                
            }
        });
        
        
       ///////////////////////////////////////////////////////////////////////// 
        
        //////BackgroundImage//////////////
        FileInputStream inputstream = new FileInputStream("./resources/images/back3.jpg"); 
        Image img = new Image(inputstream);
        BackgroundImage bImg = new BackgroundImage(img,
                                                   BackgroundRepeat.REPEAT,
                                                   BackgroundRepeat.REPEAT,
                                                   BackgroundPosition.DEFAULT,
                                                   BackgroundSize.DEFAULT);
        Background bGround = new Background(bImg);
        vBox.setBackground(bGround);
       
        ////////////////////////////////////////////////////////////////////////
        BorderPane root = new BorderPane();
        //root.getChildren().add(exit_game);
        // Store the Header Label in the Top Region 
        root.setTop(vBox);
        // Store the OK Button in the Top Region 
        //root.setRight(okBtn);
        // Store the Output Area in the Right Region 
        //root.getChildren().add(exit_Btn);
        root.setBottom(hbBtn);
        //root.setBottom(exit_Btn);
        // Store the Input Label in the Bottom Region 
        //root.setLeft(actiontarget);
        // Store the Input Area in the Center Region 
        root.setCenter(grid);
        ///////
        // Set the alignment of the Header Label to bottom center
        BorderPane.setAlignment(scenetitle,Pos.CENTER);
        // Set the alignment of the Input Label to center left
        BorderPane.setAlignment(hbBtn,Pos.CENTER);
        //BorderPane.setAlignment(exit_Btn,Pos.BOTTOM_RIGHT);
        
        
        root.setStyle("-fx-padding: 25;");
        root.setId("border-pane");
        ///////////

        Scene scene = new Scene(root, 1000, 400);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(TimeChallenge.class.getResource("/PoetryGames.css").toExternalForm());        
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch();
    }

    

}

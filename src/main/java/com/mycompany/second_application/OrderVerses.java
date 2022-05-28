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
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
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
public class OrderVerses extends Application {
    
    
    //--------------------------------------------------------------------------
    private Integer startTime=30;
    private Integer seconds=startTime;
    private Label label;
    //--------------------------------------------------------------------------
    
    
    int game_id=3;
    public String  user_name="عالي"; 
    public int Score=0;
    //--------------------------------------------------------------------------
    private int level=1;

   
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
   
    //--------------------------------------------------------------------------
   
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
   

   

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }
    
    
    //--------------------------------------------------------------------------
     //--------------------------------------------------------------------------
    //Read user file info
    
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
    
    //--------------------------------------------------------------------------
    //--------------------------------------------------------------------------
    int abyatLength=6;
    int currentBayt=0;
    String[] poem=null;
    String[] buzzleText=null;
    int [] buzzleIndex=null;
    Random rand = new Random();
    int score=0;
    
    private boolean checkAnswer(){
        for (int i=0;i<abyatLength;i++){
            
            if (buzzleIndex[i] !=i)
                return(false);
        }
        
        return(true);
    }   
    
    private void createBuzzle(){
       buzzleIndex = new int[abyatLength];
        for (int i=0; i<abyatLength;i++){
           buzzleIndex[i]=i;
        }
        
        String s;
        int j;
        for (int ii=0; ii<3;ii++){
            int r = rand.nextInt(3);
            switch(r) {
                case 0:
                    s=buzzleText[1];buzzleText[1]=buzzleText[3];buzzleText[3]=s;
                    j=buzzleIndex[1];buzzleIndex[1]=buzzleIndex[3];buzzleIndex[3]=j;
                    
                    s=buzzleText[0];buzzleText[0]=buzzleText[2];buzzleText[2]=s;
                    j=buzzleIndex[0];buzzleIndex[0]=buzzleIndex[2];buzzleIndex[2]=j;
                    break;
                case 1:
                    s=buzzleText[3];buzzleText[3]=buzzleText[5];buzzleText[5]=s;
                    j=buzzleIndex[3];buzzleIndex[3]=buzzleIndex[5];buzzleIndex[5]=j;
                    
                    s=buzzleText[2];buzzleText[2]=buzzleText[4];buzzleText[4]=s;
                    j=buzzleIndex[2];buzzleIndex[2]=buzzleIndex[4];buzzleIndex[4]=j;
                    break;
                case 2:
                    s=buzzleText[1];buzzleText[1]=buzzleText[5];buzzleText[5]=s;
                    j=buzzleIndex[1];buzzleIndex[1]=buzzleIndex[5];buzzleIndex[5]=j;
                    
                    s=buzzleText[0];buzzleText[0]=buzzleText[4];buzzleText[4]=s;
                    j=buzzleIndex[0];buzzleIndex[0]=buzzleIndex[4];buzzleIndex[4]=j;
                    break;    
            }

        }
    }
    
    private void readAbyat() throws Exception{
        buzzleText = new String[abyatLength];
	for (int i=0;i<abyatLength;i++){
            if ((poem !=null)&&(currentBayt<poem.length)&&(poem[currentBayt] != null)){
		buzzleText[i]= poem[currentBayt];
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

        
        
       
        
        File folder = new File("./resouces/poets/content/kidslevel");
        File[] listOfFiles = folder.listFiles();
        if (my_level==2)
        {
             folder = new File("./resouces/poets/content/startinglevel");
             listOfFiles = folder.listFiles();
        }
        else if(my_level==3)
        {
            folder = new File("./resouces/poets/content/expertlevel");
            listOfFiles = folder.listFiles();
            
        }
        
        
        
        
        rand = new Random();

        File file = listOfFiles[rand.nextInt(listOfFiles.length)];
        
        
        f =new FileInputStream (file);
        br = new BufferedReader(new InputStreamReader(f,"UTF-8"));
        
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
        createBuzzle();
        temp_totalScore=getTotal_Score();    
        //----------------------------------------------------------------------
        
        label=new Label();
        label.setTextFill(Color.ORANGE);
        label.setFont(Font.font(20));
        HBox layout=new HBox(5);
        layout.getChildren().add(label);
        //----------------------------------------------------------------------
        Stage popup = new Stage();
        
        BorderStroke borderStroke = new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, null,
                new BorderWidths(2));
        Border border=new Border(borderStroke);
        //---------------------------------------------------------------------
        popup.setTitle("إنتباه!");
        
        Text errortitle = new Text("اجابة خاطئة");
        errortitle.setStyle("-fx-font-size: 20px;" +
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
        
       
        double width = primaryStage.getWidth() / 1.6;
        double height = primaryStage.getHeight() / 1.6;
        popup.setScene(new Scene(error_root,width,height));
        //----------------------------------------------------------------------
        
        
        
        primaryStage.setTitle("ألعاب شعرية");
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> event.consume());
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        
        //----------------------------------------------------------------------
        
        //----------------------------------------------------------------------
        //grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("لعبة ترتيب الأبيات");
        scenetitle.setId("welcome-text");
        //grid.add(scenetitle, 0, 0, 2, 1);

        
        Button b2 = new Button("▲"); //grid.add(b2, 0, 1);
        Label l2 = new Label(buzzleText[2-1]); grid.add(l2, 2, 1);
        Button b1 = new Button("▼"); //grid.add(b1, 1, 1);
        Label l1 = new Label(buzzleText[1-1]); grid.add(l1, 4, 1);
        Label x1 = new Label("***"); grid.add(x1, 3, 1);
        
        HBox hbBtn1 = new HBox(1); hbBtn1.setAlignment(Pos.CENTER); 
        hbBtn1.getChildren().add(b2); hbBtn1.getChildren().add(b1);
        grid.add(hbBtn1, 1, 1);
        
        Button b4 = new Button("▲"); //grid.add(b4, 0, 2);
        Label l4 = new Label(buzzleText[4-1]); grid.add(l4, 2, 2);
        Button b3 = new Button("▼"); //grid.add(b3, 1, 2);
        Label l3 = new Label(buzzleText[3-1]); grid.add(l3, 4, 2);
        Label x2 = new Label("***"); grid.add(x2, 3, 2);
        
        HBox hbBtn2 = new HBox(1); hbBtn2.setAlignment(Pos.CENTER); 
        hbBtn2.getChildren().add(b4); hbBtn2.getChildren().add(b3);
        grid.add(hbBtn2, 1, 2);
        
        Button b6 = new Button("▲"); //grid.add(b6, 0, 3);
        Label l6 = new Label(buzzleText[6-1]); grid.add(l6, 2, 3);
        Button b5 = new Button("▼"); //grid.add(b5, 1, 3);
        Label l5 = new Label(buzzleText[5-1]); grid.add(l5, 4, 3);
        Label x3 = new Label("***"); grid.add(x3, 3, 3);
     
        HBox hbBtn3 = new HBox(1); hbBtn3.setAlignment(Pos.CENTER); 
        hbBtn3.getChildren().add(b6); hbBtn3.getChildren().add(b5);
        grid.add(hbBtn3, 1, 3);
        
       Button exit_game = new Button("عودة");  
       exit_game.setId("return_button");  
       Image imageDecline = new Image(getClass().getResourceAsStream("/return.png"));
       
       //Button button5 = new Button();
       ImageView imageView = new ImageView(imageDecline);
       imageView.setFitWidth(20);

       imageView.setFitHeight(20);
       exit_game.setGraphic(imageView);
       exit_game.setAlignment(Pos.TOP_LEFT);
       
        
        
        Button btn = new Button("     التالي     ");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(exit_game);
        hbBtn.setSpacing(20);
        hbBtn.getChildren().add(btn);

       
        final Text actiontLevel = new Text();
        final Text actiontTotalScore = new Text();
        actiontTotalScore.setId("actiontarget");
        final Text actiontarget = new Text();
        //grid.add(actiontarget, 3, 0);
        actiontarget.setId("actiontarget");
        actiontarget.setText("رتب الأبيات التالية:");
        
        actiontTotalScore.setText("النقاط الإجمالي:"+getTotal_Score());
        
        actiontLevel.setText("المستوى :"+get_Level(getLevel()));
        
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
        hBox.setSpacing(10);
        hBox.getChildren().add(actiontTotalScore);
        
        hBox.setAlignment(Pos.BASELINE_RIGHT);
       
       
       
       
       
        VBox vBox = new VBox(10);
        vBox.setBorder(border);
        vBox.getChildren().add(hBox);
        vBox.setSpacing(10);
        //vBox.setBorder(border);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(scenetitle);
        vBox.getChildren().add(actiontarget);
        vBox.getChildren().add(label);
        
        
        
        VBox vBox_bottom = new VBox(20);
       
        vBox_bottom.setAlignment(Pos.CENTER);
        vBox_bottom.getChildren().add(hbBtn);
        
        //doTime();

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                
                    
                if (checkAnswer()){
                    score=score+10;
                    actiontarget.setText(" النقاط: "+score);
                    temp_totalScore =temp_totalScore+10;
                    actiontTotalScore.setText("النقاط الإجمالي:"+temp_totalScore+"  ▲  ");
                    actiontTotalScore.setFill(Color.GREEN);
                } else {
                     actiontTotalScore.setText("النقاط الإجمالي:"+temp_totalScore) ;
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
                    
                    doTime();
                    readAbyat();
                    createBuzzle();
                    
                    l1.setText(buzzleText[1-1]);
                    l2.setText(buzzleText[2-1]);
                    l3.setText(buzzleText[3-1]);
                    l4.setText(buzzleText[4-1]);
                    l5.setText(buzzleText[5-1]);
                    l6.setText(buzzleText[6-1]);
                } catch(Exception ee) {
                    //
                }    
            }
        });

        //////////
        
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String s  = l2.getText(); l2.setText(l4.getText()); l4.setText(s);
                int j=buzzleIndex[1];buzzleIndex[1]=buzzleIndex[3];buzzleIndex[3]=j;
                
                s  = l1.getText(); l1.setText(l3.getText()); l3.setText(s);
                j=buzzleIndex[0];buzzleIndex[0]=buzzleIndex[2];buzzleIndex[2]=j;
            }
        });

        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String s  = l2.getText(); l2.setText(l6.getText()); l6.setText(s);
                int j=buzzleIndex[1];buzzleIndex[1]=buzzleIndex[5];buzzleIndex[5]=j;
            
                s  = l1.getText(); l1.setText(l5.getText()); l5.setText(s);
                j=buzzleIndex[0];buzzleIndex[0]=buzzleIndex[4];buzzleIndex[4]=j;
            }
        });
        
        b3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String s  = l4.getText(); l4.setText(l6.getText()); l6.setText(s);
                int j=buzzleIndex[3];buzzleIndex[3]=buzzleIndex[5];buzzleIndex[5]=j;
            
                s  = l3.getText(); l3.setText(l5.getText()); l5.setText(s);
                j=buzzleIndex[2];buzzleIndex[2]=buzzleIndex[4];buzzleIndex[4]=j;
            }
        });
        
        b4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String s  = l2.getText(); l2.setText(l4.getText()); l4.setText(s);
                int j=buzzleIndex[1];buzzleIndex[1]=buzzleIndex[3];buzzleIndex[3]=j;
            
                s  = l1.getText(); l1.setText(l3.getText()); l3.setText(s);
                j=buzzleIndex[0];buzzleIndex[0]=buzzleIndex[2];buzzleIndex[2]=j;
            }
        });

        b5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String s  = l2.getText(); l2.setText(l6.getText()); l6.setText(s);
                int j=buzzleIndex[1];buzzleIndex[1]=buzzleIndex[5];buzzleIndex[5]=j;
            
                s  = l1.getText(); l1.setText(l5.getText()); l5.setText(s);
                j=buzzleIndex[0];buzzleIndex[0]=buzzleIndex[4];buzzleIndex[4]=j;
            }
        });
        
        b6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String s  = l4.getText(); l4.setText(l6.getText()); l6.setText(s);
                int j=buzzleIndex[3];buzzleIndex[3]=buzzleIndex[5];buzzleIndex[5]=j;
            
                s  = l3.getText(); l3.setText(l5.getText()); l5.setText(s);
                j=buzzleIndex[2];buzzleIndex[2]=buzzleIndex[4];buzzleIndex[4]=j;
            }
        });
        
        Stage SeconderyStage = new Stage();
        exit_game.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                PoetryGames game= new PoetryGames();
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
        
        
        //----------------------------------------------------------------------
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
        //----------------------------------------------------------------------
        //////////
        BorderPane root = new BorderPane();
        //root.getChildren().add(exit_game);
        // Store the Header Label in the Top Region 
        root.setTop(vBox);
        // Store the OK Button in the Top Region 
        //root.setRight(okBtn);
        // Store the Output Area in the Right Region 
        root.setBottom(hbBtn);
        // Store the Input Label in the Bottom Region 
        //root.setLeft(actiontarget);
        // Store the Input Area in the Center Region 
        root.setCenter(grid);
        ///////
        // Set the alignment of the Header Label to bottom center
        BorderPane.setAlignment(scenetitle,Pos.CENTER);
        // Set the alignment of the Input Label to center left
        BorderPane.setAlignment(hbBtn,Pos.CENTER);
        
        root.setStyle("-fx-padding: 25;");
        root.setId("border-pane");
        ///////////
        //----------------------------------------------------------------------
        
        //----------------------------------------------------------------------

        Scene scene = new Scene(root, 1000, 500);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(OrderVerses.class.getResource("PoetryGames.css").toExternalForm());        
        primaryStage.show();
        
    }
    private void doTime()
     {
         seconds=30;
         Timeline time=new Timeline();
         time.setCycleCount(Timeline.INDEFINITE);
         if(time!=null)
         {
            time.stop();
         }
         time.playFromStart();
         KeyFrame frame=new KeyFrame(Duration.seconds(1),new EventHandler<ActionEvent>() 
         {
             @Override
             public void handle(ActionEvent t) 
             {
                 
                 --seconds;
                 System.out.println("-----------"+seconds);
                 label.setText("الوقت المتبقي :"+seconds.toString());
                 if(seconds<=0)
                 {
                    time.stop();
                 }
                 
                 
             }
         });
          
         time.getKeyFrames().add(frame);
         time.playFromStart();
     }
    public static void main(String[] args) {
        launch();
    }

}

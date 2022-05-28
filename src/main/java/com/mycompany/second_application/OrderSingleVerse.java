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


//////////// tts
// Imports the Google Cloud client library
import com.google.cloud.texttospeech.v1.AudioConfig;
import com.google.cloud.texttospeech.v1.AudioEncoding;
import com.google.cloud.texttospeech.v1.SsmlVoiceGender;
import com.google.cloud.texttospeech.v1.SynthesisInput;
import com.google.cloud.texttospeech.v1.SynthesizeSpeechResponse;
import com.google.cloud.texttospeech.v1.TextToSpeechClient;
import com.google.cloud.texttospeech.v1.VoiceSelectionParams;
import com.google.protobuf.ByteString;

import com.google.api.gax.core.CredentialsProvider;
import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.texttospeech.v1.TextToSpeechSettings;


import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import javafx.animation.PauseTransition;
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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class OrderSingleVerse extends Application {
    
    
    //--------------------------------------------------------------------------
    
    
    int game_id=6;
    public String  user_name="عالي"; 
    public int Score=0;
    MediaPlayer player;
    
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_name() {
        return user_name;
    }
    //-------------------------------------------------------------------------
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
                System.out.println("----"+Integer.parseInt(array[1].trim())+score+"-----------------------");
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
    
    //-------------------------------------------------------------------------
    
    

    int abyatLength=2;
    int currentBayt=0;
    String[] poem=null;
    String[] buzzleText=null;
    String[] sl3Answer=null;
    String[] sl3=null;
    Random rand = new Random();
    int score=0;
    boolean b0f=true,b1f=true,b2f=true,b3f=true,b4f=true,b5f=true,b6f=true,b7f=true,b8f=true,b9f=true;
    HBox hbBtn2=new HBox(1);
    GridPane grid=new GridPane();
    Label l1=new Label(""),l2=new Label("");
    
    private void createBuzzle(){   
        sl3Answer = buzzleText[1].split("\\s+");
        //sl3Answer = buzzleText[1].split(" ");
        sl3=sl3Answer;
        String s=sl3[0];
        for(int i=1; i<sl3.length;i++)
            if (rand.nextInt(2)==0)
                s=s+" "+sl3[i];
            else
                s=sl3[i]+" "+s;
        sl3 = s.split(" ");
                
        ////////////
        grid.getChildren().remove(hbBtn2);
        hbBtn2=new HBox(1);

        Button b0=null,b1=null,b2=null,b3=null,b4=null,b5=null,b6=null,b7=null,b8=null,b9=null;

        hbBtn2.setAlignment(Pos.CENTER); 
        
        if (sl3.length > 0){ b0 = new Button(sl3[0]); hbBtn2.getChildren().add(b0); b0.setId("word-button");}
        if (sl3.length > 1){ b1 = new Button(sl3[1]); hbBtn2.getChildren().add(b1); b1.setId("word-button");}
        if (sl3.length > 2){ b2 = new Button(sl3[2]); hbBtn2.getChildren().add(b2); b2.setId("word-button");}
        if (sl3.length > 3){ b3 = new Button(sl3[3]); hbBtn2.getChildren().add(b3); b3.setId("word-button");}
        if (sl3.length > 4){ b4 = new Button(sl3[4]); hbBtn2.getChildren().add(b4); b4.setId("word-button");}
        if (sl3.length > 5){ b5 = new Button(sl3[5]); hbBtn2.getChildren().add(b5); b5.setId("word-button");}
        if (sl3.length > 6){ b6 = new Button(sl3[6]); hbBtn2.getChildren().add(b6); b6.setId("word-button");}
        if (sl3.length > 7){ b7 = new Button(sl3[7]); hbBtn2.getChildren().add(b7); b7.setId("word-button");}
        if (sl3.length > 8){ b8 = new Button(sl3[8]); hbBtn2.getChildren().add(b8); b8.setId("word-button");}
        if (sl3.length > 9){ b9 = new Button(sl3[9]); hbBtn2.getChildren().add(b9); b9.setId("word-button");}
        
        grid.add(hbBtn2, 2, 2);
        
        //for(int i=0; i<sl3Answer.length;i++)
        //    System.out.println(sl3Answer[i]);
        //System.out.println("");
        
        //////////
        if (b0 != null) 
            b0.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (b0f){
                        l2.setText(l2.getText()+" "+sl3[0]);
                        b0f=false;
                    }
                }
            });
        
        if (b1 != null)
            b1.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (b1f){
                        l2.setText(l2.getText()+" "+sl3[1]);
                        b1f=false;
                    }            
                }
            });
        
        if (b2 != null)
            b2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (b2f){
                        l2.setText(l2.getText()+" "+sl3[2]);
                        b2f=false;
                    }            
                }        
            });
        
        if (b3 != null)
            b3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (b3f){
                        l2.setText(l2.getText()+" "+sl3[3]);
                        b3f=false;
                    }            
                }
            });
        
        if (b4 != null)
            b4.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (b4f){
                        l2.setText(l2.getText()+" "+sl3[4]);
                        b4f=false;
                    }            
                }
            });
        
        if (b5 != null)
            b5.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (b5f){
                        l2.setText(l2.getText()+" "+sl3[5]);
                        b5f=false;
                    }            
                }
            });
        
        if (b6 != null)
            b6.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (b6f){
                        l2.setText(l2.getText()+" "+sl3[6]);
                        b6f=false;
                    }            
                }
            });
        
        if (b7 != null)
            b7.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (b7f){
                        l2.setText(l2.getText()+" "+sl3[7]);
                        b7f=false;
                    }            
                }
            });
        
        if (b8 != null)
            b8.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (b8f){
                        l2.setText(l2.getText()+" "+sl3[8]);
                        b8f=false;
                    }            
                }
            });
        
        if (b9 != null)
            b9.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (b9f){
                        l2.setText(l2.getText()+" "+sl3[9]);
                        b9f=false;
                    }            
                }
            });
        /////////////////////////////
    }

    private boolean checkAnswer(String s){
        String[] ss = s.trim().split(" ");
        for (int i=0;i<ss.length;i++){
            
            if (! ss[i].equals(sl3Answer[i]))
                return(false);
        }
        
        return(true);
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

        // select random file
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
        
        
        
        
        rand = new Random();

        File file = listOfFiles[rand.nextInt(listOfFiles.length)];
        
        
        f =new FileInputStream (file);
        br = new BufferedReader(new InputStreamReader(f,"UTF-8"));
        
        
        // read file line by line
        int i = 0;
        while (((s=br.readLine()) != null) && (i<PoetryGames.maxLines))  {
            s =s.trim();
            //System.out.print(s);
            
            // fix bad tashkeel and remove punc
            s=s.replaceAll(" َ", "َ");
            s=s.replaceAll(" ِ", "ِ");
            s=s.replaceAll(" ً", "ً");
            s=s.replaceAll(" ٍ", "ٍ");
            s=s.replaceAll(" ْ", "ْ");
            s=s.replaceAll(" ُ", "ُ");
            s=s.replaceAll(" ٌ", "ٌ");
            s=s.replaceAll(" ّ", "ّ");
            s=s.replaceAll("[?!:.,؟،؛']", "");
            //System.out.println(" ---- "+s);
            
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
        
        //----------------------------------------------------------------------
         temp_totalScore=getTotal_Score();    
      
        
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
        
       
        double width = primaryStage.getWidth() / 1.6;
        double height = primaryStage.getHeight() / 1.6;
        popup.setScene(new Scene(error_root,width,height));
        //----------------------------------------------------------------------
        
        
        
        
        primaryStage.setTitle("ألعاب شعرية");
        
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> event.consume());
        
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(20);
        //grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("لعبة الشطر السليم والمبعثر");
        scenetitle.setId("welcome-text");
        //grid.add(scenetitle, 0, 0, 2, 1);

        l1.setMinWidth(400); l1.setAlignment(Pos.BASELINE_RIGHT);
        l2.setMinWidth(400); l2.setAlignment(Pos.BASELINE_RIGHT);
        l1.setText(buzzleText[0]); grid.add(l1, 2, 1);
        grid.add(l2, 2, 3);
        
        
        
        
        //-----------------------------------------------------------------------
       Button exit_game = new Button("عودة");  
       exit_game.setId("return_button");   
       Image imageDecline = new Image(getClass().getResourceAsStream("/return.png"));
       
       //Button button5 = new Button();
       ImageView imageView = new ImageView(imageDecline);
       imageView.setFitWidth(20);

       imageView.setFitHeight(20);
       exit_game.setGraphic(imageView);
       exit_game.setAlignment(Pos.TOP_LEFT);
      //---------------------------------------------------------------------
       
                
       Button btn = new Button("     التالي     "+" "+"⇚");
       btn.setId("return_button");
       HBox hbBtn = new HBox(10);
       hbBtn.setAlignment(Pos.CENTER);
        
        
       Button btn2 = new Button("اقرأ البيت"+" "+" 🔈 ");
        btn2.setId("tts-button");
       
        hbBtn.getChildren().add(exit_game);
        hbBtn.setSpacing(20);
        hbBtn.getChildren().add(btn);
        
        hbBtn.setSpacing(20);
        
        hbBtn.getChildren().add(btn2);
        

       final Text actiontarget = new Text();
       final Text actiontLevel = new Text();
       final Text actiontTotalScore = new Text();
       
       actiontarget.setId("actiontarget");
       actiontTotalScore.setId("actiontarget");
       
       actiontarget.setText("رتب كلمات الشطر الثاني:");
       
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

        VBox vBox = new VBox(10);
        vBox.setBorder(border);
        vBox.getChildren().add(hBox);
        vBox.setSpacing(10);
        //vBox.setBorder(border);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(scenetitle);
        vBox.getChildren().add(actiontarget);
        
        VBox vBox_bottom = new VBox(20);
       
        vBox_bottom.setAlignment(Pos.CENTER);
        vBox_bottom.getChildren().add(hbBtn);

        
        
         btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e){
                try {tts(buzzleText[0]+". "+buzzleText[1]+".");} catch(Exception ee) {};                
            }
        });
        
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                
                    
                if (checkAnswer(l2.getText())){
                    score=score+10;
                   
                    //actiontarget.setText(" النقاط: "+score);
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
                    readAbyat();
                    createBuzzle();
                    b0f=true;b1f=true;b2f=true;b3f=true;b4f=true;b5f=true;b6f=true;b7f=true;b8f=true;b9f=true;
                    
                    l1.setText(buzzleText[0]);
                    l2.setText("");

                } catch(Exception ee) {
                    //
                }    
            }
        });

        //////////
        l2.setOnMouseClicked(event ->  {
            l2.setText("");
            b0f=true;b1f=true;b2f=true;b3f=true;b4f=true;b5f=true;b6f=true;b7f=true;b8f=true;b9f=true;

        });
        ///////////////////////////////////////////////////////////////////////
        
        
         //----------------------------------------------------------------------
        Stage SeconderyStage = new Stage();
        exit_game.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                PoetryGames game= new PoetryGames();
                game.setUser_name(user_name);   
                game.setMy_level(level);
                if(score==0)
                {
                  primaryStage.hide();
                    try {
                        game.setUser_name(getUser_name());
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
        ///////////////////////////////////////////////////////////////////////
        
        BorderPane root = new BorderPane();
        //root.setStyle(user_name);
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

        Scene scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(OrderSingleVerse.class.getResource("/PoetryGames.css").toExternalForm());        
        primaryStage.show();
        
    }
     public void tts(String s) throws Exception {

        ////////////////////////
        TextToSpeechSettings textToSpeechSettings =
        TextToSpeechSettings.newBuilder()
         .setCredentialsProvider(FixedCredentialsProvider
         .create(ServiceAccountCredentials
         .fromStream(new FileInputStream("./resources/TTS/api-key/elite-hold-350610-cdc0fddf6ae5.json"))))
         .build();
        ////////////////////////
        try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create(textToSpeechSettings)) {
        // Set the text input to be synthesized
        SynthesisInput input = SynthesisInput.newBuilder().setText(s).build();

        // Build the voice request, select the language code ("en-US") and the ssml voice gender
        // ("neutral")
        VoiceSelectionParams voice =
          VoiceSelectionParams.newBuilder()
              .setLanguageCode("ar-XA")
              .setSsmlGender(SsmlVoiceGender.MALE)
              .build();

        // Select the type of audio file you want returned
        AudioConfig audioConfig =
          AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();

        // Perform the text-to-speech request on the text input with the selected voice parameters and
        // audio file type
        SynthesizeSpeechResponse response =
          textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);

        // Get the audio contents from the response
        ByteString audioContents = response.getAudioContent();

        // Write the response to the output file.
        try (OutputStream out = new FileOutputStream("./resources/TTS/audios/output.mp3")) {
            out.write(audioContents.toByteArray());
            //System.out.println("Audio content written to file \"output.mp3\"");
            
            String uriString = new File("./resources/TTS/audios/output.mp3").toURI().toString();
            player = new MediaPlayer( new Media(uriString));
            
                
            ///new Thread(() -> player.seek(new Duration(player.getCurrentTime().toMillis() +10000))).start();
            player.play();
            out.close();          

        }
       }
    }
    public static void main(String[] args) {
        launch();
    }

}

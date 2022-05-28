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
import javafx.scene.paint.Color;

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
import io.grpc.LoadBalancerRegistry;


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
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class ValidVerse extends Application {
    
    
    //----------------------------------
    
    int game_id=4;
    MediaPlayer player;
    public String  user_name="عالي"; 
    public int Score=0;
    
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
    
    
    //--------------------------------------------------------------------------
    String[] poem=null;
    String[] buzzleText=null;
    Random rand = new Random();
    int score=0;
    int poemIndex=0;
    boolean answer=true;
    
    private void select2Verses() throws Exception{
        buzzleText = new String[4];
        int i;
        
        poem = readRandomPoem(getLevel());
        i = selectRandomVerse();
        buzzleText[0]=poem[i];
        buzzleText[1]=poem[i+1];
        
        poem = readRandomPoem(getLevel());
        i = selectRandomVerse();
        buzzleText[2]=poem[i];
        buzzleText[3]=poem[i+1];     
    }
    
    private void createBuzzle(){
        answer=true;
        if (rand.nextInt(2)==0){
            buzzleText[1]=buzzleText[3];
            answer=false;
        }
        
        buzzleText[2]="";
        buzzleText[3]="";     
    }
    
    private int selectRandomVerse(){
        int i=0;
        
        i = rand.nextInt(poemIndex);
        if(Math.IEEEremainder(i, 2)!=0)
            i--;
        return(i);
    }
    
    private String[] readRandomPoem(int my_level)throws Exception {
        String s;
        String [] poem = new String[PoetryGames.maxLines];
        FileInputStream f;
        BufferedReader br;

        // select random file
        //int fileNo = rand.nextInt(PoetryGames.numOfFiles)+1; 
        //f =new FileInputStream (getClass().getResource(fileNo+".txt").getFile());
	//br = new BufferedReader(new InputStreamReader(f,"UTF-8"));
        
        
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
        
        
        
        
        rand = new Random();

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
	
	poemIndex=i-1;
        
        f.close();
	br.close();
		
    return (poem);
    
}
    @Override
    public void start(Stage primaryStage) throws Exception{
        
       


        
        select2Verses();
        createBuzzle();
        //---------------------------------------------------------------------
        temp_totalScore=getTotal_Score();    
        //----------------------------------------------------------------------
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
        //grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("لعبة البيت السليم");
        scenetitle.setId("welcome-text");
        //grid.add(scenetitle, 0, 0, 2, 1);

        
        Label l2 = new Label(buzzleText[2-1]); grid.add(l2, 2, 1);
        Label l1 = new Label(buzzleText[1-1]); grid.add(l1, 4, 1);
        Label x1 = new Label("***"); grid.add(x1, 3, 1);
        
        
        //----------------------------------------------------------------------
        //---------------------------------------------------------------------
         Button exit_game = new Button("عودة");  
        exit_game.setId("return_button");   
        Image imageDecline = new Image(getClass().getResourceAsStream("/return.png"));
       
        //Button button5 = new Button();
        ImageView imageView = new ImageView(imageDecline);
        imageView.setFitWidth(20);

        imageView.setFitHeight(20);
        exit_game.setGraphic(imageView);
        exit_game.setAlignment(Pos.CENTER);
        
        //---------------------------------------------------------------------
        Button btn2 = new Button("اقرأ البيت"+" "+" 🔈 ");
        btn2.setId("tts-button");
        //----------------------------------------------------------------------
        
        
        
        
        
        
        Button btnYes = new Button("✓");
        Button btnNo = new Button("⨯");
        btnYes.setMinWidth(100);
        btnNo.setMinWidth(100);

        btnYes.setId("Yes");
        btnNo.setId("No");

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        
        hbBtn.getChildren().add(exit_game);
        hbBtn.getChildren().add(btnNo);
        hbBtn.getChildren().add(btnYes);
        hbBtn.getChildren().add(btn2);
        
        //grid.add(hbBtn, 2, 4);

        final Text actiontarget = new Text();
        
        final Text actiontLevel = new Text();
        final Text actiontTotalScore = new Text();
        actiontTotalScore.setId("actiontarget");
        
        actiontarget.setId("actiontarget");
        actiontarget.setText("هل البيت التالي صحيح أم خاطئ؟");
        
        
        actiontTotalScore.setText("مجموع النقاط "+" : "+getTotal_Score());
        actiontLevel.setText("المستوى "+" : "+get_Level(getLevel()));

        //---------------------------------------------------------------------
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
        //---------------------------------------------------------------------
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
        
        
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(scenetitle);
        vBox.getChildren().add(actiontarget);
        
        
        VBox vBox_bottom = new VBox(20);
       
        vBox_bottom.setAlignment(Pos.CENTER);
        vBox_bottom.getChildren().add(hbBtn);
        
        
        
        //----------------------------------------------------------------------
        Stage SeconderyStage = new Stage();
        exit_game.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
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
        
        
        //----------------------------------------------------------------------
        

        btnYes.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                
                    
                if (answer==true){
                   
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
                    select2Verses();
                    createBuzzle();
                    
                    l1.setText(buzzleText[1-1]);
                    l2.setText(buzzleText[2-1]);
        
                } catch(Exception ee) {
                    //
                }    
            }
            
        });
        
        btnNo.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                
                    
                if (answer==false){
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
                    select2Verses();
                    createBuzzle();
                    
                    l1.setText(buzzleText[1-1]);
                    l2.setText(buzzleText[2-1]);
        
                } catch(Exception ee) {
                    //
                }    
            }
            
        });
        
        
         btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e){
                try {tts(buzzleText[0]+". "+buzzleText[1]+".");} catch(Exception ee) {};                
            }
        });
        
        //////////
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
        ////////////////////
        
        
        //////////
        BorderPane root = new BorderPane();
        
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

        Scene scene = new Scene(root, 1000, 350);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(ValidVerse.class.getResource("/PoetryGames.css").toExternalForm());        
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

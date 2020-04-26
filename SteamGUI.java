import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.Tab;
import javafx.scene.control.Alert.*;
import javafx.scene.control.TextField;
import java.util.ArrayList;

public class SteamGUI extends Application{
   static Stage primaryStage;
   private static String Username = "";
   private static String Password = "";
   private static BorderPane prime;
   private static BusinessLayer BL;
   private static ApplicationLayer AL;
   private static TextField userIn;
   private static PasswordField passIn;
   private static TabPane tabs;
   private static dbLayer db;
   private static Boolean loggedIn;
   
   private String setUsername(String Username){
      this.Username = Username;
      return Username;
   }
   
   private String setPassword(String Password){
      this.Password = Password;
      return Password;
   }
   
   private boolean loginSuccess(String Username, String Password){
      db.connect();
      boolean testHash = db.login(Username, Password);
      return testHash;
   }
   
   private void regesterWindow(){
      Label resLabel = new Label("Choose a Username/Password");
      prime.setTop(resLabel);
      userIn = new TextField();
      passIn = new PasswordField();
      
      Label label1 = new Label("Username: ");
      Label label2 = new Label("Password: ");
      
      HBox HBox1 = new HBox(label1, userIn);
      
      HBox HBox2 = new HBox(label2, passIn);
      
      Button loginbtn = new Button();
      loginbtn.setText("Regester");
      loginbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              String user = setUsername(userIn.getText());
              String pass = setPassword(passIn.getText());
           //   System.out.println("user: "+user);
           //  System.out.println("Pass: "+pass);
                //TODO MIKE
                db.connect();
                int regInt = db.register(user, pass);
                
                if(regInt == 1){
                  // ADD ALERT FOR REGISTERED SUCCESSFULLY
                  // instead of sout (NATHAN)
                  System.out.println("Registered Successfully");
                  
                }else{
                  System.out.println("Couldn't register");
                }//end ifelse
                prime.setCenter(loginWindow());
            }
        });
        
      Button guestbtn = new Button();
      guestbtn.setText("Continue as Guest");
      guestbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event){
            setUpMainGuest();
         }
      
      });
      
      HBox buttons = new HBox(loginbtn, guestbtn);
      
      VBox regester = new VBox(HBox1, HBox2, buttons);
      prime.setCenter(regester);
   }
   
   private VBox loginWindow(){
      Label logLabel = new Label("Enter a Username/Password");
      prime.setTop(logLabel);
      userIn = new TextField();
      passIn = new PasswordField();
      
      Label label1 = new Label("Username: ");
      Label label2 = new Label("Password: ");
      
      HBox HBox1 = new HBox(label1, userIn);
      
      HBox HBox2 = new HBox(label2, passIn);
      
      Button loginbtn = new Button();
      loginbtn.setText("login");
      loginbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUsername(userIn.getText());
                setPassword(passIn.getText());
                if(loginSuccess(Username, Password)){
                
                  loggedIn = true;
                  setUpMain();
                }
                else{
                  Label errortext = new Label("Incorrect Username/Password");
                  prime.setTop(errortext);
                }
            }
        });
      
      Button guestbtn = new Button();
      guestbtn.setText("Guest");
      guestbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event){
            setUpMainGuest();
         }
      
      });
      
      Button RegesterBtn = new Button();
      RegesterBtn.setText("Create Account");
      RegesterBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event){
            regesterWindow();
         }
      
      });
      
      HBox buttons = new HBox(loginbtn, guestbtn, RegesterBtn);
      
      VBox login = new VBox(HBox1, HBox2, buttons);
      return login;
   }
   
   public void start(Stage primaryStage){
      BL = new BusinessLayer();
      db = new dbLayer();
      AL = new ApplicationLayer();
      loggedIn = false;
      this.primaryStage = primaryStage;
      prime = new BorderPane();
      
      prime.setPrefWidth(500);
      prime.setPrefHeight(300);
      prime.setCenter(loginWindow());
      prime.setRight(help());
      Scene scene = new Scene(prime);
      primaryStage.setScene(scene);
      primaryStage.show();
   }
   
   public VBox help(){
      Button help = new Button();
      help.setText("Help");
      help.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event){
            String helps = "Troubleshoot tips:\n 1. Is MySQL installed\n 2. is the database provided in createDB installed\n 3. is root/student a valid log in for the MySQL server \n 4. Is your internet connection working";
            Label helplab = new Label(helps);
            Button close = new Button();
            close.setText("Close");
            close.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event){
                  prime.setRight(help());
               }
            });
            VBox vbox = new VBox(helplab, close);
            prime.setRight(vbox);
            System.out.println("help clicked");
         }
      
      });
      VBox halp = new VBox(help);
      return halp;
   }
   
   public VBox favorites(){
      VBox vboxtest = new VBox();
      
      return vboxtest;
      
   }
   
   public VBox Search(){
      TextField apptext = new TextField();
      TextField nametext = new TextField();
      
      Label applab = new Label("AppID");
      Label namelab = new Label("Name");
      
      Button appbtn = new Button();
      appbtn.setText("Search");
      appbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event){
            String result = BL.searchFromKey(apptext.getText());
            ArrayList<String> results = new ArrayList();
            results.add(result);
            prime.setCenter(Searchp2(results));
         }
      });
      
      Button namebtn = new Button();
      namebtn.setText("Search");
      namebtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event){
            ArrayList<String> results = BL.searchFromValue(nametext.getText());
            prime.setCenter(Searchp2(results));
         }
      });
      
      HBox appsearch = new HBox(applab, apptext, appbtn);
      HBox namesearch = new HBox(namelab, nametext, namebtn);
      VBox test2 = new VBox(appsearch, namesearch);
      
      return test2;
      
   }
      
      public VBox Searchp2(ArrayList<String> results){
         ArrayList<Button> buttons = new ArrayList();
         Label label = new Label("Results");
         VBox res = new VBox(label);
         
         for(int k = 0; k<results.size(); k++){
            Button button = new Button();
            button.setText(results.get(k));
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event){
                  for(int y=0; y<buttons.size(); y++){
                     Button clicked = (Button)event.getSource();
                     int p = 0;
                     while(!buttons.get(p).equals(clicked)){
                        p++;
                     }
                     displayNews(results.get(p));
                  }
                  
               }
            });
            res.getChildren().add(button);
         }
         return res;
      }
      
      public void displayNews(String keys){
         if(loggedIn){
            //Favorites button will go here
         }
      }
     
   
   public void setUpMain(){
      tabs = new TabPane();
      
      tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
      
      Tab Favorites = new Tab("Favorites", favorites());
      Tab Search = new Tab("Search", Search());
      
      tabs.getTabs().add(Favorites);
      tabs.getTabs().add(Search);
      
      Button logout = new Button();
      logout.setText("Logout");
      logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event){
            prime.setCenter(loginWindow());
         }
      
      });
      
      HBox Hbox = new HBox(tabs, logout);
      
      prime.setTop(Hbox);
      prime.setCenter(favorites());
   }
   
   
   public void setUpMainGuest(){
      tabs = new TabPane();
      
      tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
      
      Tab Search = new Tab("Search", Search());
      
      tabs.getTabs().add(Search);

      Button logout = new Button();
      logout.setText("Login");
      logout.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event){
            prime.setCenter(loginWindow());
         }
      
      });
      
      HBox Hbox = new HBox(tabs, logout);
      
      prime.setTop(Hbox);
      prime.setCenter(Search());
   }
   
   public static void main(String[] args){
      Application.launch();
   }
}
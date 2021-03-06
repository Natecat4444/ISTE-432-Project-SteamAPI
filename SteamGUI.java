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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollBar;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.Group;
import javafx.geometry.Orientation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
   private static VBox faveres;
   private static ArrayList<String> labels;
   private static ArrayList<Button> favebuttons;
   
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
      loginbtn.setText("Register");
      loginbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              String user = setUsername(userIn.getText());
              String pass = setPassword(passIn.getText());
           //   System.out.println("user: "+user);
           //  System.out.println("Pass: "+pass);
                //TODO MIKE
                boolean connected = db.connect();
                System.out.println(connected);
                int regInt = db.register(user, pass);
                
                if(regInt == 1){
                  // ADD ALERT FOR REGISTERED SUCCESSFULLY
                  // instead of sout (NATHAN)
                  System.out.println("Registered Successfully");
                  
                }else{
                  System.out.println("Couldn't register");
                }//end ifelse
                prime.setCenter(loginWindow());
                db.close();
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
                  db.close();
                  setUpMain();
                }
                else{
                  Label errortext = new Label("Incorrect Username/Password");
                  prime.setTop(errortext);
                  db.close();
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
      
      ScrollBar scrollbar = new ScrollBar();
      scrollbar.setOrientation(Orientation.VERTICAL);
      
      Group group = new Group();
      // group.getChildren().addAll(prime, scrollbar);
      prime.setPrefWidth(500);
      prime.setPrefHeight(500);
      prime.setCenter(loginWindow());
      prime.setRight(help());
      Scene scene = new Scene(prime);
      
      primaryStage.setScene(scene);
      
      scrollbar.setLayoutX(scene.getWidth());
      scrollbar.setMin(0);
      // scrollbar.setMax(360);
      scrollbar.setPrefHeight(360);
      
      scrollbar.valueProperty().addListener(new ChangeListener<Number>() {
         public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val){
               prime.setLayoutY(-new_val.doubleValue());
            }
         });
      
      prime.setLayoutX(5);
      
      // prime.setLeft(scrollbar);
      
      primaryStage.setMaximized(true);
      primaryStage.show();
   }
   
   public VBox help(){
      Button help = new Button();
      help.setText("Help");
      help.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event){
            String helps = "Troubleshoot tips:\n 1. Is MySQL installed\n 2. is the database provided in createDB installed\n 3. is root/student a valid log in for the MySQL server \n 4. Is your internet connection working \n 5. Results not loading? rebooting may fix the problem";
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
      prime.setCenter(null);
      
      db.connect();
      
      ArrayList<ArrayList<String>> results = db.getFavorites(Username);
      
      ArrayList<String> ids = new ArrayList();
      
      for(int y =0; y<results.size(); y++){
         for(int r= 0; r<results.get(y).size(); r++){
               ids.add(results.get(y).get(r));
            
         }
      }
      
      labels = new ArrayList();
      
      for(int p = 0; p<ids.size(); p++){
        labels.add(getFavebtnLabel(ids.get(p)));
      }
      System.out.println("End Favorites");
      
      return Favep2(labels);
      
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
            System.out.println("Appidsearch: " +result);
            String[] framents = result.split("News");
            String p1 = framents[0];
            results.add(p1.substring(0, framents[0].length()-2));
            System.out.println("Appidsearchp2: " +results.get(0));
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
         System.out.println("Results: "+results.size());
         
         int max = results.size();
         
         if(results.size() >= 3){
            max = max-2;
         }
         else if(results.size() ==1){
            if(results.get(0).equals("No game are matched!") || results.get(0).equals("Please enter the Name!") || results.get(0).equals("Please enter AppI") || results.get(0).equals("AppID inval")){
               Label labelend = new Label("No results found");
               res.getChildren().add(labelend);
               return res;
            }
         }
         
         for(int k = 0; k<max; k++){
            Button button = new Button();
            button.setText(results.get(k));
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event){
                     Button clicked = (Button)event.getSource();
                     int p = 0;
                     while(!buttons.get(p).equals(clicked)){
                        p++;
                     }
                     System.out.println(p);
                     displayNews(results.get(p), false);
                  
               }
            });
            buttons.add(button);
            res.getChildren().add(button);
         }
         System.out.println("Results set");
         return res;
      }
      
      public String getFavebtnLabel(String id){
         String result = BL.searchFromKey(id);
         String[] framents = result.split("News");
         String p1 = framents[0];
         result = p1.substring(0, framents[0].length()-2);
         System.out.println(result);
         return result;
      }
      
      public VBox Favep2(ArrayList<String> results){
         favebuttons = new ArrayList();
         
         Button refresh = new Button();
         refresh.setText("Refresh");
         refresh.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
               favorites();
            }
         });
         Label label = new Label("Favorites");
         faveres = new VBox(label);
         // faveres.getChildren().add(refresh);
         System.out.println("Results: "+results.size());
         
         int max = results.size();
         
         
         for(int k = 0; k<max; k++){
            System.out.println(results.get(k));
            Button button = new Button();
            button.setText(results.get(k));
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event){
                     Button clicked = (Button)event.getSource();
                     int p = 0;
                     while(!favebuttons.get(p).equals(clicked)){
                        p++;
                     }
                     System.out.println(p);
                     displayNews(results.get(p), true);
                  
               }
            });
            favebuttons.add(button);
            faveres.getChildren().add(button);
         }
         return faveres;
      }
      
      public void displayNews(String keys, Boolean faver){
         VBox news = new VBox();
         
         System.out.println(keys);
         String[] keysplit = keys.split(" ");
         
         System.out.println("AppID: "+keysplit[1]);
         ArrayList<String> results = AL.NewsInfo(keysplit[1]);
         
         if(loggedIn && (faver == false)){
            Button fave = new Button();
            fave.setText("Add to Favorites");
            fave.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event){
                   db.connect();
                   db.addFavorite(Username, keysplit[1]);
                   db.close();
                   Alert got = new Alert(Alert.AlertType.INFORMATION, String.format("Favorite Added"));
                   addFav(keys);
                   got.show();
               }
            });
            news.getChildren().add(fave);
         }
         else if(loggedIn && faver){
            Button unfave = new Button();
            unfave.setText("Remove from Favorites");
            unfave.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event){
                   db.connect();
                   db.delFavorite(Username, keysplit[1]);
                   db.close();
                   removeFav(keys);
                   Alert got = new Alert(Alert.AlertType.INFORMATION, String.format("Favorite Removed"));
                   got.show();
               }
            });
            news.getChildren().add(unfave);
         }
         
         ArrayList<Button> newsbuttons = new ArrayList();
         
         System.out.println("Number of News Items: "+results.size());
         
         int max = results.size();
         
         if(max>18){
            max = 18;
         }
         
         for(int r =0; r<max; r++){
            Button button = new Button();
            button.setText(results.get(r));
            
            button.setOnMouseClicked(new EventHandler<MouseEvent>() {
               @Override
               public void handle(MouseEvent event){
                   Button clicked = (Button)event.getSource();
                   int p = 0;
                   while(!newsbuttons.get(p).equals(clicked)){
                        p++;
                   }
                   WebView Art = article(keysplit[1], p);
                   prime.setCenter(Art);
               }
            });
            newsbuttons.add(button);
            news.getChildren().add(button);
            
         }
         prime.setCenter(news);
      }
     
   
   public void setUpMain(){
      tabs = new TabPane();
      
      tabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
      prime.setCenter(null);
      
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
            loggedIn = false;
         }
      
      });
      
      HBox Hbox = new HBox(tabs, logout);
      
      prime.setTop(Hbox);
   }
   
   
   public void setUpMainGuest(){
      prime.setCenter(null);
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
      
   }
   
   public static void main(String[] args){
      Application.launch();
   }
   
   public void removeFav(String key){
      for(int k = 0; k<labels.size(); k++){
         if(labels.get(k).equals(key)){
         System.out.println(favebuttons.size());
            System.out.println(favebuttons.get(k));
            faveres.getChildren().remove(favebuttons.get(k));
            return;
         }
      }
   }
   
   public void addFav(String keys){
      Button button = new Button();
      button.setText(keys);
      button.setOnMouseClicked(new EventHandler<MouseEvent>() {
         @Override
         public void handle(MouseEvent event){
            Button clicked = (Button)event.getSource();
            int p = 0;
            while(!favebuttons.get(p).equals(clicked)){
               p++;
            }
            System.out.println(p);
            displayNews(keys, true);
                  
         }
         });
   favebuttons.add(button);
   faveres.getChildren().add(button);
   }
   
   public WebView article(String key, int loc){
      ArrayList<String> Articles = AL.GetContent(key);
      WebView newsItem = new WebView();
      newsItem.getEngine().loadContent(Articles.get(loc));
      return newsItem;
   }
}
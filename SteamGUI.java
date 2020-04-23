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


public class SteamGUI extends Application{
   static Stage primaryStage;
   private static String Username = "";
   private static String Password = "";
   private static BorderPane prime;
   // private static BusinessLayer BL;
   private static TextField userIn;
   private static PasswordField passIn;
   
   private void setUsername(String Username){
      this.Username = Username;
   }
   
   private void setPassword(String Password){
      this.Password = Password;
   }
   
   private boolean loginSuccess(String Username, String Password){
      //TODO
      return false;
   }
   
   private VBox loginWindow(){
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
      
      HBox buttons = new HBox(loginbtn, guestbtn);
      
      VBox login = new VBox(HBox1, HBox2, buttons);
      return login;
   }
   
   public void start(Stage primaryStage){
      // BL = new BuisnessLayer();
      this.primaryStage = primaryStage;
      prime = new BorderPane();
      prime.setCenter(loginWindow());
      Scene scene = new Scene(prime);
      primaryStage.show();
   }
   
   public void setUpMain(){
   
   }
   
   public void setUpMainGuest(){
   
   }
   
   public static void main(String[] args){
      Application.launch();
   }
}
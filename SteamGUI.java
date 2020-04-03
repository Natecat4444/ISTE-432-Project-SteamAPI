import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.layout.Hbox;
import javafx.scene.layout.Vbox;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;


public class SteamGUI extends Application{
   static Stage primaryStage;
   private static String Username = "";
   private static String Password = "";
   private static BorderPane prime;
   private static BuisnessLayer BL;
   
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
   
   private Vbox loginWindow(){
      TextField userin = new TextField();
      PasswordField passin = new PasswordField();
      
      Label label1 = new Label("Username: ");
      Label label2 = new Label("Password: ");
      
      Hbox Hbox1 = new Hbox(label1, userin);
      
      Hbox Hbox2 = new Hbox(label2, passin);
      
      Button loginbtn = new Button();
      loginbtn.setText("login");
      loginbtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                setUsername(UserIn.getText());
                setPassword(PassIn.getText());
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
      
      Hbox buttons = new Hbox(loginbtn, guestbtn);
      
      Vbox login = new Vbox(userin, passin, buttons);
      return login;
   }
   
   public void start(Stage primaryStage){
      BL = new BuisnessLayer();
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
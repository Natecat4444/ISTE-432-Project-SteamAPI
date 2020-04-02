import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class SteamGUI extends Application{
   static Stage primaryStage;
   private static String Username = "";
   private static String Password = "";
   
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
                if(loginSuccess(Username, Password){
                  //TODO
                }
                else{
                  //TODO
                  //Pop up for bad login, try again
                }
            }
        });
      
      Vbox login = new Vbox();
      return login;
   }
   
   public void start(Stage primaryStage){
   
   }
}
//import static org.junit.jupiter.api.Assertions.assertEquals;

//import org.junit.jupiter.*;
import java.util.*;
public class testHashing{

   public static void main(String args[]) {
   /*    testHashing hash = new testHashing();
      
      String password = "testPassword";
      try{
      String newPass1 = hash.createHash(password);
      System.out.println("NewPass1: " + newPass1);
      boolean verify = hash.verifyPassword(password, newPass1);
      System.out.println("verifyhash: " + verify);
      
      String newPass2 = hash.createHash(password);
      System.out.println("NewPass2: " + newPass2);
      }catch(Exception e){
      e.printStackTrace();
      }
   }*/
      dbLayer db = new dbLayer();
      
      if (db.connect() == true){
         System.out.println("connected");
         
               //Begin testing for register account
         int registerInt = db.register("testUser2","password2");
         //int registerInt = 0;
         if(registerInt == 1){
            System.out.println("Registered Successfully!!!!");
         }else{
            System.out.println("Could not register");
         }//end if 
         boolean testHash = db.login("testuser2", "password2"); // if checkhash returns true then the user will be able to login
         System.out.println("TestHash: " + testHash);
         if(testHash == true){
            System.out.println("LOGIN SUCCESSFUL FOR TESTUSER2!!!");
         }
      
      // begin code for adding to favorites
        System.out.println("\ntesting adding to favorites");
      
         int testInt = db.addFavorite("testUser", "13");
         if(testInt == 1){
            System.out.println("Inserted Sucessfully");
         
         }//end if */
         //end test code for adding favorites
         
         // Begin code for getting favorites
         
         ArrayList<ArrayList<String>>fav = db.getFavorites("testUser");
         System.out.println(fav);
         String msg = new String();
         String output = new String();
         for(ArrayList<String> record : fav){
            msg = "";
            for(String attribute : record){
               msg += String.format(" %-11s", attribute);
            }//end inner for
            output += msg + "\n";
         }//end for
         System.out.println(output);// This outputs each favorite for the user "testUser" we can do anything with 
                                    // the output such as print app names
         
         //End code for getting favorites
         
         
      
      }else{
         System.out.println("Cannot connect to database");
      
      }//end if  
      if(db.close() == true){
         System.out.println("Closed connection to db");
         
      }else{
         System.out.println("Cannot close connection");
      }//end if
      
   }
}


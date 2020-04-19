import java.util.*;
import java.sql.*;
import static org.mockito.Mockito.*;
import org.junit.runners.*;


public class mockLayer { 
   private Statement mockStatement;
   private Connection mockConn;
   
   public static void main(String args[]){
      dbLayer db;
      db = mock(dbLayer.class);
   //   System.out.println(db);
      when(db.connect()).thenReturn(true);
      if(db.connect() == true ){
         System.out.println("TRUE TEST");
         System.out.println("Testing LOGIN with correct password");
         
       //  when(db.login("testuser", "password")).thenReturn(false);
      boolean test = db.login("testuser", "password");
      System.out.println("test output: " + test); // This returns false because we are using the mocked dbLayer.class
      //  mock(db.login());
      //when(db.login()).thenReturn(true);
         ArrayList<ArrayList<String>> testArray = db.getData("Aaa", "AAA");
         System.out.println(testArray);
      }else{
         System.out.println("TEST FALSE");
      
      }//end if
      
   } 


}// end mockLayer
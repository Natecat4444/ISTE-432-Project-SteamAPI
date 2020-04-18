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
      System.out.println(db);
      when(db.connect()).thenReturn(true);
      if(db.connect() == true ){
         System.out.println("TRUE TEST");
         System.out.println("Testing LOGIN with correct password");
         
       //  when(db.login("testuser", "password")).thenReturn(false);
         db.login("testuser", "password");
      }else{
         System.out.println("TEST FALSE");
      
      }//end if
      
   } 


}// end mockLayer
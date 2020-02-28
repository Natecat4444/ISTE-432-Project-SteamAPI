// DB Layer

import java.sql.*;
import java.util.*;

public class dbLayer{
   private final String URI = "jdbc:mysql://localhost/login?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
   private final String DRIVER = "com.mysql.cj.jdbc.Driver";
   private final String USER = "root";
   private final String PASS = "student";

   private Connection conn = null;
   
   public boolean connect(){
      try{
         Class.forName(DRIVER);
         conn = DriverManager.getConnection(URI, USER, PASS);

      }catch(Exception e){
      
      }//end trycatch
      return true;
   }//end connect
   public boolean close(){
      try{
         conn.close();
      }catch(Exception e){
         e.printStackTrace();
      }//end trycatch
      return true;
   }//end close

}//end dbLayer
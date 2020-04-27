// DB Layer
// user testHashing.java for testing purposes to test every method
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import java.sql.*;
import java.util.*;
import java.io.*;
public class dbLayer{
   private final String URI = "jdbc:mysql://localhost/login?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
   private final String DRIVER = "com.mysql.cj.jdbc.Driver";
   private final String USER = "root";
   private final String PASS = "student";

   private Connection conn = null;
   private ResultSet rs;
   private Statement stmt;
   PasswordStorage hash = new PasswordStorage();
// gets called by a method in the APILayer file   
   public boolean connect(){
      try{
         Class.forName(DRIVER);
         conn = DriverManager.getConnection(URI, USER, PASS);
      
      }catch(Exception e){
         e.printStackTrace();
         return false;
      }//end trycatch
      return true;
      // returns true if the connection to the login database is succesful
   }//end connect
   public boolean close(){
      try{
         conn.close();
      }catch(Exception e){
         e.printStackTrace();
      }//end trycatch
      return true;
      // returns false if the closing of the conn to the login database is successful
   }//end close
   
   
   
   public boolean login(String userName, String password){
      int colCount = 0;
      String sql = new String();
   //      System.out.println("User Inputted Password: " + password); 
      ArrayList<ArrayList<String>> newArray = getData(userName, password);
      String dbPassword = newArray.get(1).get(0);
   //     System.out.println("dbPassword = " + dbPassword); //used for debugging
      boolean checkHash = true;
      try{
         
         checkHash =  hash.verifyPassword(password, dbPassword); 
      //   System.out.println("CheckHash: " + checkHash);
      }catch(Exception e){
         e.printStackTrace();
      
      
      }//end try/catch
      return checkHash;
   }//end 
   
   
   
   public ArrayList<ArrayList<String>>getData(String username, String password){
      ArrayList<ArrayList<String>> allData = new ArrayList<ArrayList<String>>();
      ArrayList<String> colName = new ArrayList<String>();
      String sql = "Select pass from userinfo where username='" + username + "' ;";
   //     System.out.println("TESTING");
      try{
         Statement stmnt = conn.createStatement();
         ResultSet rs = stmnt.executeQuery(sql);
         ResultSetMetaData rsmd = rs.getMetaData();
         int numcols = rsmd.getColumnCount();
      
         for(int i = 1; i <= numcols; i++){
         
            colName.add(rsmd.getColumnName(i));
         
         }
         allData.add(colName);
         while(rs.next()){
            ArrayList<String> row = new ArrayList<String>();
            for(int i = 1; i <= numcols; i++){
               row.add(rs.getString(i));
               String test = row.get(0);
             // System.out.println(test);
            }//end if
            allData.add(row);
            System.out.println("row: " + row);
         }//end while    
         System.out.println(allData);
         String password1 = allData.get(1).get(0);
      
      
      }catch(SQLException sqle){
         sqle.printStackTrace();
      
      }catch(Exception e){
         e.printStackTrace();
      }
      return allData;
   }//end getData
   public int addFavorite(String username, String appid){
      int rows = 0;
      String sql = new String();
   //   String newPass = hash.createPassword(pass);
    //  System.out.println("hashed Password: " + pass);
      try{
      //create stmt and sql statement
         stmt = conn.createStatement();
         sql = "INSERT INTO favorites VALUES(\"" + username + "\",\"" + appid+"\");";
         System.out.println("sql: "+sql);
      
         rows = stmt.executeUpdate(sql); //inserts into the passenger table
      
      }catch(SQLIntegrityConstraintViolationException e){
         System.out.println("Already in Favorites!!");
         rows = 0;
      }catch(Exception e){
         e.printStackTrace();
         
      }//end try/catch
      return (rows);
   
   
   }//end register
   
   public int register(String username, String pass){
      int rows = 0;
      String sql = new String();
     
      
      try{
      String newPass = hash.createHash(pass);
      System.out.println("hashed Password: " + newPass);
      //create stmt and sql statement
      System.out.println(conn);
         Statement stmt1 = conn.createStatement();
         sql = "INSERT INTO userInfo VALUES( \"" + username+ "\",\"" + newPass+"\");";
         System.out.println("sql: "+sql);
      
         rows = stmt1.executeUpdate(sql); //inserts into the passenger table
      
      }catch(SQLException sqle){
         System.out.println("couldnt update with insert statement");
         sqle.printStackTrace();
      
      }catch (Exception e){
         e.printStackTrace();
      }//end try/catch
      return (rows);
   
   
   }//end register 
   public ArrayList<ArrayList<String>>getFavorites(String username){
      ArrayList<ArrayList<String>> allData = new ArrayList<ArrayList<String>>();
      ArrayList<String> colName = new ArrayList<String>();
      String sql = "Select appid from favorites where username = '"+username+"';";
    //  System.out.println(sql);
   //     System.out.println("TESTING");
      try{
         Statement stmnt = conn.createStatement();
         ResultSet rs = stmnt.executeQuery(sql);
         ResultSetMetaData rsmd = rs.getMetaData();
         int numcols = rsmd.getColumnCount();
      
         for(int i = 1; i <= numcols; i++){
         
            colName.add(rsmd.getColumnName(i));
         
         }
        // allData.add(colName);
         while(rs.next()){
            ArrayList<String> row = new ArrayList<String>();
            for(int i = 1; i <= numcols; i++){
               row.add(rs.getString(i));
               String test = row.get(0);
             // System.out.println(test);
            }//end if
            allData.add(row);
         }//end while    
         String password1 = allData.get(1).get(0);
      
      
      }catch(SQLException sqle){
         sqle.printStackTrace();
      
      }catch(Exception e){
         e.printStackTrace();
      }
      return allData;
   }//end getData

   
   @Test 
   public void testRun(){
      System.out.println("Testing DB LAYER");
      try{
         connect();
      
         login("testuser", "password");
         System.out.println("DB Tests passed!!");
      }catch(org.junit.ComparisonFailure e){
         System.out.println(e.toString()+" \nTest Failed!");
      
      }//end trycatch
   
   }//end testRun
}//end dbLayer
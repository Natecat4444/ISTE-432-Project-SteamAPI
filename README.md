Team Members and Roles
	Presentation Layer - Nathan Kaufman
	Business Layer -  Zhuguang Jiang (Jason)
	Application Layer - Andrew Kukielka
	Data Layer - Michael Reniere
Background
	Steam’s primary function is as a cloud based video game library and store. It allows users to buy their games and then play them on whatever device they wish. Many of the games within steam have their own news feeds, articles detailing content to be released in future updates, Q&As with developers, and other miscellaneous information that may be of interest to players or journalists, scattered about in an inconvenient way. Users need a better way to access the newsfeeds and the information held within. A newsfeed is a page of all recent news relating to a game. It can be anything from game updates to news about development. 
Project Description
	Users of the app will be able to search for games by name. Once the program has the name of the game, it will query the api for that game's unique identifier. Once the unique identifier has been found by the program, another API call will be made in order to retrieve that games news feed.
Project Req.
	Users will be able to either sign in or proceed as a guest. From here users will be able to search for games and view their associated news feed items. Users that login will be able to utilize a favorites list.
Business Rules
	Users need to log in in order to utilise favorites and modify preferences.
Technology Used
	Java
	Steam Web API
	MySQL
Design Patterns
	We will be using MVC as the design pattern for our app. MVC is the best available option because it allows for repetitive calls of different data in the same framework. For example, if a user wants to make different searches for different games, MVC will keep the process of making that search the same each time, despite the user wanting different results.
	The pattern we’re going to be using is MVC. The models will be objects that are used by the program to represent games and other software hosted by steam. The views would represent the front end ui which allows the user to enter their searches and view their favorites and selected newsfeeds. The controller will be used to call the steam api and get information from it such as newsfeed items and game ids and how much of each news item to show. Additionally it will be used to communicate with the database whenever a user saves or loads a game from their favorites.
Timeline
	Milestone 3 due Feb. 28th
		Layering
		Have our code separated into different layers (database, app, presentation)
		Plan exactly what code will be in what layer and why we did it that way
	Milestone 4 due march 20th
		Exception handling
		Add portion in our book of exceptions that we should be catching
		Plan where exceptions will be handled
		Milestone 5 due April 3rd
		Performance and refactoring
		Add section to book that has code samples that can improve the performance of the project
	Milestone 6 due april 17th
		Testing
		Test many different processes and actions that a user could do to the project
		Test many different scenarios
	Milestone 7 due April 24th
		Deployment packaging
		What will be with the code when it is deployed
		Ex: README, any packages that need to be installed
		Anything that the user needs to run the code without any issues and instructions on how to do so
		Final code due April 27th


```java

// Jar File is the classpath
//
//
import java.net.*;
import java.io.*;  // Import the File class
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;  // Import the IOException class to handle errors
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class CreateFile {
  public static void main(String[] args) throws Exception{
    
    
    URL url = new URL("http://api.steampowered.com/ISteamApps/GetAppList/v2");
        BufferedReader read = new BufferedReader(
        new InputStreamReader(url.openStream()));
        String i;
        String json = "";
         while ((i = read.readLine()) != null){
             json = json + i;
             }
      
      readJson(json);
      
  }
  
  public static void readJson(String jsonObj){
  
      JSONParser jsonParser = new JSONParser();
         
        try 
        {
            //Read JSON file
            JSONObject obj = (JSONObject) jsonParser.parse(jsonObj);
            JSONObject app = (JSONObject) obj.get("applist");
            JSONArray appList = (JSONArray) app.get("apps");
             
            //Iterate over app array
            appList.forEach( apps -> parseAppObject( (JSONObject) apps ) );
           
 
        } catch (ParseException e) {
            e.printStackTrace();
        }
  
  }
  
   private static void parseAppObject(JSONObject app) 
    {
        Long appid = (Long) app.get("appid");
         System.out.println(appid);
        
        
        String name = (String) app.get("name");
         System.out.println(name);
         
    }
}
```

Layers:
Presentation Layer:
	The presentation layer will be the sole layer that the user interacts with, taking input and displaying requested data it receives from the business layer. When completed, this layer will consist of the SteamGUI class. It will be responsible for taking and on some level checking user input before sending it on down to the business layer. This serves as the view model for the user.

Business Layer:
	The business layer acts as a middle man between the application and presentation layer as well as the data and presentation layer. All data obtained from the API calls will be filtered through the business layer and formatted into a usable format for the presentation layer here. Similarly, all data saved and received for or from the database will pass through here on its way to or from the presentation layer. It will use the Coordinator class that interacts with each other layer.

Application Layer:
The Application layer is responsible for handing the API calls in classes called appslist and newsfeeds. These classes will serve as controllers for the business layer.

Data Layer:
	This layer will be responsible for writing to and reading from the database in a class called dbLayer. This class will serve as a controller for the business layer.

The code contained here has two portions: one is a view of the database as it will be used by the data layer. The other is an example of the other three layers working together to obtain information about steam names and app ids.

dbLayer
```java

// DB Layer
 
import java.sql.*;
import java.util.*;
 
public class dbLayer{
   private final String URI = "jdbc:mysql://localhost/login?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
   private final String DRIVER = "com.mysql.cj.jdbc.Driver";
   private final String USER = "root";
   private final String PASS = "student";
 
   private Connection conn = null;
// gets called by a method in the APILayer file   
   public boolean connect(){
      try{
         Class.forName(DRIVER);
         conn = DriverManager.getConnection(URI, USER, PASS);
 
      }catch(Exception e){
      
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
 
}//end dbLayer

```

APILayer
```java
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;  // Import the File class
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class APILayer extends JFrame {

   HashMap<Long, String> hmap = new HashMap<Long, String>();

   public APILayer(){
   
      
      JPanel centerPanel = new JPanel();
      JButton keySearch = new JButton("AppID Search");
      JButton valueSearch = new JButton("Name Search");
      
      
      keySearch.setEnabled(false);
      valueSearch.setEnabled(false);
      setLayout(new BorderLayout());
     
      centerPanel.add(keySearch);
      centerPanel.add(valueSearch);
     
      keySearch.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               //Presentation UI Creation
               JPanel messagePanel = new JPanel();
               JTextField keyword = new JTextField(10);
               JLabel title = new JLabel("AppID:");
            
               messagePanel.add(title);
               messagePanel.add(keyword);
               JOptionPane.showMessageDialog(null,messagePanel); 
               //Business code interaction
               try{
                  Long key = Long. parseLong(keyword.getText());
                  String value = hmap.get(key);  
               
                  if(!value.equals(null)){   
                     System.out.println("AppID: "+key+" Name: "+value);
                  }
               }catch(NumberFormatException nfe){
                  System.out.println("Please enter AppID!");
               }catch(NullPointerException ne){
               
                  System.out.println("AppID invalid");
               }
                      
            }
         });
         
      valueSearch.addActionListener(
         new ActionListener(){
            public void actionPerformed(ActionEvent ae){
               JPanel messagePanel = new JPanel();
               JTextField keyword = new JTextField(10);
               JLabel title = new JLabel("Name:");
            
               messagePanel.add(title);
               messagePanel.add(keyword);
               JOptionPane.showMessageDialog(null,messagePanel); 
                
               try{
                  String value = keyword.getText();
                  Long key = 0L;
                  ArrayList<String> searchList = new ArrayList<String>();
                                       
                  for(Map.Entry entry: hmap.entrySet()){
                        boolean isFound = false;
                        String word = entry.toString();
                        isFound = word.contains(value);
                        
                        if (isFound == true){
                        Object oj = entry.getKey();
                        int inde = word.indexOf(value);
                        String name = word.substring(inde);
                        key = Long. parseLong(oj.toString());
                          searchList.add("AppID: "+key+" Name: "+name); 
                        }
                        
                         
                     
                  }
                  
                  if(key == 0L){
                     System.out.println("No game is match!");
                  }else{
                     for(String game: searchList){
                        System.out.println(game);
                     }
                     System.out.println("\n"+searchList.size()+" games are searched.");
                  }
                
               }catch(NumberFormatException nfe){
                  System.out.println("Please enter AppID!");
               }catch(NullPointerException ne){
               
                  System.out.println("AppID invalid");
               }
            }
         });
      //Presentation UI Creation
      add(centerPanel, BorderLayout.CENTER);
     
      pack();
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
         
      //Application API interaction  
      String json = "";
      try 
      {
         URL url = new URL("http://api.steampowered.com/ISteamApps/GetAppList/v2");
         BufferedReader read = new BufferedReader(
            new InputStreamReader(url.openStream()));
         String i;
         while ((i = read.readLine()) != null){
            json += i;
         }
               
      }catch(Exception e){
         System.out.println("Error with reading JSON");
      }     
      System.out.println("Loading to the appID ......");       
      readJson(json);
      System.out.println("Compeleted!\n");
      keySearch.setEnabled(true);
      valueSearch.setEnabled(true);
   }
   
   void readJson(String jsonObj){
   
      JSONParser jsonParser = new JSONParser();
         
      try 
      {
            //Read JSON String
         JSONObject obj = (JSONObject) jsonParser.parse(jsonObj);
         JSONObject app = (JSONObject) obj.get("applist");
         JSONArray appList = (JSONArray) app.get("apps");
          
            //Iterate over app array
         appList.forEach( apps -> parseAppObject( (JSONObject) apps ) );
      
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }
  
   private void parseAppObject(JSONObject app) 
   {
      Long appid = (Long) app.get("appid");
      String name = (String) app.get("name");
      
      hmap.put(appid,name);
         
   }
    
   public static void main(String[] args) throws Exception{
    
      new APILayer(); 
   }
}
```


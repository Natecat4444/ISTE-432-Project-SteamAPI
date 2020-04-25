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

Exception Handling:


```java
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

   try{
                   if(!keyword.getText().equals("")){
                      String value = keyword.getText();
                      Long key = 0L;
                      Long mainKey = 0L;
                      String mainName = "";
                      ArrayList<String> searchList = new ArrayList<String>();
                      System.out.println("");                    
                      for(Map.Entry entry: hmap.entrySet()){
                         boolean isFound = false;
                         String word = entry.toString();
                         isFound = word.contains(value);
                        
                         if (isFound == true){
                            Object oj = entry.getKey();
                            int inde = word.indexOf("=");
                            String name = word.substring(inde+1);
                            key = Long. parseLong(oj.toString());
                            searchList.add("AppID: "+key+" Name: "+name);
                            if(value.equals(entry.getValue())){
                               mainName = name;
                               mainKey = Long. parseLong(oj.toString());
                              
                            }  
                         }
                      }
                  
                      if(key == 0L){
                         System.out.println("No game are matched!");
                      }
                      else{
                         for(String game: searchList){
                            System.out.println(game);
                         }
                         System.out.println("\n"+searchList.size()+" games are searched.");
                     
                         if(mainKey != 0L){
                            System.out.println("\nOne game is exactly matched: AppID: "+mainKey+" Name: "+mainName);
                         }else if(mainKey == 0L){
                            System.out.println("\nNo game are exactly matched!");
                         }else{
                            System.out.println("No game are matched!");
                         }
                      }
                   }else{
                      System.out.println("Please enter the Name!");
                   }
                }catch(NumberFormatException nfe){
                   System.out.println("Please enter the AppID!");
                }catch(NullPointerException ne){
               
                   System.out.println("AppID invalid");
                }

```

The majority of exceptions will occur from one of two sources: user input, or data returned from the API. Exceptions occurring from user input will not occur in the presentation layer. This is due to the fact that the data itself is automatically passed from the presentation layer to the business layer, where it is then checked. Additionally, errors should not be presented to the user, only information, such as “Your search returned no results”. This keeps back-end information being passed to the user at a minimum, and also creates a good point of consolidation for exception handling.

Exceptions occurring from the API will also be passed to the business layer, as it is where information is always passed to before being sent to the user in the presentation layer. The main example of this kind of exception is when a game has no items in its news feed. In this case, the exception will be caught in the application layer where it happens, and passed to the business layer. Afterwards, the info will be passed to the presentation layer, where the user will see it.

There are a few other exceptions that can occur that do not fall under these two main categories. An incorrect username/password will be caught in the data layer, and passed to the business layer, where the info will then be sent to the user in the presentation layer. There is also a possibility of the Steam API being down. This will be automatically caught when the application first attempts to retrieve the full list of games, in which case the info can be sent from the application layer to the presentation layer after passing through the business layer. The last category is if the database goes down. If the user attempts to log-in, this will be caught automatically, and info sent to both the user and the DBA, so that it can be fixed ASAP. If the database is down, the user can still use the application while logged out, but the favorites feature will not be available to them.


Milestone 5: Refactoring:

The main way we’re improving performance is by saving the list of steam games client-side when the user loads up the application. When the application first begins, an API call is made to the Steam servers that fetches every software on Steam, and saves the results locally. The reason for this is that the only way to search the Steam API for games is to get a list of every single piece of software on the service. Thus, instead of getting a list of every game through the API any time the user wants to make a search, the list can instead be saved client-side, minimizing the number of API calls that need to be made. This code is located on lines 17-40 in the Application Layer, and is called in lines 19-23 in the Business Layer.

The main set of refactoring we did was in the Business Layer. When we first made the layer, all the code was created inside of the constructor, as it was just used to separate it into the layer at the start. Now that we’re involving the Presentation Layer, however, we moved the code out into separate methods, so they are more easily run by the other layers. This code can be found below line 28 in the Business Layer, with the original code commented out.

```java

import java.util.*;
// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.*;
import java.net.*;
import java.io.*;  // Import the File class
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org. json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class BusinessLayer{

   HashMap<Long, String> hmap = new HashMap<Long,String>();
   
   
   public BusinessLayer(){
   
   ApplicationLayer al = new ApplicationLayer();
      hmap = al.run();
      while((hmap == null)==true){
         al.run();
      } 
   
   
   }
      
     //  JPanel centerPanel = new JPanel();
//       JButton keySearch = new JButton("AppID Search");
//       JButton valueSearch = new JButton("Name Search");
//       
//       
//       keySearch.setEnabled(false);
//       valueSearch.setEnabled(false);
//       setLayout(new BorderLayout());
//      
//       centerPanel.add(keySearch);
//       centerPanel.add(valueSearch);
//      
//       keySearch.addActionListener(
//          new ActionListener(){
//             public void actionPerformed(ActionEvent ae){
//                //Presentation UI Creation
//                JPanel messagePanel = new JPanel();
//                JTextField keyword = new JTextField(10);
//                JLabel title = new JLabel("AppID:");
//             
//                messagePanel.add(title);
//                messagePanel.add(keyword);
//                JOptionPane.showMessageDialog(null,messagePanel); 
               //Business code interaction
               
         public String searchFromKey(String keyString){
         
               try{
                  Long key = Long. parseLong(keyString);
                  String value = hmap.get(key);  
               
                  if(!value.equals(null)){   
                     return ("AppID: "+key+" Name: "+value);
                  }
               }catch(NumberFormatException nfe){
                  return ("Please enter AppID!");
               }catch(NullPointerException ne){
                  return ("AppID invalid");
               }
                  return ("AppID invalid");    
            }
         // });
         
      // valueSearch.addActionListener(
//          new ActionListener(){
//             public void actionPerformed(ActionEvent ae){
//                JPanel messagePanel = new JPanel();
//                JTextField keyword = new JTextField(10);
//                JLabel title = new JLabel("Name:");
//             
//                messagePanel.add(title);
//                messagePanel.add(keyword);
//                JOptionPane.showMessageDialog(null,messagePanel); 
//                 

         public ArrayList<String> searchFromValue(String valueString){
         
         ArrayList<String> searchList = new ArrayList<String>();
               try{
                  if(!valueString.equals("")){
                     String value = valueString;
                     Long key = 0L;
                     Long mainKey = 0L;
                     String mainName = "";
                     
                     System.out.println("");                     
                     for(Map.Entry entry: hmap.entrySet()){
                        boolean isFound = false;
                        String word = entry.toString();
                        isFound = word.contains(value);
                        
                        if (isFound == true){
                           Object oj = entry.getKey();
                           int inde = word.indexOf("=");
                           String name = word.substring(inde+1);
                           key = Long. parseLong(oj.toString());
                           searchList.add("AppID: "+key+" Name: "+name);
                           if(value.equals(entry.getValue())){
                              mainName = name;
                              mainKey = Long. parseLong(oj.toString());
                              
                           }   
                        }
                     }
                  
                     if(key == 0L){
                        searchList.add("No game are matched!");
                     }
                     else{
                        for(String game: searchList){
                           System.out.println(game);
                        }
                        searchList.add("\n"+searchList.size()+" games are searched.");
                     
                        if(mainKey != 0L){
                           searchList.add("\nOne game is exactly matched: AppID: "+mainKey+" Name: "+mainName);
                        }else if(mainKey == 0L){
                           searchList.add("\nNo game are exactly matched!");
                        }else{
                           searchList.add("No game are matched!");
                        }
                     }
                  }else{
                     searchList.add("Please enter the Name!");
                  }
               }catch(NumberFormatException nfe){
                  searchList.add("Please enter the AppID!");
               }catch(NullPointerException ne){
               
                  searchList.add("AppID invalid");
               }
               
               return searchList;
            }
         // });
      //Presentation UI Creation
      // add(centerPanel, BorderLayout.CENTER);
//      
//       pack();
//       setLocationRelativeTo(null);
//       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//       setVisible(true);
         
      //Application API interaction  
         
      
      // keySearch.setEnabled(true);
//       valueSearch.setEnabled(true);
  // }
   
   
    
   public static void main(String[] args) throws Exception{
   
      
      new BusinessLayer();
   }
}

```

Milestone 6: Testing



The testing framework we use for this program is JUnit. There are 3 major tests we are currently performing, one in each of the application, business, and data layers. While all the test methods are located in their respective layers, we have a dedicated class for the tests that calls them all, called TestJunit1.java. This class processes each test one by one, signalling when each starts and whether it is successful or if it failed. Each of the tests is meant to make sure the connection between the application and the destination, whether it is the database or the Steam API, is active and working.


The test in the application layer is located from line 97 to line 112. The purpose of this test is to make sure the program can retrieve news information from the API. To do this, it connects to the API and retrieves the news list for a sample game. The test checks to make sure that retrieving the news feed does indeed return something. For this test, the game chosen is the ID 500, which corresponds to “Left 4 Dead”, an older game released in 2008. Because it is released by Valve, who are the creators of Steam, and due to its age, it is highly unlikely for the game to be removed from the service for any reason, so it should always return something. This means that if there is an error, the most likely cause is the connection between the program and the API being broken.



The test in the business layer is located from line 110 to line 128. The purpose of this test is to make sure the user is able to reliably search through the applications on Steam. When the program first starts up, it fetches all of the games on steam through the API, and saves them to an array. This is done because the only way to find a game on Steam in the API is to grab a list of all of them. When the user is searching through games, they are actually searching this array. The unit test makes sure that this array is functioning. It first checks to make sure that the searching functions are working by feeding it a pair of searches that shouldn’t come back with anything. Then it checks to make sure that the searching the array properly works by searching for a sample game. This game is ID 699480, which corresponds to “The Doorbreaker”. The reason this game was chosen is due to how the searching works - picking a game like Left 4 Dead will come up with a large list of results, due to large amounts of DLC, a sequel, and more. The Doorbreaker, on the other hand, is made by a small studio and has no DLC, which means it will always have just one result.


The test in the data layer is located from line 104 to line 117. The purpose of this test is to make sure the program is able to connect to the database, and that users are able to login. First, the test attempts to connect to the database, as it normally would when a user logs in. If this doesn’t work, it means the database is offline or disconnected. If it does work, the test then attempts to log in with hard-coded test credentials saved in the database. This serves to test a few things - first, it tests that the database currently has all the data it should have, as if the test credentials are missing it means the database was wiped somehow. It also tests to make sure the password hashing method is working properly, which is essential for logging in and to make sure users’ information is secure.


Milestone 7: Installation


To be able to run this application, the user should download it from https://github.com/Natecat4444/ISTE-432-Project-SteamAPI, choosing the “Download ZIP” option. Once downloaded, the user should extract the folder to a location of their choice. The user will need the following files in that folder:


createDB.sql
SteamNews.jar
run.bat (For Windows users only)
For users that want to be able to create an account and log-in to save their favorite games, they will need MySQL and the createDB.sql file. Running that file in MySQL will create the database necessary to be able to save favorite games.
Once the files necessary are downloaded, and the database is created, the user can launch the program. If the user is on Mac or Linux, they can run SteamNews.jar to launch the program. Windows users should use the run.bat file instead to launch, though they will still need to have the SteamNews.jar file in that folder to be able to do so. Users on any operating system are also able to run the program by opening the folder in command prompt and typing “java -jar SteamNews.jar”.

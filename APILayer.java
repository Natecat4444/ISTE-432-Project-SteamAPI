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
              
               JPanel messagePanel = new JPanel();
               JTextField keyword = new JTextField(10);
               JLabel title = new JLabel("AppID:");
            
               messagePanel.add(title);
               messagePanel.add(keyword);
               JOptionPane.showMessageDialog(null,messagePanel); 
                
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
                  Hashtable table = new Hashtable();
                  
                  for(Map.Entry entry: hmap.entrySet()){
                     if(value.equals(entry.getValue())){
                        Object oj = entry.getKey();
                        key = Long. parseLong(oj.toString());
                        
                        break; 
                     }
                  }
                  if(key == 0L){
                     System.out.println("Value: '"+value+"' not found!");
                  }else{
                  System.out.println("AppID: "+key+" Name: "+value);
                  }
                  //long key = hmap.get(value);  
                  //System.out.println("AppID: "+key);
                
               }catch(NumberFormatException nfe){
                  System.out.println("Please enter AppID!");
               }catch(NullPointerException ne){
               
                  System.out.println("AppID invalid");
               }
            }
         });
     
      add(centerPanel, BorderLayout.CENTER);
     
      setSize(400,60);
      setLocationRelativeTo(null);
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setVisible(true);
         
         
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
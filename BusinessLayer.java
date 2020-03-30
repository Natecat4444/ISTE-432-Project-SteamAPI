import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;  // Import the File class
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org. json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class BusinessLayer extends JFrame {

   HashMap<Long, String> hmap = new HashMap<Long, String>();
   ApplicationLayer al = new ApplicationLayer();
   public BusinessLayer(){
   
      
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
      hmap = al.run();
      while((hmap == null)==true){
         al.run();
      }
      keySearch.setEnabled(true);
      valueSearch.setEnabled(true);
   }
   
   
    
   public static void main(String[] args) throws Exception{
    
      new BusinessLayer(); 
   }
}
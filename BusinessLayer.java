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
import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import java.util.*;
import java.net.*;
import java.io.*;  // Import the File class
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class BusinessLayer{

   private HashMap<Long, String> hmap = new HashMap<Long,String>();
   private ApplicationLayer al = new ApplicationLayer();
   
   public BusinessLayer(){
   
   
      hmap = al.run();
      while((hmap == null)==true){
         al.run();
      } 
   
   
   }
      
               
         public String searchFromKey(String keyString){
         
               try{
                  Long key = Long. parseLong(keyString);
                  String value = hmap.get(key);  
               
                  if(!value.equals(null)){ 
                     
                     String appid = String.valueOf(key);
                      
                     
                     return ("AppID: "+key+" Name: "+value+"\n"+al.NewsInfo(appid));
                     
                  }
               }catch(NumberFormatException nfe){
                  return ("Please enter AppID!");
               }catch(NullPointerException ne){
                  return ("AppID invalid");
               }
                  return ("AppID invalid");    
            }
       
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
   @Test
   public void  testRun() {
   
        
     try{   
      System.out.println("Start Testing Business Layer searchFromKey()!");

     
       assertEquals("Please enter AppID!", searchFromKey("asd"));
       assertEquals("AppID invalid", searchFromKey("1"));
       String name = "The Doorbreaker";
       assertEquals("AppID: 699480 Name: "+name+"\n"+al.NewsInfo("699480"), searchFromKey("699480"));
     
        System.out.println("Test passed!");
      }catch(org.junit.ComparisonFailure e){
         System.out.println(e.toString()+"\nTest Failed! ");
         e.printStackTrace();
      }
   }
    
   public static void main(String[] args) throws Exception{
   
      
      new BusinessLayer();
   }
}
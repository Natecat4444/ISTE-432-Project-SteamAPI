import java.util.*;
import java.net.*;
import java.io.*; 
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org. json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class ApplicationLayer{

   HashMap<Long, String> hmap = new HashMap<Long, String>();
   
   public ApplicationLayer(){
   }
   
   
   public HashMap<Long, String> run(){
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
      while((hmap == null)==true){
         readJson(json);
      }
      System.out.println("Compeleted!\n");
      
      return hmap;
   }
   
   void readJson(String jsonObj){
   
      
         
      try 
      {
      
      
         JSONParser jsonParser = new JSONParser();
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

   
  
   
   
   
   public static void main(String [] args){
      
         
   }


}
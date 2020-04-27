import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*; 
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org. json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public class ApplicationLayer{

   private HashMap<Long, String> hmap = new HashMap<Long, String>();
   private ArrayList<String> al = new ArrayList<String>();
   
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
   public ArrayList<String> NewsInfo(String key){
   al.removeAll(al);
   String json="";
      try{
         URL newsUrl = new URL("https://api.steampowered.com/ISteamNews/GetNewsForApp/v2/?appid="+key);
          BufferedReader read = new BufferedReader(
            new InputStreamReader(newsUrl.openStream()));
         String i;
         while ((i = read.readLine()) != null){
            json += i;
         }
         GetNews(json);
         
         
       }catch(Exception e){
         System.out.println("Eorror on getting the news informations!");
       }  
        
        return al;
   }
   
   @Test
   public void testNewsInfo(){
   
   try{   
      System.out.println("Start Testing Application Layer NewsInfo()!");

     
       assertEquals(al, NewsInfo("500"));
     
        System.out.println("Test passed!");
      }catch(org.junit.ComparisonFailure e){
         System.out.println(e.toString()+"\nTest Failed! ");
      }

   
   }
   
   private void GetNews(String jsonObj){
   
      JSONParser jsonParser = new JSONParser();
         
      try 
      {
            //Read JSON String
         JSONObject obj = (JSONObject) jsonParser.parse(jsonObj);
         JSONObject app = (JSONObject) obj.get("appnews");
         JSONArray newslist = (JSONArray) app.get("newsitems");
          
            //Iterate over app array
        newslist.forEach( news -> parseNewsObject( (JSONObject) news ) );
      
      } catch (ParseException e) {
         e.printStackTrace();
      }
   }
   private void parseNewsObject(JSONObject news) 
   {
      String contents = (String) news.get("contents");
      String newsTitle = (String) news.get("title");
      Long unix_seconds = (Long) news.get("date");
      
      Date date = new Date(unix_seconds*1000L); 
      // format of the date
      SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
      jdf.setTimeZone(TimeZone.getTimeZone("GMT-4"));
      String java_date = jdf.format(date);
      
      
      
      
      al.add("News Title: "+newsTitle);
      al.add("Date: "+java_date+"\n");
      al.add(contents);
      
         
   }




}
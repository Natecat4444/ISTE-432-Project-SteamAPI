import org.junit.Test;
import org.junit.Ignore;
import static org.junit.Assert.assertEquals;

@Ignore
public class TestJunit1 {

   
   BusinessLayer bl = new BusinessLayer();
   ApplicationLayer al = new ApplicationLayer();
   dbLayer db = new dbLayer();   
   public  TestJunit1(){
      
         
    bl.testRun();
    al.testNewsInfo();
    db.testRun();
   }
   
   public static void main(String [] args){
   
      new TestJunit1();
      
   }

	
}

  
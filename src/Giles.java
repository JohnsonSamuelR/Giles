
public class Giles{
   public static void main(String args[]){      
      Console UI = new Console("Giles, Text Input");
      //add check to see time of day. E.g. morning, afternoon, evening
      UI.prompt("Good morning, sir. How may I be of assistance?");
      String input;
      OptimusPrime panzer = new OptimusPrime(".data/Optimus/");
      while(true){
         input = UI.getInput();
         UI.prompt(String.valueOf((int)'a'));
      }
   }
}
enum Intents{
   CHAT,//default intent, doesn't match any other intent
   MATH,//perform basic math operations
   WEATHER,//check the weather
   READ,//read text from a website
   OPEN_FILE;//find and open a file on the current hardware
   
   private Intents(){}
}
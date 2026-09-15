public class Giles{
   public static void main(String args[]){      
      Console UI = new Console("Giles, Text Input");
      //add check to see time of day. E.g. morning, afternoon, evening
      String input;
      UI.prompt("Good morning, sir. How may I be of assistance?");//opening prompt
      while(true){
         input = UI.getInput();
         ICBM.process(input);
         UI.prompt("Okay, so, I can't talk back yet. But see console for my interpretation of your intent!");
      }
   }
}

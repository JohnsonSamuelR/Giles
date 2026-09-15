public class Giles{
   public static void main(String args[]){      
      Console UI = new Console("Giles, Text Input");
      String input;
      UI.prompt("Good morning, sir. How may I be of assistance?");//opening prompt
      while(true){
         //gets input
         input = UI.getInput();
         //tells the intent based classifier to process the text
         ICBM.process(input);
         //tells the generative sequence model to process the text (nowhere near done yet)

         //returns output to the user
         UI.prompt("Okay, so, I can't talk back yet. But see console for my interpretation of your intent!");
      }
   }
}

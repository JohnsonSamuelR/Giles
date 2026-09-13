import java.util.StringTokenizer;

public class IntentBasedClassifier{
NeuralNetwork nn;
String[] vocab = TrainingData.getVocabList();
   public IntentBasedClassifier(){
      //score
      //train nn
      //nn = new NeuralNetwork(new int[]{maxInputLength,____,Intents.values().length)
      //pass through nn
      //execute intent
   }
   public void tokenize(String big){
      //breaking into words
      StringTokenizer tizer = new StringTokenizer(big);
      String[] tokens = new String[tizer.countTokens()];
      for(int i=0;i<tokens.length;i++){
         tokens[i] = tizer.nextToken();
      }
      
      int[] tokenIDs = new int[tokens.length];
      for(int i=0;i<tokenIDs.length;i++){
         for(int j=0;j<vocab.length;j++){
            if(tokens[i].equals(vocab[j])){
               tokenIDs[i] = j;
               break;
            }
         }
      }
   }
}
enum Intents{
   MATH,
   WEATHER,
   CHAT,
   OPEN_FILE,
   FIND;
   
   private Intents(){}
}
public class TrainingData{
   private static String[] vocabList = new String[]{"a","abacus","abalone","abandoned","an"};
   public static String[] getVocabList(){
      return vocabList;
   }
   
   
   public static float[][][] XOR(){
      float[][][] trainingData = new float[4][2][97];//[batch][layer][row]
      trainingData[0][0] = new float[]{0,0};
      trainingData[1][0] = new float[]{0,1};
      trainingData[2][0] = new float[]{1,0};
      trainingData[3][0] = new float[]{1,1};
      
      trainingData[0][1] = new float[]{0};
      trainingData[1][1] = new float[]{1};
      trainingData[2][1] = new float[]{1};
      trainingData[3][1] = new float[]{0};
      return trainingData;
   }
}
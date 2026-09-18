import java.util.*;
import java.io.*;

public class OptimusPrime{
   //tokens/vocab are only whole words
   
   //training data stored in dir/data.giles
   //table stored in dir/table.giles
   //vocab stored in dir/vocab.giles
   
   private String dir;
   //private float[][] table;
   private int dmodel;
   private float lr;
   
   public OptimusPrime(int dmodel,String dir){
      this.lr = (float)0.5;
      this.dir = dir;
      this.dmodel = dmodel;
      if(!Matrix.exists(dir + "table.giles")){
         float[][]temp = new float[2][];
         for(int i=0;i<temp.length;i++){
            temp[i] = Matrix.randomise(new float[this.dmodel]);
         }
         Matrix.write(dir + "table.giles",temp);
      }
      if(!Matrix.exists(dir + "vocab.giles")){
         Matrix.write(dir + "table.giles",new String[]{"initial","vocab"});
      }
   }
   public OptimusPrime(String dir){
      this(512,dir);
   }
   
   public float[][] embed(String whole){
      //breaks input into words
      StringTokenizer buster = new StringTokenizer(whole);
      String[] sentence = new String[buster.countTokens()];
      for(int i=0;i<sentence.length;i++){
         sentence[i] = buster.nextToken();
      }
      float result[][] = new float[sentence.length][this.dmodel];
      //loops through each word in the input sentence
      for(int z=0;z<sentence.length;z++){
         String input = sentence[z];
         int dir = 0;
         //checks if we already know that word
         if(vocabLookup(input) == -1){
            //if we don't, add it the vocab
            addWord(input);
            //if its not already there....
            String data = "";
            for(String bit : sentence){
               data+=bit+" ";
            }
            List<String> dataList = new ArrayList<>(Arrays.asList(getData()));
            if(!dataList.contains(data)){
               //add whole sentence to training data
               try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.dir + "data.giles", true))) {
                  writer.write(data);
                  writer.newLine();
               } catch (IOException e) {
                  e.printStackTrace();
               }
            }
            //retrain to make ouput at least a little more accurate
            train();
         }
         //find directory of the word in vocab and record the array
         result[z] = getTable()[vocabLookup(input)];
      }
      return result;
   }
   public void train(){
      for(int z=0;z<getData().length;z++){
         //breaks it into words and cleans training data
         StringTokenizer buster = new StringTokenizer(getData()[z].toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", ""));
         String[] words = new String[buster.countTokens()];
         for(int i=0;i<words.length;i++){
            words[i] = buster.nextToken();
         }
         //for each word, this....
         for(int i=0;i<words.length;i++){
            //if it's not already there....
            System.out.println("its in vocabLookup()");
            if(vocabLookup(words[i]) == -1){
               addWord(words[i]);
            }
            //pairs it with words before and adjusts the weights
            float[][] table = getTable();
            for(int j=0;j<i;j++){
               System.out.println(j);
               float[] temp = Matrix.add(table[vocabLookup(words[i])],Matrix.multiply(table[vocabLookup(words[j])],this.lr));
               table[i] = temp;
            }
            //pairs it with words after and adjusts the weights
            for(int j=words.length-1;j>i;j--){
               float[] temp = Matrix.add(table[vocabLookup(words[i])],Matrix.multiply(table[vocabLookup(words[j])],this.lr));
               table[i] = temp;
            }
            Matrix.write(dir + "table.giles",table);
         }
      }
   }
   private int vocabLookup(String word){
      int result = -1;
      for(int i=0;i<getVocab().length;i++){
         System.out.println(i);
         if(word == getVocab()[i]){
            System.out.println("\n\n\n\n");
            return i;
         }
      }
      System.out.println("\n\n\n\n");
      return -1;
   }
   private void addWord(String word){
      //adds word to the vocab
      List<String> tempVocab = new ArrayList<>(Arrays.asList(getVocab()));
      tempVocab.add(word);
      Matrix.write(this.dir + "vocab.giles",tempVocab.toArray(new String[0]));
      //and the table 
      List<float[]> tempTable = new ArrayList<>(Arrays.asList(getTable()));
      tempTable.add(Matrix.randomise(new float[dmodel]));
      Matrix.write(this.dir + "table.giles",tempTable.toArray(new float[0][dmodel]));
   }
   private String[] getData(){
      String[] data = new String[97];
      //get the next line of training data from file
      try (BufferedReader reader = new BufferedReader(new FileReader(this.dir + "data.giles"))) {
         ArrayList<String> tempData = new ArrayList<>();
         String line = reader.readLine();
         while(line != null){
            tempData.add(line);
            line = reader.readLine();
         }
         reader.close();
         data = tempData.toArray(String[]::new);
      }catch (IOException e){
         e.printStackTrace();
      }
      return data;
   }
   private String[] getVocab(){
      return Matrix.readStrings(dir + "vocab.giles");
   }
   private float[][] getTable(){
      return Matrix.read2dFloats(dir + "table.giles");
   }
}
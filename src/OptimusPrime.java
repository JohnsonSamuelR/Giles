import java.util.*;
import java.io.*;

public class OptimusPrime{
   //tokens/vocab are only whole words
   //sentences are summed/compressed into one vector
   
   //training data stored in dir/data.giles
   //table stored in dir/table.giles
   //vocab stored in dir/vocab.giles
   
   private String dir;
   private float[][] table;
   private String[] vocab;
   private int dmodel;
   private float lr;
   
   public OptimusPrime(int dmodel,String dir){
      this.lr = (float)0.5;
      this.dir = dir;
      this.dmodel = dmodel;
      this.vocab = new String[]{"initial","vocab"};//basic, initial vocab. Not final.
      //if weights data file exists
         //retrieve needed weights
      //else if ONLY training data file exists
         this.table = Matrix.randomise(new float[vocab.length][dmodel]);
         train();
      //else
         //error out saying you need one of the two
   }
   public OptimusPrime(String dir){
      this(512,dir);
   }
   
   public float[][] embed(String whole){
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
         if(!vocabHasWord(input)){
            //if we don't, add it the vocab
            addWord(input);
            //add whole sentence to training data
            String path = this.dir + "data.giles";
            String data = "";
            for(String bit : sentence){
               data+=bit+" ";
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path, true))) {
               writer.write(data);
               writer.newLine();
            } catch (IOException e) {
               e.printStackTrace();
            }
            //retrain to make ouput at least a little more accurate
            train();
         }
         //find directory of the word in vocab and record the array
         for(int i=0;i<vocab.length;i++){
            if(vocab[i].equals(input)){
               result[z] = table[i];
               break;
            }
         }
      }
      return result;
   }
   public void train(){
      String[] data = new String[97];
      String path = this.dir + "data.giles";
      //get the next line of training data from file
      try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
         ArrayList<String> tempData = new ArrayList<>();
         String line = reader.readLine();
         while(line != null){
            tempData.add(line);
            line = reader.readLine();
         }
         reader.close();
         data = tempData.toArray(String[]::new);
      }catch (IOException e){
         System.out.println("Retrieving training data from " + path + " failed.");
         e.printStackTrace();
      }
      for(int z=0;z<data.length;z++){
         //breaks it into words and cleans training data
         StringTokenizer buster = new StringTokenizer(data[z].toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", ""));
         String[] words = new String[buster.countTokens()];
         for(int i=0;i<words.length;i++){
            words[i] = buster.nextToken();
         }
         //for each word, this....
         for(int i=0;i<words.length;i++){
            //if it's not already there....
            if(!vocabHasWord(words[i])){
               addWord(words[i]);
            }
            //pairs it with words before and adjusts the weights
            for(int j=0;j<i;j++){
               float[] temp = Matrix.add(table[i],Matrix.multiply(table[j],this.lr));
               table[i] = temp;
            }
            //pairs it with words after and adjusts the weights
            for(int j=words.length-1;j>i;j--){
               float[] temp = Matrix.add(table[i],Matrix.multiply(table[j],this.lr));
               table[i] = temp;
            }
         }
      }
      //records table
      path = this.dir + "table.giles";
      Matrix.write(path,this.table);
   }
   private void addWord(String word){
      //adds word to the vocab
      List<String> tempVocab = new ArrayList<>(Arrays.asList(this.vocab));
      tempVocab.add(word);
      this.vocab = tempVocab.toArray(new String[0]);
      //and the table 
      List<float[]> tempTable = new ArrayList<>(Arrays.asList(this.table));
      tempTable.add(Matrix.randomise(new float[dmodel]));
      this.table = tempTable.toArray(new float[0][dmodel]);
   }
   private boolean vocabHasWord(String word){
      //checks if vocab Has Word
      boolean result = false;
      for(String vocab : this.vocab){
         if(word.equals(vocab)){
            result = true;
            break;
         }
      }
      return result;
   }
}
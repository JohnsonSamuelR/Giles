import java.util.*;
import java.io.*;

public class OptimusPrime{
   //needs file retrievals and writing to be finished
   //needs adjustable size table
   //tokens/vocab are only whole words
   
   private String dir;
   private float[][] table;
   private String[] vocab;
   private int dmodel;
   
   public OptimusPrime(int dmodel,String dir){
      this.dir = dir;
      this.dmodel = dmodel;
      this.vocab = new String[]{"initial","vocab"};//basic, initial vocab. Not final.
      //if weights data file exists
         //retrieve needed weights
      //else if ONLY training data file exists
         this.table = Matrix.randomise(new float[vocab.length][dmodel]);
         train(this.dir);
      //else
         //error out saying you need one of the two
   }
   public OptimusPrime(String dir){
      this(512,dir);
   }
   
   public void encode(String input){
      
   }
   public void decode(String input){
      
   }
   public void train(String dir){
      //tokenizer that builds it's own vocab from training data - attention based
      String data = null;
      String path = dir + "data.giles";
      for(int z=1;z>0;z++){
         //get the next line of training data from file
         try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            data = reader.readLine();
            reader.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
         if(data != null){
            //breaks it into words and cleans training data
            StringTokenizer buster = new StringTokenizer(data.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", ""));
            String[] words = new String[buster.countTokens()];
            for(int i=0;i<words.length;i++){
               words[i] = buster.nextToken();
            }
            //for each word, this....
            for(int i=0;i<words.length;i++){
               //if it's not already there....
               if(!tempVocab.contains(words[i])){
                  //adds it to the vocab
                  List<String> tempVocab = new ArrayList<>(Arrays.asList(this.vocab));
                  tempVocab.add(words[i]);
                  this.vocab = tempVocab.toArray(new String[0]);
                  //and the table 
                  List<float[]> tempTable = new ArrayList<>(Arrays.asList(this.table));
                  tempTable.add(Matrix.randomise(new float[dmodel]));
                  this.table = tempTable.toArray(new float[0][]);
               }
               for(int j=0;j<i;j++){
                  //pairs it with words before
                  //adjusts the weights
               }
               for(int j=words.length-1;j>i;j--){
                  //and words after
                  //(also adjusting weights)
               }
            }
         }else{
            z = -97;
         }
      }
   }
}
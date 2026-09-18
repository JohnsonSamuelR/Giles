public class IntentBasedClassifier{
   private NeuralNetwork nn;
   private OptimusPrime tokenizer;
   private int dmodel;
   private String dir;
   private float[][] intents;
   
   public IntentBasedClassifier(String dir){
      //field initialisation
      this.intents = Matrix.randomise(new float[Intents.values().length][dmodel]);
      this.dir = dir;
      this.dmodel = 512;
      this.nn = new NeuralNetwork(this.dir + "NeuralNet/",new int[]{dmodel,dmodel,dmodel});
      this.tokenizer = new OptimusPrime(dmodel,dir + "Optimus/");
      //training the software
      update();
   }
      
   public void process(String input){
      //use OptimusPrime (text transformer) to embed
      float[][] embeddings = tokenizer.embed(input);
      //runs each embedding through nn
      float[][] processed = new float[embeddings.length][];
      for(int i=0;i<processed.length;i++){
         processed[i] = nn.feedForward(embeddings[i]);
      }
      //calculates probability for each intent
      float[]probs = new float[Intents.values().length];
      for(int i=0;i<probs.length;i++){
         probs[i] = Matrix.sum(Matrix.subtract(intents[i],processed[i]));
      }
      //calculates most probable intent
      int place = 0;
      float highest = 0;
      for(int i=0;i<probs.length;i++){
         if(probs[i] > highest){
            highest = probs[i];
            place = i;
         }
      }
      //executes
      Intents.values()[place].execute();
   }
   private void update(){
      //train tokenizer
      System.out.println("update() method started....");
      tokenizer.train();
      System.out.println("tokenizer is trained....");
      //train the nn
      float[][][] data = new float[Intents.values().length][2][97];
      data[0][0] = tokenizer.embed("chat")[0];
      data[0][1] = intents[0];
      data[1][0] = tokenizer.embed("math")[0];
      data[1][1] = intents[1];
      data[2][0] = tokenizer.embed("weather")[0];
      data[2][1] = intents[2];
      data[3][0] = tokenizer.embed("read")[0];
      data[3][1] = intents[3];
      data[4][0] = tokenizer.embed("call")[0];
      data[4][1] = intents[4];
      data[5][0] = tokenizer.embed("email")[0];
      data[5][1] = intents[5];
      data[6][0] = tokenizer.embed("update")[0];
      data[6][1] = intents[6];
      data[7][0] = tokenizer.embed("open")[0];
      data[7][1] = intents[7];
      System.out.println("training data created....");
      System.out.println(nn.train(data));
      System.out.println("neural net is trained....");
   }
}
enum Intents{
   CHAT("chat"),//default intent, doesn't match any other intent
   MATH("math"),//perform basic math operations
   WEATHER("weather"),//check the weather
   READ("read"),//read text from a text file
   CALL("call"),//call someone
   EMAIL("email"),//send an email
   UPDATE("update"),//update all weights, biases, and tables
   OPEN_FILE("open file");//find and open a file on the current hardware
   
   private String printable;
   
   private Intents(String printable){
      this.printable = printable;
   }
   
   public void execute(){
      System.out.println(printable);
   }
}
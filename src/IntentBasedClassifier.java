public class IntentBasedClassifier{
   private NeuralNetwork nn;
   private OptimusPrime tokenizer;
   private int dmodel;
   private String dir;
   private float[][] intents;
   
   public IntentBasedClassifier(){
      //field initialisation
      this.intents = Matrix.randomise(new float[Intents.values().length][dmodel]);
      this.dir = ".data/ICBM/";
      this.dmodel = 512;
      int length = Intents.values().length;
      this.nn = new NeuralNetwork(new int[]{dmodel,dmodel,dmodel});
      this.tokenizer = new OptimusPrime(dmodel,dir + "Optimus/");
      //training the software
      update();
   }
      
   public void process(String input){
      //use OptimusPrime (text transformer) to embed
      float[][] embeddings = tokenizer.embed(input);
      //runs each embedding through nn
      float[][] processed = new float[embeddings.length][97];
      for(int i=0;i<processed.length;i++){
         processed[i] = nn.feedForward(embeddings[i]);
      }
      //compares each nn output to the intent table
      //adds total difference(calc prob for each intent)
      //executes most probable intent
   }
   private void update(){
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
      nn.train(data);
      //train tokenizer
      tokenizer.train();
   }
}
enum Intents{
   CHAT,//default intent, doesn't match any other intent
   MATH,//perform basic math operations
   WEATHER,//check the weather
   READ,//read text from a website
   CALL,//call someone
   EMAIL,//send an email
   UPDATE,//update all weights, biases, and tables
   OPEN_FILE;//find and open a file on the current hardware
   
   private Intents(){}
}
import java.util.Random;

public class NeuralNetwork{
   float lr = (float)0.1;//learning rate for training
   private int[] layers;//stores the number of nodes at each layer
   private float[][][] weights;//[layer][row][collumn]
   private float[][] bias;//[layer][row]
   
   private String pathB;
   private String pathW;

   public NeuralNetwork(int[] layers){
      this.layers = layers;
      
      //pathB = "XOR/bias.giles";
      
      //first, try to retrieve previously calculated weights/biases
      
      this.weights = new float[this.layers.length-1][97][97];
      for(int i=0;i<this.weights.length;i++){
         this.weights[i] = Matrix.randomise(new float[this.layers[i+1]][this.layers[i]]);
      }
   
      this.bias = new float[this.layers.length-1][97];
      for(int i=0;i<this.bias.length;i++){
         this.bias[i] = Matrix.randomise(new float[this.layers[i+1]]);
      }
   }
   
   private float[][] feedForward(float[] input,boolean train){
      if(input.length == this.layers[0]){
         float[][] result = new float[this.layers.length-1][97];
         result[0] = Matrix.sigmoid(Matrix.add(Matrix.multiply(weights[0],input),bias[0]));
         for(int i = 1;i<weights.length;i++){
            result[i] = Matrix.sigmoid(Matrix.add(Matrix.multiply(weights[i],result[i-1]),bias[i]));
         }
         return result;
      }else{
         System.out.println("Input Length Error.\nNeuralNetwork.java, line 34.");
         return null;
      }
   }
   public float[] feedForward(float[] input){//overloaded, user facing method
      float[][] result = feedForward(input,false);
      return result[result.length-1];
   }
   
   public void train(float[] targets,float[] inputs){
      float[][] output = feedForward(inputs,true);//[layer][row]
   
      //calculates the portion of the error that each node is responsible for
      float[][] nodeErr = new float[this.layers.length-1][97];//[layer][row]
      nodeErr[nodeErr.length-1] = Matrix.subtract(targets,output[output.length - 1]);
      for(int i=nodeErr.length-2;i>=0;i--){
         nodeErr[i] = Matrix.multiply(Matrix.transpose(weights[i+1]),nodeErr[i+1]);
      }
   
      //calculates the amount each bias needs to change
      float[][] deltaBias = new float[this.bias.length][97];//[layer][row]
      for(int i=0;i<deltaBias.length;i++){
         deltaBias[i] = math(nodeErr[i],output[i]);
      }
      //then changes them
      for(int i=0;i<this.bias.length;i++){
         bias[i] = Matrix.add(bias[i],deltaBias[i]);
      }
   
      //calculates how much each weight needs to change
      float[][][] deltaWeight = new float[this.weights.length][97][97];//[layer][row][collumn]
      deltaWeight[0] = Matrix.multiply(deltaBias[0],Matrix.transpose(inputs));
      for(int i=1;i<deltaWeight.length;i++){
         deltaWeight[i] = Matrix.multiply(deltaBias[i],Matrix.transpose(output[i-1]));
      }
      //then changes them
      for(int i=0;i<this.weights.length;i++){
         this.weights[i] = Matrix.add(this.weights[i],deltaWeight[i]);
      }
   }
   public int train(float[][][] data){//mass training, repeated until total error is within a certain limit
      float totalErr = 97;
      int iterations = 0;
      while(Math.abs(totalErr) > 1E-3){
         iterations++;
         totalErr = 0;
         for(int i=0;i<data.length;i++){
            train(data[i][1],data[i][0]);
            totalErr += Matrix.sum(Matrix.subtract(data[i][1],feedForward(data[i][0])));
         }
         //System.out.println(totalErr);//debugging
      }
      return iterations;
   }
   private float[] math(float[] nodeErr,float[] outputs){//I just didn't want to write out that ugly line more than once
      return Matrix.eMultiply(Matrix.multiply(nodeErr,lr),Matrix.eMultiply(outputs,Matrix.subtract(1,outputs)));
   }
   public void record(){
      //Writer.write(pathB,bias);
   }
}
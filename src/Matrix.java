import java.util.Random;
import java.io.*;

public class Matrix{
   private Matrix(){}
   public static float[] multiply(float[] matrix,float scalar){
      float[] output = new float[matrix.length];
      for(int i=0;i<output.length;i++){
         output[i] = matrix[i]*scalar;
      }
      return output;
   }
   public static float[] subtract(float A,float[] B){
      float[] result = new float[B.length];
      for(int i=0;i<B.length;i++){
         result[i]=A-B[i];
      }
      return result;
   }
   public static float[] add(float[] A,float[] B){
      if(A.length == B.length){
         float output[] = new float[A.length];
         for(int i = 0;i<A.length;i++){
            output[i] = A[i] + B[i];
         }
         return output;
      }else{
         return null;//add error handling later
      }
   }
   public static float[][] add(float[][] oMat,float[][] factor){
      if(oMat.length == factor.length && oMat[0].length == factor[0].length){
         float output[][] = new float[oMat.length][oMat[0].length];
         for(int i = 0;i<oMat.length;i++){
            for(int j = 0;j<oMat[0].length;j++){
               output[i][j]=oMat[i][j]+factor[i][j];
            }
         }
         return output;
      }else{
         return null;//add error handling later
      }
   }
   public static float[] subtract(float[] A,float[] B){
      if(A.length == B.length){
         float[] output = new float[A.length];
         for(int i = 0;i<A.length;i++){
            output[i] = A[i] - B[i];
         }
         return output;
      }else{
         return null;//add error handling later
      }
   }
   public static float[] eMultiply(float[] A,float[] B){
      if(A.length==B.length){
         float[] result = new float[A.length];
         for(int i=0;i<A.length;i++){
            result[i]=A[i]*B[i];
         }
         return result;
      }else{
         return null;//add error handling later
      }
   }
   public static float[][] multiply(float[][] A,float[][] B){//review to understand math
      if(A[0].length == B.length){
         float[][] output = new float[A[0].length][B.length];
         for(int i = 0;i<B.length;i++){
            for(int j = 0;j<B.length;j++){
               for(int k = 0;k<B.length;k++){
                  output[i][j] += A[i][k] * B[k][j];
               } 
            }
         }
         return output;
      }else{
         return null;//add error handling later
      }
   }
   public static float[] multiply(float[][] A,float[] B){
      if(A[0].length == B.length){
         float[] output = new float[A.length];
         for(int i = 0;i<A.length;i++){
            for(int j = 0;j<A[0].length;j++){
               float jim = A[i][j] * B[j];
               output[i] += jim;
            }
         }
         return output;
      }else{
         return null;//add error handling later
      }
   }
   public static float[][] multiply(float[] A,float[][] B){
      if(B.length == 1){
         float[][] output = new float[A.length][B[0].length];
         for(int i=0;i<output.length;i++){
            for(int j=0;j<output[0].length;j++){
               output[i][j]=A[i]*B[0][j];
            }
         }
         return output;
      }else{
         return null;//add error handling later
      }
   }
   public static float[] randomise(float[] input){
      Random rand = new Random();
      for(int i = 0;i<input.length;i++){
         input[i] = rand.nextFloat();
      }
      return input;
   }
   public static float[][] randomise(float[][] input){
      Random rand = new Random();
      for(int i = 0;i<input.length;i++){
         for(int j = 0;j<input[0].length;j++){
            input[i][j] = rand.nextFloat();
         }
      }
      return input;
   }
   public static float[][][] randomise(float[][][] input){
      Random rand = new Random();
      for(int i=0;i<input.length;i++){
         for(int j = 0;j<input[0].length;j++){
            for(int k = 0;k<input[0][0].length;k++){
               input[i][j][k] = rand.nextFloat();
            }
         }
      }
      return input;
   }
   public static float[][] transpose(float[][] input){
      float[][] output = new float[input[0].length][input.length];
      for(int i = 0;i<output.length;i++){
         for(int j = 0;j<output[0].length;j++){
            output[i][j] = input[j][i];
         }
      }
      return output;
   }
   public static float[][] transpose(float[] input){
      float[][] result = new float[1][input.length];
      for(int i=0;i<input.length;i++){
         result[0][i] = input[i];
      }
      return result;
   }
   public static float[][] sigmoid(float[][] input){
      float[][] output = new float[input.length][input[0].length];
      double e = Math.E;
      for(int i = 0;i<input.length;i++){
         for(int j = 0;i<input[0].length;i++){
            output[i][j] = (float)(1 / (1 + (Math.pow(Math.E,(-1 * input[i][j])))));
         }
      }
      return output;
   }
   public static float[] sigmoid(float[] input){
      float[] result = new float[input.length];
      for(int i=0;i<input.length;i++){
         result[i] = (float)(1 / (1 + (Math.pow(Math.E,(-1 * input[i])))));
      }
      return result;
   }
   public static float sum(float[] input){
      float result = 0;
      for(int i=0;i<input.length;i++){
         result += input[i];
      }
      return result;
   }
   
   
   public static void write(String path,float[][] data){
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
         for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
               writer.write(data[i][j] + " ");
            }
            writer.newLine();
         }
      }catch (IOException e){
         System.out.println("Could not write your 2d array to " + path);
         e.printStackTrace();
      }
   }
   public static void write(String path,float[] data){
   
   }
   public static void write(String path,String[][] data){
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
         for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
               writer.write(data[i][j] + " ");
            }
            writer.newLine();
         }
      }catch (IOException e){
         System.out.println("Could not write your 2d array to " + path);
         e.printStackTrace();
      }
   }
   public static void write(String path,String[] data){
   
   }
}
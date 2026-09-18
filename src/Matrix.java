import java.util.*;
import java.io.*;
import java.nio.file.Paths;
import java.nio.file.Files;

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
      for(float num : input){
         result += num;
      }
      return result;
   }
   public static void write(String path,String[] data){//writing String vectors as bits with \n as a separator
      try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
         for(String word : data){
            dos.writeChars(word);
            dos.writeChar('\n');
         }
         dos.close();
      }catch (IOException e){
         System.out.println("Could not write your Strings to " + path);
         e.printStackTrace();
      }
   }
   public static void write(String path,float[] data){//writing float vectors as bits
      try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
         dos.writeInt(data.length);//rows
         for (float num : data) {
            dos.writeFloat(num);//data points
         }
         dos.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   public static void write(String path,float[][] matrix){//writing float matrices as bits
      try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
         dos.writeInt(matrix.length);//rows
         dos.writeInt(matrix[0].length);//collumns
         for (float[] row : matrix) {
            for(float num : row){
               dos.writeFloat(num);
            }
         }
         dos.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
   public static void write(String path,float[][][] data){//writing 3d matrices as bits
      try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
         dos.writeInt(data.length);//layers
         for(float[][] matrix : data){
            dos.writeInt(matrix.length);//rows
            dos.writeInt(matrix[0].length);//collumns
            for(float[] vector : matrix){
               for(float num : vector){
                  dos.writeFloat(num);//data point
               }
            }
         }
         dos.close();
      }catch (IOException e){
         e.printStackTrace();
      }
   }
   public static String[] readStrings(String path){
      String[] result = null;
      try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(path)))) {
         //readInt() to get length
         int length = dis.readInt();
         result = new String[length];
         //create array of that length
         int dir = 0;
         String tempString = "";
         while(dir<result.length){
            //read the next char
            char c = dis.readChar();
            if(c == '\n'){
               //current directory = tempString
               result[dir] = tempString;
               tempString = "";
               //move to next directory
               dir++;
            }else{
               //add it to the temp string
               tempString += c;
            }
         }
         dis.close();
      } catch (EOFException e) {
         //End of file reached safely
      } catch (IOException e) {
         e.printStackTrace();
      }
      return result;
   }
   //reads the bits of a float vector from path
   public static float[] readFloats(String path){
      float[] result = null;
      try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(path)))) {
         result = new float[dis.readInt()];
         for(int i=0;i<result.length;i++){
            result[i] = dis.readFloat();
         } 
         dis.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return result;
   }
   public static float[][] read2dFloats(String path){
      float[][] matrix = null;
      try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(path)))){
         int rows = dis.readInt();
         int collumns = dis.readInt();
         matrix = new float[rows][collumns];
         for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[0].length;j++){
               matrix[i][j] = dis.readFloat();
            }
         }
         dis.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return matrix;
   }
   public static float[][][] read3dFloats(String path){
      float[][][] result = null;
      try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(path)))){
         int layers = dis.readInt();
         result = new float[layers][][];
         for(int i=0;i<layers;i++){
            int rows = dis.readInt();
            int collumns = dis.readInt();
            result[i] = new float[rows][collumns];
            for(int j=0;j<rows;j++){
               for(int k=0;k<collumns;k++){
                  result[i][j][k] = dis.readFloat();
               }
            }
         }
         dis.close();
      } catch (IOException e) {
         e.printStackTrace();
      }
      return result;
   }
   public static boolean exists(String path){
      return Files.exists(Paths.get(path));
   }
}
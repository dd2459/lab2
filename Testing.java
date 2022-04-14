import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

public class Testing {

  public static String RANDOM_FILE_IN = "./random.in";
  public static String RANDOM_FILE_OUT = "./random.out";
  public static String PAIRWISE_FILE_IN = "./pairwise.in";
  public static String PAIRWISE_FILE_OUT = "./pairwise.out";

  public static int MAX_VALUE = 50;
  public static int N = 75;
  public static int TC = 3000;
  public static int[] TYPICAL_ARRAY = {1,-1}; //k=2, default value is the first one
  public static int[] TYPICAL_KEY = {2,0};

  public static void main(String[] args) {
    int mode = Integer.parseInt(args[0]);
    switch(mode) {
      case 0:
        printRandomTestCase();
        break;
      case 1:
        printPairwiseTestCase();
        break;
      case 2:
        runRandom();
        break;
      case 3:
        runPairwise();
        break;
      default:
        System.err.println("bad option");
    }
  }

  public static void runRandom() { 
    String out = "";
    for(Entry<Integer, int[]> e : readFromFile(RANDOM_FILE_IN)){
      out += Functions.membershipUnsorted(e.getValue(), e.getKey()) +"\n";
    }
    writeToFile(RANDOM_FILE_OUT, out);
    System.out.println(runOracles(RANDOM_FILE_OUT, RANDOM_FILE_IN));
  }

  public static void runPairwise() {
    String out = "";
    for(Entry<Integer, int[]> e : readFromFile(PAIRWISE_FILE_IN)){
      out += Functions.membershipUnsorted(e.getValue(), e.getKey()) +"\n";
    }
    writeToFile(PAIRWISE_FILE_OUT, out);
    System.out.println(runOracles(PAIRWISE_FILE_OUT, PAIRWISE_FILE_IN));
  }
  
  public static void printRandomTestCase() {
    String toPrint = "";
    String testCase;
    for (int i = 0; i < TC; i++)
    {
      double value;
      value = (Math.random() - 0.5)*2; // 50% positive, 50% negative
      // value between -1 and 1
      value *= MAX_VALUE;              // value between -MAX and MAX
      int key = (int) value;
      testCase = key+"";

      for (int j = 0; j < N; j++)
      {
        value = (Math.random() - 0.5)*2; // 50% positive, 50% negative
        // value between -1 and 1
        value *= MAX_VALUE;              // value between -MAX and MAX
        testCase += " "+(int) value;
      }
      toPrint += testCase+"\n";
    }
    writeToFile(RANDOM_FILE_IN,toPrint);
  }

  public static void printPairwiseTestCase(){
    String testsCases = "";
    //create 0-wise TC (default, default, ...)
    String wise0 = TYPICAL_KEY[0]+"";
    for(int i =0; i < N; i++) {
      wise0 += " " + (i+1)*TYPICAL_ARRAY[0];
    }
    testsCases += wise0+"\n";

    //create 1-wise TC (only 1 non default value, all values must appear at least once)
    // foreach input variable -> we need a tc with this input var is non-default
    
    String wise1 = TYPICAL_KEY[1]+"";
    for(int i = 0; i < N; i ++)
      wise1 += " "+(i+1)*TYPICAL_ARRAY[0];
    wise1+="\n";
    for(int i = 0; i < N; i++) {
      wise1 += TYPICAL_KEY[0];
      for(int j =0; j < N; j++) { //j is the non default input var
        wise1 += (j == i)?" "+ (j+1)*TYPICAL_ARRAY[1]:" "+(j+1)*TYPICAL_ARRAY[0];
      }
      wise1 += "\n";
    }
    testsCases += wise1;

    //create 2-wise TC (only 2 non default values, 
    //all pairs of values must appear at least once)

    //do it for the key as well

    String wise2 = "";
    for(int i = 0; i < N; i++) {
      wise2 += TYPICAL_KEY[1];
      for(int j =0; j < N; j++) { //j is the second non default input var
         wise2 += (j==i)?" "+(j+1)*TYPICAL_ARRAY[1]:" "+ (j+1)*TYPICAL_ARRAY[0];
      }
      wise2 += "\n";
    }
    for(int i =0; i < N; i++) {//for each input var
      for(int j = i+1; j < N; j++) { // for each pair (i,j)
        // i and j -> non default
        wise2 += TYPICAL_KEY[0];
        for(int k = 0; k < N; k++) {
          wise2 += (k == i || k == j)?" "+(k+1)*TYPICAL_ARRAY[1]:" "+(k+1)*TYPICAL_ARRAY[0];
        }
        wise2 += "\n";
      }
    }
    testsCases += wise2; 

    writeToFile(PAIRWISE_FILE_IN,testsCases);
  }

  public static boolean oracle(int[] A, int key, int result) {
    int expected = 0;
    for(int i = 0; i < A.length; i++){
      if(A[i] == key) {
        expected = 1;
        break;
      }
    }
    return (expected == result);
  }

  public static List<Boolean> runOracles(String pathResult, String pathTC) {
    List<Boolean> ret = new LinkedList<Boolean>();
    for(Entry<Integer, int[]> e : readFromFile(pathTC)){
      int result = Functions.membershipUnsorted(e.getValue(), e.getKey());
      ret.add(oracle(e.getValue(), e.getKey(), result));
    }
    return ret;
  }
    


  public static List<Entry<Integer, int[]>> readFromFile(String path) {
    List<Entry<Integer,int[]>> ret = new LinkedList<Entry<Integer, int[]>>();
    try {
      Scanner reader = new Scanner(new File(path));
      String line;
      String[] tmp;
      int[] values;
      int key, size;
      Entry<Integer, int[]> entry; 
      while(reader.hasNextLine()){
        line = reader.nextLine();
        tmp = line.split(" ");
        size = tmp.length-1;
        values = new int[size];
        key = Integer.parseInt(tmp[0]);
        for(int i = 1; i <= size; i++) {
          values[i-1] = Integer.parseInt(tmp[i]);
        }
        entry = new SimpleEntry<Integer, int[]>(key, values);
        ret.add(entry);
      }
    } catch (Exception e) {
      System.err.println("Error occured while reading the file: "+e);
    } finally {
      return ret;
    }
  }

  public static void writeToFile(String path, String str){
    try {
      BufferedWriter writer = new BufferedWriter(new FileWriter(path));
      writer.write(str);
      writer.close();
    } catch (Exception e) {
      System.err.println("Error occured while writing the file: "+e);
    }
  }
}

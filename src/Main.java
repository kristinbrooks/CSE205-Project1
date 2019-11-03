//*********************************************************************************************************************
// CLASS: Main (Main.java)
//
// DESCRIPTION
//
//
// COURSE AND PROJECT INFO
// CSE205 Object Oriented Programming and Data Structures, Fall B 2019
// Project Number: 1
//
// AUTHOR: Kristin Brooks, krbrook7, krbrook7@asu.edu
//*********************************************************************************************************************
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;

public class Main {
    // global variables
    private final int RUNS_UP = 37;
    private final int RUNS_DN = 73;

    public static void main(String[] args) {
        new Main().run();
    }
    /**
     *
     */
    private void run() {
        ArrayList<Integer> list = readFile("/Users/Ktown/workspace/CSE205-Project1/txt-files" +
                "/input/p01-in.txt");

        ArrayList<Integer> listRunsUpCount = findsRuns(list, RUNS_UP);
        ArrayList<Integer> listRunsDnCount = findsRuns(list, RUNS_DN);

        ArrayList<Integer> listRunsCount = merge(listRunsUpCount, listRunsDnCount);

        output("/Users/Ktown/workspace/CSE205-Project1/txt-files/p01-runs.txt", listRunsCount);
    }
    /**
     * reads the integers in the input file into an ArrayList and returns the ArrayList
     */
    private ArrayList<Integer> readFile(String inputFile) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File(inputFile));
            while (input.hasNextInt()) {
                list.add(input.nextInt());
            }
            input.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Unfortunately your input file could not be found. The program will now terminate.");
            System.exit(-1);
        }
        return list;
    }
    /**
     *
     */
    private ArrayList<Integer> findsRuns(ArrayList<Integer> list, int direction) {
        ArrayList<Integer> listRunsCount = arrayListCreate(list.size(), 0);
        int i = 0;
        int k = 0;
        while (i < list.size()-1) {
            if (direction == RUNS_UP && list.get(i) <= list.get(i + 1)) {
                k++;
            } else if (direction == RUNS_DN && list.get(i) >= list.get(i + 1)) {
                k++;
            } else {
                if (k != 0) {
                    listRunsCount.add(k,listRunsCount.get(k) + 1);
                    k = 0;
                }
            }
        }
        if (k != 0) {
            listRunsCount.add(k,listRunsCount.get(k) + 1);
            k = 0;
        }
        return listRunsCount;
    }
    /**
     *
     */
    private ArrayList<Integer> arrayListCreate(int size, int initValue) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            list.add(initValue);
        }
        return list;
    }
    /**
     *
     */
    private ArrayList<Integer> merge(ArrayList<Integer> listRunsUpCount, ArrayList<Integer> listRunsDnCount) {
        ArrayList<Integer> listRunsCount = arrayListCreate(listRunsUpCount.size(), 0);
        for  (int index = 0; index < listRunsUpCount.size(); index++) {
            listRunsCount.add(index, listRunsUpCount.get(index) + listRunsDnCount.get(index));
        }
        return listRunsCount;
    }
    /**
     *
     */
    private void output(String outputFileName, ArrayList<Integer> listRuns) {
        try{
            PrintWriter output = new PrintWriter(outputFileName);
            output.println("runs_total, " + runsSum(listRuns) + "\n");
            for (int k = 1; k < listRuns.size(); k++) {
                output.println("runs-" + k + ", " + listRuns.get(k) + "\n");
            }
            output.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Unfortunately your output file could not be found. The program will now terminate.");
            System.exit(-1);
        }
    }
    /**
     *
     */
    private int runsSum(ArrayList<Integer> listRuns) {
        int sum = 0;
        for (Integer run : listRuns) {
            sum += run;
        }
        return sum;
    }
}
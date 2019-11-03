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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    // global variables
    private final int RUNS_UP = 37;
    private final int RUNS_DN = 73;
    /**
     *
     */
    public static void main(String[] args) {
        String filesDir = args.length == 1 ? args[0] : ".";
        new Main().run(filesDir);
    }
    /**
     *
     */
    private void run(String filesDir) {
        ArrayList<Integer> list = readFile(filesDir + "/p01-in.txt" );

        ArrayList<Integer> listRunsUpCount = findsRuns(list, RUNS_UP);
        ArrayList<Integer> listRunsDnCount = findsRuns(list, RUNS_DN);

        ArrayList<Integer> listRunsCount = merge(listRunsUpCount, listRunsDnCount);

        output("p01-runs.txt", listRunsCount);
    }
    /**
     * reads the integers in the input file into an ArrayList and returns the ArrayList
     */
    private ArrayList<Integer> readFile(String inputFile) {
        ArrayList<Integer> list = new ArrayList<>();
        try {
            File source = new File(inputFile);
            Scanner input = new Scanner(source);
            while (input.hasNextInt()) {
                list.add(input.nextInt());
            }
            input.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Unfortunately 'p01-in.txt' could not be opened. The program will now stop running.");
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
        while (i < list.size() - 1) {
            if (direction == RUNS_UP && list.get(i) <= list.get(i + 1)) {
                k++;
            } else if (direction == RUNS_DN && list.get(i) >= list.get(i + 1)) {
                k++;
            } else {
                if (k != 0) {
                    listRunsCount.set(k, listRunsCount.get(k) + 1);
                    k = 0;
                }
            }
            i++;
        }
        if (k != 0) {
            listRunsCount.set(k, listRunsCount.get(k) + 1);
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
        for (int index = 0; index < listRunsUpCount.size(); index++) {
            int e = listRunsUpCount.get(index) + listRunsDnCount.get(index);
            listRunsCount.set(index, e);
        }
        return listRunsCount;
    }
    /**
     *
     */
    private void output(String outputFileName, ArrayList<Integer> listRuns) {
        try {
            PrintWriter output = new PrintWriter(outputFileName);
            output.println("runs_total, " + runsSum(listRuns));
            for (int k = 1; k < listRuns.size(); k++) {
                output.println("runs_" + k + ", " + listRuns.get(k));
            }
            output.close();
        } catch (FileNotFoundException exception) {
            System.out.println("Unfortunately 'p01-runs.txt' could not be found. The program will now stop running.");
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
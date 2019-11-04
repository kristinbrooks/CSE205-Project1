//*********************************************************************************************************************
// CLASS: Main (Main.java)
//
// DESCRIPTION
// This program reads a text file containing integers into an ArrayList. Then it loops through the Arraylist and finds
// monotonically increasing or decreasing sequences in the list. These sequences are called either a run up or a
// run down. It counts the numbers of runs that occur at each possible length for the list (i.e. greater than 0,
// less than the length of the list) and the total number of runs. The run counts are then output to a txt file.
//        note: A ternary operator was added to the 'main' method so that different configurations could be added in
//              the IDE for the test cases to make them more convenient to run.
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
     * The 'main' method creates an instance of the 'Main' class and calls the 'run' method on it to run the program.
     */
    public static void main(String[] args) {
        // The ternary operator was added so that different configurations could be added for the test cases
        // to make them easier to run.
        String filesDirectory = args.length == 1 ? args[0] : ".";
        new Main().run(filesDirectory);
    }

    /**
     * This method creates ArrayLists calls the other methods in the necessary order to run the program and ouput the
     * required txt file.
     */
    private void run(String filesDirectory) {
        // The pseudocode was changed from two lines to one line to declare and fill the ArrayLists to
        // simplify the code.
        ArrayList<Integer> list = readFile(filesDirectory + "/testcases/p01-in.txt");

        ArrayList<Integer> listRunsUpCount = findsRuns(list, RUNS_UP);
        ArrayList<Integer> listRunsDnCount = findsRuns(list, RUNS_DN);

        ArrayList<Integer> listRunsCount = merge(listRunsUpCount, listRunsDnCount);

        // One of the parameters of the 'output' method was removed to be more efficient.
        output(listRunsCount);
    }

    /**
     * This method reads the integers in the 'p01-in.txt' into the ArrayList 'list' and returns 'list'. It will also
     * catch a 'FileNotFoundException' if one occurs and terminate the program.
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
     * This methods finds runs going up or down in the ArrayList of the data from the text file. It uses k to keep
     * track of the length of the run. (The length = k + 1.) When it finds a run, an element at the index of
     * the k value is incremented on a new ArrayList created for counting the runs. It returns the new ArrayList.
     */
    private ArrayList<Integer> findsRuns(ArrayList<Integer> list, int direction) {
        ArrayList<Integer> listRunsCount = arrayListCreate(list.size());
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
        }
        return listRunsCount;
    }

    /**
     * This method creates an ArrayList of the size provided filled with 0s and returns the ArrayList.
     */
    private ArrayList<Integer> arrayListCreate(int size) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int index = 0; index < size; index++) {
            list.add(0);
        }
        return list;
    }

    /**
     * This method adds the elements at the same index from two separate ArrayLists and sets the new value into a
     * new ArrayList at the same index. Then it returns the new ArrayList.
     */
    private ArrayList<Integer> merge(ArrayList<Integer> listRunsUpCount, ArrayList<Integer> listRunsDnCount) {
        ArrayList<Integer> listRunsCount = arrayListCreate(listRunsUpCount.size());
        for (int index = 0; index < listRunsUpCount.size(); index++) {
            listRunsCount.set(index, listRunsUpCount.get(index) + listRunsDnCount.get(index));
        }
        return listRunsCount;
    }

    /**
     * This method outputs a txt file containing the total number of runs found and a list of the number of runs
     * found for each value of k.
     */
    private void output(ArrayList<Integer> listRuns) {
        try {
            // The 'outputFileName' parameter was was removed from this method because it was always 'p01-runs.txt'
            // so it is now just inserted directly to PrintWriter.
            PrintWriter output = new PrintWriter("p01-runs.txt");
            output.println("runs_total, " + calculateRunTotal(listRuns));
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
     * This method calculates the total number of runs that were found in the data in the txt file by looping
     * through the ArrayList of the run counts and summing all its elements.
     */
    private int calculateRunTotal(ArrayList<Integer> listRuns) {
        int sum = 0;
        for (Integer run : listRuns) {
            sum += run;
        }
        return sum;
    }
}
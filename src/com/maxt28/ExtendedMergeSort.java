package com.maxt28;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class ExtendedMergeSort {

    private static int avaliableMemory = 3;
    private static int fileCount = 0;

    public static void mergeSort(File file) {
        makeReserveFiles(file);
        loopSort();
        mergeFiles();
    }

    private static void mergeFiles() {
        try {
            String filename = "SortedArray.txt";
            new File(filename).delete();
            FileWriter fw = new FileWriter(filename, true);
            int[] bufferArray;
            for (int i = 0; i < fileCount; i++) {
                File currentFile = new File(i + ".txt");
                bufferArray = readArrayFromFile(currentFile);
                for (int arrayElement : bufferArray) {
                    fw.write(arrayElement + " ");
                }
                currentFile.delete();
            }
            fw.close();
        } catch (IOException ioe) {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }

    private static void loopSort() {
        for (int firstFileIndex = 0; firstFileIndex < fileCount - 1; firstFileIndex++) {
            File firstFile = new File(firstFileIndex + ".txt");
            int[] firstArray = readArrayFromFile(firstFile);
            for (int secondFileIndex = firstFileIndex + 1; secondFileIndex < fileCount; secondFileIndex++) {
                File secondFile = new File(secondFileIndex + ".txt");
                int[] secondArray = readArrayFromFile(secondFile);
                sortBothArrays(firstFileIndex, firstArray, secondFileIndex, secondArray);
            }
        }
    }

    private static void sortBothArrays(int firstFileIndex, int[] firstArray, int secondFileIndex, int[] secondArray) {
        int[] mergedArray = new int[firstArray.length + secondArray.length];
        System.arraycopy(firstArray, 0, mergedArray, 0, firstArray.length);
        System.arraycopy(secondArray, 0, mergedArray, firstArray.length, secondArray.length);
        mergedArray = MergeSort.mergeSortArray(mergedArray);
        System.arraycopy(mergedArray, 0, firstArray, 0, firstArray.length);
        System.arraycopy(mergedArray, firstArray.length, secondArray, 0, secondArray.length);
        writeFile(firstArray, firstFileIndex + ".txt");
        writeFile(secondArray, secondFileIndex + ".txt");
    }

    private static int[] readArrayFromFile(File file) {
        try {
            Scanner sc = new Scanner(file);
            List<Integer> list = new ArrayList<>();
            while (sc.hasNextInt()) {
                list.add(sc.nextInt());
            }
            return convertListToArray(list);
        } catch (FileNotFoundException e) {
            System.out.println("readArrayFromFile exception");
            return null;
        }
    }

    private static void makeReserveFiles(File file) {
        try {
            Scanner sc = new Scanner(file);
            List<Integer> list = new ArrayList<>();
            int counter = 1;
            int fileIndex = 0;
            while (sc.hasNextInt()) {
                list.add(sc.nextInt());
                if (counter++ >= avaliableMemory || !sc.hasNextInt()) {
                    String fileName = fileIndex++ + ".txt";
                    counter = 1;
                    writeFile(convertListToArray(list), fileName);
                    list.clear();
                    fileCount++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("makeReverseFile exception");
        }
    }

    private static int[] convertListToArray(List<Integer> list) {
        int[] array = new int[list.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    private static void writeFile(int[] array, String fileName) {
        try {
            FileWriter writer = new FileWriter(fileName);
            for (int arrayElement : array) {
                writer.write(arrayElement + " ");
            }
            writer.close();
        } catch (IOException e1) {
            System.out.println("writeFile exception");
        }
    }
}

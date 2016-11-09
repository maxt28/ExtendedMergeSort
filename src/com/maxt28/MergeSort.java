package com.maxt28;

public abstract class MergeSort {

    public static int[] mergeSortArray(int[] array) {
        int n = array.length;
        if (n <= 1) {
            return null;
        }
        int middle = n / 2;
        int[] leftArray = new int[middle];
        int[] rightArray = new int[n - middle];
        for (int i = 0; i < middle; i++) {
            leftArray[i] = array[i];
        }
        for (int i = middle; i < n; i++) {
            rightArray[i - middle] = array[i];
        }
        mergeSortArray(leftArray);
        mergeSortArray(rightArray);
        mergeArrays(leftArray, rightArray, array);
        return array;
    }

    private static void mergeArrays(int[] leftArray, int[] rightArray, int[] array) {
        int leftIndex = 0;
        int rightIndex = 0;
        boolean leftHasNext = true;
        boolean rightHasNext = true;
        for (int i = 0; i < array.length; i++) {
            if (leftIndex >= leftArray.length) {
                leftHasNext = false;
            }
            if (rightIndex >= rightArray.length) {
                rightHasNext = false;
            }
            if (leftHasNext && rightHasNext) {
                if (leftArray[leftIndex] < rightArray[rightIndex]) {
                    array[i] = leftArray[leftIndex++];
                } else {
                    array[i] = rightArray[rightIndex++];
                }
            } else if (leftHasNext) {
                array[i] = leftArray[leftIndex++];
            } else {
                array[i] = rightArray[rightIndex++];
            }
        }
    }
}

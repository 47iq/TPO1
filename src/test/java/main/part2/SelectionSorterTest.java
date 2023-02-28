package main.part2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SelectionSorterTest {

    SelectionSorter selectionSorter;

    @BeforeEach
    public void setup() {
        selectionSorter = new SelectionSorter();
    }

    @Test
    void sortRegularArray() {
        int[] sortArray = {10, 8, 4, 9, 6, 5, 3, 4, 1, 10};
        int[] expectedArray = {1, 3, 4, 4, 5, 6, 8, 9, 10, 10};
        selectionSorter.sort(sortArray);
        assertArrayEquals(expectedArray, sortArray);
    }

    @Test
    void sortAlreadySortedArray() {
        int[] sortArray = {1, 3, 4, 4, 5, 6, 8, 9, 10, 10};
        int[] expectedArray = {1, 3, 4, 4, 5, 6, 8, 9, 10, 10};
        selectionSorter.sort(sortArray);
        assertArrayEquals(expectedArray, sortArray);
    }

    @Test
    void sortSingleElement() {
        int[] sortArray = {1000};
        int[] expectedArray = {1000};
        selectionSorter.sort(sortArray);
        assertArrayEquals(expectedArray, sortArray);
    }

    @Test
    void sortNegativeAndPositiveArray() {
        int[] sortArray = {-8, -7, -9, -7, -4, 6, 5, -8, 4, -1};
        int[] expectedArray = {-9, -8, -8, -7, -7, -4, -1, 4, 5, 6};
        selectionSorter.sort(sortArray);
        assertArrayEquals(expectedArray, sortArray);
    }

    @Test
    void sortNegativeArray() {
        int[] sortArray = {-2, -5, -3, -9, -1, -9, -3, -7, -7, -4};
        int[] expectedArray = {-9, -9, -7, -7, -5, -4, -3, -3, -2, -1};
        selectionSorter.sort(sortArray);
        assertArrayEquals(expectedArray, sortArray);
    }

    @Test
    void sortNullArray() {
        int[] sortArray = null;
        assertDoesNotThrow(() -> {
            selectionSorter.sort(sortArray);
        });
    }


}
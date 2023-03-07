package part2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class SelectionSorterTest {

    SelectionSorter selectionSorter;

    public static Stream<Arguments> sortRegularArray() {
        return Stream.of(
                Arguments.of(new int[] {10, 8, 4, 9, 6, 5, 3, 4, 1, 10}, new int[] {1, 3, 4, 4, 5, 6, 8, 9, 10, 10}),
                Arguments.of(new int[] {3, 7, 2, 1, 0, 0, 1, 1, 7, 3}, new int[] {0, 0, 1, 1, 1, 2, 3, 3, 7, 7})
        );
    }

    public static Stream<Arguments> sortAlreadySortedArray() {
        return Stream.of(
                Arguments.of(new int[] {1, 3, 4, 4, 5, 6, 8, 9, 10, 10}, new int[] {1, 3, 4, 4, 5, 6, 8, 9, 10, 10}),
                Arguments.of(new int[] {0, 0, 1, 1, 1, 2, 3, 3, 7, 7}, new int[] {0, 0, 1, 1, 1, 2, 3, 3, 7, 7})
        );
    }

    public static Stream<Arguments> sortSingleElement() {
        return Stream.of(
                Arguments.of(new int[] {1000}, new int[] {1000}),
                Arguments.of(new int[] {Integer.MAX_VALUE}, new int[] {Integer.MAX_VALUE}),
                Arguments.of(new int[] {Integer.MIN_VALUE}, new int[] {Integer.MIN_VALUE})
        );
    }

    public static Stream<Arguments> sortNegativeAndPositiveArray() {
        return Stream.of(
                Arguments.of(new int[] {-8, -7, -9, -7, -4, 6, 5, -8, 4, -1}, new int[] {-9, -8, -8, -7, -7, -4, -1, 4, 5, 6}),
                Arguments.of(new int[] {9, -1, 2, 6, -8, 4, 10, -5, -1, -1}, new int[] {-8, -5, -1, -1, -1, 2, 4, 6, 9, 10})
        );
    }

    public static Stream<Arguments> sortNegativeArray() {
        return Stream.of(
                Arguments.of(new int[] {-2, -5, -3, -9, -1, -9, -3, -7, -7, -4}, new int[] {-9, -9, -7, -7, -5, -4, -3, -3, -2, -1}),
                Arguments.of(new int[] {-3, -10, -1, -7, -8, -5, -2, -5, -7, -5}, new int[] {-10, -8, -7, -7, -5, -5, -5, -3, -2, -1})
        );
    }

    @BeforeEach
    public void setup() {
        selectionSorter = new SelectionSorter();
    }

    @ParameterizedTest
    @MethodSource
    void sortRegularArray(int[] input, int[] expected) {
        selectionSorter.sort(input);
        assertArrayEquals(input, expected);
    }

    @ParameterizedTest
    @MethodSource
    void sortAlreadySortedArray(int[] input, int[] expected) {
        selectionSorter.sort(input);
        assertArrayEquals(input, expected);
    }

    @ParameterizedTest
    @MethodSource
    void sortSingleElement(int[] input, int[] expected) {
        selectionSorter.sort(input);
        assertArrayEquals(input, expected);
    }

    @ParameterizedTest
    @MethodSource
    void sortNegativeAndPositiveArray(int[] input, int[] expected) {
        selectionSorter.sort(input);
        assertArrayEquals(input, expected);
    }

    @ParameterizedTest
    @MethodSource
    void sortNegativeArray(int[] input, int[] expected) {
        selectionSorter.sort(input);
        assertArrayEquals(input, expected);
    }

    @Test
    void sortNullArray() {
        assertThrowsExactly(NullPointerException.class, () -> {
            selectionSorter.sort(null);
        });
    }


}
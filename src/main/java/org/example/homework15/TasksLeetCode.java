package org.example.homework15;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TasksLeetCode {

    public int[] twoSum(int[] numbers, int target) {

        Map<Integer, List<Integer>> map = IntStream.range(0, numbers.length)
                .boxed()
                .collect(Collectors.groupingBy(i -> numbers[i], Collectors.toList()));

        return IntStream.range(0, numbers.length)
                .filter(i -> numbers[i] != target - numbers[i] ? map.containsKey(target - numbers[i]) : map.get(numbers[i]).size() > 1)
                .toArray();
    }

    public int fibonacciNumber(int number) {

        if (number > 1) {
            return fibonacciNumber(number - 1) + fibonacciNumber(number - 2);
        } else
            return number;
    }

    public List<List<Integer>> pascalsTriangle(int number) {

        List<List<Integer>> allRows = new ArrayList<List<Integer>>();
        ArrayList<Integer> row = new ArrayList<Integer>();

        if (number > 0) {
            for (int i = 0; i < number; i++) {
                row.add(0, 1);
                for (int j = 1; j < row.size() - 1; j++)
                    row.set(j, row.get(j) + row.get(j + 1));
                allRows.add(new ArrayList<Integer>(row));
            }
            return allRows;
        } else
            throw new IllegalArgumentException("The number must be greater than zero");
    }
}

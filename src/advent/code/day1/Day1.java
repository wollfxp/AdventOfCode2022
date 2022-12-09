package advent.code.day1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Day1 implements Runnable {

    public Map<Integer, Integer> calorieMap = new HashMap<>();

    public Day1() {
        File file = new File("src/resources/day1-input.txt");

        try (Stream<String> ss = Files.lines(Paths.get(file.getPath()))) {
            AtomicInteger index = new AtomicInteger();

            ss.forEach(s -> {
                if (s.isEmpty()) {
                    index.getAndIncrement();
                } else {
                    int key = index.get();
                    int currentValue = calorieMap.getOrDefault(key, 0);
                    currentValue += Integer.parseInt(s);
                    calorieMap.put(key, currentValue);
                }
            });
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void run() {

        SolveDay1Part1();
        SolveDay1Part2();
    }

    public void SolveDay1Part1() {
        int highestIndex = calorieMap.entrySet().stream().max(Map.Entry.comparingByValue()).orElseThrow().getKey();
        System.out.println("DAY 1 (1): \tHighest index was " + highestIndex + " with " + calorieMap.get(highestIndex) + " calories");
    }

    public void SolveDay1Part2() {
        int topThreeCalories = calorieMap.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .limit(3)
                .map(Map.Entry::getValue)
                .reduce(0, Integer::sum);

        System.out.println("DAY 1 (2): \tTop three elves are carrying " + topThreeCalories + " calories");
    }
}

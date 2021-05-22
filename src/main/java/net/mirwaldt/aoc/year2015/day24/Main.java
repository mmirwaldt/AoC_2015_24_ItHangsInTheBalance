package net.mirwaldt.aoc.year2015.day24;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        final List<String> lines = Files.readAllLines(Path.of("input"), StandardCharsets.US_ASCII);
        final List<Long> packages = lines.stream().mapToLong(Long::parseLong).boxed().collect(Collectors.toList());

        GroupsDivider groupsDividerForPartOne = new OptimizedGroupsDivider(3);
        System.out.println(groupsDividerForPartOne.divideIntoGroups(packages)); // result: 10439961859

        GroupsDivider groupsDividerForPartTwo = new OptimizedGroupsDivider(4);
        System.out.println(groupsDividerForPartTwo.divideIntoGroups(packages)); // result: 72050269
    }
}

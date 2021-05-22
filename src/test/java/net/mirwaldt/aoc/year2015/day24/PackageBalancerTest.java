package net.mirwaldt.aoc.year2015.day24;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PackageBalancerTest {
    @Test
    void testOptimizedPackageBalancer_partOne() {
        PackageBalancer packageBalancer = new OptimizedPackageBalancer(3);
        assertEquals(99, packageBalancer.balance(asList(1L, 2L, 3L, 4L, 5L, 7L, 8L, 9L, 10L, 11L)));
    }

    @Test
    void testOptimizedPackageBalancer_partTwo() {
        PackageBalancer packageBalancer = new OptimizedPackageBalancer(4);
        assertEquals(44, packageBalancer.balance(asList(1L, 2L, 3L, 4L, 5L, 7L, 8L, 9L, 10L, 11L)));
    }
}

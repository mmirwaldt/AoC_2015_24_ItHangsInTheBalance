package net.mirwaldt.aoc.year2015.day24;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.lang.Math.min;

public class OptimizedPackageBalancer implements PackageBalancer {
    private final int numberOfGroups;

    public OptimizedPackageBalancer(int numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }

    @Override
    public long balance(List<Long> packages) {
        final long sum = packages.stream().mapToLong(Long::intValue).sum();
        packages.sort(Comparator.<Long>naturalOrder().reversed());
        if (sum % numberOfGroups == 0) {
            final long weightOfEachGroup = sum / numberOfGroups;
            long[] minQuantumEntanglement = new long[] { Integer.MAX_VALUE };
            findPackages(weightOfEachGroup, new ArrayList<>(), packages,
                    new long[] { Integer.MAX_VALUE }, minQuantumEntanglement);
            return minQuantumEntanglement[0];
        } else {
            throw new IllegalArgumentException(
                    "Sum of packages " + packages + "cannot be divided by " + numberOfGroups + ". sum=" + sum);
        }
    }

    private void findPackages(
            long remainingWeight,
            List<Long> selectedPackages,
            List<Long> remainingPackages,
            long[] minSize,
            long[] minQuantumEntanglement) {
        List<Long> newRemainingPackages = new ArrayList<>(remainingPackages);
        int newSize = selectedPackages.size();
        for (Long remainingPackage : remainingPackages) {
            if(newSize <= minSize[0]) {
                if (remainingWeight == remainingPackage) {
                    long quantumEntanglement = quantumEntanglement(selectedPackages) * remainingPackage;
                    if(newSize < minSize[0]) {
                        minSize[0] = newSize;
                        minQuantumEntanglement[0] = quantumEntanglement;
                    } else if(newSize == minSize[0]) {
                        minQuantumEntanglement[0] = min(quantumEntanglement, minQuantumEntanglement[0]);
                    }
                } else if (remainingPackage < remainingWeight) {
                    long newRemainingWeight = remainingWeight - remainingPackage;
                    newRemainingPackages.remove(remainingPackage);
                    selectedPackages.add(remainingPackage);
                    findPackages(newRemainingWeight, selectedPackages, newRemainingPackages, minSize, minQuantumEntanglement);
                    selectedPackages.remove(remainingPackage);
                    newRemainingPackages.add(remainingPackage);
                }
            } else {
                break;
            }
        }
    }

    static long quantumEntanglement(List<Long> group) {
        return quantumEntanglement(group.stream());
    }

    static long quantumEntanglement(Stream<Long> stream) {
        return stream.reduce(1L, (product, val) -> product * val);
    }
}

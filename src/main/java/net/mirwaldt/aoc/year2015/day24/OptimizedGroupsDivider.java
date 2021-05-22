package net.mirwaldt.aoc.year2015.day24;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.min;

public class OptimizedGroupsDivider implements GroupsDivider {
    private final int numberOfGroups;

    public OptimizedGroupsDivider(int numberOfGroups) {
        this.numberOfGroups = numberOfGroups;
    }

    @Override
    public long divideIntoGroups(List<Long> packages) {
        final long sum = packages.stream().mapToLong(Long::intValue).sum();
        packages.sort(Comparator.<Long>naturalOrder().reversed());
        if (sum % numberOfGroups == 0) {
            final long weightOfEachGroup = sum / numberOfGroups;
            return new InternalGroupsDivider().divideIntoGroups(weightOfEachGroup, packages);
        } else {
            throw new IllegalArgumentException(
                    "Sum of packages " + packages + "cannot be divided by " + numberOfGroups + ". sum=" + sum);
        }
    }

    static class InternalGroupsDivider {
        private long minSize;
        private long minQuantumEntanglement;

        public long divideIntoGroups(long weightOfEachGroup, List<Long> packages) {
            minSize = Integer.MAX_VALUE;
            minQuantumEntanglement = Integer.MAX_VALUE;
            recursiveDivideIntoGroups(weightOfEachGroup, 1, new ArrayList<>(), packages);
            return minQuantumEntanglement;
        }

        public void recursiveDivideIntoGroups(
                long remainingWeight, long quantumEntanglement,
                List<Long> selectedPackages, List<Long> remainingPackages) {
            int newSize = selectedPackages.size() + 1;
            for (int i = 0; i < remainingPackages.size() && newSize <= minSize; i++) {
                Long remainingPackage = remainingPackages.get(i);
                long newQuantumEntanglement = quantumEntanglement * remainingPackage;
                if (remainingWeight == remainingPackage) {
                    if (newSize < minSize) {
                        minSize = newSize;
                        minQuantumEntanglement = newQuantumEntanglement;
                    } else if (newSize == minSize) {
                        minQuantumEntanglement = min(newQuantumEntanglement, minQuantumEntanglement);
                    }
                } else if (remainingPackage < remainingWeight) {
                    final List<Long> newRemainingPackages = remainingPackages.subList(i + 1, remainingPackages.size());
                    if(!newRemainingPackages.isEmpty()) {
                        final long newRemainingWeight = remainingWeight - remainingPackage;
                        selectedPackages.add(remainingPackage);
                        recursiveDivideIntoGroups(
                                newRemainingWeight, newQuantumEntanglement, selectedPackages, newRemainingPackages);
                        selectedPackages.remove(remainingPackage);
                    }
                }
            }
        }
    }
}
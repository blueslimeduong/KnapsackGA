import java.util.*;

public class KnapsackDP {
    public static void main(String[] args) {
        // Define input parameters
//        int capacity = 10;
//        int numItems = 5;
//        int[] weights = new int[numItems];
//        int[] values = new int[numItems];

//        // Generate random input
//        Random random = new Random();
//        for (int i = 0; i < numItems; i++) {
//            weights[i] = random.nextInt(5) + 1; // random weight between 1 and 5
//            values[i] = random.nextInt(10) + 1; // random value between 1 and 10
//        }
        int capacity = 500;
        int numItems = 200;
        int populationSize = 1000;
        int maxGenerations = 1000;
        double mutationProbability = 0.01;
        int[] itemWeights = new int[numItems];
        int[] itemValues = new int[numItems];
        Random rand = new Random();
        for (int i = 0; i < numItems; i++) {
            itemWeights[i] = rand.nextInt(10) + 1;
            itemValues[i] = rand.nextInt(50) + 1;
//            System.out.println(itemWeights[i] + " - " + itemValues[i] + ", ");
        }

//        int[] itemWeights =new int []{7, 5, 3, 4, 10, 9, 5, 2, 9, 1, 6, 5, 8, 8, 5, 8, 6, 10, 3, 2};
//        int[] itemValues = new int[]{7, 1, 16, 10, 2, 19, 19, 9, 7, 2, 8, 15, 13, 2, 9, 2, 3, 1, 17, 18};

        // Print input
        System.out.println("Capacity: " + capacity);
        System.out.println("Weights: " + Arrays.toString(itemWeights));
        System.out.println("Values: " + Arrays.toString(itemValues));

        long startTimeGA = System.nanoTime();
        KnapsackGA knapsackGA = new KnapsackGA(capacity, numItems, populationSize, maxGenerations,
                mutationProbability, itemWeights, itemValues);
        knapsackGA.solve();
        long endTimeGA   = System.nanoTime();
        long totalTimeGA = endTimeGA - startTimeGA;
        System.out.println("GA runtime: " + totalTimeGA);

        // Solve knapsack problem
        long startTimeDP = System.nanoTime();
        int[][] dp = new int[numItems + 1][capacity + 1];
        for (int i = 1; i <= numItems; i++) {
            for (int j = 1; j <= capacity; j++) {
                if (itemWeights[i-1] <= j) {
                    dp[i][j] = Math.max(itemValues[i-1] + dp[i-1][j-itemWeights[i-1]], dp[i-1][j]);
                } else {
                    dp[i][j] = dp[i-1][j];
                }
            }
        }
        int maxVal = dp[numItems][capacity];

        // Print solution
        System.out.println("Maximum value(DP): " + maxVal);
        long endTimeDP   = System.nanoTime();
        long totalTimeDP = endTimeDP - startTimeDP;
        System.out.println("GA runtime: " + totalTimeDP);
    }
}

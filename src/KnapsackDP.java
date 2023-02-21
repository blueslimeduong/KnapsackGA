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
        int capacity = 50;
        int numItems = 20;
        int populationSize = 100;
        int maxGenerations = 100;
        double mutationProbability = 0.01;
//        int[] itemWeights = new int[numItems];
//        int[] itemValues = new int[numItems];
//        Random rand = new Random();
//        for (int i = 0; i < numItems; i++) {
//            itemWeights[i] = rand.nextInt(10) + 1;
//            itemValues[i] = rand.nextInt(20) + 1;
////            System.out.println(itemWeights[i] + " - " + itemValues[i] + ", ");
//        }

        int[] itemWeights =new int []{5, 10, 1, 5, 6, 1, 2, 2, 5, 5, 1, 4, 5, 6, 10, 10, 1, 6, 10, 4};
        int[] itemValues = new int[]{3, 14, 20, 20, 19, 3, 14, 19, 19, 20, 3, 16, 2, 20, 20, 16, 10, 7, 20, 20};

        // Print input
        System.out.println("Capacity: " + capacity);
        System.out.println("Weights: " + Arrays.toString(itemWeights));
        System.out.println("Values: " + Arrays.toString(itemValues));

        KnapsackGA knapsackGA = new KnapsackGA(capacity, numItems, populationSize, maxGenerations,
                mutationProbability, itemWeights, itemValues);
        knapsackGA.solve();


        // Solve knapsack problem
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
        System.out.println("Maximum value: " + maxVal);
    }
}

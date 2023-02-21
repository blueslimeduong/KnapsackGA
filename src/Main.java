import java.util.Random;

public class Main {

    public static void main(String[] args) {
        int knapsackCapacity = 50;
        int numItems = 20;
        int populationSize = 10;
        int maxGenerations = 50;
        double mutationProbability = 0.01;
        int[] itemWeights = new int[numItems];
        int[] itemValues = new int[numItems];
        Random rand = new Random();
        for (int i = 0; i < numItems; i++) {
            itemWeights[i] = rand.nextInt(10) + 1;
            itemValues[i] = rand.nextInt(20) + 1;
            System.out.println(itemWeights[i] + " - " + itemValues[i] + ", ");
        }

        KnapsackGA knapsackGA = new KnapsackGA(knapsackCapacity, numItems, populationSize, maxGenerations,
                mutationProbability, itemWeights, itemValues);
        knapsackGA.solve();
    }
}

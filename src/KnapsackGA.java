import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class KnapsackGA {

    private int knapsackCapacity;
    private int numItems;
    private int[] itemWeights;
    private int[] itemValues;
    private int populationSize;
    private int maxGenerations;
    private double mutationProbability;
    private List<int[]> population;

    public KnapsackGA(int knapsackCapacity, int numItems, int populationSize, int maxGenerations, double mutationProbability,
                      int[] itemWeights, int[] itemValues) {
        this.knapsackCapacity = knapsackCapacity;
        this.numItems = numItems;
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.mutationProbability = mutationProbability;
        this.population = new ArrayList<>();
        this.itemWeights = itemWeights;
        this.itemValues = itemValues;

    }

    public void solve() {
        initializePopulation();
        for (int generation = 0; generation < maxGenerations; generation++) {
            List<int[]> offspring = new ArrayList<>();
            for (int i = 0; i < populationSize; i++) {
                int[] parent1 = selectParent();
                int[] parent2 = selectParent();
                int[] child = crossover(parent1, parent2);
                mutate(child);
                offspring.add(child);
            }
            population = selectSurvivors(offspring);
            if (generation % 10 == 0) {
                System.out.println("Generation " + generation + ": Best fitness = " + getFitness(population.get(0)));
            }
        }
        int[] bestSolution = population.get(0);
        System.out.println("Best solution found: " + toString(bestSolution));
    }

    private void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            int[] chromosome = new int[numItems];
            for (int j = 0; j < numItems; j++) {
                chromosome[j] = new Random().nextInt(2);
            }
            population.add(chromosome);
        }
    }

    private int[] selectParent() {
        Random rand = new Random();
        int index1 = rand.nextInt(populationSize);
        int index2 = rand.nextInt(populationSize);
        return getFitness(population.get(index1)) > getFitness(population.get(index2)) ? population.get(index1) : population.get(index2);
    }

    private int[] crossover(int[] parent1, int[] parent2) {
        int[] child = new int[numItems];
        Random rand = new Random();
        int crossoverPoint = rand.nextInt(numItems);
        for (int i = 0; i < crossoverPoint; i++) {
            child[i] = parent1[i];
        }
        for (int i = crossoverPoint; i < numItems; i++) {
            child[i] = parent2[i];
        }
        return child;
    }

    private void mutate(int[] child) {
        Random rand = new Random();
        for (int i = 0; i < numItems; i++) {
            if (rand.nextDouble() < mutationProbability) {
                child[i] = 1 - child[i];
            }
        }
    }

//    private List<int[]> selectSurvivors(List<int[]> offspring) {
//        List<int[]> combinedPopulation = new ArrayList<>();
//        combinedPopulation.addAll(population);
//        combinedPopulation.addAll(offspring);
//        combinedPopulation.sort((c1, c2) -> Double.compare(getFitness(c2), getFitness(c1)));
//        List<int[]> survivors = new ArrayList<>();
//        for (int i = 0; i < populationSize; i++) {
//            survivors.add(combinedPopulation.get(i));
//        }
//        return survivors;
//    }
    private List<int[]> selectSurvivors(List<int[]> offspring) {
        List<int[]> combinedPopulation = new ArrayList<>();
        combinedPopulation.addAll(population);
        combinedPopulation.addAll(offspring);
        int totalFitness = 0;
        double[] relativeFitness = new double[combinedPopulation.size()];
        for (int i = 0; i < combinedPopulation.size(); i++) {
            double fitness = getFitness(combinedPopulation.get(i));
            totalFitness += fitness;
            relativeFitness[i] = fitness;
        }
        for (int i = 1; i < relativeFitness.length; i++) {
            relativeFitness[i] += relativeFitness[i - 1];
        }
        List<int[]> survivors = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            double rand = new Random().nextDouble() * totalFitness;
            int index = Arrays.binarySearch(relativeFitness, rand);
            if (index < 0) {
                index = -index - 1;
            }
            survivors.add(combinedPopulation.get(index));
        }
        return survivors;
    }
    private double getFitness(int[] chromosome) {
        int totalWeight = 0;
        int totalValue = 0;
        for (int i = 0; i < numItems; i++) {
            if (chromosome[i] == 1) {
                totalWeight += itemWeights[i];
                totalValue += itemValues[i];
            }
        }
        if (totalWeight > knapsackCapacity) {
            return 0;
        } else {
            return totalValue;
        }
    }

    private String toString(int[] chromosome) {
        StringBuilder sb = new StringBuilder();
        int totalWeight = 0;
        int totalValue = 0;
        for (int i = 0; i < numItems; i++) {
            if (chromosome[i] == 1) {
                sb.append("1");
                totalWeight += itemWeights[i];
                totalValue += itemValues[i];
            } else {
                sb.append("0");
            }
        }
        sb.append("\nTotal weight: ").append(totalWeight).append("\nTotal value: ").append(totalValue);
        return sb.toString();
    }

//    public static void main(String[] args) {
//        int knapsackCapacity = 50;
//        int numItems = 20;
//        int populationSize = 100;
//        int maxGenerations = 1000;
//        double mutationProbability = 0.01;
//        KnapsackGA knapsackGA = new KnapsackGA(knapsackCapacity, numItems, populationSize, maxGenerations, mutationProbability);
//        knapsackGA.solve();
//    }
}
package mgr.algorithm.weed.optimization;

import mgr.test.functions.AlgorithmFactory;
import mgr.test.functions.AlgsEnum;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WeedColony {

    public static final int INITIAL_POPULATION = 10;
    public static final int MAX_ITERATIONS = 500;
    public static final int DIMENSION = 2;
    public static final int MAX_NUMBER_OF_PLANT_POPULATION = 30;
    public static final int MAX_NUMBER_OF_SEEDS = 5;
    public static final int MIN_NUMBER_OF_SEEDS = 0;
    public static final int NONLINEAR_MODULATION = 3;
    public static final double INIT_VALUE_OF_STANDARD_DEV = 10.0;
    public static final double FINAL_VALUE_OF_STANDARD_DEV = 0.1;

    private static final double LOWER_BOUNDARY = -4.5;
    private static final double UPPER_BOUNDARY = 4.5;

    private int currentIteration = 1;

    private AlgsEnum algorithm;

    private ArrayList<Weed> bestWeeds;
    private ArrayList<Weed> seedCandidates;

    private double currentMaxFitness;
    private double currentMinFitness;

    private double currentStandardDeviation;

    private Random rand;

    private double bestGlobalFitness;
    private double[] bestGlobalPositions;

    public WeedColony(AlgsEnum alg){
        this.algorithm = alg;
        this.bestWeeds = new ArrayList<Weed>();
        this.seedCandidates = new ArrayList<Weed>();
        this.bestGlobalPositions = new double[DIMENSION];
        this.rand = new Random();
        initAgentsAtBeginning();
    }

    private void initAgentsAtBeginning(){
        for (int i = 0; i < INITIAL_POPULATION; i++) {
            this.bestWeeds.add(new Weed(DIMENSION));
        }
        calculateWeedsFitnessAtBeginning();
        sortAndSaveMinMaxFitness();
        setGlobalBestWeedPositionsAndFitness();
    }

    public void calculateWeedsFitnessAtBeginning(){
        double[] eachWeedPositions;
        double eachWeedFitness;
        for (Weed weed : bestWeeds){
            eachWeedPositions = weed.getPositions();
            eachWeedFitness = AlgorithmFactory.getResultOfAlgorithm(algorithm, eachWeedPositions[0], eachWeedPositions[1]);
            weed.setFitness(eachWeedFitness);
        }
    }

    public void sortAndSaveMinMaxFitness(){
        Collections.sort(this.bestWeeds);
        double minFitness = getBestWeed().getFitness();
        setCurrentMinFitness(minFitness);
        double maxFitness = getWorstWeed().getFitness();
        setCurrentMaxFitness(maxFitness);
    }

    private Weed getBestWeed(){
        return this.bestWeeds.get(0);
    }

    private void setCurrentMinFitness(double newMinFitness){
        this.currentMinFitness = newMinFitness;
    }

    private Weed getWorstWeed(){
        return this.bestWeeds.get(bestWeeds.size()-1);
    }

    private void setCurrentMaxFitness(double newMaxFitness){
        this.currentMaxFitness = newMaxFitness;
    }

    private void setGlobalBestWeedPositionsAndFitness(){
        Weed bestWeed = getBestWeed();
        this.bestGlobalFitness = bestWeed.getFitness();
        System.arraycopy(bestWeed.getPositions(), 0, this.bestGlobalPositions, 0, bestWeed.getPositions().length);
    }

    public void getMinimum(){
        initAgentsAtBeginning();
        while(currentIteration <= MAX_ITERATIONS){
            setCurrentStandardDeviation();
            for (int i = 0; i < this.bestWeeds.size(); i++) {
                setNumberOfSeedsToProduce(i);
            }

            currentIteration++;
        }
    }

    private void setNumberOfSeedsToProduce(int weedIndex){
        double currentWeedFitness = this.bestWeeds.get(weedIndex).getFitness();
        int seedsNumber = (int) ((MAX_NUMBER_OF_SEEDS*(currentMaxFitness-currentWeedFitness))/(currentMaxFitness-currentMinFitness));
        seedsNumber = trimMaxNumberOfSeeds(seedsNumber);
        this.bestWeeds.get(weedIndex).setNumberOfSeeds(seedsNumber);
    }

    private int trimMaxNumberOfSeeds(int seeds){
        return seeds <= MAX_NUMBER_OF_SEEDS ? seeds : MAX_NUMBER_OF_SEEDS;
    }

    private void trimBestWeedsList(){
        for (int i = bestWeeds.size()-1; i > MAX_NUMBER_OF_PLANT_POPULATION; i--) {
            bestWeeds.remove(i);
        }
    }

    private void setCurrentStandardDeviation(){
        this.currentStandardDeviation = (Math.pow(MAX_ITERATIONS - currentIteration, NONLINEAR_MODULATION)
                /Math.pow(MAX_ITERATIONS, NONLINEAR_MODULATION))*(INIT_VALUE_OF_STANDARD_DEV-FINAL_VALUE_OF_STANDARD_DEV)
                +FINAL_VALUE_OF_STANDARD_DEV;
    }

    public void printWeedColony(){
        for (Weed weed : bestWeeds){
            weed.printWeed();
        }
    }

    public void printBestWeed(){
        System.out.println("Best Weed Position 0: " + getBestWeed().getPositionAtIndex(0) + ", Position 1: " + getBestWeed().getPositionAtIndex(1)
                + " Fitness: " + getBestWeed().getFitness());
    }
}

package mgr.algorithm.weed.optimization;

import mgr.algorithm.SwarmAlgorithm;
import mgr.test.functions.TestFuncEnum;
import mgr.test.functions.TestFuncFactory;
import mgr.test.functions.TestFunction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WeedColony implements SwarmAlgorithm{

    public static final int DIMENSION = 2;
    public static final int INITIAL_AGENTS = 10*DIMENSION;
    public static final int MAX_NUMBER_OF_PLANT_POPULATION = 20*DIMENSION;
    public static final int MAX_ITERATIONS = 1000;
    public static final int MAX_NUMBER_OF_SEEDS = 5;
    public static final int MIN_NUMBER_OF_SEEDS = 0;
    public static final int NONLINEAR_MODULATION = 3;

//    private static final double LOWER_BOUNDARY = -4.5;
//    private static final double UPPER_BOUNDARY = 4.5;

    private int currentIteration = 1;

//    private AlgsEnum algorithm;
    private TestFunction testFunction;

    private ArrayList<Weed> bestWeeds;
    private ArrayList<Weed> seedCandidates;

    private double currentMaxFitness;
    private double currentMinFitness;

    private double currentStandardDeviation;

    private Random rand;

    private double initValueOfStandardDev;
    private double finalValueOfStandardDev;

    private double lowerBoundary;
    private double upperBoundary;


    public WeedColony(TestFuncEnum alg, double initValueOfStandardDev, double finalValueOfStandardDev){
        this.testFunction = TestFuncFactory.getTestFunction(alg);
        initBoundariesFromTestFunc();
        this.initValueOfStandardDev = initValueOfStandardDev;
        this.finalValueOfStandardDev = finalValueOfStandardDev;
        this.bestWeeds = new ArrayList<Weed>();
        this.seedCandidates = new ArrayList<Weed>();
        this.rand = new Random();
    }

    private void initBoundariesFromTestFunc(){
        this.lowerBoundary = testFunction.getLowerBoundary();
        this.upperBoundary = testFunction.getUpperBoundary();
    }

    /*private void setGlobalBestWeedPositionsAndFitness(){
        Weed bestWeed = getBestWeed();
        bestGlobalFitness = bestWeed.getFitness();
        System.arraycopy(bestWeed.getPositions(), 0, this.bestGlobalPositions, 0, bestWeed.getPositions().length);
    }*/

    public int getMinimum(){
        initAgentsAtBeginning();
        while(currentIteration <= MAX_ITERATIONS){
            setCurrentStandardDeviation();
            clearSeedCandidatesList();
            setNumberOfSeedsToProduce();
            produceNewSeeds();
            setUpdatedPositionsToSeeds();
            calculateWeedsFitnessAtList(seedCandidates);
            addBothListAndSortIt();
            trimBestWeedsList();
            if (testFunction.isSolutionEnoughNearMinimum(getBestFitness())) {
                System.out.println("Algorytm wykonał " + currentIteration + " iteracji.");
                return currentIteration;
            }
            currentIteration++;
        }
        return 0;
    }

    private void initAgentsAtBeginning(){
        for (int i = 0; i < INITIAL_AGENTS; i++) {
            this.bestWeeds.add(new Weed(DIMENSION, lowerBoundary, upperBoundary));
        }
        calculateWeedsFitnessAtList(bestWeeds);
        sortAndSaveMinMaxFitness();
//        setGlobalBestWeedPositionsAndFitness();
    }

    private void calculateWeedsFitnessAtList(ArrayList<Weed> listOfWeeds){
        double[] eachWeedPositions;
        double eachWeedFitness;
        for (Weed weed : listOfWeeds){
            eachWeedPositions = weed.getPositions();
//            eachWeedFitness = TestFuncFactory.getResultOfAlgorithm(algorithm, eachWeedPositions[0], eachWeedPositions[1]);
            eachWeedFitness = testFunction.getResult(eachWeedPositions);
            weed.setFitness(eachWeedFitness);
        }
    }

    private void sortAndSaveMinMaxFitness(){
        Collections.sort(this.bestWeeds);
        double minFitness = getBestWeed().getFitness();
        setCurrentMinFitness(minFitness);
        double maxFitness = getWorstWeed().getFitness();
        setCurrentMaxFitness(maxFitness);
    }

    private Weed getBestWeed(){
        return bestWeeds.get(0);
    }

    private double getBestFitness(){
        return getBestWeed().getFitness();
    }

    private void setCurrentMinFitness(double newMinFitness){
        this.currentMinFitness = newMinFitness;
    }

    private Weed getWorstWeed(){
        return bestWeeds.get(bestWeeds.size()-1);
    }

    private void setCurrentMaxFitness(double newMaxFitness){
        this.currentMaxFitness = newMaxFitness;
    }

    private void setCurrentStandardDeviation(){
        this.currentStandardDeviation = (Math.pow(MAX_ITERATIONS - currentIteration, NONLINEAR_MODULATION)
                /Math.pow(MAX_ITERATIONS, NONLINEAR_MODULATION))*(initValueOfStandardDev-finalValueOfStandardDev)
                +finalValueOfStandardDev;
    }

    private void clearSeedCandidatesList(){
        seedCandidates.clear();
    }

    private void setNumberOfSeedsToProduce(){
        for (int i = 0; i < this.bestWeeds.size(); i++) {
            setNewSeedsNumberForEachWeed(i);
        }
    }

    private void setNewSeedsNumberForEachWeed(int weedIndex){
        double currentWeedFitness = this.bestWeeds.get(weedIndex).getFitness();
        int seedsNumber = (int) ((MAX_NUMBER_OF_SEEDS*(currentMaxFitness-currentWeedFitness))/(currentMaxFitness-currentMinFitness));
        seedsNumber = trimMaxNumberOfSeeds(seedsNumber);
        this.bestWeeds.get(weedIndex).setNumberOfSeeds(seedsNumber);
    }

    private int trimMaxNumberOfSeeds(int seeds){
        return seeds <= MAX_NUMBER_OF_SEEDS ? seeds : MAX_NUMBER_OF_SEEDS;
    }

    private void produceNewSeeds(){
        int numberOfSeeds;
        double[] parentWeedPositions;
        for (int i = 0; i < bestWeeds.size(); i++) {
            numberOfSeeds = bestWeeds.get(i).getNumberOfSeeds();
            parentWeedPositions = bestWeeds.get(i).getPositions();
            produceSeeds(numberOfSeeds, parentWeedPositions);
        }
    }

    private void produceSeeds(int numberOfSeeds, double[] parentPositions){
        for (int i = 0; i < numberOfSeeds; i++) {
            Weed newSeed = new Weed(DIMENSION, parentPositions);
            seedCandidates.add(newSeed);
        }
    }

    private void setUpdatedPositionsToSeeds(){
        for (Weed seed : seedCandidates){
            seed.updateOldPositions(getNewRandomPositionsWithCurrentStandardDeviation());
        }
    }

    private double[] getNewRandomPositionsWithCurrentStandardDeviation(){
        double[] newRandomPositions = new double[DIMENSION];
        for (int i = 0; i < newRandomPositions.length; i++) {
            newRandomPositions[i] = rand.nextDouble()*currentStandardDeviation;
        }
        return newRandomPositions;
    }

    private void addBothListAndSortIt(){
        bestWeeds.addAll(seedCandidates);
        Collections.sort(bestWeeds);
    }

    private void trimBestWeedsList(){
        for (int i = bestWeeds.size()-1; i > MAX_NUMBER_OF_PLANT_POPULATION; i--) {
            bestWeeds.remove(i);
        }
    }

    /*public void printWeedColony(){
        for (Weed weed : bestWeeds){
            weed.printWeed();
        }
    }*/

    public void printBestWeed(){
        System.out.println("BEST WEED: ");
        System.out.println("Best Weed Position 0: " + getBestWeed().getPositionAtIndex(0) + ", Position 1: " + getBestWeed().getPositionAtIndex(1)
                + " Fitness: " + getBestWeed().getFitness());
    }
}

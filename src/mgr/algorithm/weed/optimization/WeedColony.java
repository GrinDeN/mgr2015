package mgr.algorithm.weed.optimization;

import mgr.algorithm.SwarmAlgorithm;
import mgr.config.Config;
import mgr.teacher.NetworkTeacher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class WeedColony implements SwarmAlgorithm{

    public static final int DIMENSION = Config.NUM_OF_WEIGHTS;
//    public static final int INITIAL_AGENTS = 3*DIMENSION;
    public static final int INITIAL_AGENTS = 7;
//    public static final int MAX_NUMBER_OF_PLANT_POPULATION = 5*DIMENSION;
    public static final int MAX_NUMBER_OF_PLANT_POPULATION = 15;
//    public static final int MAX_ITERATIONS = 200;
    public static final int MAX_NUMBER_OF_SEEDS = 5;
    public static final int MIN_NUMBER_OF_SEEDS = 0;
    public static final int NONLINEAR_MODULATION = 3;

//    private static final double LOWER_BOUNDARY = -4.5;
//    private static final double UPPER_BOUNDARY = 4.5;

    private int currentIteration = 1;

//    private AlgsEnum algorithm;
    private NetworkTeacher netTeacher;

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

    private int iterations;

    public WeedColony(NetworkTeacher teacher, int numOfIters, double initValueOfStandardDev, double finalValueOfStandardDev){
        this.netTeacher = teacher;
        initBoundariesFromTestFunc();
        this.iterations = numOfIters;
        this.initValueOfStandardDev = initValueOfStandardDev;
        this.finalValueOfStandardDev = finalValueOfStandardDev;
        this.bestWeeds = new ArrayList<Weed>();
        this.seedCandidates = new ArrayList<Weed>();
        this.rand = new Random();
    }

    private void initBoundariesFromTestFunc(){
        this.lowerBoundary = Config.LOWER_BOUNDARY;
        this.upperBoundary = Config.UPPER_BOUNDARY;
    }

    /*private void setGlobalBestWeedPositionsAndFitness(){
        Weed bestWeed = getBestWeed();
        bestGlobalFitness = bestWeed.getFitness();
        System.arraycopy(bestWeed.getPositions(), 0, this.bestGlobalPositions, 0, bestWeed.getPositions().length);
    }*/

    @Override
    public int getMinimum() throws Exception{
        initAgentsAtBeginning();
        while(currentIteration <= iterations){
            setCurrentStandardDeviation();
            clearSeedCandidatesList();
            setNumberOfSeedsToProduce();
            produceNewSeeds();
            setUpdatedPositionsToSeeds();
            calculateWeedsFitnessAtList(seedCandidates);
            addBothListAndSortIt();
            trimBestWeedsList();
            currentIteration++;
        }
        netTeacher.setWeightsToNetwork(getBestPositions());
        printBestPositions();
        printBestWeed();
        return 0;
    }

    @Override
    public String getName(){
        return "WeedColony";
    }

    @Override
    public double[] getBestPositions(){
        return getBestWeed().getPositions();
    }

    private void printBestPositions(){
        double[] alphaPositions = getBestPositions();
        int rowCounter = 1;
        for (int i = 0; i < alphaPositions.length; i++) {
            if (i%Config.INPUT_SIZE <= Config.INPUT_SIZE && rowCounter <= Config.HIDD_NEURONS) {
                System.out.print(alphaPositions[i] + " ");
                if (i%Config.INPUT_SIZE == 4 ){
                    System.out.println();
                    rowCounter++;
                }
            } else {
                System.out.print(alphaPositions[i] + " ");
            }
        }
        System.out.println();
    }

    private void initAgentsAtBeginning() throws Exception{
        for (int i = 0; i < INITIAL_AGENTS; i++) {
            this.bestWeeds.add(new Weed(DIMENSION, lowerBoundary, upperBoundary));
        }
        calculateWeedsFitnessAtList(bestWeeds);
        sortAndSaveMinMaxFitness();
//        setGlobalBestWeedPositionsAndFitness();
    }

    private void calculateWeedsFitnessAtList(ArrayList<Weed> listOfWeeds) throws Exception{
        double[] eachWeedPositions;
        double eachWeedFitness;
        for (Weed weed : listOfWeeds){
            eachWeedPositions = weed.getPositions();
            eachWeedFitness = netTeacher.getErrorOfNetwork(eachWeedPositions, false);
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
        this.currentStandardDeviation = (Math.pow(iterations - currentIteration, NONLINEAR_MODULATION)
                /Math.pow(iterations, NONLINEAR_MODULATION))*(initValueOfStandardDev-finalValueOfStandardDev)
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
        System.out.println("Best Fitness: " + getBestWeed().getFitness());
    }
}

package mgr.algorithm.bat.swarm;

import mgr.algorithm.SwarmAlgorithm;
import mgr.test.functions.TestFuncEnum;
import mgr.test.functions.TestFuncFactory;
import mgr.test.functions.TestFunction;

import java.util.Random;

public class BatSwarm implements SwarmAlgorithm{

    private Bat[] batSwarm;
    private int numBats;
    private int dimension;
    private double[] bestPositions;
    private double minimumValue;

    private double pulseRate;
    private double loudness;

    private Random rand;

    private int iterations;

    private TestFunction testFunction;

    public BatSwarm(TestFuncEnum alg, int iters, int numOfBats, int dim){
        this.testFunction = TestFuncFactory.getTestFunction(alg);
        this.iterations = iters;
        this.numBats = numOfBats;
        this.dimension = dim;
        this.pulseRate = 0.5;
        this.loudness = 0.5;
        this.minimumValue = Double.POSITIVE_INFINITY;
        this.bestPositions = new double[dimension];
        rand = new Random();
        initBats();
    }

    private void initBats(){
        this.batSwarm = new Bat[numBats];
        for (int i = 0; i < numBats; i++) {
            batSwarm[i] = new Bat(dimension, testFunction.getLowerBoundary(), testFunction.getUpperBoundary());
        }
    }

    public int getMinimum(){
        double result;
        for (int i = 0; i < getNumOfBats(); i++) {
            double[] eachBatPositions = getBatAtIndex(i).get_xBestPositions();
            result = testFunction.getResult(eachBatPositions);
            getBatAtIndex(i).setCurrentMinimum(result);
            if (i==0){
                setBestMinimumValue(result);
                setBestPositions(eachBatPositions);
            } else{
                updateSolutionIfBetter(result, eachBatPositions);
            }
        }
//        updateBestPositionsInAllBats();
//        System.out.println("Najlepsze wskazane wspolrzedne w fazie wstepnej, x: " + getBestPosAtIndex(0) + " y: " + getBestPosAtIndex(1));
//        System.out.println("Najlepszy wskazany rezultat w fazie wstepnej: " + getMinimumValue());
        //koniec fazy wstepnej

        for (int iter = 0; iter < iterations; iter++) {
            if (testFunction.isSolutionEnoughNearMinimum(getMinimumValue())) {
                System.out.println("Algorytm wykonał " + iter + " iteracji.");
                return iter;
            }
            for (int i = 0; i < getNumOfBats(); i++){
                getBatAtIndex(i).updateMovement();
//                batSwarm.correctPositionsToBoundaries(i);
                getSomeRandomWalk(i);
                double[] eachBatPositions = getBatAtIndex(i).get_xPositions();
//                result = AckleyFunc.function(eachBatPositions[0], eachBatPositions[1]);
                result = testFunction.getResult(eachBatPositions);
//                batSwarm.updateSolutionIfBetter(result, eachBatPositions);
                updateBatAtIndexIfBetterSol(i, result);
                updateSolutionIfBetter(result, eachBatPositions);
//                batSwarm.updateBestPositionsInAllBats();
            }
        }
        return 0;
    }

    private Bat getBatAtIndex(int index){
        return this.batSwarm[index];
    }

//    public void correctPositionsToBoundaries(int index){
//        this.batSwarm[index].correctPositions();
//    }

    private void getSomeRandomWalk(int index){
        if (getSomeRandomDoubleValueFrom0To1() > pulseRate){
            this.batSwarm[index].generateNewPositionsNearBest();
        }
    }

    private void setBestPositions(double[] best){
        this.bestPositions = best;
    }

    private void setBestMinimumValue(double value){
        this.minimumValue = value;
    }

    private void updateSolutionIfBetter(double value, double[] positions){
        if (value < this.minimumValue){
            this.minimumValue = value;
//            this.bestPositions = positions;
            System.arraycopy(positions, 0, this.bestPositions, 0, positions.length);
            updateBestPositionsInAllBats();
        }
    }

    private void updateBestPositionsInAllBats(){
        for (int i = 0; i < numBats; i++) {
            this.batSwarm[i].setGlobalBestPositions(this.bestPositions);
        }
    }

    private void updateBatAtIndexIfBetterSol(int index, double result){
        this.batSwarm[index].updatePositionsAndMinimumIfBetter(result, loudness);
    }

//    public void updateSolutionIfBetterInEarlyPhase(double value, double[] positions){
//        if (value < this.minimumValue){
//            this.minimumValue = value;
//            this.bestPositions = positions;
//        }
//    }

    private double getSomeRandomDoubleValueFrom0To1(){
        return rand.nextDouble();
    }

    private double getNumOfBats(){
        return this.numBats;
    }

    private double getBestPosAtIndex(int index){
        return this.bestPositions[index];
    }

    private double getMinimumValue(){
        return this.minimumValue;
    }

    public void printResult(){
        System.out.println("Najlepsze wskazane wspolrzedne po wszystkich iteracjach, x: " + getBestPosAtIndex(0) + " y: " + getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat po wszystkich iteracjach: " + getMinimumValue());
    }

}

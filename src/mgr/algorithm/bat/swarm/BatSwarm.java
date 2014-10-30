package mgr.algorithm.bat.swarm;

import java.util.Random;

public class BatSwarm {

    private Bat[] batSwarm;
    private int numBats;
    private int dimension;
    private double[] bestPositions;
    private double minimumValue;

    private double pulseRate;
    private double loudness;

    private Random rand;

    public BatSwarm(int numOfBats, int dim){
        this.numBats = numOfBats;
        this.dimension = dim;
        this.pulseRate = 0.5;
        this.loudness = 0.5;
        this.minimumValue = 1000000;
        this.bestPositions = new double[dimension];
        rand = new Random();
        initBats();
    }

    private void initBats(){
        this.batSwarm = new Bat[numBats];
        for (int i = 0; i < numBats; i++) {
            batSwarm[i] = new Bat(dimension);
        }
    }

    public Bat getBatAtIndex(int index){
        return this.batSwarm[index];
    }

//    public void correctPositionsToBoundaries(int index){
//        this.batSwarm[index].correctPositions();
//    }

    public void getSomeRandomWalk(int index){
        if (getSomeRandomDoubleValueFrom0To1() > pulseRate){
            this.batSwarm[index].generateNewPositionsNearBest();
        }
    }

    public void setBestPositions(double[] best){
        this.bestPositions = best;
    }

    public void setBestMinimumValue(double value){
        this.minimumValue = value;
    }

    public void updateSolutionIfBetter(double value, double[] positions){
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

    public void updateBatAtIndexIfBetterSol(int index, double result){
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

    public double getNumOfBats(){
        return this.numBats;
    }

    public double getBestPosAtIndex(int index){
        return this.bestPositions[index];
    }

    public double getMinimumValue(){
        return this.minimumValue;
    }

}

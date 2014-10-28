package mgr.algorithm.bat.swarm;

import java.util.Random;

public class BatSwarm {

    private Bat[] batSwarm;
    private int numBats;
    private int dimension;
    private double[] currentBest;
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
        this.currentBest = new double[dimension];
        rand = new Random();
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


    public void getSomeRandomWalk(int index){
        if (getSomeRandomValueFrom0To1() > pulseRate){
            this.batSwarm[index].generateNewPositionsNearBest();
        }
    }

    public void correctPositionsToBoundaries(int index){
        this.batSwarm[index].correctPositions();
    }

    public void setBestPositions(double[] best){
        this.currentBest = best;
    }

    public void setBestMinimumValue(double value){
        this.minimumValue = value;
    }

    public void updateSolutionIfBetter(double value, double[] positions){
        if (value < this.minimumValue && getSomeRandomValueFrom0To1() < loudness){
            this.minimumValue = value;
            this.currentBest = positions;
        }
    }

    private double getSomeRandomValueFrom0To1(){
        return rand.nextDouble();
    }

}

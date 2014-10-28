package mgr.algorithm.bat.swarm;

import java.util.Random;

public class Bat {

    private int xSize;
    private double[] velocity;
    private double[] xPositions;
    private double[] xBest;
    private double frequency;

    private Random rand;

    private double currentMinimum;
    private double bestGlobalMinimum;
//    private double beta;

    private double lowerBoundary = -10;
    private double upperBoundary = 10;

    private static final double f_min = 0;
    private static final double f_max = 10;

    public Bat(int dimension){
        this.xSize = dimension;
        this.xPositions = new double[xSize];
        this.xBest = new double[xSize];
        this.velocity = new double[xSize];
        this.currentMinimum = 0;
        this.bestGlobalMinimum = 0;
        this.rand = new Random();
        initFrequency();
        initializeBat();
    }

    private void initFrequency(){
        this.frequency = getRandomFromRange(f_min, f_max);
    }

    private void initializeBat(){
        for (int i = 0; i < xSize; i++){
            this.xPositions[i] = getRandomFromRange(lowerBoundary, upperBoundary);
            this.xBest[i] = this.xPositions[i];
            this.velocity[i] = 0.0;
        }
    }

    private double getRandomFromRange(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }

    public void updateMovement(){
        this.frequency = f_min + (f_max - f_min)*getRandomFromRange(0, 1);
        updateVelocity();
        update_xPositions();
    }

    private void updateVelocity(){
        for (int i = 0; i < xSize; i++){
            this.velocity[i] = this.velocity[i] + (this.xPositions[i] - this.xBest[i])*this.frequency;
        }
    }

    private void update_xPositions(){
        for (int i = 0; i < xSize; i++){
            this.xPositions[i] = this.xPositions[i] + this.velocity[i];
        }
    }

    public double[] correctPositions(){
        double[] newPositions = this.xPositions;
        for (int i = 0; i < xSize; i++) {
            if (newPositions[i] > upperBoundary){
                newPositions[i] = upperBoundary;
            } else if (newPositions[i] < lowerBoundary){
                newPositions[i] = lowerBoundary;
            }
        }
        return newPositions;
    }

    public void generateNewPositionsNearBest(){
        for (int i = 0; i < xSize; i++){
            this.xPositions[i] = this.xBest[i] + 0.001*rand.nextGaussian();
        }
    }

    public void set_xPositions(double[] newPositions){
        this.xPositions = newPositions;
    }

    public void setGlobalBestPositions(double[] best){
        this.xBest = best;
    }

    public void setBestGlobalMinimum(double best){
        this.bestGlobalMinimum = best;
    }

    public void setCurrentMinimum(double min){
        this.currentMinimum = min;
    }

}

package mgr.algorithm.bat.swarm;

import java.util.Random;

public class Bat {

    private int xSize;
    private double[] velocity;
    private double[] current_xPositions;
    private double[] best_xPositions;
    private double[] xBestOfAllSwarm;
    private double frequency;

    private Random rand;

    private double currentMinimum;

    private double lowerBoundary;
    private double upperBoundary;

    private static final double f_min = 0;
    private static final double f_max = 2;

    public Bat(int dimension, double lowerBound, double upperBound){
        this.xSize = dimension;
        this.current_xPositions = new double[xSize];
        this.best_xPositions = new double[xSize];
        this.xBestOfAllSwarm = new double[xSize];
        this.velocity = new double[xSize];
        this.currentMinimum = 0;
        this.lowerBoundary = lowerBound;
        this.upperBoundary = upperBound;
        this.rand = new Random();
        initFrequency();
        initializeBat();
    }

    private void initFrequency(){
        this.frequency = getRandomFromRange(f_min, f_max);
    }

    private void initializeBat(){
        for (int i = 0; i < xSize; i++){
            this.best_xPositions[i] = getRandomFromRange(lowerBoundary, upperBoundary);
            this.xBestOfAllSwarm[i] = this.best_xPositions[i];
            this.velocity[i] = 0.0;
        }
    }


    private double getRandomFromRange(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }

    public void updateMovement(){
        this.frequency = f_min + (f_min - f_max)*getRandomFromRange(0, 1);
        updateVelocity();
        update_xPositions();
        correctPositions();
    }

    private void updateVelocity(){
        for (int i = 0; i < xSize; i++){
            this.velocity[i] = this.velocity[i] + (this.best_xPositions[i] - this.xBestOfAllSwarm[i])*this.frequency;
        }
    }

    private void update_xPositions(){
        for (int i = 0; i < xSize; i++){
            this.current_xPositions[i] = this.best_xPositions[i] + this.velocity[i];
        }
    }

    private void correctPositions(){
        double[] newPositions = this.best_xPositions;
        for (int i = 0; i < xSize; i++) {
            if (newPositions[i] > upperBoundary){
                newPositions[i] = upperBoundary;
            } else if (newPositions[i] < lowerBoundary){
                newPositions[i] = lowerBoundary;
            }
        }
        System.arraycopy(newPositions, 0, best_xPositions, 0, best_xPositions.length);
    }

    public void generateNewPositionsNearBest(){
        for (int i = 0; i < xSize; i++){
            this.current_xPositions[i] = this.xBestOfAllSwarm[i] + 0.001*rand.nextGaussian();
        }
    }

    public void updatePositionsAndMinimumIfBetter(double result, double loudness){
        if (result < this.currentMinimum && this.getRandomFromRange(0, 1) < loudness){
            System.arraycopy(this.current_xPositions, 0, this.best_xPositions, 0, this.best_xPositions.length);
            this.currentMinimum = result;
        }
    }

    public void set_xPositions(double[] newPositions){
        this.current_xPositions = newPositions;
    }

    public void setGlobalBestPositions(double[] best){
        this.xBestOfAllSwarm = best;
    }

    public void setCurrentMinimum(double min){
        this.currentMinimum = min;
    }

    public double[] get_xPositions(){
        return this.current_xPositions;
    }

    public double[] get_xBestPositions(){
        return this.best_xPositions;
    }

}

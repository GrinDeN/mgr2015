package mgr.algorithm.particle.swarm;

import java.util.Random;

public class Particle {

    private int xSize;
    private double[] xPositions;
    private double[] xBest;
    private double bestErrorValue;
    private double actualErrorValue;
    private double[] velocity;
    private double lowerBoundary;
    private double upperBoundary;
    private final double W = 0.79;
    private final double FI_1 = 1.49;
    private final double FI_2 = 1.49;

    public Particle(int sizeOfX){
        bestErrorValue = 10000;
        actualErrorValue = 100000;
        xSize = sizeOfX;
        xPositions = new double[xSize];
        velocity = new double[xSize];
        xBest = new double[xSize];
        lowerBoundary = -10;
        upperBoundary = 10;
    }

    public void initializeParticle(){
        for (int i=0; i<xSize; i++){
            xPositions[i] = getRandomFromRange(lowerBoundary, upperBoundary);
            xBest[i] = xPositions[i];
        }
        for (int i=0; i<xSize; i++){
            velocity[i] = getRandomFromRange(-(Math.abs(upperBoundary)-lowerBoundary), Math.abs(upperBoundary-lowerBoundary));
        }
    }

    public void setBestValueError(double val){
        bestErrorValue = val;
    }

    public void setActualValueError(double val){
        actualErrorValue = val;
    }

    public void checkErrorValues(){
        if (actualErrorValue < bestErrorValue){
            updateBestPosition();
            setBestValueError(actualErrorValue);
        }
    }

    private double getRandomFromRange(double min, double max){
        Random rand = new Random();
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }

    public double[] get_xBest(){
        return xBest;
    }

    public void updateVelocity(double[] swarmBest){
        Random rp = new Random();
        Random rg = new Random();
        for (int d=0; d<xSize; d++){
            velocity[d] = W*velocity[d] + FI_1*rp.nextDouble()*(xBest[d]-xPositions[d]) + FI_2*rg.nextDouble()*(swarmBest[d]-xPositions[d]);
        }
    }

    public void update_xPositions(){
        for (int d=0; d<xSize; d++){
            xPositions[d] = xPositions[d] + velocity[d];
        }
    }

    public void updateBestPosition(){
        for (int d=0; d<xSize; d++){
            xBest[d] = xPositions[d];
        }
    }

    public double[] get_xPositions(){
        return xPositions;
    }



    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}


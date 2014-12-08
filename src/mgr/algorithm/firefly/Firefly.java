package mgr.algorithm.firefly;

import java.util.Random;

public class Firefly implements Comparable<Firefly> {

    private double[] positions;
    private double lightness;
    private int dimension;

    private double lowerBoundary;
    private double upperBoundary;

    private Random rand;

    public Firefly(int dim, double lowerBoundary, double upperBoundary){
        this.dimension = dim;
        this.lowerBoundary = lowerBoundary;
        this.upperBoundary = upperBoundary;
        this.positions = new double[dimension];
        this.rand = new Random();
        this.lightness = 0;
        initFirefly();
    }

    private void initFirefly(){
        for (int i = 0; i < positions.length; i++) {
            this.positions[i] = getRandomFromRange(lowerBoundary, upperBoundary);
        }
    }

    private double getRandomFromRange(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }

    public double[] getPositions(){
        return this.positions;
    }

    public double getPositionAtIndex(int index){
        return this.positions[index];
    }

    public void setNewPositionAtIndex(int index, double newPositions){
        this.positions[index] = newPositions;
    }

    public void setLightness(double newLightness){
        this.lightness = newLightness;
    }

    public double getLightness(){
        return this.lightness;
    }

    public String toString(){
        return "Lightness: " + lightness + " Positions: " + positions[0] + ", " + positions[1];
    }

    @Override
    public int compareTo(Firefly otherFirefly){
        if (this.lightness <= otherFirefly.lightness){
            return -1;
        } else if(this.lightness > otherFirefly.lightness){
            return 1;
        } else {
            return 0;
        }
    }
}

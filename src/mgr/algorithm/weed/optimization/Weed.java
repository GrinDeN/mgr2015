package mgr.algorithm.weed.optimization;

import java.util.Random;

public class Weed implements Comparable<Weed>{

    private double[] positions;
    private double fitness;

    private int dimension;
    private int numberOfSeeds;

    private Random rand;

    private double lowerBoundary;
    private double upperBoundary;

    public Weed(int dim, double lowerBound, double upperBound){
        this.dimension = dim;
        this.lowerBoundary = lowerBound;
        this.upperBoundary = upperBound;
        this.positions = new double[dimension];
        this.numberOfSeeds = 0;
        this.rand = new Random();
        initRandomPositions();
    }

    public Weed(int dim, double[] startingPositions){
        this.dimension = dim;
        this.positions = new double[dimension];
        System.arraycopy(startingPositions, 0, positions, 0, startingPositions.length);
        this.numberOfSeeds = 0;
    }

    private void initRandomPositions(){
        for (int i = 0; i < positions.length; i++) {
            this.positions[i] = getRandomFromRange(lowerBoundary, upperBoundary);
        }
    }

    private double getRandomFromRange(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }

    public void setFitness(double newFitness){
        this.fitness = newFitness;
    }

    public void updateOldPositions(double[] newPositions){
        double[] positionsToUpdate = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            positionsToUpdate[i] = this.positions[i] + newPositions[i];
//            positionsToUpdate[i] = correctPosition(positionsToUpdate[i]);
        }
        System.arraycopy(positionsToUpdate, 0, this.positions, 0, positionsToUpdate.length);
    }

    private double correctPosition(double position){
        if (position > upperBoundary){
            position = upperBoundary;
        } else if (position < lowerBoundary){
            position = lowerBoundary;
        }
        return position;
    }

    public double getFitness(){
        return this.fitness;
    }

    public double[] getPositions(){
        return this.positions;
    }

    public double getPositionAtIndex(int index){
        return this.positions[index];
    }

    public void setNumberOfSeeds(int newNumberOfSeeds){
        this.numberOfSeeds = newNumberOfSeeds;
    }

    public int getNumberOfSeeds(){
        return this.numberOfSeeds;
    }

    public void printWeed(){
        System.out.println("Position 0: " + this.positions[0] + ", Position 1: " + this.positions[1] + " Fitness: " + this.fitness);
    }

    @Override
     public int compareTo(Weed otherWeed){
        if (this.fitness < otherWeed.fitness){
            return -1;
        } else if(this.fitness > otherWeed.fitness){
            return 1;
        } else {
            return 0;
        }
    }
}

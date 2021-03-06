package mgr.algorithm.ant.colony;

import java.util.Random;

public class Ant {

    private final int dimension;
    private final Random rand;
    private double funcValue;
    private double[] positions;

    private double lower;
    private double upper;

    public Ant(int dim, double lowerBound, double upperBound){
        this.dimension = dim;
        this.lower = lowerBound;
        this.upper = upperBound;
        this.positions = new double[dimension];
        this.funcValue = Double.POSITIVE_INFINITY;
        this.rand = new Random();
        initPositionsByRandom();
    }

    private void initPositionsByRandom(){
        for (int i = 0; i < positions.length; i++){
            this.positions[i] = getRandomFrom(lower, upper);
        }
    }

    private double getRandomFrom(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }

    public void setPositions(double[] newPositions){
        System.arraycopy(newPositions, 0, this.positions, 0, newPositions.length);
    }

    public void setFuncValue(double newValue){
        this.funcValue = newValue;
    }

    public double[] getPositions(){
        return this.positions;
    }

    public double getPositionAtIndex(int index){
        return this.positions[index];
    }

    public double getFuncValue(){
        return this.funcValue;
    }
}

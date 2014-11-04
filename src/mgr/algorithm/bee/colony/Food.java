package mgr.algorithm.bee.colony;

import java.util.Random;

public class Food {

    protected double[] foodPositions;
    protected double objectiveValue;
    protected double functionValue;
    protected double fitness;
    protected double trial;
    protected double prob;
    private Random rand;
    private static final double lowerBound = -5.5;
    private static final double upperBound = 5.5;

    public Food(int dimension){
        this.foodPositions = new double[dimension];
        this.rand = new Random();
    }

    public void init(){
        initRandomlyFoodVector();
        calculateFitness();
        this.trial = 0;
    }

    public void calculateFitness(){
        if(this.functionValue >= 0){
            this.fitness = 1/(this.functionValue+1);
        } else{
            this.fitness = 1 + Math.abs(this.functionValue);
        }
    }

    private void initRandomlyFoodVector(){
        for (int i = 0; i < foodPositions.length; i++){
            foodPositions[i] = getRandomFromRange(0, 1)*(upperBound-lowerBound)+lowerBound;
        }
    }

    public double[] getFoodPositions(){
        return this.foodPositions;
    }

    public void setFunctionValue(double value){
        this.functionValue = value;
    }

    private double getRandomFromRange(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }
}
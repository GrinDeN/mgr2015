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
    private static final double lowerBound = -5.0;
    private static final double upperBound = 5.0;

    public Food(int dimension){
        this.foodPositions = new double[dimension];
        this.rand = new Random();
    }

    public void init(double funcValue){
        setFunctionValue(funcValue);
        calculateFitness();
        this.trial = 0;
    }

    private void calculateFitness(){
        if(this.functionValue >= 0){
            this.fitness = 1/(this.functionValue+1);
        } else{
            this.fitness = 1 + Math.abs(this.functionValue);
        }
    }

    public void initRandomlyFoodVector(){
        for (int i = 0; i < foodPositions.length; i++){
            foodPositions[i] = getRandomFromRange(0, 1)*(upperBound-lowerBound)+lowerBound;
        }
    }

    public double[] getFoodPositions(){
        return this.foodPositions;
    }

    public double getFoodPositionAtIndex(int index){
        return this.foodPositions[index];
    }

    public void setFunctionValue(double value){
        this.functionValue = value;
    }

    public double getFunctionValue(){
        return this.functionValue;
    }

    private double getRandomFromRange(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }

    public double getFitness(){
        return this.fitness;
    }

    public void setFitness(double fitness){
        this.fitness = fitness;
    }

    public void setTrialToZero(){
        this.trial = 0;
    }

    public void setFoodPositions(double[] solutionToChange){
        System.arraycopy(solutionToChange, 0, this.foodPositions, 0, solutionToChange.length);
    }

    public void incrementTrial(){
        this.trial = this.trial+1;
    }

    public double getTrial(){
        return this.trial;
    }

    public void setProbValue(double prob){
        this.prob = prob;
    }

    public double getProbValue(){
        return this.prob;
    }
}

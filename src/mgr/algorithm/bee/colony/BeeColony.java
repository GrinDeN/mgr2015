package mgr.algorithm.bee.colony;

import java.util.Random;

public class BeeColony {

    private Food[] foodFarm;
    private double[] best_xPositions;
    private int dim;
    private double objValueSolution;
    private double fitnessSolution;
    private double globalMinimum;
    private int numOfFood;
    private Random rand;
    private int param2change;
    private int neighbour;
    private double[] solutionToChange;
    private double globalFitnessValue;

    protected static final double lowerBoundary = -5.5;
    protected static final double upperBoundary = 5.5;

    public BeeColony(int foodNumber, int dimension){
        this.numOfFood = foodNumber;
        this.dim = dimension;
        this.foodFarm = new Food[this.numOfFood];
        this.best_xPositions = new double[dim];
        this.globalMinimum = 1000000;
        this.rand = new Random();
        this.solutionToChange = new double[dim];
        initFood();
    }

    private void initFood(){
        for (int i = 0; i < numOfFood; i++) {
            this.foodFarm[i] = new Food(dim);
        }
    }

    public Food getFoodAtIndex(int index){
        return this.foodFarm[index];
    }

    public int getNumOfFood(){
        return this.numOfFood;
    }

    public void setBestxPositions(double[] eachFoodPositions){
        System.arraycopy(eachFoodPositions, 0, this.best_xPositions, 0, eachFoodPositions.length);
    }

    public void setGlobalMinimum(double result){
        this.globalMinimum = result;
    }

    public void updateSolutionIfBetter(double value, double[] positions){
        if (value < this.globalMinimum){
            setGlobalMinimum(value);
            System.arraycopy(positions, 0, this.best_xPositions, 0, positions.length);
        }
    }

    public double getBestPosAtIndex(int index){
        return this.best_xPositions[index];
    }

    public double getGlobalMinimum(){
        return this.globalMinimum;
    }

    public void sendEmployedBees(){
        for (int i = 0; i < getNumOfFood(); i++){
            this.param2change = (int)(rand.nextDouble()*dim);
            System.out.println("param2change: "+param2change);
            this.neighbour = (int)(rand.nextDouble()*getNumOfFood());
//            this.solutionToChange = getFoodAtIndex(i).getFoodPositions();
            System.arraycopy(getFoodAtIndex(i).getFoodPositions(), 0, this.solutionToChange, 0, this.solutionToChange.length);
            this.solutionToChange[param2change] = getFoodAtIndex(i).getFoodPositionAtIndex(param2change)-
                    getFoodAtIndex(neighbour).getFoodPositionAtIndex(param2change)*(rand.nextDouble()-0.5)*2;
            correctSolutionValueAtIndexToBoundaries(param2change);
        }
    }

    private void correctSolutionValueAtIndexToBoundaries(int index){
        if (this.solutionToChange[index] > upperBoundary){
            this.solutionToChange[index] = upperBoundary;
        } else if (this.solutionToChange[index] < lowerBoundary){
            this.solutionToChange[index] = lowerBoundary;
        }
    }

    public double[] getChangedSolution(){
        return this.solutionToChange;
    }

    public void calculateFitness(double result){
        if(result >= 0){
            this.globalFitnessValue = 1/(result+1);
        } else{
            this.globalFitnessValue = 1 + Math.abs(result);
        }
    }

    public void checkFitnessAndUpdate(double resultOfChangedSolution, int indexOfFood){
        if (this.globalFitnessValue > getFoodAtIndex(indexOfFood).getFitness()){
            getFoodAtIndex(indexOfFood).setTrialToZero();
            getFoodAtIndex(indexOfFood).setFoodPositions(this.solutionToChange);
            getFoodAtIndex(indexOfFood).setFunctionValue(resultOfChangedSolution);
            getFoodAtIndex(indexOfFood).setFitness(this.globalFitnessValue);
        } else{
            getFoodAtIndex(indexOfFood).incrementTrial();
        }
    }

    public void calculateProbabilities(){
        double maxFit = getFoodAtIndex(0).getFitness();
        double prob = 0;
        for (int i = 1; i < getNumOfFood(); i++){
            double fitnessOfCurrentFood = getFoodAtIndex(i).getFitness();
            if (fitnessOfCurrentFood > maxFit){
                maxFit = fitnessOfCurrentFood;
            }
        }
        for (int j = 0; j < getNumOfFood(); j++){
            prob = (0.9*(getFoodAtIndex(j).getFitness()/maxFit))+0.1;
            getFoodAtIndex(j).setProbValue(prob);
        }
    }
}

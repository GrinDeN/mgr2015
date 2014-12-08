package mgr.algorithm.bee.colony;

import mgr.test.functions.AlgsEnum;
import mgr.test.functions.TestFuncFactory;
import mgr.test.functions.TestFunction;

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

    private TestFunction testFunction;

    protected double lowerBoundary;
    protected double upperBoundary;
    private int limit;

    public BeeColony(AlgsEnum alg, int foodNumber, int dimension){
        this.testFunction = TestFuncFactory.getTestFunction(alg);
        this.numOfFood = foodNumber;
        this.dim = dimension;
        initBoundariesFromTestFunc();
        this.foodFarm = new Food[this.numOfFood];
        this.best_xPositions = new double[dim];
        this.globalMinimum = 1000000;
        this.rand = new Random();
        this.solutionToChange = new double[dim];
        this.limit = 100;
        initFood();
    }

    private void initBoundariesFromTestFunc(){
        this.lowerBoundary = testFunction.getLowerBoundary();
        this.upperBoundary = testFunction.getUpperBoundary();
    }

    private void initFood(){
        for (int i = 0; i < numOfFood; i++) {
            this.foodFarm[i] = new Food(dim, testFunction.getLowerBoundary(), testFunction.getUpperBoundary());
        }
    }

    public void getMinimum(){
        double result;
        for (int i = 0; i < getNumOfFood(); i++) {
            getFoodAtIndex(i).initRandomlyFoodVector();
            double[] eachFoodPositions = getFoodAtIndex(i).getFoodPositions();
            result = testFunction.getResult(eachFoodPositions);
            getFoodAtIndex(i).init(result);
            if (i==0){
                setBestxPositions(eachFoodPositions);
                setGlobalMinimum(result);
            } else{
                updateSolutionIfBetter(result, eachFoodPositions);
            }
        }
        System.out.println("Najlepsze wskazane wspolrzedne w fazie wstepnej, x: " + getBestPosAtIndex(0) + " y: " + getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie wstepnej: " + getGlobalMinimum());
        //koniec fazy wstepnej

        double resultOfChangedSolution;
        for (int iter = 0; iter < 5000; iter++){
            if (testFunction.isSolutionEnoughNearMinimum(getGlobalMinimum())) {
                System.out.println("Algorytm wykonał " + iter + " iteracji.");
                break;
            }
            for (int i = 0; i < getNumOfFood(); i++){
                sendEmployedBees();
                double[] changedSolution = getChangedSolution();
//                resultOfChangedSolution = EasomFunc.function(changedSolution[0], changedSolution[1]);
                resultOfChangedSolution = testFunction.getResult(changedSolution);
                calculateFitness(resultOfChangedSolution);
                checkFitnessAndUpdate(resultOfChangedSolution, i);
                calculateProbabilities();
                sendOnLookerBees();
                memorizeBestPositions();
                sendScoutBees();
            }
        }
    }

    private Food getFoodAtIndex(int index){
        return this.foodFarm[index];
    }

    private int getNumOfFood(){
        return this.numOfFood;
    }

    private void setBestxPositions(double[] eachFoodPositions){
        System.arraycopy(eachFoodPositions, 0, this.best_xPositions, 0, eachFoodPositions.length);
    }

    private void setGlobalMinimum(double result){
        this.globalMinimum = result;
    }

    private void updateSolutionIfBetter(double value, double[] positions){
        if (value < this.globalMinimum){
            setGlobalMinimum(value);
            System.arraycopy(positions, 0, this.best_xPositions, 0, positions.length);
        }
    }

    private double getBestPosAtIndex(int index){
        return this.best_xPositions[index];
    }

    private double getGlobalMinimum(){
        return this.globalMinimum;
    }

    private void sendEmployedBees(){
        for (int i = 0; i < getNumOfFood(); i++){
            this.param2change = (int)(rand.nextDouble()*dim);
//            System.out.println("param2change: "+param2change);
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

    private double[] getChangedSolution(){
        return this.solutionToChange;
    }

    private void calculateFitness(double result){
        if(result >= 0){
            this.globalFitnessValue = 1/(result+1);
        } else{
            this.globalFitnessValue = 1 + Math.abs(result);
        }
    }

    private void checkFitnessAndUpdate(double resultOfChangedSolution, int indexOfFood){
        if (this.globalFitnessValue > getFoodAtIndex(indexOfFood).getFitness()){
            getFoodAtIndex(indexOfFood).setTrialToZero();
            getFoodAtIndex(indexOfFood).setFoodPositions(this.solutionToChange);
            getFoodAtIndex(indexOfFood).setFunctionValue(resultOfChangedSolution);
            getFoodAtIndex(indexOfFood).setFitness(this.globalFitnessValue);
        } else{
            getFoodAtIndex(indexOfFood).incrementTrial();
        }
    }

    private void calculateProbabilities(){
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

    private void sendOnLookerBees(){
        int t = 0;
        int i = 0;
        while (t < getNumOfFood()){
            if(rand.nextDouble() < getFoodAtIndex(i).getProbValue()){
                t++;
                this.param2change = (int)(rand.nextDouble()*dim);
                this.neighbour = (int)(rand.nextDouble()*getNumOfFood());
                while (neighbour == i){
                    this.neighbour = (int)(rand.nextDouble()*getNumOfFood());
                }
                System.arraycopy(getFoodAtIndex(i).getFoodPositions(), 0, this.solutionToChange, 0, this.solutionToChange.length);
                this.solutionToChange[param2change] = getFoodAtIndex(i).getFoodPositionAtIndex(param2change)-
                        getFoodAtIndex(neighbour).getFoodPositionAtIndex(param2change)*(rand.nextDouble()-0.5)*2;
                correctSolutionValueAtIndexToBoundaries(param2change);
//                this.objValueSolution = TestFuncFactory.getResultOfAlgorithm(enumArg, this.solutionToChange[0], this.solutionToChange[1]);
                this.objValueSolution = testFunction.getResult(solutionToChange);
                calculateFitnessSolution();
                checkFitnessAndUpdate(this.objValueSolution, i);
            }
            i++;
            if (i == getNumOfFood()){
                i=0;
            }
        }
    }

    private void sendScoutBees(){
        int maxTrialIndex = 0;
        double result;
        for (int i = 1; i < getNumOfFood(); i++) {
            if (getFoodAtIndex(i).getTrial() > getFoodAtIndex(maxTrialIndex).getTrial()){
                maxTrialIndex = i;
            }
        }
        if (getFoodAtIndex(maxTrialIndex).getTrial() >= this.limit){
            getFoodAtIndex(maxTrialIndex).initRandomlyFoodVector();
            double[] eachFoodPositions = getFoodAtIndex(maxTrialIndex).getFoodPositions();
//            result = EasomFunc.function(eachFoodPositions[0], eachFoodPositions[1]);
            result = testFunction.getResult(eachFoodPositions);
            getFoodAtIndex(maxTrialIndex).init(result);
        }
    }

    private void calculateFitnessSolution(){
        if(this.objValueSolution >= 0){
            this.fitnessSolution = 1/(this.objValueSolution+1);
        } else{
            this.fitnessSolution = 1 + Math.abs(this.objValueSolution);
        }
    }

    private void memorizeBestPositions(){
        double eachFoodResult;
        double[] eachFoodPositions;
        for (int i = 0; i < getNumOfFood(); i++){
            eachFoodResult = getFoodAtIndex(i).getFunctionValue();
            eachFoodPositions = getFoodAtIndex(i).getFoodPositions();
            updateSolutionIfBetter(eachFoodResult, eachFoodPositions);
        }
    }

    public void printResult(){
        System.out.println("Najlepsze wskazane wspolrzedne w fazie wstepnej, x: " + getBestPosAtIndex(0) + " y: " + getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie wstepnej: " + getGlobalMinimum());
    }
}

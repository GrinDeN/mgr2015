package mgr.algorithm.bee.colony;

import mgr.algorithm.SwarmAlgorithm;
import mgr.config.Config;
import mgr.teacher.NetworkTeacher;

import java.util.Random;

public class BeeColony implements SwarmAlgorithm{

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

    private NetworkTeacher netTeacher;

    protected double lowerBoundary;
    protected double upperBoundary;
    private int limit;

    private int iterations;

    public BeeColony(NetworkTeacher teacher, int iters, int foodNumber){
        this.netTeacher = teacher;
        this.iterations = iters;
        this.numOfFood = foodNumber;
        this.dim = Config.NUM_OF_WEIGHTS;
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
        this.lowerBoundary = Config.LOWER_BOUNDARY;
        this.upperBoundary = Config.UPPER_BOUNDARY;
    }

    private void initFood(){
        for (int i = 0; i < numOfFood; i++) {
            this.foodFarm[i] = new Food(dim, this.lowerBoundary, this.upperBoundary);
        }
    }

    public int getMinimum() throws Exception{
        double result;
        for (int i = 0; i < getNumOfFood(); i++) {
            getFoodAtIndex(i).initRandomlyFoodVector();
            double[] eachFoodPositions = getFoodAtIndex(i).getFoodPositions();
//            result = testFunction.getResult(eachFoodPositions);
            result = netTeacher.getErrorOfNetwork(eachFoodPositions);
            getFoodAtIndex(i).init(result);
            if (i==0){
                setBestxPositions(eachFoodPositions);
                setGlobalMinimum(result);
            } else{
                updateSolutionIfBetter(result, eachFoodPositions);
            }
        }
//        System.out.println("Najlepsze wskazane wspolrzedne w fazie wstepnej, x: " + getBestPosAtIndex(0) + " y: " + getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie wstepnej: " + getGlobalMinimum());
        //koniec fazy wstepnej

        double resultOfChangedSolution;
        for (int iter = 0; iter < iterations; iter++){
            for (int i = 0; i < getNumOfFood(); i++){
                sendEmployedBees();
                double[] changedSolution = getChangedSolution();
//                resultOfChangedSolution = EasomFunc.function(changedSolution[0], changedSolution[1]);
//                resultOfChangedSolution = testFunction.getResult(changedSolution);
                resultOfChangedSolution = netTeacher.getErrorOfNetwork(changedSolution);
                calculateFitness(resultOfChangedSolution);
                checkFitnessAndUpdate(resultOfChangedSolution, i);
                calculateProbabilities();
                sendOnLookerBees();
                memorizeBestPositions();
                sendScoutBees();
            }
        }
        netTeacher.setWeightsToNetwork(getBestPositions());
        printBestPositions();
        printResult();
        return 0;
    }

    @Override
    public String getName(){
        return "Bee Colony";
    }

    @Override
    public double[] getBestPositions(){
        return this.best_xPositions;
    }

    private void printBestPositions(){
        double[] bestPos = getBestPositions();
        for (int i = 0; i < bestPos.length; i++) {
            System.out.println(bestPos[i]);
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

    private void sendOnLookerBees() throws Exception{
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
//                this.objValueSolution = testFunction.getResult(solutionToChange);
                this.objValueSolution = netTeacher.getErrorOfNetwork(solutionToChange);
                calculateFitnessSolution();
                checkFitnessAndUpdate(this.objValueSolution, i);
            }
            i++;
            if (i == getNumOfFood()){
                i=0;
            }
        }
    }

    private void sendScoutBees() throws Exception{
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
//            result = testFunction.getResult(eachFoodPositions);
            result = netTeacher.getErrorOfNetwork(eachFoodPositions);
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
//        System.out.println("Najlepsze wskazane wspolrzedne w fazie wstepnej, x: " + getBestPosAtIndex(0) + " y: " + getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie wstepnej: " + getGlobalMinimum());
    }
}

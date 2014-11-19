package mgr.algorithm.ant.colony;

import mgr.test.functions.AlgorithmFactory;
import mgr.test.functions.AlgsEnum;

import java.util.Random;

public class AntColony {

    private static final int DIMENSION = 2;
    private static final int NUM_OF_ANTS = 30;

    private static final double lowerBoundary = -5.0;
    private static final double upperBoundary = 5.0;

    private double fitness;
    private double previousFitness;
    private double[] globalPositions;
    private double[] minusGlobalPositions;
    private double[] positionsToUpdate;
    private Ant[] ants;

    private double alphaParam;
    private double[] dx;
    private double pheronome;

    private int maxIterations;
    private int sqrtMaxIterations;
    private Random rand;
    private AlgsEnum algorithm;

    private Direction direction;

    public AntColony(AlgsEnum alg, int maxIter){
        this.algorithm = alg;
        this.maxIterations = maxIter;
        this.sqrtMaxIterations = (int) Math.sqrt(maxIterations);
        this.globalPositions = new double[DIMENSION];
        this.minusGlobalPositions = new double[DIMENSION];
        this.positionsToUpdate = new double[DIMENSION];
        this.ants = new Ant[NUM_OF_ANTS];
        this.dx = new double[DIMENSION];
        this.fitness = Double.POSITIVE_INFINITY;
        this.previousFitness = Double.POSITIVE_INFINITY;
        this.rand = new Random();
        this.alphaParam = rand.nextDouble();
        this.pheronome = 0.5;
        initAnts();
    }

    private void initAnts(){
        for (int i = 0; i < this.ants.length; i++){
            this.ants[i] = new Ant(DIMENSION);
        }
    }

    public void setDirection(){
        setMinusGlobalPositions();
        double fitnessByMinus = AlgorithmFactory.getResultOfAlgorithm(algorithm, this.minusGlobalPositions[0], this.globalPositions[1]);
        double fitnessByBest = AlgorithmFactory.getResultOfAlgorithm(algorithm, this.globalPositions[0], this.globalPositions[1]);
        this.direction = (fitnessByMinus <= fitnessByBest) ? Direction.PLUS : Direction.MINUS;
    }

    private void setMinusGlobalPositions(){
        for (int i = 0; i < minusGlobalPositions.length; i++){
            this.minusGlobalPositions[i] = this.globalPositions[i]+(this.globalPositions[i]*0.01);
        }
    }
    
    public void updateAllAntsPositions(){
        for (int i = 0; i < ants.length; i++){
            setNewPositions();
            this.ants[i].setPositions(positionsToUpdate);
        }
    }

    public void calculateAllAntsFuncValue(){
        for (int i = 0; i < this.ants.length; i++){
            double[] antPositions = this.ants[i].getPositions();
            double antFuncValue = AlgorithmFactory.getResultOfAlgorithm(algorithm, antPositions[0], antPositions[1]);
            this.ants[i].setFuncValue(antFuncValue); // ????
        }
    }

    public void memorizeBestFuncValue(){
        double antFuncValue;
        for (int i = 0; i < this.ants.length; i++){
            antFuncValue = this.ants[i].getFuncValue();
            if (antFuncValue < this.fitness){
                this.previousFitness = this.fitness;
                this.fitness = antFuncValue;
                System.arraycopy(this.ants[i].getPositions(), 0, this.globalPositions, 0, this.globalPositions.length);
            }
        }
    }

    private void setNewPositions(){
        generateNewdxVector();
        for (int i = 0; i < this.positionsToUpdate.length; i++){
            if (direction.equals(Direction.PLUS))
                this.positionsToUpdate[i] = this.globalPositions[i]+this.dx[i];
            else if (direction.equals(Direction.MINUS))
                this.positionsToUpdate[i] = this.globalPositions[i]-this.dx[i];
        }
    }

    private void generateNewdxVector(){
        for (int i = 0; i < dx.length; i++){
            this.dx[i] = getRandomFromRange(-alphaParam, alphaParam);
        }
    }

    private double getRandomFromRange(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }

    public void setPheronomeAtFirstIterations(){
        this.pheronome = 0.1*this.pheronome;
    }

    public void setPheronomeUpdate(){
        this.pheronome = this.pheronome+(0.01*this.previousFitness);
    }

//    public void setFitness(double newFitness){
//        this.fitness = newFitness;
//    }

    public void updateAlphaParam(){
        this.alphaParam = 0.1*this.alphaParam;
    }

    public void getMinimum(){
        calculateAllAntsFuncValue();
        memorizeBestFuncValue();
        setDirection();
        System.out.println("Najlepsze wskazane wspolrzedne w fazie wstepnej, x: " + this.globalPositions[0] + " y: " + this.globalPositions[1]);
        System.out.println("Najlepszy wskazany rezultat w fazie wstepnej: " + this.fitness);
        int n = 10;
        for (int k = 0; k < n; k++) {
            for (int i = 0; i < k*sqrtMaxIterations; i++){
                updateAllAntsPositions();
                calculateAllAntsFuncValue();
                memorizeBestFuncValue();
                setPheronomeAtFirstIterations();
                setPheronomeUpdate();
            }
            updateAlphaParam();
        }
    }

    public double getFitness(){
        return this.fitness;
    }

    public double getGlobalPositionsAtIndex(int index){
        return this.globalPositions[index];
    }
}

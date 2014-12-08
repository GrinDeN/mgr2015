package mgr.algorithm.grey.wolf;

import mgr.test.functions.AlgsEnum;
import mgr.test.functions.TestFuncFactory;
import mgr.test.functions.TestFunction;

import java.util.Random;

public class WolfsPack {

    private static final int DIMENSION = 2;
    private static final int NUM_OF_AGENTS = 30;

    private double lowerBoundary;
    private double upperBoundary;

    private double[][] globalPositions;
    double fitness;
    private Random rand;
    private int max_iter;

    private Wolf alpha;
    private Wolf beta;
    private Wolf delta;

    private TestFunction testFunction;

    public WolfsPack(AlgsEnum alg, int max_iter){
        this.testFunction = TestFuncFactory.getTestFunction(alg);
        this.alpha = new Wolf(DIMENSION);
        this.beta = new Wolf(DIMENSION);
        this.delta = new Wolf(DIMENSION);
        this.globalPositions = new double[NUM_OF_AGENTS][DIMENSION];
        this.rand = new Random();
        this.max_iter = max_iter;
        initBoundariesFromTestFunc();
        initializePositions();
    }

    private void initBoundariesFromTestFunc(){
        this.lowerBoundary = testFunction.getLowerBoundary();
        this.upperBoundary = testFunction.getUpperBoundary();
    }

    private void initializePositions(){
        for (int i = 0; i < NUM_OF_AGENTS; i++) {
            for (int j = 0; j < DIMENSION; j++) {
                this.globalPositions[i][j] = getRandomFrom(0, 1)*(upperBoundary-lowerBoundary)*lowerBoundary;
            }
        }
    }

    private double getRandomFrom(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }

    public void getMinimum(){
        int iter = 0;
        while (iter < max_iter){
            if (testFunction.isSolutionEnoughNearMinimum(getAlphaScore())) {
                System.out.println("Algorytm wykonał " + iter + " iteracji.");
                break;
            }
            for (int i = 0; i < NUM_OF_AGENTS; i++){
                correctPositionsAtIndex(i);
//                fitness = TestFuncFactory.getResultOfAlgorithm(algorithm, globalPositions[i][0], globalPositions[i][1]);
                fitness = testFunction.getResult(globalPositions[i]);
                if (fitness < alpha.getScore()){
                    alpha.setScore(fitness);
                    alpha.setPositions(getGlobalPositionsAtIndex(i));
                }
                if (fitness > alpha.getScore() && fitness < beta.getScore()){
                    beta.setScore(fitness);
                    beta.setPositions(getGlobalPositionsAtIndex(i));
                }
                if (fitness > alpha.getScore() && fitness > beta.getScore() && fitness < delta.getScore()){
                    beta.setScore(fitness);
                    beta.setPositions(getGlobalPositionsAtIndex(i));
                }
            }
            double a = 2-   iter*(2/max_iter);
            for (int i = 0; i < NUM_OF_AGENTS; i++){
                for (int j = 0; j < DIMENSION; j++){
//                    r1 = rand.nextDouble();
//                    r2 = rand.nextDouble();
//                    double A1 = 2*a*r1-a;
//                    double C1 = 2*r2;
//                    double D_alpha = Math.abs(C1*alpha.getPositionAtIndex(j)-globalPositions[i][j]);
//                    double X1 = alpha.getPositionAtIndex(j)-A1*D_alpha;
//
//                    r1 = rand.nextDouble();
//                    r2 = rand.nextDouble();
//                    double A2 = 2*a*r1-a;
//                    double C2 = 2*r2;
//                    double D_beta = Math.abs(C2*beta.getPositionAtIndex(j)-globalPositions[i][j]);
//                    double X2 = alpha.getPositionAtIndex(j)-A2*D_beta;

//                    r1 = rand.nextDouble();
//                    r2 = rand.nextDouble();
//                    double A3 = 2*a*r1-a;
//                    double C3 = 2*r2;
//                    double D_delta = Math.abs(C3*delta.getPositionAtIndex(j)-globalPositions[i][j]);
//                    double X3 = delta.getPositionAtIndex(j)-A3*D_delta;

                    double X1 = calculateXParameter(alpha, a, i, j);
                    double X2 = calculateXParameter(beta, a, i, j);
                    double X3 = calculateXParameter(delta, a, i, j);

                    globalPositions[i][j] = (X1+X2+X3)/3;
                }
            }
            iter++;
        }
    }

    private double[] getGlobalPositionsAtIndex(int index){
        return globalPositions[index];
    }

    private void correctPositionsAtIndex(int index){
        double[] newPositions = new double[DIMENSION];
        System.arraycopy(globalPositions[index], 0, newPositions, 0, globalPositions[index].length);
        for (int i = 0; i < globalPositions[index].length; i++) {
            if (newPositions[i] > upperBoundary){
                newPositions[i] = upperBoundary;
            } else if (newPositions[i] < lowerBoundary){
                newPositions[i] = lowerBoundary;
            }
        }
        System.arraycopy(newPositions, 0, globalPositions[index], 0, globalPositions[index].length);
    }

    private double calculateXParameter(Wolf currentWolf, double a, int i, int j){
        double xParameter;
        double r1 = rand.nextDouble();
        double r2 = rand.nextDouble();
        double A = 2*a*r1-a;
        double C = 2*r2;
        double D = Math.abs(C*currentWolf.getPositionAtIndex(j)-globalPositions[i][j]);
        xParameter = currentWolf.getPositionAtIndex(j)-A*D;
        return xParameter;
    }

    private double getAlphaScore(){
        return this.alpha.getScore();
    }

    private double getAlphaPositionAtIndex(int index){
        return this.alpha.getPositionAtIndex(index);
    }

    public void printResults(){
        System.out.println("Najlepsze wskazane wspolrzedne, x: " + getAlphaPositionAtIndex(0) + " y: " + getAlphaPositionAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat: " + getAlphaScore());
    }

}

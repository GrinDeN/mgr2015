package mgr.algorithm.grey.wolf;

import mgr.algorithm.SwarmAlgorithm;
import mgr.config.Config;
import mgr.teacher.NetworkTeacher;

import java.util.Random;

public class WolfsPack implements SwarmAlgorithm{

    private static final int NUM_OF_AGENTS = 30;

    private double lowerBoundary;
    private double upperBoundary;

    private double[][] globalPositions;
    double fitness;
    private Random rand;
    private int max_iter;

    private wolf alpha;
    private wolf beta;
    private wolf delta;

    private NetworkTeacher networkTeacher;

    public WolfsPack(NetworkTeacher netTeacher, int max_iter){
        this.networkTeacher = netTeacher;
        this.alpha = new wolf(Config.NUM_OF_WEIGHTS);
        this.beta = new wolf(Config.NUM_OF_WEIGHTS);
        this.delta = new wolf(Config.NUM_OF_WEIGHTS);
        this.globalPositions = new double[NUM_OF_AGENTS][Config.NUM_OF_WEIGHTS];
        this.rand = new Random();
        this.max_iter = max_iter;
        initBoundariesFromTestFunc();
        initializePositions();
    }

    private void initBoundariesFromTestFunc(){
        this.lowerBoundary = Config.LOWER_BOUNDARY;
        this.upperBoundary = Config.UPPER_BOUNDARY;
    }

    private void initializePositions(){
        for (int i = 0; i < NUM_OF_AGENTS; i++) {
            for (int j = 0; j < Config.NUM_OF_WEIGHTS; j++) {
                this.globalPositions[i][j] = getRandomFrom(0, 1)*(upperBoundary-lowerBoundary)*lowerBoundary;
            }
        }
    }

    private double getRandomFrom(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }

    @Override
    public int getMinimum() throws Exception{
        int iter = 0;
        while (iter < max_iter){
            for (int i = 0; i < NUM_OF_AGENTS; i++){
                correctPositionsAtIndex(i);
                fitness = networkTeacher.getErrorOfNetwork(globalPositions[i], false);
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
            double a = 2-iter*(2/max_iter);
            for (int i = 0; i < NUM_OF_AGENTS; i++){
                for (int j = 0; j < Config.NUM_OF_WEIGHTS; j++){
                    double X1 = calculateXParameter(alpha, a, i, j);
                    double X2 = calculateXParameter(beta, a, i, j);
                    double X3 = calculateXParameter(delta, a, i, j);
                    globalPositions[i][j] = (X1+X2+X3)/3;
                }
            }
            iter++;
        }
        networkTeacher.setWeightsToNetwork(getBestPositions());
        System.out.println("Najlepszy wynik bledu po koncowej fazie: " + getAlphaScore());
        printAlphaPositions();
        return 0;
    }

    private void printAlphaPositions(){
        double[] alphaPositions = getBestPositions();
        int rowCounter = 1;
        for (int i = 0; i < alphaPositions.length; i++) {
            if (i%Config.INPUT_SIZE <= Config.INPUT_SIZE && rowCounter <= Config.HIDD_NEURONS) {
                System.out.print(alphaPositions[i] + " ");
                if (i%Config.INPUT_SIZE == 4 ){
                    System.out.println();
                    rowCounter++;
                }
            } else {
                System.out.print(alphaPositions[i] + " ");
            }
        }
        System.out.println();
    }

    @Override
    public String getName(){
        return "Grey Wolf Optimizer (GWO)";
    }

    private double[] getGlobalPositionsAtIndex(int index){
        return globalPositions[index];
    }

    private void correctPositionsAtIndex(int index){
        double[] newPositions = new double[Config.NUM_OF_WEIGHTS];
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

    private double calculateXParameter(wolf currentWolf, double a, int i, int j){
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

    public double[] getBestPositions(){
        return this.alpha.getPositions();
    }

    public void printResults(){
        System.out.println("Najlepsze wskazane wspolrzedne, x: " + getAlphaPositionAtIndex(0) + " y: " + getAlphaPositionAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat: " + getAlphaScore());
    }

}

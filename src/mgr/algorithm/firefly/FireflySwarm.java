package mgr.algorithm.firefly;

import mgr.test.functions.AlgorithmFactory;
import mgr.test.functions.AlgsEnum;

import java.util.*;

public class FireflySwarm {

    private Firefly[] fireflies;
    private Firefly[] firefliesCopy;
    private List<Firefly> firefliesList;
    private List<Firefly> firefliesListCopy;
    private double bestLightness;
//    private double[] lightnessArray;
//    private double[] lightnessArrayCopy;
    private double[] bestPositions;
//    private double[] bestPositionsCopy;

    private static final double lowerBoundary = -2.0;
    private static final double upperBoundary = 2.0;

    private double scale;

    private AlgsEnum algorithm;

    private final static int NUM_OF_FIREFLIES = 20;
    private final static int MAX_GENERATIONS = 400;

    private final static int DIMENSION = 2;

    private double alpha = 0.35;
    private final static double BETA_MIN = 0.2;
    private final static double BETA0 = 1.0;
    private final static double GAMMA = 1.0;
    private final static double DELTA = 0.97;

    private Random rand;

    public FireflySwarm(AlgsEnum alg){
        this.algorithm = alg;
        this.fireflies = new Firefly[NUM_OF_FIREFLIES];
        this.firefliesCopy = new Firefly[NUM_OF_FIREFLIES];
        this.firefliesList = new ArrayList<Firefly>();
        this.firefliesListCopy = new ArrayList<Firefly>();
        this.bestPositions = new double[DIMENSION];
        this.bestLightness = Double.POSITIVE_INFINITY;
        this.scale = Math.abs(upperBoundary-lowerBoundary);
        this.rand = new Random();
        initFireFlies();
    }

    private void initFireFlies(){
        for (int i = 0; i < fireflies.length; i++) {
            this.fireflies[i] = new Firefly(DIMENSION);
        }
    }

    public void getMinimum(){
        for (int k = 0; k < MAX_GENERATIONS; k++){
            setNewAlpha();
            getNewSolutions();
            sortFirefliesInList();
            saveCurrentBest();
            setCopyOfList();
            moveFireflies();
        }
    }

    private void setNewAlpha(){
        double delta = 1-Math.pow((Math.pow(10, -4) / 0.9), 1 / MAX_GENERATIONS);
        this.alpha = (1-delta)*this.alpha;
//        alpha = alpha*DELTA;
    }

    private void getNewSolutions(){
        double[] fireflyPositions;
        double newLightness;
        for (int i = 0; i < fireflies.length; i++) {
            fireflyPositions = this.fireflies[i].getPositions();
            newLightness = AlgorithmFactory.getResultOfAlgorithm(algorithm, fireflyPositions[0], fireflyPositions[1]);
            this.fireflies[i].setLightness(newLightness);
        }
    }

    private void sortFirefliesInList(){
//        if (firefliesList.size() != 0){
//            this.firefliesList.clear();
//        }
        this.firefliesList = Arrays.asList(fireflies);
        Collections.sort(firefliesList);
        this.fireflies = this.firefliesList.toArray(fireflies);
    }

    private void saveCurrentBest(){
        this.bestLightness = fireflies[0].getLightness();
        System.arraycopy(fireflies[0].getPositions(), 0, bestPositions, 0, bestPositions.length);
    }

    private void setCopyOfList(){
        this.firefliesListCopy.clear();
        this.firefliesListCopy.addAll(firefliesList);
        this.firefliesListCopy.toArray(firefliesCopy);
    }

    private void moveFireflies(){
        double radius;
        double beta;
        double tmpf;
        for (int i = 0; i < fireflies.length; i++) {
            for (int j = 0; j < firefliesCopy.length; j++) {
                radius = getRadius(i, j);
                if (fireflies[i].getLightness() > firefliesCopy[j].getLightness()){
//                    beta = (BETA0-BETA_MIN)*Math.exp(-GAMMA*Math.pow(radius, 2))+BETA_MIN;
                    beta = BETA0*Math.exp(-GAMMA*Math.pow(radius, 2));
//                    tmpf = alpha*(getRandomFromRange(1, DIMENSION)-0.5)*scale;
//                    updatePositionAtIndex(i, j , beta, tmpf);
                    updatePositionAtIndex(i, j , beta);
                }
            }
        }
        setBoundariesToPositions();
    }

    private double getRadius(int i, int j){
        double sumOfDistance = 0;
        for (int k = 0; k < DIMENSION; k++) {
            sumOfDistance += Math.pow(fireflies[i].getPositionAtIndex(k)-firefliesCopy[j].getPositionAtIndex(k), 2);
        }
        return Math.sqrt(sumOfDistance);
    }

    private double getRandomFromRange(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }


    private void updatePositionAtIndex(int index, int j, double beta){
        double newPosition;
        for (int k = 0; k < DIMENSION; k++) {
//            newPosition = fireflies[index].getPositionAtIndex(k)*(1-beta)+firefliesCopy[j].getPositionAtIndex(k)*beta+tmpf;
            newPosition = fireflies[index].getPositionAtIndex(k)*(1-beta)+firefliesCopy[j].getPositionAtIndex(k)*beta+
                    alpha*getRandomFromRange(0, 1)-0.5;
            fireflies[index].setNewPositionAtIndex(k, newPosition);
        }
    }

    public double getBestLightness(){
        return this.bestLightness;
    }

    public double getBestPositionAtIndex(int index){
        return this.bestPositions[index];
    }

    private void setBoundariesToPositions(){
        for (int i = 0; i < fireflies.length; i++) {
            for (int k = 0; k < DIMENSION; k++){
                if(fireflies[i].getPositionAtIndex(k) > upperBoundary){
                    fireflies[i].setNewPositionAtIndex(k, upperBoundary);
                } else if(fireflies[i].getPositionAtIndex(k) < lowerBoundary){
                    fireflies[i].setNewPositionAtIndex(k, lowerBoundary);
                }
            }
        }
    }
}

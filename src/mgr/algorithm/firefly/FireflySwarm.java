package mgr.algorithm.firefly;

import mgr.algorithm.SwarmAlgorithm;
import mgr.config.Config;
import mgr.teacher.NetworkTeacher;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FireflySwarm implements SwarmAlgorithm{

    private Firefly[] fireflies;
    private Firefly[] firefliesCopy;
    private List<Firefly> firefliesList;
    private List<Firefly> firefliesListCopy;
    private double bestLightness;
//    private double[] lightnessArray;
//    private double[] lightnessArrayCopy;
    private double[] bestPositions;
//    private double[] bestPositionsCopy;

    private double lowerBoundary;
    private double upperBoundary;

    private double scale;

    private NetworkTeacher netTeacher;

    private final static int NUM_OF_FIREFLIES = 25;
//    private final static int MAX_GENERATIONS = 500;

    private final static int DIMENSION = Config.NUM_OF_WEIGHTS;

    private double alpha = 0.67;
    private final static double BETA_MIN = 0.2;
    private final static double BETA0 = 1.0;
    private final static double GAMMA = 1.0;
    private final static double DELTA = 0.97;

    private Random rand;

    private int iterations;

    public FireflySwarm(NetworkTeacher teacher, int iters){
        this.netTeacher = teacher;
        this.iterations = iters;
        initBoundariesFromTestFunc();
        this.fireflies = new Firefly[NUM_OF_FIREFLIES];
        this.firefliesCopy = new Firefly[NUM_OF_FIREFLIES];
//        this.firefliesList = new ArrayList<Firefly>();
//        this.firefliesListCopy = new ArrayList<Firefly>();
        this.bestPositions = new double[DIMENSION];
        this.bestLightness = Double.POSITIVE_INFINITY;
        this.scale = Math.abs(upperBoundary-lowerBoundary);
        this.rand = new Random();
        initFireFlies();
    }

    private void initBoundariesFromTestFunc(){
        this.lowerBoundary = Config.LOWER_BOUNDARY;
        this.upperBoundary = Config.UPPER_BOUNDARY;
    }

    private void initFireFlies(){
        for (int i = 0; i < fireflies.length; i++) {
            this.fireflies[i] = new Firefly(DIMENSION, this.lowerBoundary, this.upperBoundary);
        }
    }

    @Override
    public int getMinimum() throws Exception{
        for (int iter = 0; iter < iterations; iter++){
            getNewSolutions();
            sortFirefliesInList();
            saveCurrentBest();
            setCopyOfArray();
            moveFireflies();
            setNewAlpha();
        }
        netTeacher.setWeightsToNetwork(getBestPositions());
        printBestPositions();
        printResult();
        return 0;
    }

    @Override
    public String getName(){
        return "FireFly Algorithm";
    }

    @Override
    public double[] getBestPositions(){
        return this.bestPositions;
    }

    private void printBestPositions(){
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

    private void setNewAlpha(){
//        double delta = 1-Math.pow((Math.pow(10, -4) / 0.9), 1 / MAX_GENERATIONS);
//        this.alpha = (1-delta)*this.alpha;
        alpha = alpha*DELTA;
    }

    private void getNewSolutions() throws Exception{
        double[] fireflyPositions;
        double newLightness;
        for (int i = 0; i < fireflies.length; i++) {
            fireflyPositions = this.fireflies[i].getPositions();
            newLightness = netTeacher.getErrorOfNetwork(fireflyPositions, false);
            this.fireflies[i].setLightness(newLightness);
        }
    }

    private void sortFirefliesInList(){
//        if (firefliesList.size() != 0){
//            this.firefliesList.clear();
//        }
//        this.firefliesList = Arrays.asList(fireflies);
//        Collections.sort(firefliesList);
//        this.fireflies = this.firefliesList.toArray(fireflies);
        Arrays.sort(this.fireflies);
    }

    private void saveCurrentBest(){
        this.bestLightness = fireflies[0].getLightness();
        System.arraycopy(fireflies[0].getPositions(), 0, bestPositions, 0, bestPositions.length);
    }

    private void setCopyOfArray(){
//        this.firefliesListCopy.clear();
//        this.firefliesListCopy.addAll(firefliesList);
//        this.firefliesListCopy.toArray(firefliesCopy);
        firefliesCopy = Arrays.copyOf(fireflies, NUM_OF_FIREFLIES);
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
                    alpha*(getRandomFromRange(0, 1)-0.5);
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

    public void printResult(){
//        System.out.println("Najlepsze wskazane wspolrzedne w fazie koncowej, x: " + getBestPositionAtIndex(0) + " y: " + getBestPositionAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie koncowej: " + getBestLightness());
    }
}

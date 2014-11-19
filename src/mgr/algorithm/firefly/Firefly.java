package mgr.algorithm.firefly;

import java.util.Random;

public class Firefly {

    private double[] positions;
    private double lightness;
    private int dimension;

    private static final double lowerBoundary = -5.5;
    private static final double upperBoundary = -5.5;

    private Random rand;

    public Firefly(int dim){
        this.dimension = dim;
        this.positions = new double[dimension];
        this.rand = new Random();
        this.lightness = Double.POSITIVE_INFINITY;
        initFirefly();
    }

    private void initFirefly(){
        for (int i = 0; i < positions.length; i++) {
            this.positions[i] = getRandomFromRange(lowerBoundary, upperBoundary);
        }
    }

    private double getRandomFromRange(double min, double max){
        double result = min + (rand.nextDouble()*(max-min));
        return result;
    }
}

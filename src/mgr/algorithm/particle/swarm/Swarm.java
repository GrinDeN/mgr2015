package mgr.algorithm.particle.swarm;

import mgr.test.functions.AlgsEnum;
import mgr.test.functions.TestFuncFactory;
import mgr.test.functions.TestFunction;

public class Swarm {

    private int numberOfParticles;
    private int xSize;
    private Particle[] particles;
    private double[] bestPositionOfSwarm;
    private double actualBestValueOfMinFunc;

    private TestFunction testFunction;

    public Swarm(AlgsEnum alg, int numOfParticles, int xSize){
        this.testFunction = TestFuncFactory.getTestFunction(alg);
        numberOfParticles = numOfParticles;
        particles = new Particle[numberOfParticles];
        this.xSize = xSize;
        bestPositionOfSwarm = new double[xSize];
    }

    public void getMinimum(){
        initializeSwarm();
        double result;
        for (int i = 0; i < getNumberOfParticles() ; i++){
            double[] particle_xPositions = getParticleAtIndex(i).get_xPositions();
            result = testFunction.getResult(particle_xPositions);
            getParticleAtIndex(i).setBestValueError(result);
            if (i==0){
                setActualBestValueOfMinFunc(result);
                updateBestPositionOfSwarm(particle_xPositions);
            } else {
                checkIfVectorIsBetter(result, particle_xPositions);
            }
        }
        System.out.println("Najlepsze wskazane wspolrzedne w fazie wstepnej, x: " + getBestPosAtIndex(0) + " y: " + getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie wstepnej: " + getActualBestValueOfMinFunc());
        //koniec fazy wstepnej

        for (int k = 0; k < 100; k++){
            if (testFunction.isSolutionEnoughNearMinimum(getActualBestValueOfMinFunc())){
                System.out.println("Algorytm wykonał " + k + "iteracji.");
                break;
            }
            for (int i = 0; i < getNumberOfParticles(); i++){
                updateParticlesAtIndex(i);
                double[] particle_xPositions = getParticleAtIndex(i).get_xPositions();
                result = testFunction.getResult(particle_xPositions);
                getParticleAtIndex(i).setActualValueError(result);
                getParticleAtIndex(i).checkErrorValues();
                checkIfVectorIsBetter(result, particle_xPositions);
            }
        }
        System.out.println("Najlepsze wskazane wspolrzedne po wszystkich iteracjach, x: " + getBestPosAtIndex(0) + " y: " + getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat po wszystkich iteracjach: " + getActualBestValueOfMinFunc());
    }

    private void initializeSwarm(){
        actualBestValueOfMinFunc = Double.POSITIVE_INFINITY;
        for (int i=0; i<numberOfParticles; i++){
            particles[i] = new Particle(xSize, testFunction.getLowerBoundary(), testFunction.getUpperBoundary());
            particles[i].initializeParticle();
        }
    }

    private void updateParticlesAtIndex(int index){
        particles[index].updateVelocity(bestPositionOfSwarm);
        particles[index].update_xPositions();
    }

    private void updateBestPositionOfSwarm(double[] particleBest){
        for (int i=0; i<xSize; i++){
            bestPositionOfSwarm[i] = particleBest[i];
        }
    }

    private void setActualBestValueOfMinFunc(double val){
        actualBestValueOfMinFunc = val;
    }

    private Particle getParticleAtIndex(int index){
        return particles[index];
    }

    private int getNumberOfParticles(){
        return numberOfParticles;
    }

    private void checkIfVectorIsBetter(double E, double[] xPositions){
        if (E < actualBestValueOfMinFunc){
            actualBestValueOfMinFunc = E;
            updateBestPositionOfSwarm(xPositions);
        }
    }

    private double getActualBestValueOfMinFunc(){
        return actualBestValueOfMinFunc;
    }

    private double getBestPosAtIndex(int index){
        return bestPositionOfSwarm[index];
    }

}


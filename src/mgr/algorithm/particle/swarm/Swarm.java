package mgr.algorithm.particle.swarm;

import mgr.algorithm.SwarmAlgorithm;
import mgr.config.Config;
import mgr.teacher.NetworkTeacher;

public class Swarm implements SwarmAlgorithm{

    private int numberOfParticles;
    private int xSize;
    private Particle[] particles;
    private double[] bestPositionOfSwarm;
    private double actualBestValueOfMinFunc;
    private int iterations;

    private NetworkTeacher networkTeacher;

    public Swarm(NetworkTeacher netTeacher, int numOfIters, int numOfParticles, int xSize){
        this.networkTeacher = netTeacher;
        this.iterations = numOfIters;
        numberOfParticles = numOfParticles;
        particles = new Particle[numberOfParticles];
        this.xSize = xSize;
        bestPositionOfSwarm = new double[xSize];
    }

    @Override
    public int getMinimum() throws Exception{
        initializeSwarm();
        double result = 0;
        for (int i = 0; i < getNumberOfParticles() ; i++){
            double[] particle_xPositions = getParticleAtIndex(i).get_xPositions();
            result = networkTeacher.getErrorOfNetwork(particle_xPositions, false);
            getParticleAtIndex(i).setBestValueError(result);
            if (i==0){
                setActualBestValueOfMinFunc(result);
                updateBestPositionOfSwarm(particle_xPositions);
            } else {
                checkIfVectorIsBetter(result, particle_xPositions);
            }
        }
        System.out.println("Najlepszy wynik bledu po wstepnej fazie: " + actualBestValueOfMinFunc);
        for (int iter = 0; iter < iterations; iter++){
            for (int i = 0; i < getNumberOfParticles(); i++){
                updateParticlesAtIndex(i);
                double[] particle_xPositions = getParticleAtIndex(i).get_xPositions();
                result = networkTeacher.getErrorOfNetwork(particle_xPositions, false);
                getParticleAtIndex(i).setActualValueError(result);
                getParticleAtIndex(i).checkErrorValues();
                checkIfVectorIsBetter(result, particle_xPositions);
            }
        }
        networkTeacher.setWeightsToNetwork(getBestPositions());
        System.out.println("Najlepszy wynik bledu po koncowej fazie: " + actualBestValueOfMinFunc);
        printBestPositions();
        return 0;
    }

    @Override
    public String getName(){
        return "PSO";
    }

    @Override
    public double[] getBestPositions(){
        return this.bestPositionOfSwarm;
    }

    private void printBestPositions(){
        double[] alphaPositions = getBestPositions();
        int rowCounter = 1;
        for (int i = 0; i < alphaPositions.length; i++) {
            if (i%Config.INPUT_SIZE <= Config.INPUT_SIZE && rowCounter <= Config.HIDD_NEURONS) {
                System.out.print(alphaPositions[i] + " ");
//               System.out.println(alphaPositions[i]);
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

    private void initializeSwarm(){
        actualBestValueOfMinFunc = Double.POSITIVE_INFINITY;
        for (int i=0; i<numberOfParticles; i++){
            particles[i] = new Particle(xSize, Config.LOWER_BOUNDARY, Config.UPPER_BOUNDARY);
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


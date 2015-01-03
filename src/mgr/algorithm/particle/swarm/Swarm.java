package mgr.algorithm.particle.swarm;

import mgr.algorithm.SwarmAlgorithm;
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
//            result = testFunction.getResult(particle_xPositions);
            result = networkTeacher.getErrorOfNetwork(particle_xPositions);
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
//                result = testFunction.getResult(particle_xPositions);
                result = networkTeacher.getErrorOfNetwork(particle_xPositions);
                getParticleAtIndex(i).setActualValueError(result);
                getParticleAtIndex(i).checkErrorValues();
                checkIfVectorIsBetter(result, particle_xPositions);
            }
        }
        System.out.println("Najlepszy wynik bledu po koncowej fazie: " + actualBestValueOfMinFunc);
        return 0;
    }

    @Override
    public String getName(){
        return "PSO";
    }

    private void initializeSwarm(){
        actualBestValueOfMinFunc = Double.POSITIVE_INFINITY;
        for (int i=0; i<numberOfParticles; i++){
            particles[i] = new Particle(xSize, -2.0, 2.0);
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


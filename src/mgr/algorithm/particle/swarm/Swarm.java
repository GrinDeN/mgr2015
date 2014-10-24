package mgr.algorithm.particle.swarm;

public class Swarm {

    private int numberOfParticles;
    private int xSize;
    private Particle[] particles;
    private double[] bestPositionOfSwarm;
    private double actualBestValueOfMinFunc;

    public Swarm(int numOfParticles, int xSize){
        numberOfParticles = numOfParticles;
        particles = new Particle[numberOfParticles];
        this.xSize = xSize;
        bestPositionOfSwarm = new double[xSize];
    }

    public void initializeSwarm(){
        actualBestValueOfMinFunc = 100000;
        for (int i=0; i<numberOfParticles; i++){
            particles[i] = new Particle(xSize);
            particles[i].initializeParticle();
        }
    }

    public void updateParticlesAtIndex(int index){
        particles[index].updateVelocity(bestPositionOfSwarm);
        particles[index].update_xPositions();
    }

    public void updateBestPositionOfSwarm(double[] particleBest){
        for (int i=0; i<xSize; i++){
            bestPositionOfSwarm[i] = particleBest[i];
        }
    }

    public void setActualBestValueOfMinFunc(double val){
        actualBestValueOfMinFunc = val;
    }

    public Particle getParticleAtIndex(int index){
        return particles[index];
    }

    public int getNumberOfParticles(){
        return numberOfParticles;
    }

    public void checkIfVectorIsBetter(double E, double[] xPositions){
        if (E < actualBestValueOfMinFunc){
            actualBestValueOfMinFunc = E;
            updateBestPositionOfSwarm(xPositions);
        }
    }

    public double getActualBestValueOfMinFunc(){
        return actualBestValueOfMinFunc;
    }

    public double getBestPosAtIndex(int index){
        return bestPositionOfSwarm[index];
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}


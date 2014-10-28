package mgr.test.functions;

import mgr.algorithm.particle.swarm.Swarm;

public class MainTest {

    public static void main(String[] args){
        //faza wstepna - wylonienie najlepszej wylosowanej pozycji
        Swarm swarm = new Swarm(20, 2);
        swarm.initializeSwarm();
        double result;
        for (int i = 0; i < swarm.getNumberOfParticles() ; i++){
            double[] particle_xPositions = swarm.getParticleAtIndex(i).get_xPositions();
            result = EasomFunc.function(particle_xPositions[0], particle_xPositions[1]);
            swarm.getParticleAtIndex(i).setBestValueError(result);
            if (i==0){
                swarm.setActualBestValueOfMinFunc(result);
                swarm.updateBestPositionOfSwarm(particle_xPositions);
            } else {
                swarm.checkIfVectorIsBetter(result, particle_xPositions);
            }
        }
        System.out.println("Najlepsze wskazane wspolrzedne w fazie wstepnej, x: " + swarm.getBestPosAtIndex(0) + " y: " + swarm.getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie wstepnej: " + swarm.getActualBestValueOfMinFunc());
        //koniec fazy wstepnej

        for (int k = 0; k < 100; k++){
            for (int i = 0; i < swarm.getNumberOfParticles(); i++){
                swarm.updateParticlesAtIndex(i);
                double[] particle_xPositions = swarm.getParticleAtIndex(i).get_xPositions();
                result = EasomFunc.function(particle_xPositions[0], particle_xPositions[1]);
                swarm.getParticleAtIndex(i).setActualValueError(result);
                swarm.getParticleAtIndex(i).checkErrorValues();
                swarm.checkIfVectorIsBetter(result, particle_xPositions);
            }
        }
        System.out.println("Najlepsze wskazane wspolrzedne po wszystkich iteracjach, x: " + swarm.getBestPosAtIndex(0) + " y: " + swarm.getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat po wszystkich iteracjach: " + swarm.getActualBestValueOfMinFunc());
    }

}

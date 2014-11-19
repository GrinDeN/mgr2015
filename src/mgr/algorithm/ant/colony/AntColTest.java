package mgr.algorithm.ant.colony;

import mgr.test.functions.AlgsEnum;

public class AntColTest {

    private static final int MAX_ITER = 1000;

    public static void main(String[] args){
        AntColony ants = new AntColony(AlgsEnum.ACKLEY, MAX_ITER);
        ants.getMinimum();

        System.out.println("Najlepsze wskazane wspolrzedne w fazie koncowej, x: " + ants.getGlobalPositionsAtIndex(0) + " y: " + ants.getGlobalPositionsAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie koncowej: " + ants.getFitness());
    }
}

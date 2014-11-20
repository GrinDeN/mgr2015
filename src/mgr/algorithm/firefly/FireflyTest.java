package mgr.algorithm.firefly;

import mgr.test.functions.AlgsEnum;

public class FireflyTest {

    public static void main(String[] args){
        FireflySwarm ffswarm = new FireflySwarm(AlgsEnum.ACKLEY);
        ffswarm.getMinimum();

        System.out.println("Najlepsze wskazane wspolrzedne w fazie koncowej, x: " + ffswarm.getBestPositionAtIndex(0) + " y: " + ffswarm.getBestPositionAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie koncowej: " + ffswarm.getBestLightness());
    }
}

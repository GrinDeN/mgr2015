package mgr.algorithm.firefly;

import mgr.test.functions.AlgsEnum;

public class FireflyTest {

    public static void main(String[] args){
        AlgsEnum alg = AlgsEnum.GOLDSTEINPRICE;
        FireflySwarm ffswarm = new FireflySwarm(alg);
        ffswarm.getMinimum();
        ffswarm.printResult();
    }
}

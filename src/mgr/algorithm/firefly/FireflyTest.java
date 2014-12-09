package mgr.algorithm.firefly;

import mgr.test.functions.TestFuncEnum;

public class FireflyTest {

    public static void main(String[] args){
        TestFuncEnum alg = TestFuncEnum.GOLDSTEINPRICE;
        FireflySwarm ffswarm = new FireflySwarm(alg, 500);
        ffswarm.getMinimum();
        ffswarm.printResult();
    }
}

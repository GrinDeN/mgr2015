package mgr.algorithm.bee.colony;

import mgr.test.functions.TestFuncEnum;

public class BeeTest {

    public static void main(String[] args){
        TestFuncEnum alg = TestFuncEnum.EASOM;
        BeeColony beeColony = new BeeColony(alg, 5000, 30, 2);
        beeColony.getMinimum();
        beeColony.printResult();
    }
}

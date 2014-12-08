package mgr.algorithm.bee.colony;

import mgr.test.functions.AlgsEnum;

public class BeeTest {

    public static void main(String[] args){
        AlgsEnum alg = AlgsEnum.EASOM;
        BeeColony beeColony = new BeeColony(alg, 30, 2);
        beeColony.getMinimum();
        beeColony.printResult();
    }
}

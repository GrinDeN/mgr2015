package mgr.algorithm.bat.swarm;

import mgr.test.functions.TestFuncEnum;

public class BatTest {
    public static void main(String[] args){
        TestFuncEnum alg = TestFuncEnum.EASOM;
        BatSwarm batSwarm = new BatSwarm(alg, 20, 2);
        batSwarm.getMinimum();
        batSwarm.printResult();
    }
}

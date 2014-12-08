package mgr.algorithm.bat.swarm;

import mgr.test.functions.AlgsEnum;

public class BatTest {
    public static void main(String[] args){
        AlgsEnum alg = AlgsEnum.EASOM;
        BatSwarm batSwarm = new BatSwarm(alg, 20, 2);
        batSwarm.getMinimum();
        batSwarm.printResult();
    }
}

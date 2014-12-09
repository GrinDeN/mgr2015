package mgr.algorithm.particle.swarm;

import mgr.test.functions.TestFuncEnum;

public class SwarmTest {
    public static void main(String[] args){
        TestFuncEnum alg = TestFuncEnum.EASOM;
        Swarm swarm = new Swarm(alg, 200, 20, 2);
        swarm.getMinimum();
    }
}

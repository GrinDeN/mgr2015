package mgr.algorithm.particle.swarm;

import mgr.test.functions.AlgsEnum;

public class SwarmTest {
    public static void main(String[] args){
        AlgsEnum alg = AlgsEnum.EASOM;
        Swarm swarm = new Swarm(alg, 20, 2);
        swarm.getMinimum();
    }
}

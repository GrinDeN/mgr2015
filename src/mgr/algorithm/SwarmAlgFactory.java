package mgr.algorithm;

import mgr.algorithm.grey.wolf.WolfsPack;
import mgr.algorithm.particle.swarm.Swarm;
import mgr.config.Config;
import mgr.teacher.NetworkTeacher;

public class SwarmAlgFactory {
    public static SwarmAlgorithm getSwarmAlgorithm(SwarmEnum alg, NetworkTeacher netTeacher){
        switch (alg){
            case ANT:
//                return new AntColony(testFun, 1000);
            case BAT:
//                return new BatSwarm(testFun, 1000, 20, 2);
            case BEE:
//                return new BeeColony(testFun, 700, 30, 2);
            case FIREFLY:
//                return new FireflySwarm(testFun, 500);
            case WOLF:
                return new WolfsPack(netTeacher, 200);
            case PSO:
                return new Swarm(netTeacher, 100, 30, Config.NUM_OF_WEIGHTS);
            case WEED:
//                return new WeedColony(testFun, 1.5, 0.001);
        }
        return null;
    }
}

package mgr.algorithm;

import mgr.algorithm.ant.colony.AntColony;
import mgr.algorithm.bat.swarm.BatSwarm;
import mgr.algorithm.bee.colony.BeeColony;
import mgr.algorithm.firefly.FireflySwarm;
import mgr.algorithm.grey.wolf.WolfsPack;
import mgr.algorithm.particle.swarm.Swarm;
import mgr.algorithm.weed.optimization.WeedColony;
import mgr.config.Config;
import mgr.teacher.NetworkTeacher;

public class SwarmAlgFactory {
    public static SwarmAlgorithm getSwarmAlgorithm(SwarmEnum alg, NetworkTeacher netTeacher){
        int iters = Config.NUM_OF_ITERS;
        switch (alg){
            case ANT:
                return new AntColony(netTeacher, iters);
            case BAT:
                return new BatSwarm(netTeacher, iters, 30);
            case BEE:
                return new BeeColony(netTeacher, iters, 20);
            case FIREFLY:
                return new FireflySwarm(netTeacher, iters);
            case WOLF:
                return new WolfsPack(netTeacher, iters);
            case PSO:
                return new Swarm(netTeacher, iters, 30, Config.NUM_OF_WEIGHTS);
            case WEED:
                return new WeedColony(netTeacher, iters, 2.0, 0.1);
        }
        return null;
    }
}

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
        switch (alg){
            case ANT:
                return new AntColony(netTeacher, 100);
            case BAT:
                return new BatSwarm(netTeacher, 100, 20);
            case BEE:
                return new BeeColony(netTeacher,500, 20);
            case FIREFLY:
                return new FireflySwarm(netTeacher, 200);
            case WOLF:
                return new WolfsPack(netTeacher, 100);
            case PSO:
                return new Swarm(netTeacher, 100, 30, Config.NUM_OF_WEIGHTS);
            case WEED:
                return new WeedColony(netTeacher, 5.0, 0.001);
        }
        return null;
    }
}

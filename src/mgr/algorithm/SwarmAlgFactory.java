package mgr.algorithm;

import mgr.algorithm.ant.colony.AntColony;
import mgr.algorithm.bat.swarm.BatSwarm;
import mgr.algorithm.bee.colony.BeeColony;
import mgr.algorithm.firefly.FireflySwarm;
import mgr.algorithm.grey.wolf.WolfsPack;
import mgr.algorithm.particle.swarm.Swarm;
import mgr.algorithm.weed.optimization.WeedColony;
import mgr.test.functions.TestFuncEnum;

public class SwarmAlgFactory {
    public static SwarmAlgorithm getSwarmAlgorithm(SwarmEnum alg, TestFuncEnum testFun){
        switch (alg){
            case ANT:
                return new AntColony(testFun, 1000);
            case BAT:
                return new BatSwarm(testFun, 1000, 20, 2);
            case BEE:
                return new BeeColony(testFun, 1000, 30, 2);
            case FIREFLY:
                return new FireflySwarm(testFun, 500);
            case WOLF:
                return new WolfsPack(testFun, 500);
            case PSO:
                return new Swarm(testFun, 200, 20, 2);
            case WEED:
                switch (testFun){
                    case ACKLEY:
                        return new WeedColony(testFun, 4.0, 0.001);
                    case BEALES:
                        return new WeedColony(testFun, 4.0, 0.001);
                    case GOLDSTEINPRICE:
                        return new WeedColony(testFun, 1.0, 0.001);
                    case MATYAS:
                        return new WeedColony(testFun, 8.0, 0.0001);
                    case EASOM:
                        return new WeedColony(testFun, 40.0, 0.0001);
                }
                return null;
        }
        return null;
    }
}

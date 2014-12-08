package mgr.algorithm;

import mgr.algorithm.firefly.FireflySwarm;
import mgr.algorithm.weed.optimization.SwarmEnum;
import mgr.test.functions.*;

public class SwarmAlgFactory {
    public static SwarmAlgorithm getSwarmAlgorithm(SwarmEnum alg, TestFuncEnum testFun){
        switch (alg){
            case ANT:
//                return new AckleyFunc();
            case BAT:
//                return new BealesFunc();
            case BEE:
//                return new EasomFunc();
            case FIREFLY:
                return new FireflySwarm(testFun);
            case WOLF:
//                return new MatyasFunc();
            case PSO:
//                return new MatyasFunc();
            case WEED:
//                return new MatyasFunc();
        }
        return null;
    }
}

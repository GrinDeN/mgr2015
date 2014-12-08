package mgr.algorithm;

import mgr.algorithm.weed.optimization.SwarmEnum;
import mgr.test.functions.TestFuncEnum;

public class MainTest {

    private final static int ITERS = 100;

    public static void main(String[] args){
        SwarmEnum swarmAlg = SwarmEnum.FIREFLY;
        TestFuncEnum testFunc = TestFuncEnum.EASOM;
        AlgTester algTester = new AlgTester(swarmAlg, testFunc, ITERS);
        algTester.test();
    }
}

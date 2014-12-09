package mgr.algorithm;

import mgr.test.functions.TestFuncEnum;

public class MainTest {

    private final static int ITERS = 100;

    public static void main(String[] args){
        SwarmEnum swarmAlg = SwarmEnum.WEED;
        TestFuncEnum testFunc = TestFuncEnum.GOLDSTEINPRICE;
        AlgTester algTester = new AlgTester(swarmAlg, testFunc, ITERS);
        algTester.test();
    }
}

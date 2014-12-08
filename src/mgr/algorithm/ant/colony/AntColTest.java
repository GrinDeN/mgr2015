package mgr.algorithm.ant.colony;

import mgr.test.functions.TestFuncEnum;

public class AntColTest {

    private static final int MAX_ITER = 1000;

    public static void main(String[] args){
        AntColony ants = new AntColony(TestFuncEnum.EASOM, MAX_ITER);
        ants.getMinimum();
        ants.printResult();
    }
}

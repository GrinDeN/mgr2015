package mgr.algorithm.grey.wolf;

import mgr.test.functions.TestFuncEnum;

public class WolfTest {

    public static void main(String[] args){
        TestFuncEnum alg = TestFuncEnum.MATYAS;
        WolfsPack wolfsPack = new WolfsPack(alg, 500);
        wolfsPack.getMinimum();
        wolfsPack.printResults();
    }
}

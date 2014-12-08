package mgr.algorithm.grey.wolf;

import mgr.test.functions.AlgsEnum;

public class WolfTest {

    public static void main(String[] args){
        AlgsEnum alg = AlgsEnum.MATYAS;
        WolfsPack wolfsPack = new WolfsPack(alg, 500);
        wolfsPack.getMinimum();
        wolfsPack.printResults();
    }
}

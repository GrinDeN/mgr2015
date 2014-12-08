package mgr.algorithm.weed.optimization;

import mgr.test.functions.AlgsEnum;

public class WeedTest {

    public static void main(String[] args){
        AlgsEnum alg = AlgsEnum.MATYAS;
        WeedColony weedColony = new WeedColony(alg, 8.0, 0.0001);
        weedColony.getMinimum();
        weedColony.printBestWeed();
    }
}

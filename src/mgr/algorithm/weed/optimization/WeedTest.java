package mgr.algorithm.weed.optimization;

import mgr.test.functions.AlgsEnum;

public class WeedTest {

    public static void main(String[] args){
        AlgsEnum alg = AlgsEnum.EASOM;
        WeedColony weedColony = new WeedColony(alg);
        weedColony.getMinimum();
        weedColony.printBestWeed();
    }
}

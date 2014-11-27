package mgr.algorithm.weed.optimization;

import mgr.test.functions.AlgsEnum;

public class WeedTest {

    public static void main(String[] args){
        AlgsEnum alg = AlgsEnum.ACKLEY;
        WeedColony weedColony = new WeedColony(alg);
        System.out.println("PRZED SORTOWANIEM");
        weedColony.printWeedColony();
        System.out.println("SORTOWANIE");
        System.out.println("PO SORTOWANIU");
        weedColony.sortAndSaveMinMaxFitness();
        weedColony.printWeedColony();
        System.out.println("BEST WEED: ");
        weedColony.printBestWeed();
    }
}

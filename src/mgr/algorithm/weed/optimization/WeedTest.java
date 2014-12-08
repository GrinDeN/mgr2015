package mgr.algorithm.weed.optimization;

import mgr.test.functions.TestFuncEnum;

public class WeedTest {

    public static final int ITERS = 100;

    public static void main(String[] args){
        TestFuncEnum alg = TestFuncEnum.EASOM;
        int i = 1;
        int breakCounter = 0;
        int sumOfIterations = 0;
        double sredniaIteracji;
        long start = System.nanoTime();
        while (i<=ITERS){
            WeedColony weedColony = new WeedColony(alg, 70.0, 0.0001);
            int min = weedColony.getMinimum();
            if (min != 0){
                breakCounter++;
                sumOfIterations += min;
            }
            weedColony.printBestWeed();
            i++;
        }
        long end = System.nanoTime();
        double difference = (end - start)/1e6;
        sredniaIteracji = sumOfIterations/breakCounter;
        System.out.println("Algorytm znalazł " + breakCounter + " rozwiazan wczesniej(breaki).");
        System.out.println("Srednia ilosc iteracji w breakach: " + sredniaIteracji);
        System.out.println("Czas wykonania " + ITERS + " iteracji wynosi: " + difference + " ms.");
    }
}

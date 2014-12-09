package mgr.algorithm;

import mgr.test.functions.TestFuncEnum;

public class AlgTester {

    private int iterations;
    private SwarmEnum swarmAlgEnum;
    private TestFuncEnum testFunEnum;

    public AlgTester(SwarmEnum swarmAlg, TestFuncEnum testFun, int iters){
        this.swarmAlgEnum = swarmAlg;
        this.testFunEnum = testFun;
        this.iterations = iters;
    }

    public void test(){
        int i = 1;
        int breakCounter = 0;
        int sumOfIterations = 0;
        double sredniaIteracji = 0.0;
        long start = System.nanoTime();
        while (i<=iterations){
            SwarmAlgorithm swarmAlg = SwarmAlgFactory.getSwarmAlgorithm(swarmAlgEnum, testFunEnum);
            int min = swarmAlg.getMinimum();
            if (min != 0){
                breakCounter++;
                sumOfIterations += min;
            }
            i++;
        }
        long end = System.nanoTime();
        double difference = (end - start)/1e6;
        if (breakCounter!=0)
            sredniaIteracji = sumOfIterations/breakCounter;
        System.out.println("Algorytm znalazł " + breakCounter + " rozwiazan wczesniej(breaki).");
        System.out.println("Srednia ilosc iteracji w breakach: " + sredniaIteracji);
        System.out.println("Czas wykonania " + iterations + " iteracji wynosi: " + difference + " ms.");
    }
}

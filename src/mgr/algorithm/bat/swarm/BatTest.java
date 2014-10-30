package mgr.algorithm.bat.swarm;

import mgr.test.functions.AckleyFunc;

public class BatTest {
    public static void main(String[] args){
        BatSwarm batSwarm = new BatSwarm(20, 2);
        double result;
        for (int i = 0; i < batSwarm.getNumOfBats(); i++) {
            double[] eachBatPositions = batSwarm.getBatAtIndex(i).get_xBestPositions();
            result = AckleyFunc.function(eachBatPositions[0], eachBatPositions[1]);
            batSwarm.getBatAtIndex(i).setCurrentMinimum(result);
            if (i==0){
                batSwarm.setBestMinimumValue(result);
                batSwarm.setBestPositions(eachBatPositions);
            } else{
                batSwarm.updateSolutionIfBetter(result, eachBatPositions);
            }
        }
//        batSwarm.updateBestPositionsInAllBats();
        System.out.println("Najlepsze wskazane wspolrzedne w fazie wstepnej, x: " + batSwarm.getBestPosAtIndex(0) + " y: " + batSwarm.getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie wstepnej: " + batSwarm.getMinimumValue());
        //koniec fazy wstepnej

        for (int k = 0; k < 5000; k++) {
            for (int i = 0; i < batSwarm.getNumOfBats(); i++){
                batSwarm.getBatAtIndex(i).updateMovement();
//                batSwarm.correctPositionsToBoundaries(i);
                batSwarm.getSomeRandomWalk(i);
                double[] eachBatPositions = batSwarm.getBatAtIndex(i).get_xPositions();
                result = AckleyFunc.function(eachBatPositions[0], eachBatPositions[1]);
//                batSwarm.updateSolutionIfBetter(result, eachBatPositions);
                batSwarm.updateBatAtIndexIfBetterSol(i, result);
                batSwarm.updateSolutionIfBetter(result, eachBatPositions);
//                batSwarm.updateBestPositionsInAllBats();
            }
        }
        System.out.println("Najlepsze wskazane wspolrzedne po wszystkich iteracjach, x: " + batSwarm.getBestPosAtIndex(0) + " y: " + batSwarm.getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat po wszystkich iteracjach: " + batSwarm.getMinimumValue());
    }
}

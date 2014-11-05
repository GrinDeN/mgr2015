package mgr.algorithm.bee.colony;

import mgr.test.functions.AckleyFunc;

public class BeeTest {

    public static void main(String[] args){
        BeeColony beeColony = new BeeColony(20, 2);
        double result;
        for (int i = 0; i < beeColony.getNumOfFood(); i++) {
            double[] eachFoodPositions = beeColony.getFoodAtIndex(i).getFoodPositions();
            result = AckleyFunc.function(eachFoodPositions[0], eachFoodPositions[1]);
            beeColony.getFoodAtIndex(i).init(result);
            if (i==0){
                beeColony.setBestxPositions(eachFoodPositions);
                beeColony.setGlobalMinimum(result);
            } else{
                beeColony.updateSolutionIfBetter(result, eachFoodPositions);
            }
        }
        System.out.println("Najlepsze wskazane wspolrzedne w fazie wstepnej, x: " + beeColony.getBestPosAtIndex(0) + " y: " + beeColony.getBestPosAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie wstepnej: " + beeColony.getGlobalMinimum());
        //koniec fazy wstepnej

        double resultOfChangedSolution;
        for (int k = 0; k < 5000; k++){
            for (int i = 0; i < beeColony.getNumOfFood(); i++){
                beeColony.sendEmployedBees();
                double[] changedSolution = beeColony.getChangedSolution();
                resultOfChangedSolution = AckleyFunc.function(changedSolution[0], changedSolution[1]);
                beeColony.calculateFitness(resultOfChangedSolution);
                beeColony.checkFitnessAndUpdate(resultOfChangedSolution, i);
                beeColony.calculateProbabilities();

            }
        }
    }
}

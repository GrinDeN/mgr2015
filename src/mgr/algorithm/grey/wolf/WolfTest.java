package mgr.algorithm.grey.wolf;

import static mgr.test.functions.AlgsEnum.EASOM;

public class WolfTest {

    public static void main(String[] args){
        WolfsPack wolfsPack = new WolfsPack(EASOM, 200);
        wolfsPack.getMinimum();

        System.out.println("Najlepsze wskazane wspolrzedne w fazie wstepnej, x: " + wolfsPack.getAlphaPositionAtIndex(0) + " y: " + wolfsPack.getAlphaPositionAtIndex(1));
        System.out.println("Najlepszy wskazany rezultat w fazie wstepnej: " + wolfsPack.getAlphaScore());
    }
}

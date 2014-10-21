package mgr.min.kierunkowa;

public class ZlotyPodzial {

    private static final double GOLDEN_PARAM = 0.613;
//    private static double firstParam;
//    private static double secondParam;

    public static double getMinimum(MinKierunkowa minKierunkowa, double firstParam, double secParam){
        double al1 = secParam-GOLDEN_PARAM*(secParam-firstParam);
        double al2 = GOLDEN_PARAM*(secParam-firstParam)+firstParam;
        double newFirst = 0.0;
        double newSecond = 0.0;
        double ff1 = 0.0;
        double ff2 = 0.0;
        for (int i = 0; i < 15; i++){
            ff1 = minKierunkowa.goForwardMinKierunkowa(al2);
            ff2 = minKierunkowa.goForwardMinKierunkowa(al1);
            if (ff1 > ff2){
                newSecond = al2;
                al2 = al1;
                al1 = GOLDEN_PARAM*newFirst+(1 - GOLDEN_PARAM)*newSecond;
            } else {
                newFirst = al1;
                al1 = al2;
                al2 = GOLDEN_PARAM*newSecond+(1 - GOLDEN_PARAM)*newFirst;
            }
        }
        return (newFirst+newSecond)/2;
    }

}

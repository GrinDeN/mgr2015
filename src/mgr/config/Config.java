package mgr.config;

import mgr.math.utils.ActivateFunc;

public class Config {

    public static final int NUM_OF_LAYERS = 2;
    public static int HIDD_NEURONS = 2;
    public final static int OUT_NEURONS = 1;
    public static int INPUT_SIZE = 4;

    public static int NUM_OF_WEIGHTS = (INPUT_SIZE*HIDD_NEURONS)+HIDD_NEURONS+1;

    public static int S = 0;
    public static int P = 500;
    public static int K = HIDD_NEURONS;

    public static int N1 = 0;
    public static int N2 = 0;

    public static final ActivateFunc ACTIVATE_FUNCTION = ActivateFunc.SIGMOID_BI;

    public static final double BIAS_VALUE = 1.0;
    public static final int BIAS_POSITION = 0;

    public static final boolean DEBUG = false;

    public static void setINPUT_SIZE(){
        INPUT_SIZE = N1 + N2;
    }

    public static void setNUM_OF_WEIGHTS(int weights){
        NUM_OF_WEIGHTS = weights;
    }

    public static void setN1(int value){
        N1 = value;
    }

    public static void setN2(int value){
        N2 = value;
    }

//    public static void setS(int value){
//        S = value;
//    }

//    public static void setP(int value){
//        P = value;
//    }

}

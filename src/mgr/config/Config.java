package mgr.config;

import mgr.algorithm.SwarmEnum;
import mgr.input.builder.ParamPair;
import mgr.math.utils.ActivateFunc;

import java.util.ArrayList;

public class Config {

    public static final int NUM_OF_LAYERS = 2;
    public static int HIDD_NEURONS = 12;
    public final static int OUT_NEURONS = 1;
    public static int INPUT_SIZE = 5;

    public static String dataFilename;

    public static final boolean VERIFIER_ONLY = false;

    public static int NUM_OF_WEIGHTS = (INPUT_SIZE*HIDD_NEURONS)+HIDD_NEURONS+1;

    public static int S = 2;
    public static int P = 2000;
    public static int K = HIDD_NEURONS;

    public static SwarmEnum swarmAlg;

    //suma N1+N2 zawsze o jeden mniejsza niz INPUT_SIZE (bo 1 dla BIAS)
    //N1 to ilosc wejsc powiazanych z u
    //N2 to ilosc wejsc powiazanych z y
    public static int N1 = 2;
    public static int N2 = 2;

    public static int BFGS_ITERATIONS = 200;

    public static int NUM_OF_ITERS = 100;

    public static final ActivateFunc ACTIVATE_FUNCTION = ActivateFunc.SIGMOID_BI;

    public static final double BIAS_VALUE = 1.0;
    public static final int BIAS_POSITION = 0;

    public static final boolean DEBUG = false;

    public static final double LOWER_BOUNDARY = -1.0;
    public static final double UPPER_BOUNDARY = 1.0;

    public static void setINPUT_SIZE(int value){
        INPUT_SIZE = 1 + value;
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

    public static boolean STATIC_NET = false;

    public static ArrayList<ParamPair> params = new ArrayList<ParamPair>();

    public static void setParams(ArrayList<ParamPair> newParams){
        params.clear();
        params.addAll(newParams);
    }

    public static void setP(int value){
        P = value;
    }

    public static void setHiddNeurons(int value){
        HIDD_NEURONS = value;
    }

    public static void setNumOfIters(int value){
        NUM_OF_ITERS = value;
    }

    public static void setDataFilename(String filename){
        dataFilename = filename;
    }

    public static void setSwarmAlg(SwarmEnum algorithm){
        swarmAlg = algorithm;
    }

    public static void setTypeOfNetwork(int typeOfNetwork){
        if(typeOfNetwork == 0){
            STATIC_NET = true;
        } else if(typeOfNetwork == 1){
            STATIC_NET = false;
        }
    }
}

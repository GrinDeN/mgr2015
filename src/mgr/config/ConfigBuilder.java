package mgr.config;

import mgr.input.builder.ParamPair;

import java.util.ArrayList;

public class ConfigBuilder {

    public static void selectSParam(ArrayList<ParamPair> params){
        int sParamValue = 0;
        int currentSecParamValue = 0;
        for (ParamPair eachPair : params){
            currentSecParamValue = eachPair.getSecondValue();
            if (currentSecParamValue > sParamValue){
                sParamValue = currentSecParamValue;
            }
        }
        sParamValue = sParamValue+1;
        Config.S = sParamValue;
    }

    public static void selectPParam(int value){
        Config.P = value;
    }
}

package mgr.network;

import mgr.config.ConfigBuilder;
import mgr.file.utils.DataFileExtractor;
import mgr.file.utils.DataFileReader;
import mgr.gradient.Gradient;
import mgr.input.builder.InputBuilder;
import mgr.input.builder.ParamPair;
import mgr.teacher.NetworkTeacher;

import java.util.ArrayList;

import static mgr.config.Config.P;
import static mgr.config.Config.S;

public class Test {

    public static void main(String[] args){
        String filename = "dane_spr_grad.txt";
        DataFileReader dfr = new DataFileReader(filename);
        dfr.readData();
        DataFileExtractor dfe = new DataFileExtractor(dfr.getValueList());
        dfe.extractData();
        ArrayList<ArrayList<Double>> dataValues = dfe.getDataValues();
        ArrayList<Double> demandValues = dfe.getDemandValues();

        Network net = new Network();

        ArrayList<ParamPair> params = new ArrayList<ParamPair>();
        params.add(new ParamPair(1, 2));
        params.add(new ParamPair(1));

        ConfigBuilder.selectSParam(params);
//        ConfigBuilder.selectPParam(dfe.getSizeOfDataValues());

        NetworkTeacher teacher = new NetworkTeacher(net);
        teacher.setDemandValues(demandValues);
        teacher.addDemandAsFirstNetOutputs();

        ArrayList<Double> networkOutputs = new ArrayList<Double>();
        networkOutputs.addAll(teacher.getNetOutputs());

        InputBuilder inputBuilder = new InputBuilder(dataValues, params);

        Gradient grad = new Gradient(net, teacher);

        for (int t = S; t <= P; t++){
            inputBuilder.build(t, networkOutputs);
            ArrayList<Double> inputBuilt = inputBuilder.getInput();
            double[] arr = new double[inputBuilt.size()];
            inputBuilt.toArray(arr);// zmaiana input z double[] na ArrayList<Double> :<<
            net.setNetworkInput(arr);
            double currentOutput = net.calculateOutput();
            // tu jeszcze teacher moglby blad sobie liczyc w sensie liczyc y-d i dodawaj do swojej listy bledow
            teacher.addNetOutput(currentOutput);
            grad.computeAllGradients(t);
        }


//        InputBuilder inputBuild = new InputBuilder(dataValues, params);
//        inputBuild.build(4, networkOutputs);
//        ArrayList<Double> input = inputBuild.getInput();
    }
}

package mgr.network;

import mgr.config.Config;
import mgr.config.ConfigBuilder;
import mgr.file.utils.DataFileExtractor;
import mgr.file.utils.DataFileReader;
import mgr.input.builder.ParamPair;

import java.util.ArrayList;

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

//        NetworkTeacher teacher = new NetworkTeacher(net, demandValues);
//        teacher.addDemandAsFirstNetOutputs();
//        teacher.addFirstNetErrors();

//        ArrayList<Double> beginningNetworkOutputs = new ArrayList<Double>();
//        beginningNetworkOutputs.addAll(teacher.getNetOutputs());

//        System.out.println("POCZATKOWE WARTOSCI OUTPUTOW");
//        for (Double val : beginningNetworkOutputs){
//            System.out.println(val);
//        }

//        InputBuilder inputBuilder = new InputBuilder(dataValues, params, beginningNetworkOutputs);

//        Gradient grad = new Gradient(net, teacher);

        for (int t = Config.S; t <= Config.P; t++){
//            networkOutputs.clear();
//            inputBuilder.build(Config.S, networkOutputs);
//            double[] input = inputBuilder.build(t, teacher.getLastOutput());
//            for (int i = 0; i < arr.length; i++){
//                System.out.print(arr[i]);
//                System.out.print(" ");
//            }
//            System.out.println();
//            net.setNetworkInput(input);
//            double currentOutput = net.calculateOutput();
            net.calculateOutput();
            // tu jeszcze teacher moglby blad sobie liczyc w sensie liczyc y-d i dodawaj do swojej listy bledow
//            teacher.addCurrentNetOutput();
//            teacher.addNetError(t);
//            grad.computeAllGradients(t);
        }

//        teacher.sumarizeNetErrors();

        System.out.println("OUTPUTS: ");
//        teacher.printNetOutputs();

        System.out.println();
//        teacher.printErrors();

//        teacher.printError();


//        InputBuilder inputBuild = new InputBuilder(dataValues, params);
//        inputBuild.build(4, networkOutputs);
//        ArrayList<Double> input = inputBuild.getInput();
    }
}

package mgr.teacher;

import mgr.config.Config;
import mgr.config.ConfigBuilder;
import mgr.file.utils.DataFileExtractor;
import mgr.file.utils.DataFileReader;
import mgr.input.builder.InputBuilder;
import mgr.input.builder.ParamPair;
import mgr.network.Network;

import java.util.ArrayList;

public class NetworkTeacher {

    private Network network;
    private String filenameOfData;
    private DataFileReader reader;
    private DataFileExtractor extractor;
    private InputBuilder builder;

    private ArrayList<Double> demandValues;
    private ArrayList<ArrayList<Double>> dataValues;

    private ArrayList<ParamPair> params;

    private NetworkErrorCounter errorCounter;

    public NetworkTeacher(Network net, String filename, ArrayList<ParamPair> params){
        this.network = net;
        this.filenameOfData = filename;
        this.params = params;
        readData();
        extractData();
        getDemandValues();
        getDataValues();
        ConfigBuilder.selectSParam(params); // do wyjebania na zewnatrz w przyszlosci, narazie aby nie zapomniec
        initInputBuilder();
        initErrorCounter();
    }

    private void readData(){
        this.reader = new DataFileReader(filenameOfData);
        this.reader.readData();
    }

    private void extractData(){
        this.extractor = new DataFileExtractor(reader.getValueList());
        this.extractor.extractData();
    }

    private void getDemandValues(){
        this.demandValues = extractor.getDemandValues();
    }

    private void getDataValues(){
        this.dataValues = extractor.getDataValues();
    }

    private void initInputBuilder(){
        this.builder = new InputBuilder(dataValues, params, getFirstNetOutputsAsDemandValues());
    }

    private ArrayList<Double> getFirstNetOutputsAsDemandValues(){
        ArrayList<Double> firstNetOutputs = new ArrayList<Double>();
        for (int i = 0; i <= Config.S-1; i++) {
            firstNetOutputs.add(i, this.demandValues.get(i));
        }
        return firstNetOutputs;
    }

    private void initErrorCounter(){
        this.errorCounter = new NetworkErrorCounter(demandValues);
        this.errorCounter.addFirstPartOfNetworkOutputs(getFirstNetOutputsAsDemandValues());
    }

    private void resetErrorCounterNetOutputsList(){
        this.errorCounter.resetNetOutputList();
        this.errorCounter.addFirstPartOfNetworkOutputs(getFirstNetOutputsAsDemandValues());
    }

    public double getErrorOfNetwork(double[] weights) throws Exception{
        if (weights != null){
            network.setWeights(weights);
        }
        resetErrorCounterNetOutputsList();
        double[] input;
        for (int t = Config.S; t <= Config.P; t++){
            if (t == Config.S){
                input = builder.build(t, demandValues.get(t));
            } else {
                input = builder.build(t, network.getCurrentOutput());
            }
//            for (int i = 0; i < arr.length; i++){
//                System.out.print(arr[i]);
//                System.out.print(" ");
//            }
//            System.out.println();
            network.setNetworkInput(input);
//            double currentOutput = net.calculateOutput();
            network.calculateOutput();
            // tu jeszcze teacher moglby blad sobie liczyc w sensie liczyc y-d i dodawaj do swojej listy bledow
//            teacher.addCurrentNetOutput();
//            teacher.addNetError(t);
//            grad.computeAllGradients(t);
//            System.out.println("Current net output: " + network.getCurrentOutput());
//            System.out.println("iteracja: " +t);
            errorCounter.addNetworkOutput(network.getCurrentOutput());
        }
        double error = errorCounter.sumarizeErrors();
        return error;
    }

    public void setWeightsToNetwork(double[] weights){
        double[] weightsToNetwork = new double[weights.length];
        System.arraycopy(weights, 0, weightsToNetwork, 0, weightsToNetwork.length);
        network.setWeights(weightsToNetwork);
    }

    public void printNetworkWeights(){
        double[] networkWeights = network.getWeights();
        for (int i = 0; i < networkWeights.length; i++) {
            System.out.println(networkWeights[i]);
        }
    }
}

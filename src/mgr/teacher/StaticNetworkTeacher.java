package mgr.teacher;

import mgr.config.Config;
import mgr.file.utils.DataFileExtractor;
import mgr.file.utils.DataFileReader;
import mgr.input.builder.StaticInputBuilder;
import mgr.network.Network;

import java.util.ArrayList;

public class StaticNetworkTeacher implements NetworkTeacher{

    private Network network;
    private String filenameOfData;
    private DataFileReader reader;
    private DataFileExtractor extractor;
    private StaticInputBuilder builder;

    private ArrayList<Double> demandValues;
    private ArrayList<ArrayList<Double>> dataValues;

    private NetworkErrorCounter errorCounter;

    public StaticNetworkTeacher(Network net, String filename){
        this.network = net;
        this.filenameOfData = filename;
        readData();
        extractData();
        getDemandValues();
        getDataValues();
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
        this.builder = new StaticInputBuilder(dataValues);
    }

    private void initErrorCounter(){
        errorCounter = new NetworkErrorCounter(demandValues);
        errorCounter.addZeroAsFirstElement();
    }

    private void resetErrorCounterNetOutputsList(){
        errorCounter.resetNetOutputList();
        errorCounter.addZeroAsFirstElement();
    }

    @Override
    public double getErrorOfNetwork(double[] weights) throws Exception{
        if (weights != null){
            network.setWeights(weights);
        }
        resetErrorCounterNetOutputsList();
        double[] input;
        for (int t = 1; t <= Config.P; t++){
            input = builder.build(t);
            network.setNetworkInput(input);
            network.calculateOutput();
//            grad.computeAllGradients(t);
            errorCounter.addNetworkOutput(network.getCurrentOutput());
        }
        double error = errorCounter.sumarizeErrors();
        return error;
    }

    @Override
    public void setWeightsToNetwork(double[] weights){
        double[] weightsToNetwork = new double[weights.length];
        System.arraycopy(weights, 0, weightsToNetwork, 0, weightsToNetwork.length);
        network.setWeights(weightsToNetwork);
    }

    @Override
    public void printNetworkWeights(){
        double[] networkWeights = network.getWeights();
        for (int i = 0; i < networkWeights.length; i++) {
            System.out.println(networkWeights[i]);
        }
    }
}

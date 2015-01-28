package mgr.teacher;

import mgr.config.Config;
import mgr.config.ConfigBuilder;
import mgr.file.utils.DataFileExtractor;
import mgr.file.utils.DataFileReader;
import mgr.input.builder.ParamPair;
import mgr.input.builder.RecursiveInputBuilder;
import mgr.network.Network;

import java.util.ArrayList;

public class RecursiveNetworkTeacher implements NetworkTeacher{

    private Network network;
    private String filenameOfData;
    private DataFileReader reader;
    private DataFileExtractor extractor;
    private RecursiveInputBuilder builder;

    private ArrayList<Double> demandValues;
    private ArrayList<ArrayList<Double>> dataValues;

    private ArrayList<ParamPair> params;

    private NetworkErrorCounter errorCounter;

    public RecursiveNetworkTeacher(Network net, String filename, ArrayList<ParamPair> params){
        this.network = net;
        this.filenameOfData = filename;
        this.params = params;
        ConfigBuilder.selectSParam(params); // do wyjebania na zewnatrz w przyszlosci, narazie aby nie zapomniec
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
        this.builder = new RecursiveInputBuilder(dataValues, params, getFirstNetOutputsAsDemandValues());
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

    public double getDemandValueFromErrorCounter(int t){
        return errorCounter.getDemandValueAtIndex(t);
    }

    public double getNetworkOutputFromErrorCounter(int t){
        return errorCounter.getNetworkOutputAtIndex(t);
    }

    @Override
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
            network.setNetworkInput(input);
            network.calculateOutput();
//            grad.computeAllGradients(t);
            errorCounter.addNetworkOutput(network.getCurrentOutput());
        }
        double error = errorCounter.sumarizeErrors();
        return error;
    }

    public void updateWeightsInNetwork(double[] differenceToUpdate){
        double[] weightsToNetwork = new double[differenceToUpdate.length];
        System.arraycopy(differenceToUpdate, 0, weightsToNetwork, 0, weightsToNetwork.length);
        network.updateWeights(weightsToNetwork);
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

    public double[] getNetworkWeights(){
        return this.network.getWeights();
    }

    public void printActualErrorNetwork(){
        System.out.println("Aktualna wartosc bledu: " + errorCounter.getErrorValue());
    }

    public String getFilenameOfData(){
        return this.filenameOfData;
    }

    public ArrayList<ParamPair> getParams(){
        return this.params;
    }

    public Network getNetwork(){
        return network;
    }
}

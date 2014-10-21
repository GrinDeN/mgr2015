package mgr.teacher;

import mgr.network.Network;

import java.util.ArrayList;

import static mgr.config.Config.S;

public class NetworkTeacher {

    private Network network;
    private ArrayList<Double> demandValues;
    private ArrayList<Double> netOutputs;
    private ArrayList<Double> netErrors;
    private double error;

    public NetworkTeacher(Network net, ArrayList<Double> demandValues){
        this.network = net;
        initializeDemandValues(demandValues);
        initializeNetOutputs();
        initializeNetErrors();
    }

    private void initializeDemandValues(ArrayList<Double> demandValues){
        this.demandValues = new ArrayList<Double>();
        setDemandValues(demandValues);
    }

    private void setDemandValues(ArrayList<Double> demValues){
        this.demandValues.addAll(demValues);
    }

    private void initializeNetOutputs(){
        this.netOutputs = new ArrayList<Double>();
        addDemandAsFirstNetOutputs();
    }

    private void addDemandAsFirstNetOutputs(){
        for (int i = 0; i <=S-1; i++) {
            this.netOutputs.add(i, this.demandValues.get(i));
        }
    }

    private void initializeNetErrors(){
        this.netErrors = new ArrayList<Double>();
        addFirstNetErrors();
    }

    private void addFirstNetErrors(){
        for (int i = 0; i <=S-1; i++) {
            addNetError(i);
        }
    }

    public void addNetError(int index){
        double netOutput = getCurrentNetOutput(index);
        double demandValue = getCurrentDemandValue(index);
        double result = Math.pow(netOutput-demandValue, 2);
        this.netErrors.add(result);
    }

    private double getCurrentNetOutput(int current){
        return this.netOutputs.get(current);
    }

    private double getCurrentDemandValue(int current){
        return this.demandValues.get(current);
    }

    public void addCurrentNetOutput(){
        this.netOutputs.add(network.getCurrentOutput());
    }

    public double getNetOutput(int t){
        return this.netOutputs.get(t);
    }

    public double getDemandOutput(int t){
        return this.demandValues.get(t);
    }

    public ArrayList<Double> getNetOutputs(){
        return this.netOutputs;
    }

    public double getLastOutput(){
        return this.netOutputs.get(netOutputs.size()-1);
    }

    public void sumarizeNetErrors(){
        setZeroNetError();
        for (Double value : netErrors){
            this.error += value;
        }
    }

    private void setZeroNetError(){
        this.error = 0;
    }

    public void printNetOutputs(){
        for (Double value : netOutputs){
            System.out.println(String.valueOf(value));
        }
    }

    public void printDemandValues(){
        for (Double value : demandValues){
            System.out.println(String.valueOf(value));
        }
    }

    public void printErrors(){
        for (Double value : netErrors){
            System.out.println(String.valueOf(value));
        }
    }

    public void printError(){
        System.out.println("Sumaryczny błąd: " + error);
    }
}

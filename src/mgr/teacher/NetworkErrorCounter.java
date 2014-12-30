package mgr.teacher;

import java.util.ArrayList;

public class NetworkErrorCounter {

    private double error;
    private ArrayList<Double> networkOutputs;
    private ArrayList<Double> demandValues;

    public NetworkErrorCounter(ArrayList<Double> demands){
        resetErrorValue();
        initNetworkOutputsList();
        initDemandValuesList(demands);
    }

    private void resetErrorValue(){
        this.error = 0;
    }

    public void initNetworkOutputsList(){
        this.networkOutputs = new ArrayList<Double>();
//        this.networkOutputs.add(0, 0.0);
    }

    public void initDemandValuesList(ArrayList<Double> demands){
        this.demandValues = new ArrayList<Double>();
        this.demandValues.addAll(demands);
    }

    public void resetNetOutputList(){
        this.networkOutputs.clear();
    }

    public void addFirstPartOfNetworkOutputs(ArrayList<Double> firstNetOutputs){
        this.networkOutputs.addAll(firstNetOutputs);
    }

    public void addNetworkOutput(double netOutputValue){
        this.networkOutputs.add(netOutputValue);
    }

    public double sumarizeErrors() throws Exception{
        if (networkOutputs.size() != demandValues.size()){
            System.out.println("Rozmiar networkOutputs: " + networkOutputs.size());
            System.out.println("Rozmiar demandValues: " + demandValues.size());
            throw new Exception("Różny rozmiar list: demandValues oraz networkOutputs");
        }
        resetErrorValue();
        for (int elem = 0; elem < networkOutputs.size(); elem++){
            this.error += Math.pow(demandValues.get(elem)-networkOutputs.get(elem) ,2);
        }
        return getErrorValue();
    }

    public double getErrorValue(){
        return this.error;
    }

    public static void main(String[] args) throws Exception{
        ArrayList<Double> demands = new ArrayList<Double>();
        demands.add(0.0);
        demands.add(5.3);
        demands.add(3.2);
        NetworkErrorCounter errorCounter = new NetworkErrorCounter(demands);
        errorCounter.addNetworkOutput(3.3);
        errorCounter.addNetworkOutput(4.0);
        double error = errorCounter.sumarizeErrors();
        System.out.println("Blad: " + error);
    }
}

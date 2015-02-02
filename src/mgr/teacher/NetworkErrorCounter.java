package mgr.teacher;

import java.util.ArrayList;

public class NetworkErrorCounter {

    private double error;
    private ArrayList<Double> networkOutputs;
    private ArrayList<Double> demandValues;
//    private ArrayList<Double> errors;

    public NetworkErrorCounter(ArrayList<Double> demands){
        resetErrorValue();
//        this.errors = new ArrayList<Double>();
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

    public void addZeroAsFirstElement(){
        this.networkOutputs.add(0, 0.0);
    }

    public void addNetworkOutput(double netOutputValue){
        this.networkOutputs.add(netOutputValue);
    }

    public double getNetworkOutputAtIndex(int index){
        return this.networkOutputs.get(index);
    }

    public double getDemandValueAtIndex(int index){
        return this.demandValues.get(index);
    }

    public double sumarizeErrors() throws Exception{
        if (networkOutputs.size() != demandValues.size()){
            System.out.println("Rozmiar networkOutputs: " + networkOutputs.size());
            System.out.println("Rozmiar demandValues: " + demandValues.size());
            throw new Exception("Różny rozmiar list: demandValues oraz networkOutputs");
        }
        resetErrorValue();
        for (int elem = 1; elem < networkOutputs.size(); elem++){
            this.error += Math.pow(demandValues.get(elem)-networkOutputs.get(elem), 2);
        }
        return getErrorValue();
    }

    public double getErrorValue(){
        return this.error;
    }

//    public double getErrorAtIndex(int index){
//        return this.errors.get(index);
//    }

}

package mgr.teacher;

import mgr.network.Network;

import java.util.ArrayList;

import static mgr.config.Config.S;

public class NetworkTeacher {

    private Network network;
    private ArrayList<Double> demandValues;
    private ArrayList<Double> netOutputs;

    public NetworkTeacher(Network net){
        this.network = net;
        initializeDemandValues();
        initializeNetOutputs();
    }

    private void initializeDemandValues(){
        this.demandValues = new ArrayList<Double>();
    }

    private void initializeNetOutputs(){
        this.netOutputs = new ArrayList<Double>();
    }

    public void addNetOutput(double output){
        this.netOutputs.add(output);
    }

    public void addDemandAsFirstNetOutputs(){
        for (int i = 0; i <=S-1; i++) {
            this.netOutputs.add(i, this.demandValues.get(i));
        }
    }

    public double getNetOutput(int t){
        return this.netOutputs.get(t);
    }

    public double getDemandOutput(int t){
        return this.demandValues.get(t);
    }

    public void setDemandValues(ArrayList<Double> demValues){
        this.demandValues.addAll(demValues);
    }

    public ArrayList<Double> getNetOutputs(){
        return this.netOutputs;
    }

    public double getLastOutput(){
        return this.netOutputs.get(netOutputs.size()-1);
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
}

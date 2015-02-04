package mgr.teacher;

import mgr.input.builder.ParamPair;
import mgr.network.Network;

import java.util.ArrayList;

public class Verifier {

    private RecursiveNetworkTeacher netTeacher;
    private String dataFilename;

    public Verifier(Network network, String filename, ArrayList<ParamPair> params){
        this.dataFilename = filename;
        this.netTeacher = new RecursiveNetworkTeacher(network, filename, params);
    }

    public void verify(double[] netWeights) throws Exception{
        double sumarizedVerifyError = netTeacher.getErrorOfNetwork(netWeights);
        System.out.println("Sumaryczny blad dla zadanych wag dla danych z pliku " + dataFilename + ": " + sumarizedVerifyError);
    }

    public void printActualNetworkWeights(){
        netTeacher.printNetworkWeights();
    }

}

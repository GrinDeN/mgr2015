package mgr.teacher;

import mgr.input.builder.ParamPair;
import mgr.network.Network;

import java.util.ArrayList;

public class Verifier {

    private NetworkTeacher netTeacher;
    private String dataFilename;

    public Verifier(Network network, String filename, ArrayList<ParamPair> params){
        this.dataFilename = filename;
        if (params != null){
            this.netTeacher = new RecursiveNetworkTeacher(network, filename, params);
        } else{
            this.netTeacher = new StaticNetworkTeacher(network, filename);
        }
    }

    public void verify(double[] netWeights) throws Exception{
        double sumarizedVerifyError = netTeacher.getErrorOfNetwork(netWeights, false);
        System.out.println("Sumaryczny blad dla zadanych wag dla danych z pliku " + dataFilename + ": " + sumarizedVerifyError);
    }
}

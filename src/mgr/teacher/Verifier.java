package mgr.teacher;

import mgr.input.builder.ParamPair;
import mgr.network.Network;

import java.util.ArrayList;

public class Verifier {

    private RecursiveNetworkTeacher netTeacher;

    public Verifier(Network network, String filename, ArrayList<ParamPair> params){
        this.netTeacher = new RecursiveNetworkTeacher(network, filename, params);
    }

    public void verify() throws Exception{
        double sumarizedVerifyError = netTeacher.getErrorOfNetwork(null);
        System.out.println("Sumaryczny blad po danych werfyikacyjnych: " + sumarizedVerifyError);
    }

    public void printActualNetworkWeights(){
        netTeacher.printNetworkWeights();
    }

}

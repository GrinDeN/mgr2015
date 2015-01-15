package mgr.algorithm;

import mgr.input.builder.ParamPair;
import mgr.network.Network;
import mgr.teacher.NetworkTeacher;
import mgr.teacher.RecursiveNetworkTeacher;
import mgr.teacher.StaticNetworkTeacher;

import java.util.ArrayList;

import static mgr.config.Config.STATIC_NET;

public class AlgTester {

    private SwarmEnum swarmAlgEnum;
    private Network neuralNet;
    private SwarmAlgorithm swarmAlg;

    public AlgTester(){
    }

    public void test(Network net, SwarmEnum swarmAlgorithmEnum, String filename, ArrayList<ParamPair> dataParams) throws Exception{
        this.neuralNet = net;
        this.swarmAlgEnum = swarmAlgorithmEnum;
        ArrayList<ParamPair> params = null;
        if (dataParams != null){
            params = new ArrayList<ParamPair>();
            params.addAll(dataParams);
        }
        NetworkTeacher netTeacher;
        if (STATIC_NET == false){
            netTeacher = new RecursiveNetworkTeacher(neuralNet, filename, params);
        } else if (STATIC_NET == true){
            netTeacher = new StaticNetworkTeacher(neuralNet, filename);
        }
        this.swarmAlg = SwarmAlgFactory.getSwarmAlgorithm(swarmAlgEnum, netTeacher);
        long start = System.nanoTime();
        this.swarmAlg.getMinimum();
        long end = System.nanoTime();
        double difference = (end - start)/1e6;
        System.out.println("Czas wykonania " + swarmAlg.getName() + " wynosi: " + difference + " ms.");
    }
}

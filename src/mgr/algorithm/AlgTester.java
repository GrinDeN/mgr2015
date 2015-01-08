package mgr.algorithm;

import mgr.input.builder.ParamPair;
import mgr.network.Network;
import mgr.teacher.NetworkTeacher;

import java.util.ArrayList;

public class AlgTester {

    private SwarmEnum swarmAlgEnum;
    private Network neuralNet;
    private SwarmAlgorithm swarmAlg;

    public AlgTester(){
    }

    public void test(Network net, SwarmEnum swarmAlgorithmEnum, String filename, ArrayList<ParamPair> dataParams) throws Exception{
        this.neuralNet = net;
        this.swarmAlgEnum = swarmAlgorithmEnum;
        ArrayList<ParamPair> params = new ArrayList<ParamPair>();
        params.addAll(dataParams);
        NetworkTeacher netTeacher = new NetworkTeacher(neuralNet, filename, params);
        this.swarmAlg = SwarmAlgFactory.getSwarmAlgorithm(swarmAlgEnum, netTeacher);
        long start = System.nanoTime();
        this.swarmAlg.getMinimum();
        long end = System.nanoTime();
        double difference = (end - start)/1e6;
        System.out.println("Czas wykonania " + swarmAlg.getName() + " wynosi: " + difference + " ms.");
    }
}

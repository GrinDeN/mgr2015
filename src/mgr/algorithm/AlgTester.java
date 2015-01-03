package mgr.algorithm;

import mgr.input.builder.ParamPair;
import mgr.network.Network;
import mgr.teacher.NetworkTeacher;

import java.util.ArrayList;

public class AlgTester {

    private SwarmEnum swarmAlgEnum;

    public AlgTester(SwarmEnum swarmAlg){
        this.swarmAlgEnum = swarmAlg;
    }

    public void test(String filename, ArrayList<ParamPair> dataParams) throws Exception{
        Network net = new Network();
        ArrayList<ParamPair> params = new ArrayList<ParamPair>();
        params.addAll(dataParams);
        NetworkTeacher netTeacher = new NetworkTeacher(net, filename, params);
        SwarmAlgorithm swarmAlg = SwarmAlgFactory.getSwarmAlgorithm(swarmAlgEnum, netTeacher);
        long start = System.nanoTime();
        swarmAlg.getMinimum();
        long end = System.nanoTime();
        double difference = (end - start)/1e6;
        System.out.println("Czas wykonania " + swarmAlg.getName() + " wynosi: " + difference + " ms.");
    }
}

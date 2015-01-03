package mgr.algorithm.grey.wolf;

import mgr.input.builder.ParamPair;
import mgr.network.Network;
import mgr.teacher.NetworkTeacher;

import java.util.ArrayList;

public class WolfTest {
    public static void main(String[] args) throws Exception{
        Network net = new Network();
        String filename = "dane_spr_grad.txt";
        ArrayList<ParamPair> params = new ArrayList<ParamPair>();
        params.add(new ParamPair(1, 2));
        params.add(new ParamPair(1));
        NetworkTeacher netTeacher = new NetworkTeacher(net, filename, params);
        WolfsPack wolfsPack = new WolfsPack(netTeacher, 100);
        long start = System.nanoTime();
        wolfsPack.getMinimum();
        long end = System.nanoTime();
        double difference = (end - start)/1e6;
        System.out.println("Czas wykonania wynosi: " + difference + " ms.");
    }
}

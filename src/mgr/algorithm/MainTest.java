package mgr.algorithm;

import mgr.input.builder.ParamPair;
import mgr.network.Network;
import mgr.teacher.Verifier;

import java.util.ArrayList;

import static mgr.config.Config.STATIC_NET;

public class MainTest {
    // TODO
    // weryfikator ++++++
    // dokonczenie podpiecia pozostalych algorytmow pod netTeacher ++++++
    // zrobienie statycznej sieci (modyfikacja inputBuildera zeby bral probki w tym samym momencie wszystkie)
    //
    public static void main(String[] args) throws Exception{
        Network net = new Network();
        String dataFilename;
        SwarmEnum swarmAlg = SwarmEnum.PSO;
        AlgTester algTester = new AlgTester();
        if (STATIC_NET == false){
            dataFilename = "daneucz2000.txt";
            ArrayList<ParamPair> params = new ArrayList<ParamPair>();
            params.add(new ParamPair(1, 2));
            params.add(new ParamPair(2));

            algTester.test(net, swarmAlg, dataFilename, params);

            String verifyFilename = "danewer2000.txt";
            Verifier verifier = new Verifier(net, verifyFilename, params);
            verifier.verify();
            verifier.printActualNetworkWeights();
        } else if(STATIC_NET == true){
            dataFilename = "daneucz8000_bezprzedostatniej.txt";
            algTester.test(net, swarmAlg, dataFilename, null);
        }
    }
}

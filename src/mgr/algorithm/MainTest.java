package mgr.algorithm;

import mgr.input.builder.ParamPair;
import mgr.network.Network;
import mgr.teacher.Verifier;

import java.util.ArrayList;

import static mgr.config.Config.STATIC_NET;
import static mgr.config.Config.VERIFIER_ONLY;

public class MainTest {
    // TODO
    // weryfikator ++++++
    // dokonczenie podpiecia pozostalych algorytmow pod netTeacher ++++++
    // zrobienie statycznej sieci (modyfikacja inputBuildera zeby bral probki w tym samym momencie wszystkie)
    //
    public static void main(String[] args) throws Exception{
        Network net = new Network();
        String dataFilename;
        SwarmEnum swarmAlg = SwarmEnum.WOLF;
        AlgTester algTester = new AlgTester();
        if (STATIC_NET == false){
            ArrayList<ParamPair> params = new ArrayList<ParamPair>();
            params.add(new ParamPair(1, 2));
            params.add(new ParamPair(2));
            if (VERIFIER_ONLY == true){
                String verifyFilename = "daneucz2000.txt";
//                String verifyFilename = "daneucz10000.dan";
                Verifier verifier = new Verifier(net, verifyFilename, params);
                double[] weights = new double[]{
                        -3.2181787647e-001,-3.5737379721e-001,2.7772908156e-002,-1.5572002763e+000,-1.4657592656e+000,
                        -1.1023696276e-001,-2.8193248378e-001,6.9581594104e-002,-6.9137171223e-001,-9.0962342563e-001,
                        4.0155347414e-001,6.6509761989e-001,4.9634252075e-001,2.3523385062e+000,5.8347418768e-001,
                        -1.2614755834e-001,-4.3819825381e-001,-2.8556516865e-001,-1.8943895167e+000,-1.5973008135e-003,
                        1.6202095203e+000,2.2885921501e-003,-2.2482722367e-001,1.8452237884e+000,-2.7607152439e-001,
                        4.2701604938e-002,-5.7653795497e-001,-2.3300138934e-001,1.3075037944e-001,9.5119531040e-001,
                        1.0482189951e-001,4.7254848533e-001,2.1094148444e-001,-2.8458327619e-001,9.0122034475e-001,
                        -2.6835398204e-002,4.7599170038e-001,2.7975785071e-001,-1.1448674334e+000,2.8443349278e-001,
                        -1.2332223099e-001,-7.9951494213e-002,-7.1860088324e-001,-4.2651879281e-001,-6.6088200389e-001,
                        1.1902731638e-001,3.7543437453e-001,-1.3343107251e-001,1.5222762463e-001,-1.5492365711e-001,
                        2.1563850284e+000,-3.4302900162e-002,4.1170242774e-001,-2.3029914583e+000,-1.3616356199e-001,
                        -3.5490594295e-001,-9.4140741668e-001,-3.6894637754e-001,-1.0464337572e+000,-9.9115487776e-001,
                        -2.4885596514e-001,-8.0186587887e-001,6.8076258255e-001,-1.2147056170e+000,-1.0401405314e+000,1.1426158055e+000,4.1104736156e-001,-9.6851986827e-001,8.2061157781e-001,-6.7292777054e-001,2.7183340039e-001,-8.1510420794e-001,-3.9481304003e-001
                };
                verifier.verify(weights);
                //dane weryfikacyjne
                String verifyFilename1 = "danewer2000.txt";
//                String verifyFilename1 = "danewer10000.dan";
                Verifier verifier1 = new Verifier(net, verifyFilename1, params);
                verifier1.verify(weights);
            } else {
                dataFilename = "daneucz2000.txt";
//                dataFilename = "daneucz10000.dan";
                algTester.testSwarm(net, swarmAlg, dataFilename, params);
//                algTester.testBFGS();
            }
        } else if(STATIC_NET == true){
            dataFilename = "daneucz8000_bezprzedostatniej.txt";
            algTester.testSwarm(net, swarmAlg, dataFilename, null);
        }
    }
}

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
                Verifier verifier = new Verifier(net, verifyFilename, params);
                double[] weights = new double[]{
                        -2.0766988038e-001, -2.9211052234e-001, -1.4343768185e-001, 1.2768444812e+000, -2.6452831740e+000,
                        -4.6460210717e-001, -4.8387132470e-002, -7.7463461727e-004, 3.8637754832e-001, 6.0641828550e-001,
                        1.7870591578e-001, 3.0467394709e-001, 2.0257681487e-001, 2.7347873972e+000, -7.0192932931e-001,
                        -1.4765048394e+000, -8.4095405506e-003, 3.8480876437e-002, -8.3978457086e-001, -9.2994975184e-001,
                        -6.2886978149e-002, -1.5826006362e-001, -1.0623703291e-001, 2.1094811480e+000, -3.2918749338e-001,
                        -5.3440469784e-001, 2.5439469108e-001, -5.2446686037e-001, -1.0685449478e+000, -3.1972672247e+000,
                        -4.7354896237e-001, -4.0182976866e-001, -4.4425935555e-001, -4.7380834830e+000, 1.1165519642e+000,
                        1.0613739141e-001, 6.7123360369e-001, 1.4388766168e+000, 1.6126059009e+000, -5.1278329368e-001, -9.7611300821e-001, -4.2818611405e-001, 8.2089798688e-001
                };
                verifier.verify(weights);
                //dane weryfikacyjne
                String verifyFilename1 = "danewer2000.txt";
                Verifier verifier1 = new Verifier(net, verifyFilename1, params);
                verifier1.verify(weights);
            } else {
                dataFilename = "daneucz2000.txt";
                algTester.testSwarm(net, swarmAlg, dataFilename, params);
//            algTester.testBFGS();
            }
        } else if(STATIC_NET == true){
            dataFilename = "daneucz8000_bezprzedostatniej.txt";
            algTester.testSwarm(net, swarmAlg, dataFilename, null);
        }
    }
}

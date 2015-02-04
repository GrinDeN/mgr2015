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
                        -2.7347307373e-001,2.1268753604e-001,-2.6880524352e-001,8.5127716953e-001,-1.3752150189e+000,
                        2.4657592067e+000,2.9889489096e+000,-1.7337483222e+000,5.1236975939e+000,9.7561685414e-002,
                        3.2527738611e+000,1.7665772198e+000,-2.7239210921e-001,-7.5763137128e-001,5.3823539252e-001,
                        -6.5529103568e-001,-7.5165877307e-001,1.0833727945e+000,-1.6520712635e+000,-2.4136268644e-001,
                        1.3890196718e+000,-1.6255790520e+000,1.5218888960e+000,-3.2348991203e+000,-1.0495792330e+000,
                        -4.8105826230e-001,-4.7835041827e-001,6.2642636264e-001,-1.7990325639e+000,7.0259732392e-001,
                        1.2623678377e+000,-1.5139586803e+000,1.1841097819e+000,-3.3677520210e+000,-5.5395481082e-001,
                        1.6424714051e-001,-1.0900435911e+000,1.0470099030e-001,-6.7596572353e-001,9.9526383602e-001,1.5190695402e+000,-1.6070041058e+000,-1.6103177250e+000,
                        /*-5.246744848207425E-6, -1.2853480746665735E-6, 5.772479827898029E-5, 1.9107429914368344E-10, -1.3796620517803244E-8,
                        -1.5624478748700279E-6, -0.007749787293324875, -6.474828924653825E-6, 8.09891882212134E-6, -1.858706480491673E-5,
                        3.082213900997491E-8, -1.2444353121232733E-10, 3.2996936186744117E-6, -8.494447313602243E-5, -2.370816787960111E-6,
                        -7.289120473262532E-5, -5.51266050929801E-7, 8.110344179385567E-6, -1.3804835681169483E-7, 2.46714120218375E-7,
                        -2.0312575499892837E-6, -6.600107459227255E-6, 2.231633943377825E-7, 2.987459720603662E-6, 2.0261059589080825E-4,
                        1.0059193258155338E-5, 7.114725765726525E-7, -1.6864865188793706E-6, -7.358290752556651E-7, 5.327131936367493E-5,
                        2.2344769732447147E-9, -0.0035418256273236667, -1.901974130297548, -5.940453790637939E-8, 9.685569979835993E-8,
                        -1.7838349051373462E-6, -1.309327237156958E-8, 8.749374817897512E-5, 1.8599529449128913E-8, 1.1150349670871578E-11, 1.996983911573199E-6, -1.0718256283246422E-7, -0.7433893882438095*/
                };
                verifier.verify(weights);
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

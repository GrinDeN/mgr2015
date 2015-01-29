package mgr.gradient;

import mgr.math.utils.MatrixUtils;
import mgr.network.Network;
import mgr.teacher.RecursiveNetworkTeacher;

import java.util.ArrayList;

import static mgr.config.Config.*;

public class Gradient {

    private double[][][] dZdW1;
    private double[][] dZdW2;
    private double[][][] dVdW1;
    private double[][] dVdW2;
    private double[][][] dYdW1;
    private double[][] dYdW2;
    private double[][][] dEdW1;
    private double[][] dEdW2;

    private ArrayList<Double> gW;
    private ArrayList<Double> previousgW;
    private ArrayList<Double> pWk;

    private HiddenGradient hiddenGradient;
    private OutputGradient outputGradient;

    private Network network;
    private RecursiveNetworkTeacher netTeacher;

    public Gradient(RecursiveNetworkTeacher teacher){
        this.netTeacher = teacher;
        this.network = teacher.getNetwork();

        this.hiddenGradient = new HiddenGradient(network, teacher, this);
        this.outputGradient = new OutputGradient(network, teacher, this);

        this.gW = new ArrayList<Double>();
        this.previousgW = new ArrayList<Double>();
        this.pWk = new ArrayList<Double>();

        initAllArrays();
        initZeroGradient(S);
    }

    private void initAllArrays(){
        this.dZdW1 = new double[HIDD_NEURONS+1][HIDD_NEURONS+1][INPUT_SIZE];
        this.dZdW2 = new double[HIDD_NEURONS+1][HIDD_NEURONS+1];

        this.dVdW1 = new double[HIDD_NEURONS+1][HIDD_NEURONS+1][INPUT_SIZE];
        this.dVdW2 = new double[HIDD_NEURONS+1][HIDD_NEURONS+1];

        this.dYdW1 = new double[P+1][HIDD_NEURONS+1][INPUT_SIZE];
        this.dYdW2 = new double[P+1][HIDD_NEURONS+1];

        this.dEdW1 = new double[P+1][HIDD_NEURONS+1][INPUT_SIZE];
        this.dEdW2 = new double[P+1][HIDD_NEURONS+1];
    }

    private void initZeroGradient(int t){
        for (int t0=1; t0<=S-1; t0++){
            for (int i=0; i<=HIDD_NEURONS; i++){
                this.dYdW2[t-t0][i] = 0;
                for (int j=0; j<INPUT_SIZE; j++){
                    this.dYdW1[t-t0][i][j] = 0;
                }
            }
        }
    }

    private void computeAlldZdW2(int t){
        for (int i=0; i<=K; i++){
            for (int n=1; n<=K; n++){
                this.dZdW2[n][i] = outputGradient.compute_dZdW2(t, n, i);
            }
        }
    }

    private void computeAlldVdW2(){
        for (int i=0; i<=K; i++){
            for (int n=1; n<=K; n++){
                this.dVdW2[n][i] = outputGradient.compute_dVdW2(n, i);
            }
        }
    }

    private void computeAlldYdW2(int t){
        for (int i=0; i<=K; i++){
            this.dYdW2[t][i] = outputGradient.compute_dYdW2(i);
        }
    }

    private void computeAlldEdW2(int t){
        for (int i=0; i<=K; i++){
            this.dEdW2[t][i] = outputGradient.compute_dEdW2(t, i);
        }
    }

    private void computeAlldZdW1(int t){
        for (int n=1; n<=K; n++){
            for (int i=1; i<=K; i++){
                for (int j=0; j<INPUT_SIZE; j++){
                    this.dZdW1[n][i][j] = hiddenGradient.compute_dZdW1(t, n, i, j);
                }
            }
        }
    }

    private void computeAlldVdW1(){
        for (int n=1; n<=K; n++){
            for (int i=1; i<=K; i++){
                for (int j=0; j<INPUT_SIZE; j++){
                    this.dVdW1[n][i][j] = hiddenGradient.compute_dVdW1(n, i, j);
                }
            }
        }
    }

    private void computeAlldYdW1(int t){
        for (int i=1; i<=K; i++){
            for (int j=0; j<INPUT_SIZE; j++){
                this.dYdW1[t][i][j] = hiddenGradient.compute_dYdW1(i, j);
            }
        }
    }

    private void computeAlldEdW1(int t){
        for (int i=1; i<=K; i++){
            for (int j=0; j<INPUT_SIZE; j++){
                this.dEdW1[t][i][j] = hiddenGradient.compute_dEdW1(t, i, j);
            }
        }
    }

    protected double getElementdEdW1AtIndex(int t, int i, int j){
        return this.dEdW1[t][i][j];
    }

    protected double getElementdEdW2AtIndex(int t, int i){
        return this.dEdW2[t][i];
    }

    protected double getElementdYdW1AtIndex(int t, int i, int j){
        return this.dYdW1[t][i][j];
    }

    protected double getElementdYdW2AtIndex(int t, int i){
        return this.dYdW2[t][i];
    }

    protected double getCurrent_dZdW1(int n, int i, int j){
//        System.out.println("i: "+i);
//        System.out.println("j: "+j);
//        System.out.println("n: "+n);
        return this.dZdW1[n][i][j];
    }

    protected double getCurrent_dZdW2(int n, int i){
        return this.dZdW2[n][i];
    }

    protected double getCurrent_dVdW1(int n, int i, int j){
        return this.dVdW1[n][i][j];
    }

    protected double getCurrent_dVdW2(int n, int i){
        return this.dVdW2[n][i];
    }

    public void computeAllGradients(int t){
        computeAlldZdW2(t);
        computeAlldVdW2();
        computeAlldYdW2(t);
        computeAlldEdW2(t);

        computeAlldZdW1(t);
        computeAlldVdW1();
        computeAlldYdW1(t);
        computeAlldEdW1(t);
    }

    public void setCurrent_gW(){
        makeCopyOfgWAsPrevgW();
        this.gW.clear();
        double[][] sum_dEdW1 = new double[K+1][INPUT_SIZE];
        double[] sum_dEdW2 = new double[K+1];
        for (int i = 1; i<=K; i++){
            for (int j = 0; j<INPUT_SIZE; j++){
                for (int t = S; t<P; t++){
                    sum_dEdW1[i][j] += getElementdEdW1AtIndex(t, i, j);
                }
            }
        }
        for (int i = 0; i<=K ; i++){
            for (int t = S; t<=P; t++){
                sum_dEdW2[i] += getElementdEdW2AtIndex(t, i);
            }
        }
        for (int i = 1; i<=K; i++){
            for (int j = 0; j<INPUT_SIZE; j++){
                gW.add(sum_dEdW1[i][j]);
            }
        }
        for (int i = 0; i<=K; i++){
            gW.add(sum_dEdW2[i]);
        }
    }

    private void makeCopyOfgWAsPrevgW(){
        this.previousgW.addAll(this.gW);
    }

    public ArrayList<Double> getgW(){
        return this.gW;
    }

    public ArrayList<Double> getPreviousgW(){
        return this.previousgW;
    }

    public ArrayList<Double> getpWk(){
        return this.pWk;
    }

    public void setNewPwk_BFGS(double[][] vk){
        Double[] gwArray = new Double[gW.size()];
        gwArray = gW.toArray(gwArray);
        double[] HWgW = MatrixUtils.multiplyMatrixByPionowyVec(vk, gwArray);
        for (int i=0; i<pWk.size(); i++){
            this.pWk.add(i,-1*HWgW[i]);
        }
    }

    public Network getNetwork(){
        return network;
    }
}

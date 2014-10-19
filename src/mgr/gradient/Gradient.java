package mgr.gradient;

import mgr.network.Network;
import mgr.teacher.NetworkTeacher;

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

    private HiddenGradient hiddenGradient;
    private OutputGradient outputGradient;

    private Network network;
    private NetworkTeacher netTeacher;

    public Gradient(Network net, NetworkTeacher teacher){
        this.network = net;
        this.netTeacher = teacher;

        this.hiddenGradient = new HiddenGradient(net, teacher, this);
        this.outputGradient = new OutputGradient(net, teacher, this);

        this.gW = new ArrayList<Double>();

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

    protected double getCurrent_dZdW1(int i, int j, int n){
//        System.out.println("i: "+i);
//        System.out.println("j: "+j);
//        System.out.println("n: "+n);
        return this.dZdW1[n][i][j];
    }

    protected double getCurrent_dZdW2(int i, int n){
        return this.dZdW2[i][n];
    }

    protected double getCurrent_dVdW1(int i, int j, int n){
        return this.dVdW1[n][i][j];
    }

    protected double getCurrent_dVdW2(int i, int n){
        return this.dVdW2[i][n];
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
        this.gW.clear();
        double[][] sum_dEdW1 = new double[K+1][INPUT_SIZE+1];
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
        for (int i = 1; i<=K; i++){
            gW.add(sum_dEdW2[i]);
        }
    }
}

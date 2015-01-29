package mgr.gradient;

import mgr.math.utils.MatrixUtils;
import mgr.network.Network;

import java.util.ArrayList;

import static mgr.config.Config.NUM_OF_WEIGHTS;

public class BFGS {

    private Network net;
    private Gradient grad;

    private double[][] Vk;
    private double[][] Vk1;

    private double[] sk;
    private double[] rk;

    public BFGS(Gradient grad){
        this.net = grad.getNetwork();
        this.grad = grad;

        this.Vk = new double[NUM_OF_WEIGHTS][NUM_OF_WEIGHTS];
        this.Vk1 = new double[NUM_OF_WEIGHTS][NUM_OF_WEIGHTS];

        this.sk = new double[NUM_OF_WEIGHTS];
        this.rk = new double[NUM_OF_WEIGHTS];
    }

    public void initializeSk(){
//        Dendrite[] actualConns = this.net.getConnections();
//		Dendrite[] previousConns = this.net.getPrevConnections();
        double[] actualWeights = net.getWeights();
        double[] x;// = new double[NUM_OF_WEIGHTS];
        x = net.getPreviousWeights();
        if (actualWeights.length == sk.length){
            for (int i=0; i<sk.length; i++){
                this.sk[i] = actualWeights[i] - x[i];
//				System.out.println("sk["+i+"]: " + sk[i]);
            }
        } else {
            System.out.println("CHUJOWE ROZMIARY");
        }
    }

    public void initializeRk(){
        ArrayList<Double> gWList = this.grad.getgW();
        ArrayList<Double> prevgWList = this.grad.getPreviousgW();
        Double[] actualGradW = gWList.toArray(new Double[gWList.size()]);
        Double[] previousGradW = prevgWList.toArray(new Double[prevgWList.size()]);
        if (actualGradW.length == rk.length){
            for (int i=0; i<rk.length; i++){
//				System.out.println("ActualGrad["+i+"]: " + actualGradW[i]);
//				System.out.println("previousGrad["+i+"]: " + previousGradW[i]);
                this.rk[i] = actualGradW[i] - previousGradW[i];
//				System.out.println("rk["+i+"]: " + rk[i]);
            }
        } else {
            System.out.println("CHUJOWE ROZMIARY");
        }
    }

    public void initializeVkForEyeMatrix(){
        for (int i=0; i<this.Vk.length; i++){
            for (int j=0; j<this.Vk.length; j++){
                this.Vk[i][j] = (i == j) ? 1.0 : 0.0;
            }
        }
    }

    public double getMianownik(){
        return MatrixUtils.multiplyTwoVectors(sk, rk);
    }

    public double[][] getLastElement(){
        double result[][]; //= new double[this.Vk.length][this.Vk.length];
        double result1[][]; //= new double[this.Vk.length][this.Vk.length];
        double resultAdd[][]; //= new double[this.Vk.length][this.Vk.length];
        double tmp[][]; //= new double[this.Vk.length][this.Vk.length];
        tmp = MatrixUtils.multiplyVectorsToMatrix(sk, rk);
        result = MatrixUtils.MultiplyTwoMatricies(tmp, Vk1);

        tmp = MatrixUtils.multiplyVectorsToMatrix(rk, sk);
        result1 = MatrixUtils.MultiplyTwoMatricies(Vk1, tmp);

        resultAdd = MatrixUtils.addTwoMatricies(result, result1);
        resultAdd = MatrixUtils.divideMatrixByScalar(resultAdd, getMianownik());
        return resultAdd;
    }

    public double[][] getElementAfterBrackets(){
        double[][] result; //= new double[this.Vk.length][this.Vk.length];
        result = MatrixUtils.multiplyVectorsToMatrix(sk, sk);
        result = MatrixUtils.divideMatrixByScalar(result, getMianownik());
        return result;
    }

    public double getElementInBrackets(){
        double result = 0;
        double[] tmp = MatrixUtils.multiplyVecPozMatrix(rk, Vk1);
        double tmpLicznik = MatrixUtils.multiplyTwoVectors(tmp, rk);
        result = tmpLicznik / getMianownik();
        result = result + 1;
        return result;
    }

    public void makeVMatrix(){
        double[][] tmp;// = new double[this.Vk.length][this.Vk.length];
        double[][] result;// = new double[this.Vk.length][this.Vk.length];
        tmp = MatrixUtils.MultiplyMatrixByScalar(getElementAfterBrackets(), getElementInBrackets());
        result = MatrixUtils.addTwoMatricies(this.Vk1, tmp);
        result = MatrixUtils.substractTwoMatricies(result, getLastElement());
        for (int i=0; i<this.Vk.length; i++){
            for (int j=0; j<this.Vk.length; j++){
                this.Vk[i][j] = result[i][j];
            }
        }
    }

    public void setVkAsVk1(){
        for (int i=0; i<this.Vk.length; i++){
            for (int j=0; j<this.Vk.length; j++){
                this.Vk1[i][j] = this.Vk[i][j];
            }
        }
    }

    //dla iteracji k
    //gdzies tu blad - zwraca NaN xDD
    public double[][] getVkMatrix(int k){
        if (k==0){
            initializeVkForEyeMatrix();
//			this.rk = this.grad.getgW();
//			for (int i=0; i<sk.length; i++){
//				this.sk[i] = this.net.getConnections()[i].getWeight();
//			}
            setVkAsVk1();
        }
//        else if (k>0 ){
//            initializeRk();
//            initializeSk();
//            initializeVkForEyeMatrix();
//            setVkAsVk1();
//        }
        else {
            initializeRk();
//			for (int i=0; i<rk.length; i++){
//				System.out.println("rk["+i+"]: "+this.rk[i]);
//			}
            initializeSk();
//			for (int i=0; i<rk.length; i++){
//				System.out.println("sk["+i+"]: "+this.sk[i]);
//			}
            makeVMatrix();
            setVkAsVk1();
        }
		return getVk();
    }

    private double[][] getVk(){
        return this.Vk;
    }
}

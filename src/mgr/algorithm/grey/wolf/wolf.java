package mgr.algorithm.grey.wolf;

public class wolf {

    private double[] positions;
    private int dim;
    private double score;

    public wolf(int dimension){
        this.dim = dimension;
        this.positions = new double[dim];
        this.score = Double.POSITIVE_INFINITY;
        initializePositionsByZero();
    }

    private void initializePositionsByZero(){
        for (int i = 0; i < this.positions.length; i++) {
            this.positions[i] = 0;
        }
    }

    public double getScore(){
        return score;
    }

    public void setScore(double score){
        this.score = score;
    }

    public void setPositions(double[] newPositions){
        System.arraycopy(newPositions, 0, this.positions, 0, newPositions.length);
    }

    public double getPositionAtIndex(int index){
        return this.positions[index];
    }

    public double[] getPositions(){
        return this.positions;
    }

}

package mgr.input.builder;

/**
 * Created by Lukasz on 2014-10-05.
 */
public class ParamPair {

    private int first;
    private int second;

    public ParamPair(int firstValue, int secValue){
        this.first = firstValue;
        this.second = secValue;
    }

    public ParamPair(int secValue){
        this(1, secValue);
    }

    public int getFirstValue(){
        return this.first;
    }

    public int getSecondValue(){
        return this.second;
    }

}

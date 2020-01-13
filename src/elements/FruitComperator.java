package elements;

import java.util.Comparator;

public class FruitComperator implements Comparator<Fruit> {
    public FruitComperator() {;}
    @Override
    public int compare(Fruit o1, Fruit o2) {
        int dp =(int)( o2.getValue() - o1.getValue());
        return dp;
    }


}

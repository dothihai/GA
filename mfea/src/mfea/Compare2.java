package mfea;
import java.util.Comparator;

public class Compare2 implements Comparator<Individual> {
    @Override
    public int compare(Individual first, Individual second) {
       if (first.getFitness(1)<second.getFitness(1)) return 1;
       if (first.getFitness(1)>second.getFitness(1)) return -1;
       return 0;
    }
}
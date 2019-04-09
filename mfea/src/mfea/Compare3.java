package mfea;
import java.util.Comparator;

public class Compare3 implements Comparator<Individual> {
    @Override
    public int compare(Individual first, Individual second) {
       if (first.getFitness(2)<second.getFitness(2)) return 1;
       if (first.getFitness(2)>second.getFitness(2)) return -1;
       return 0;
    }
}
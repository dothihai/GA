package mfea;
import java.util.Comparator;

public class Compare1 implements Comparator<Individual> {
    @Override
    public int compare(Individual first, Individual second) {
       if (first.getFitness(0)<second.getFitness(0)) return 1;
       if (first.getFitness(0)>second.getFitness(0)) return -1;
       return 0;
    }
}

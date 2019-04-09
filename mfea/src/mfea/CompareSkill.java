package mfea;

import java.util.Comparator;

public class CompareSkill implements Comparator<Individual> {
    @Override
    public int compare(Individual first, Individual second) {
       return first.scalar_fitness-second.scalar_fitness;
    }
}

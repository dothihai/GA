package mfea;

import java.util.List;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.Arrays;

public class Individual {
    private byte[] genes = new byte[22];
    // Cache
    private double[] fitness = new double[] {-10000,-10000,-10000};
    int[] rank = new int[3];
    int skill_factor; 
    int scalar_fitness; 
    // Create a random individual
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
        for (int i = 0; i < 3; i++) {
			calFitness(i);
		}
    }
    
    public byte getGene(int index) {
        return genes[index];
    }

    public void setGene(byte[] gene) {
        genes = gene;
    }

    /* Public methods */
    public int size() {
        return genes.length;
	}

	public double getFitness(int i) {
		return fitness[i];
	}

	public void calFitness(int task) {
	    double x;
		if (task == 0) {
			double y = 0;
			for (int i = 0; i < 22; i++) {
				y= y*2+genes[i];
			}
			x = -1.0 + (y * 2.0) / ((1 << 22) - 1);
			fitness[0] = x * Math.sin(10 * Math.PI * x) + 1;
		}
		if (task == 1) {
			double y = 0;
			for (int i = 0; i < 22; i++) {
				y = y*2+genes[i];
			}
			x = -1.0 + (y * 2.0) / ((1 << 22) - 1);
			fitness[1] = x * Math.cos(10 * Math.PI * x);
		}
		if (task == 2) {
			double y = 0;
			for (int i = 0; i < 22; i++) {
				y = y*2+genes[i];
			}
			x = -1.0 + (y * 2.0) / ((1 << 22) - 1);
			fitness[2] = x * Math.sin(4 * Math.PI * x+1);
		}
	}
    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return "gene: "+geneString+" value: ";
    }
}

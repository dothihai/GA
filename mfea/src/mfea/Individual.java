package mfea;

public class Individual {
    private byte[] genes = new byte[22];
    // Cache
    private double[] fitness = new double[] {-10000,-10000,-10000};
    int[] rank = new int[3];
    int skill_factor; 
    int scalar_fitness; 
    private double x;

    // Create a random individual
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
        calFitness1();calFitness2();calFitness3();
    }
    
    public byte getGene(int index) {
        return genes[index];
    }

    public void setGene(byte[] gene,int task) {
        genes = gene;
        if(task == 0) calFitness1();
        else if(task == 1) calFitness2();
        else if(task == 2) calFitness3();
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }
    public double getFitness(int i) {
    	return fitness[i];
    }
    private void calFitness1() {
    	double y=0;
        for (int i = 0; i < 22; i++) {
			y += genes[i]*(1<<(22-1-i));		
		}
        x = -1.0 +  (y*3.0)/((1<<22)-1);
        fitness[0] = x*Math.sin(10*Math.PI*x)+1;
    }
    private void calFitness2() {
    	double y=0;
        for (int i = 0; i < 22; i++) {
			y += genes[i]*(1<<(22-1-i));		
		}
        x = -2.5 +  (y*5.0)/((1<<22)-1);
        fitness[1] = x*Math.cos(10*Math.PI*x)+1;
    }
    private void calFitness3() {
    	double y=0;
        for (int i = 0; i < 22; i++) {
			y += genes[i]*(1<<(22-1-i));		
		}
        x = -0.5 +  (y*1.0)/((1<<22)-1);
        fitness[2] = x*Math.cos(3*Math.PI*x)+1;
    }
    @Override
    public String toString() {
        String geneString = "";
        for (int i = 0; i < size(); i++) {
            geneString += getGene(i);
        }
        return "gene: "+geneString+" value: "+x;
    }
}

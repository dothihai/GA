package demo;

public class Individual {
    private byte[] genes = new byte[22];
    // Cache
    private double fitness = 0;
    private double x;

    // Create a random individual
    public void generateIndividual() {
        for (int i = 0; i < size(); i++) {
            byte gene = (byte) Math.round(Math.random());
            genes[i] = gene;
        }
        calFitness();
    }
    
    public byte getGene(int index) {
        return genes[index];
    }

    public void setGene(int index, byte value) {
        genes[index] = value;
        if (index == 21)
        	calFitness();
    }

    /* Public methods */
    public int size() {
        return genes.length;
    }
    public double getFitness() {
    	return fitness;
    }
    private void calFitness() {
    	double y=0;
        for (int i = 0; i < 22; i++) {
			y += genes[i]*(1<<(22-1-i));		
		}
        x = -1.0 +  (y*3.0)/((1<<22)-1);
        fitness = x*Math.sin(10*Math.PI*x)+1;
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
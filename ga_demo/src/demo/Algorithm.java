package demo;

public class Algorithm {
	private static int popsize = 50;
	private static int generation = 400;
	private static double mutationRate = 0.1; // xác suất  mutation
	private static double uniformRate = 0.5; // xác suất 1 gene được truyền cho con

	public static void main(String[] args) {
		// init population
		Individual[] current = new Individual[popsize];
		for (int i = 0; i < popsize; i++) {
			Individual newIndividual = new Individual();
			newIndividual.generateIndividual(); ///  random ca the
			current[i] = newIndividual;
			System.out.println(newIndividual);
		}

		// breed
		for (int i = 0; i < generation; i++) {
			System.out.print(i + 1 + " Generation:");
			current = evolvePopulation(current);
		}
	}

	// Crossover individuals
	private static Individual crossover(Individual indiv1, Individual indiv2) {
		Individual newSol = new Individual();
		// Loop through genes
		for (int i = 0; i < indiv1.size(); i++) {
			// Crossover
			if (Math.random() <= uniformRate) {
				newSol.setGene(i, indiv1.getGene(i));
			} else {
				newSol.setGene(i, indiv2.getGene(i));
			}
		}
		return newSol;
	}

	// Mutate an individual
	private static void mutate(Individual indiv) {
		// Loop through genes
		for (int i = 0; i < indiv.size(); i++) {
			if (Math.random() <= mutationRate) {
				// Create random gene
				byte gene = (byte) Math.round(Math.random());
				indiv.setGene(i, gene);
			}
		}
	}

	// Select individuals for crossover
	private static Individual tournamentSelection(Individual[] parent) {
		// Create a tournament population
		int tournamentSize = 10;
		Individual[] tournament = new Individual[tournamentSize];
		// For each place in the tournament get a random individual
		for (int i = 0; i < tournamentSize; i++) {
			int randomId = (int) (Math.random() * popsize);
			tournament[i] = parent[randomId];
		}
		// Get the fittest
		Individual fittest = tournament[0];
		// Loop through individuals to find fittest
		for (int i = 0; i < tournamentSize; i++) {
			if (fittest.getFitness() <= tournament[i].getFitness()) {
				fittest = tournament[i];
			}
		}
		return fittest;
	}

	public static Individual[] evolvePopulation(Individual[] parent) {
		Individual[] child = new Individual[popsize];
		for (int i = 0; i < popsize; i++) {
			Individual indiv1 = tournamentSelection(parent); // chon loc bo me
			Individual indiv2 = tournamentSelection(parent);
			Individual newIndiv = crossover(indiv1, indiv2); // lai ghep
			child[i] = newIndiv;
		}

		// Mutate population
		for (int i = 0; i < popsize; i++) {
			mutate(child[i]);
		}
		Individual best = child[0];
		double max = child[0].getFitness();
		for (int i = 0; i < popsize; i++) {
			if (max > child[i].getFitness())
				continue;
			max = child[i].getFitness();
			best = child[i];
		}

		System.out.println(best + " f(x)=" + max);
		return child;
	}
}

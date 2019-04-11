package mfea;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import mfea.Individual;

public class Algorithm {
	private static int popsize = 30;
	private static int generation = 1000;
	private static double rmp = 0.3;
	private static double mutationRate = 0.4; // xác suất mutation
	private static double uniformRate = 0.5; // xác suất 1 gene được truyền cho con
	static List<Comparator<Individual>> compare = Arrays.asList(new Compare1(),new Compare2(),new Compare3());

	public static void main(String[] args) {
		// init population
		List<Individual> current = new ArrayList<>();
		for (int i = 0; i < popsize; i++) {
			Individual newIndividual = new Individual();
			newIndividual.generateIndividual(); /// random ca the
			current.add(newIndividual);
//			System.out.println(newIndividual);
		}
		for (int i = 0; i < 3; i++) {
			Collections.sort(current, compare.get(i));
			for (int j = 0; j < popsize; j++) {current.get(j).rank[i] = j;}
		}
		
		for (int i = 0; i < popsize; i++) {
			Individual x = current.get(i);
			int max = 100;
			for (int j = 0; j < 3; j++) {
				if (max>x.rank[j]) {max = x.rank[j]; x.skill_factor=j;}
			}
			x.scalar_fitness=max;
//			System.out.println(x.rank[0]+" "+x.rank[1]+" "+x.rank[2]+" "+x.skill_factor);
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
		int task;
		byte[] gene = new byte[22];
		// Loop through genes
		for (int i = 0; i < indiv1.size(); i++) {
			// Crossover
			if (Math.random() <= uniformRate) {
				gene[i]=indiv1.getGene(i);
			} else {
				gene[i]=indiv2.getGene(i);
			}
		}
		if (Math.random()<0.5) task = indiv1.skill_factor;
		else task = indiv2.skill_factor;
		newSol.skill_factor=task;
		newSol.setGene(gene);
		newSol.calFitness(task);
		return newSol;
	}

	// Mutate an individual
	private static Individual mutate(Individual indiv) {
		// Loop through genes
		Individual newSol = new Individual();
		byte[] gene = new byte[22];
		for (int i = 0; i < indiv.size(); i++) {
			if (Math.random() <= mutationRate) {
				// Create random gene
				gene[i] = (byte) Math.round(Math.random());
			}
		}
		newSol.setGene(gene);
		newSol.skill_factor=indiv.skill_factor;
		newSol.calFitness(indiv.skill_factor);
		return newSol;
	}


	public static List<Individual>  evolvePopulation(List<Individual> parent) {
		Individual child1,child2;
		for (int i = 0; i < popsize/2; i++) {
			int x1 = (int) (Math.random()*popsize);
			int x2 = (int) (Math.random()*popsize);
			while(x2==x1) {
				x2 = (int) (Math.random()*popsize);
			}
			Individual indiv1 = parent.get(x1) ;// chon loc bo me
			Individual indiv2 = parent.get(x2) ;
			if ((Math.random() <= rmp)|(indiv1.skill_factor==indiv2.skill_factor)) {
				child1 = crossover(indiv1, indiv2); // lai ghep
				child2 = crossover(indiv1, indiv2);
			}
			else {
				child1=mutate(indiv1);
				child2=mutate(indiv2);
			}
			parent.add(child1);
			parent.add(child2);
			
		}
		double best[] = new double[3];
		for (int i = 0; i < 3; i++) {
			Collections.sort(parent, compare.get(i));
			for (int j = 0; j < parent.size(); j++) {parent.get(j).rank[i] = j;}
			best[i]= parent.get(0).getFitness(i);
		}


		for (int i = 0; i < parent.size(); i++) {
			Individual x = parent.get(i);
			int max = 10000;
			for (int j = 0; j < 3; j++) {
				if (max>x.rank[j]) {max = x.rank[j]; x.skill_factor=j;}
			}
			x.scalar_fitness=max;
			
		}
		Collections.sort(parent, new CompareSkill());
		List<Individual> next = new ArrayList<>();
		for (int i = 0; i < popsize; i++) {
				next.add(parent.get(i));
//				System.out.print(parent.get(i).scalar_fitness+" ");
		}
		System.out.println(best[0]+ " " + best[1] +" "+ best[2]);
		return next;
	}
}

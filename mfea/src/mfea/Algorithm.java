package mfea;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mfea.Individual;

public class Algorithm {
	private static int popsize = 30;
	private static int generation = 2000;
	private static double rmp = 0.2;
	private static double mutationRate = 0.3; // xác suất mutation
	private static double uniformRate = 0.5; // xác suất 1 gene được truyền cho con

	public static void main(String[] args) {
		// init population
		List<Individual> current = new ArrayList<>();
		for (int i = 0; i < popsize; i++) {
			Individual newIndividual = new Individual();
			newIndividual.generateIndividual(); /// random ca the
			current.add(newIndividual);
//			System.out.println(newIndividual);
		}
		Collections.sort(current, new Compare1());
		for (int i = 0; i < popsize; i++) {current.get(i).rank[0] = i;}
		Collections.sort(current, new Compare2());
		for (int i = 0; i < popsize; i++) {current.get(i).rank[1] = i;}
		Collections.sort(current, new Compare3());
		for (int i = 0; i < popsize; i++) {current.get(i).rank[2] = i;}
		
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
		newSol.setGene(gene, task);
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
		newSol.setGene(gene, indiv.skill_factor);
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
		Collections.sort(parent, new Compare1());
		for (int i = 0; i < 2*popsize; i++) {parent.get(i).rank[0] = i;}
		double best1= parent.get(0).getFitness(0);
		Collections.sort(parent, new Compare2());
		for (int i = 0; i < 2*popsize; i++) {parent.get(i).rank[1] = i;}
		double best2= parent.get(0).getFitness(1);
		Collections.sort(parent, new Compare3());
		for (int i = 0; i < 2*popsize; i++) {parent.get(i).rank[2] = i;}
		double best3= parent.get(0).getFitness(2);
		int k = parent.get(0).scalar_fitness;

		for (int i = 0; i < 2*popsize; i++) {
			Individual x = parent.get(i);
			int max = 100;
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
		System.out.println(best1 + " " + best2 +" "+ best3);
		return next;
	}
}

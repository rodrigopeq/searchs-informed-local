package searchs_eigth_queens;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFunctions;
import aima.core.environment.nqueens.NQueensGenAlgoUtil;
import aima.core.environment.nqueens.QueenAction;
import aima.core.environment.nqueens.NQueensBoard.Config;
import aima.core.search.framework.Node;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.problem.Problem;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.AStarSearch;
import aima.core.search.informed.BestFirstSearch;
import aima.core.search.informed.EvaluationFunction;
import aima.core.search.informed.GreedyBestFirstSearch;
import aima.core.search.informed.RecursiveBestFirstSearch;
import aima.core.search.local.FitnessFunction;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.Individual;
import aima.core.search.local.Scheduler;
import aima.core.search.local.SimulatedAnnealingSearch;

public abstract class EigthQueensBase {
	final private static int numberQueens = 8;

	public static void solveNQueensWithAStarSearch() {
		Problem<NQueensBoard, QueenAction> problem = NQueensFunctions
				.createCompleteStateFormulationProblem(numberQueens, Config.QUEENS_IN_FIRST_ROW);
		SearchForActions<NQueensBoard, QueenAction> search = new AStarSearch<>(new GraphSearch<>(),
				NQueensFunctions::getNumberOfAttackingPairs);
		Optional<List<QueenAction>> actions = search.findActions(problem);

		actions.ifPresent(qActions -> qActions.forEach(System.out::println));
		System.out.println(search.getMetrics());
	}

	public static void solveNQueensWithBestFirstSearch() {
		Problem<NQueensBoard, QueenAction> problem = NQueensFunctions
				.createCompleteStateFormulationProblem(numberQueens, Config.QUEENS_IN_FIRST_ROW);
		SearchForActions<NQueensBoard, QueenAction> search = new BestFirstSearch<>(new GraphSearch<>(),
				createEvalHn(NQueensFunctions::getNumberOfAttackingPairs));
		Optional<List<QueenAction>> actions = search.findActions(problem);

		actions.ifPresent(qActions -> qActions.forEach(System.out::println));
		System.out.println(search.getMetrics());
	}

	public static void solveNQueensWithRecursiveBestFirstSearch() {
		Problem<NQueensBoard, QueenAction> problem = NQueensFunctions
				.createCompleteStateFormulationProblem(numberQueens, Config.QUEENS_IN_FIRST_ROW);
		SearchForActions<NQueensBoard, QueenAction> search = new RecursiveBestFirstSearch<>(
				createEvalFn(NQueensFunctions::getNumberOfAttackingPairs));
		Optional<List<QueenAction>> actions = search.findActions(problem);

		actions.ifPresent(qActions -> qActions.forEach(System.out::println));
		System.out.println(search.getMetrics());
	}

	public static void solveNQueensWithGreedyBestFirstSearch() {
		Problem<NQueensBoard, QueenAction> problem = NQueensFunctions
				.createCompleteStateFormulationProblem(numberQueens, Config.QUEENS_IN_FIRST_ROW);
		SearchForActions<NQueensBoard, QueenAction> search = new GreedyBestFirstSearch<>(new GraphSearch<>(),
				NQueensFunctions::getNumberOfAttackingPairs);
		Optional<List<QueenAction>> actions = search.findActions(problem);

		actions.ifPresent(qActions -> qActions.forEach(System.out::println));
		System.out.println(search.getMetrics());
	}

	public static void solveNQueensWithGeneticAlgorithmSearch() {
		FitnessFunction<Integer> fitnessFunction = NQueensGenAlgoUtil.getFitnessFunction();
		Predicate<Individual<Integer>> goalTest = NQueensGenAlgoUtil.getGoalTest();
		Set<Individual<Integer>> population = new HashSet<>();
		for (int i = 0; i < 50; i++)
			population.add(NQueensGenAlgoUtil.generateRandomIndividual(numberQueens));
		GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<>(numberQueens,
				NQueensGenAlgoUtil.getFiniteAlphabetForBoardOfSize(numberQueens), 0.15);
		Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 1000L);
		System.out.println(
				"Max time 1 second, Best Individual:\n" + NQueensGenAlgoUtil.getBoardForIndividual(bestIndividual));
		System.out.println("Board Size      = " + numberQueens);
		System.out.println("# Board Layouts = " + (new BigDecimal(numberQueens)).pow(numberQueens));
		System.out.println("Fitness         = " + fitnessFunction.apply(bestIndividual));
		System.out.println("Is Goal         = " + goalTest.test(bestIndividual));
		System.out.println("Population Size = " + ga.getPopulationSize());
		System.out.println("Iterations      = " + ga.getIterations());
		System.out.println("Took            = " + ga.getTimeInMilliseconds() + "ms.");
		bestIndividual = ga.geneticAlgorithm(population, fitnessFunction, goalTest, 0L);
		System.out.println("");
		System.out.println(
				"Max time unlimited, Best Individual:\n" + NQueensGenAlgoUtil.getBoardForIndividual(bestIndividual));
		System.out.println("Board Size      = " + numberQueens);
		System.out.println("# Board Layouts = " + (new BigDecimal(numberQueens)).pow(numberQueens));
		System.out.println("Fitness         = " + fitnessFunction.apply(bestIndividual));
		System.out.println("Is Goal         = " + goalTest.test(bestIndividual));
		System.out.println("Population Size = " + ga.getPopulationSize());
		System.out.println("Itertions       = " + ga.getIterations());
		System.out.println("Took            = " + ga.getTimeInMilliseconds() + "ms.");
	}

	public static void solveNQueensWithHillClimbingSearch() {
		Problem<NQueensBoard, QueenAction> problem = NQueensFunctions
				.createCompleteStateFormulationProblem(numberQueens, Config.QUEENS_IN_FIRST_ROW);
		HillClimbingSearch<NQueensBoard, QueenAction> search = new HillClimbingSearch<>(
				n -> -NQueensFunctions.getNumberOfAttackingPairs(n));
		Optional<List<QueenAction>> actions = search.findActions(problem);

		actions.ifPresent(qActions -> qActions.forEach(System.out::println));
		System.out.println(search.getMetrics());
		System.out.println("Final State:\n" + search.getLastState());
	}

	public static void solveNQueensWithSimulatedAnnealingSearch() {
		Problem<NQueensBoard, QueenAction> problem = NQueensFunctions
				.createCompleteStateFormulationProblem(numberQueens, Config.QUEENS_IN_FIRST_ROW);
		SimulatedAnnealingSearch<NQueensBoard, QueenAction> search = new SimulatedAnnealingSearch<>(
				NQueensFunctions::getNumberOfAttackingPairs, new Scheduler(20, 0.045, 100));
		Optional<List<QueenAction>> actions = search.findActions(problem);

		actions.ifPresent(qActions -> qActions.forEach(System.out::println));
		System.out.println(search.getMetrics());
		System.out.println("Final State:\n" + search.getLastState());
	}

	public static <S, A> EvaluationFunction<S, A> createEvalFn(ToDoubleFunction<Node<S, A>> h) {
		return new EvaluationFunction<S, A>(h) {
			@Override
			public double applyAsDouble(Node<S, A> node) {
				return node.getPathCost() + this.h.applyAsDouble(node);
			}
		};
	}

	public static <S, A> EvaluationFunction<S, A> createEvalHn(ToDoubleFunction<Node<S, A>> h) {
		return new EvaluationFunction<S, A>(h) {
			@Override
			public double applyAsDouble(Node<S, A> node) {
				return this.h.applyAsDouble(node);
			}
		};
	}
}

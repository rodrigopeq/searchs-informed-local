package searchs_map_agent;

import java.util.function.ToDoubleFunction;

import aima.core.agent.Agent;
import aima.core.agent.EnvironmentListener;
import aima.core.agent.impl.DynamicPercept;
import aima.core.agent.impl.SimpleEnvironmentView;
import aima.core.environment.map.ExtendableMap;
import aima.core.environment.map.MapAgent;
import aima.core.environment.map.MapEnvironment;
import aima.core.environment.map.MoveToAction;
import aima.core.environment.map.SimpleMapAgent;
import aima.core.environment.map.SimplifiedRoadMapOfRomania;
import aima.core.search.framework.Node;
import aima.core.search.framework.SearchForActions;
import aima.core.search.informed.EvaluationFunction;

public abstract class MapAgentBase {

	protected static void searchs(SearchForActions<String, MoveToAction> search, String destination, ExtendableMap map ) {

		Agent<DynamicPercept, MoveToAction> agent;

		System.out.println();
		System.out.println("----------------------------------------");
		System.out.println("--------------- MapAgent ---------------");
		System.out.println("----------------------------------------");
		agent = new MapAgent(map, search, destination);
		agents(map, agent, search);

		System.out.println("----------------------------------------");
		System.out.println("------------ SimpleMapAgent ------------");
		System.out.println("----------------------------------------");
		agent = new SimpleMapAgent(map, search, destination);
		agents(map, agent, search);
	}

	private static void agents(ExtendableMap map, Agent<DynamicPercept, MoveToAction> agent,
			SearchForActions<String, MoveToAction> search) {
		MapEnvironment env = new MapEnvironment(map);
		EnvironmentListener<Object, Object> envView = new SimpleEnvironmentView();
		env.addEnvironmentListener(envView);

		String agentLoc = SimplifiedRoadMapOfRomania.ARAD;

		env.addAgent(agent, agentLoc);
		env.stepUntilDone();
		envView.notify(search.getMetrics().toString());
	}
	
	public static <S, A> EvaluationFunction<S, A> createEvalFn(ToDoubleFunction<Node<S, A>> h) {
        return new EvaluationFunction<S, A>(h) {
            @Override
            public double applyAsDouble(Node<S, A> node) {
                return node.getPathCost() + this.h.applyAsDouble(node);
            }
        };
    }
}
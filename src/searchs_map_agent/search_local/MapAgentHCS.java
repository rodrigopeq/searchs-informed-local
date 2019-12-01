package searchs_map_agent.search_local;

import aima.core.environment.map.ExtendableMap;
import aima.core.environment.map.MapFunctions;
import aima.core.environment.map.MoveToAction;
import aima.core.environment.map.SimplifiedRoadMapOfRomania;
import aima.core.search.framework.SearchForActions;

import aima.core.search.local.HillClimbingSearch;
import searchs_map_agent.MapAgentBase;

public class MapAgentHCS extends MapAgentBase{
	public static void main(String[] args) {
		ExtendableMap map = new ExtendableMap();
		SimplifiedRoadMapOfRomania.initMap(map);

		String destination = SimplifiedRoadMapOfRomania.BUCHAREST;
		
		SearchForActions<String, MoveToAction> search;
		search = new HillClimbingSearch<>(MapFunctions.createSLDHeuristicFunction(destination, map));
		searchs(search,destination,map);
	}
}

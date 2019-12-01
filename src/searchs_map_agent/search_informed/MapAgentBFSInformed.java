package searchs_map_agent.search_informed;

import aima.core.environment.map.ExtendableMap;
import aima.core.environment.map.MapFunctions;
import aima.core.environment.map.MoveToAction;
import aima.core.environment.map.SimplifiedRoadMapOfRomania;
import aima.core.search.framework.SearchForActions;
import aima.core.search.framework.qsearch.GraphSearch;
import aima.core.search.informed.BestFirstSearch;
import searchs_map_agent.MapAgentBase;

public class MapAgentBFSInformed extends MapAgentBase {
	public static void main(String[] args) {
		ExtendableMap map = new ExtendableMap();
		SimplifiedRoadMapOfRomania.initMap(map);

		String destination = SimplifiedRoadMapOfRomania.BUCHAREST;
		
		SearchForActions<String, MoveToAction> search;
		search = new BestFirstSearch<>(new GraphSearch<>());
		searchs(search,destination,map);
	}
}
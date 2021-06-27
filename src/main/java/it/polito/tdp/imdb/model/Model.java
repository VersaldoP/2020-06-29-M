package it.polito.tdp.imdb.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;

public class Model {
	
	private ImdbDAO dao;
	private SimpleWeightedGraph<Director,DefaultWeightedEdge> grafo;
	private Map<Integer,Director> idMap;
	private List<Adiacenze> archi;
	
	public Model() {
		dao = new ImdbDAO();
		
	}
	
	public void creaGrafo(int anno) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap= new HashMap<>(dao.listAllDirectors(anno));
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		
		
	}
	
	
	

}

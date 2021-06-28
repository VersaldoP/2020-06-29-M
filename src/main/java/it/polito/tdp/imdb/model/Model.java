package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
	private List<Director> soluzione;
	private int maxP;
	
	public Model() {
		dao = new ImdbDAO();
		
	}
	
	public void creaGrafo(int anno) {
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap= new HashMap<>(dao.listAllDirectors(anno));
		Graphs.addAllVertices(this.grafo, idMap.values());
		
		archi = new ArrayList<>();
		dao.listArchi(idMap, anno, archi);
	   
		
		for(Adiacenze a :archi) {
			Graphs.addEdge(this.grafo, a.getD1(), a.getD2(), a.getPeso());
		}	
		
	}
	
	public int getNvertici() {
		return this.grafo.vertexSet().size();
		}
	
	public int getNArchi(){
		return this.grafo.edgeSet().size();
		
	}
	public List<Director> getVertex(){
		List<Director> result = new ArrayList<>(idMap.values());
		Collections.sort(result);
		return  result;
	}
	
	public List<Adiacenze> getRegistiAdiacenti(Director d){
		List<Adiacenze> result = new ArrayList<>();
		
		for( DefaultWeightedEdge e: this.grafo.edgesOf(d)) {
			result.add(new Adiacenze(d,Graphs.getOppositeVertex(this.grafo, e, d),(int)this.grafo.getEdgeWeight(e)));
		}
		
		Collections.sort(result);
		
		return result;
	}
	
	public String cerca(Director id,Integer i) {
		Director d = idMap.get(id.getId());
		if(d==null) {
			return "\nErrore, id non trovato \n";
		} else {
			String result = null;
			soluzione = new ArrayList<>();
			List<Director> parziale = new ArrayList<>();
			parziale.add(d);
			maxP=i;
			ricorsivo(parziale);
			result="\n il numero di attori condivisi totali è "+peso(soluzione)+"\n";
			for(Director dr :soluzione){
				result+=dr+"\n";
				
			}
			
			return result;
			
		}
		
		
	}

	private void ricorsivo(List<Director> parziale) {
		Director ultimo= parziale.get(parziale.size()-1);
		
		if(peso(parziale)<=maxP) {
			if(parziale.size()>soluzione.size()) {
				soluzione = new ArrayList<>(parziale);
			}
		}
		
		for(Director d :Graphs.neighborListOf(this.grafo, ultimo)) {
			
			if(!parziale.contains(d)) {
				parziale.add(d);
				ricorsivo(parziale);
				parziale.remove(parziale.size()-1);
			}
			
			
			
		}
		
		
	}

	private int peso(List<Director> parziale) {
		int peso =0;
		for(DefaultWeightedEdge e :this.grafo.edgesOf(parziale.get(parziale.size()-1))) {
			peso+=this.grafo.getEdgeWeight(e);
		}
		return peso;
	}
	
	

}

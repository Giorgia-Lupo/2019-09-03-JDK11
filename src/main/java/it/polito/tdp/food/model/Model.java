package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.Adiacenza;
import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	FoodDao dao;
	Graph<String, DefaultWeightedEdge> grafo;
	List<String> vertici;
	
	public Model() {
		dao = new FoodDao();
	}
		
	public void creaGrafo(int calorie) {	
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class); 
	
		vertici = dao.getVertici(calorie);
		Graphs.addAllVertices(this.grafo, vertici);
		
		List<Adiacenza> archi = dao.getAdiacenze();
		for(Adiacenza a : archi) {
			if(this.grafo.vertexSet().contains(a.getP1()) && this.grafo.vertexSet().contains(a.getP2())) {
				Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
			}
		}	
		
		/*Se public String creaGrafo(){...
		 * 
		 * return String.format("Grafo creato (%d vertici, %d archi)\n",
		 * this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
		 */
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<String> getVertici(){
		return this.vertici;
	}
	
	//FATTA DAL PROF, uguale alla mia ma con la classe.
	public List<PorzioneAdiacente> getAdiacenti(String tipo){
		List<String> vicini = Graphs.neighborListOf(this.grafo, tipo);
		List<PorzioneAdiacente> result = new ArrayList<>();
		for(String v : vicini) {
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(tipo, v));
			result.add(new PorzioneAdiacente(v, peso));
		}
		return result;
	}
	
	/*SENZA AVERE CREATO LA CLASSE DELLE PORZIONI ADIACENTI
	public List<String> getVicini(String tipo){
		List<String> vicini = Graphs.neighborListOf(this.grafo, tipo);
		double peso = 0;
		String si = "";
		List<String> v = new ArrayList<>();
		for(String s : vicini) {
			peso = this.grafo.getEdgeWeight(this.grafo.getEdge(tipo, s));
			si = s + peso + "\n";
			v.add(si);
		}
		return v;
	}
	*/
	
	
}


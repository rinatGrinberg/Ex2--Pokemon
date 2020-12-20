package api;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class DWGraph_Algo implements dw_graph_algorithms{

	private directed_weighted_graph graph;
	
	private HashMap<Integer, Integer> prev; // { n0.key = null, n1.key = n0.key, n2.key = n0.key }
	private HashMap<Integer, Double> dist; // { n0.key = n0 distance from source }
	
	
	public DWGraph_Algo() {


	}
	 public void init(directed_weighted_graph g) {
			this.graph = g;
			this.prev = new HashMap<>();
			this.dist = new HashMap<>();
	 }

	   
	    public directed_weighted_graph getGraph() {
	    	return this.graph;
	    }
	    
	    public directed_weighted_graph copy() {


	    	directed_weighted_graph new_graph = new DWGraph_DS();
			
			for (node_data vertex : this.graph.getV()) {
				new_graph.addNode(vertex); //new copy of vertex (node)
				
				new_graph.getNode(vertex.getKey()).setInfo(vertex.getInfo());
				new_graph.getNode(vertex.getKey()).setTag(vertex.getTag());
				new_graph.getNode(vertex.getKey()).setWeight(vertex.getWeight());
			}
			
			for (node_data v : this.graph.getV()) { //for each node in graph
				for (edge_data edge : this.graph.getE(v.getKey())) { //for each neighbor of node

					new_graph.connect(v.getKey(), edge.getDest(), edge.getWeight());
				}
			}
			
			return new_graph;
	    }
	    
	    public void djikstra(int source) { 
			
			PriorityQueue<node_data> q = new PriorityQueue<node_data>();
			
			for (node_data node : this.graph.getV()) {
				
				if(node.getKey() == source) {
					this.dist.put(node.getKey(), 0.0);
					this.prev.put(node.getKey(), node.getKey());
					node.setWeight(0.0); 
				}
				else {
					
					this.dist.put(node.getKey(), Double.MAX_VALUE);
					this.prev.put(node.getKey(), null);
					node.setWeight(Double.MAX_VALUE); 
					
				}
				
				q.add(node);
			}
			
			
//			q.add(this.graph.getNode(source));
			
			while (!q.isEmpty()) {
				
				node_data u = q.poll();
				
				for (edge_data edgeNeighbor : this.graph.getE(u.getKey())) {
					
					double alt = this.dist.get(u.getKey()) + edgeNeighbor.getWeight();

					if (alt < this.dist.get(edgeNeighbor.getDest())) {
						
						this.dist.put(edgeNeighbor.getDest(), alt);
						this.prev.put(edgeNeighbor.getDest(), u.getKey());
						
						graph.getNode(edgeNeighbor.getDest()).setWeight(alt);
						
						//q.decrease_priority
						
						q.remove(graph.getNode(edgeNeighbor.getDest()));
						q.add(graph.getNode(edgeNeighbor.getDest()));
					}
					
				}

			}
			
		}
	    
	    public boolean isConnected() {

	    	if (this.DFS()>1)
	    	return false;
	    	else {
				return true;
			}

	    	// djkistra(src)
			// transpose(graph)
			// djikstra(src)
		}
	    
	 
	    public double shortestPathDist(int src, int dest)
	    {
			djikstra(src);
	    	return this.dist.get(dest);
	    }


	    public List<node_data> shortestPath(int src, int dest)
	    {
	    	{
	    		
	    		djikstra(src);
	    		
	    		List<node_data> res = new ArrayList<>();
	    		
	    		// res = [dest,      ]
	    		
	    		res.add(this.graph.getNode(dest));
	    		
	    		Integer next = dest;
	    		
	    		while(true && next != null) {
	    			
	    			 if (next == src)
	    				 break;
	    			 
	    			try {
		    			res.add(this.graph.getNode(this.prev.get(next)));
		    			next = this.prev.get(next);
		    			
					} catch (Exception e) {
						return null;
					}

	    			
	    		}
	    		
	    		List<node_data> back_res = new ArrayList<>();
	    		
	    		for (int i = res.size()-1; i >= 0; i--) {
	    			back_res.add(res.get(i));
	    		}
	    		
	    		return back_res;
	    	}
	    }

	    /**
	     * Saves this weighted (directed) graph to the given
	     * file name - in JSON format
	     * @param file - the file name (may include a relative path).
	     * @return true - iff the file was successfully saved
	     */
public boolean save(String file) {
	    	
	    	try {
	    		
	    	Gson gson =new Gson();
	    	List<edge_data> answerList=new ArrayList<edge_data>();
	    	
	    	FileWriter fWriter;
				fWriter = new FileWriter(file);
				fWriter.flush();
	    	
			
				
				
	for (node_data node : this.graph.getV()) { /// run over graph edges
					
					for (edge_data ni : this.graph.getE(node.getKey())) {
					
						answerList.add(ni); /// all the edges to List of edges
		
	    
					}
					
	}
	gson.toJson(answerList,fWriter); 
	fWriter.close();

	
			}
	
			 catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
	    	return true;
	    }
	    

	    public void DFS_init(int vertex,boolean visited[])
	    { 	
	    	
	    	visited[vertex]=true;
			
	    Collection <edge_data> edgeNeighbor =this.graph.getE(vertex);


	    Iterator <edge_data> iterator= edgeNeighbor.iterator();
	    while(iterator.hasNext() )//O(|V|)
	    {
	    	int n = iterator.next().getDest();
	    	if(!visited[n])
	    	{
	    		
	    		DFS_init(n, visited);
	    	}
	    }
	   
	    
	    }
	    
	    
	    

	    public int DFS()
	    { 
	    	int connectedComponents=0;
	    	boolean visited[]=new boolean[graph.nodeSize()];
	    	
	    	 for (node_data vertex: this.graph.getV()) {
	    		 if (visited[vertex.getKey()]==false) {
	    			 connectedComponents++;
	    			 DFS_init(vertex.getKey(), visited);
					
				}		
			
		
			}
 			return connectedComponents;
	    }
	    
	    
	    
	    /**
	     * This method load a graph to this graph algorithm.
	     * if the file was successfully loaded - the underlying graph
	     * of this class will be changed (to the loaded one), in case the
	     * graph was not loaded the original graph should remain "as is".
	     * @param file - file name of JSON file
	     * @return true - iff the graph was successfully loaded.
	     */
	    public boolean load(String file) {
	    	try
			{
	    		GsonBuilder builder=new GsonBuilder();
	    		builder.registerTypeAdapter(DWGraph_DS.class, new GraphJsonDeserializer());
	    		Gson gson=builder.create();
	    		FileReader reader=new FileReader(file);
	    		directed_weighted_graph g=gson.fromJson(reader, DWGraph_DS.class);
	    		init(g);
	    		
	    		
				return true;

						}			
			
			catch (IOException  e) {
				e.printStackTrace();
				return false;
			}
	    }
}
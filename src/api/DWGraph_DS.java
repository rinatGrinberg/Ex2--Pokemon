package api;

import java.io.Serializable;
import java.util.*;

public class DWGraph_DS implements directed_weighted_graph, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private HashMap<Integer, node_data> nodes; 
	
//	private HashMap<Integer, HashMap<Integer, Double>> neighbors;
	
	private HashMap<Integer, HashMap<Integer, edge_data>> neighbors;
	private HashMap<Integer, List<Integer>> connected_to;

	
	
//	private HashMap<api.edge_data, Integer> edgesDistance;
	
	private int mode_count;
	private int edge_count;
	
//	private int node_size;
	

	//it will be a inner class in the nest development 

	
	///////////////// Graph from Here

//the constructor:
	public DWGraph_DS() {
		
		this.nodes = new HashMap<>();
		this.neighbors = new HashMap<>();
//		this.edgesDistance =new HashMap<>(); 
		
		this.connected_to = new HashMap<>();
		
		this.edge_count = 0;
//		this.node_size = 0;
		this.mode_count = 0;
		
	}
	
	public node_data getNode(int key) {
	
		if (this.nodes.containsKey(key)){
			return this.nodes.get(key);
		}
		
		return null;
	}


	
	public edge_data getEdge(int src, int dest) {
		
        if (this.nodes.containsKey(src) && this.nodes.containsKey(dest)) {
			
			if (src != dest) {
				
				if (this.nodes.get(src) != null && this.nodes.get(dest) != null) {
				
					//if(this.neighbors.get(src).containsKey(dest))
					
					return this.neighbors.get(src).get(dest);
				
				}
				
			}
        }
            
			return null;
	}

	public void addNode(node_data n) {

		if (!this.nodes.containsKey(n.getKey())) {
			
//			this.nodes.put(n.getKey(), new Node(n.getKey()));

			this.nodes.put(n.getKey(), n);
			
//			this.node_size++;
			this.mode_count++;
			
			this.neighbors.put(n.getKey(), new HashMap<>()); 
			this.connected_to.put(n.getKey(), new ArrayList<>());
		
		}		
	}
	
//	 * Connects an edge with weight w between node src to node dest.
//	 * * Note: this method should run in O(1) time.
//	 * @param src - the source of the edge.
//	 * @param dest - the destination of the edge.
//	 * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
//	 */
		public void connect(int src, int dest, double w)
		{		
			if (w >= 0) 
			{

				if (src != dest)
				{
					
					if (this.nodes.containsKey(src) && this.nodes.containsKey(dest)
							&& this.neighbors.containsKey(src) && this.neighbors.containsKey(dest)) 
					{
						
						if (this.nodes.get(src) != null && this.nodes.get(dest) != null)
						{
							
						//we will need to add the hasedge function but in the interface i can't see it;
							if(!this.neighbors.get(src).containsKey(dest)) 
							{ //no edge between n1 -> n2
								
								this.neighbors.get(src).put(getNode(dest).getKey(),
										
													new Edge(src, dest, w));
								
								this.connected_to.get(dest).add(src);
								
								this.edge_count++;
								
							}
							
//							else { //there is an edge between n1 and n2 already 
//								   //update the weight:
//								this.neighbors.get(src).put(getNode(dest).getKey(),
//										
//										new edge(src, dest, w));
//								
//							this.mode_count++;
//							}
							
						}
						
					}
					
				}
				
			}
		}
		
		public Collection<node_data> getV(){
			
			return this.nodes.values();
		}
		
		public Collection<edge_data> getE(int node_id){
			
//			List<api.edge_data> collection = new ArrayList<>();
//			if (this.nodes.containsKey(node_id) && this.neighbors.containsKey(node_id)) {
//				
//				for (Map.Entry<Integer, Double> entry : this.neighbors.get(node_id).entrySet()) {
//					
//				
//					collection.add(getEdge(nodes.get(node_id).getKey() ,entry.getKey()));
//					
//				}
//				
//				
//				}
//				return collection;
	
			return this.neighbors.get(node_id).values(); //o(k=degree)
			
		}
			

		/**
		 * Deletes the node (with the given ID) from the graph -
		 * and removes all edges which starts or ends at this node.
		 * This method should run in O(k), V.degree=k, as all the edges should be removed.
		 * @return the data of the removed node (null if none). 
		 * @param key
		 */
		public node_data removeNode(int key) {
			 {					
					if (this.nodes.containsKey(key) && this.neighbors.containsKey(key)) {
						
						for (Integer k : this.connected_to.get(key)) { //for each connected node to key
							
							this.neighbors.get(k).remove(key);
							this.edge_count--;
							this.mode_count++;
						}
						
						for (edge_data neighbor : getE(key)) { //worst case o(|v|)
							
							this.neighbors.get(key).remove(neighbor.getSrc());
							this.edge_count--;
							this.mode_count++;
						}
						
						
						this.neighbors.remove(key); // possible clear() needed

						return this.nodes.remove(key);
						
					}
					
					return null;
				}
		}
		
		public edge_data removeEdge(int src, int dest){
	
			if (this.nodes.containsKey(src) && this.nodes.containsKey(dest)
					&& this.neighbors.containsKey(src) && this.neighbors.containsKey(dest)) {
				
				if (this.nodes.get(src) != null && this.nodes.get(dest) != null) {
					
					if (this.neighbors.get(src).containsKey(dest) && src != dest) { //if edge exists.

						this.mode_count++;
						this.edge_count--;
						
						this.connected_to.get(dest).remove(src);
						
						return this.neighbors.get(src).remove(dest);							

					}
					
				}
			}
			
			return null;
		}
			
		public int edgeSize() {return this.edge_count;}

		public int getMC() {return this.mode_count;}
		
		public int nodeSize() {return this.nodes.size();}
		}
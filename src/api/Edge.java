package api;

import java.io.Serializable;

public class Edge implements edge_data, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

    public int src;
    public double wieght;
    public int dest;
    public String info;
    public int tag;


	public Edge(int src, int dest, double w) {
		this.src = src;
	    this.dest = dest;
		this.info = "";
		this.tag = 0;
		this.wieght = w;
		
	}
    
//	public edge(api.node_data api.node_data, api.node_data node_data2) {
//		this.src = api.node_data;
//	    this.dest = node_data2;
//		this.info = "";
//		this.tag = 0;
//		this.wieght = 0.0;
//		
//		
//	}
    
//	public edge(api.node_data src,api.node_data dest,String info,int tag,double wieght) {
//		
//		this.src = src;
//	    this.dest = dest;
//		this.info = info;
//		this.tag = tag;
//		this.wieght = wieght;
//	
//	
//	}
	
//===============================inner class- for the location of the edge===========================================
		public class edgeLocation implements edge_location{
			
			private edge_data e;
			private double ratio;

			@Override
			public edge_data getEdge() {
				return this.e;
			}

			@Override
			public double getRatio() {
				
				return this.ratio;
			}
			
			
		
		}
//===========================================================================================================


	public int getSrc() {return this.src;}
	/**
	 * The id of the destination node of this edge
	 * @return
	 */
	public int getDest() {return this.dest;}
	/**
	 * @return the weight of this edge (positive value).
	 */
	public double getWeight() {return this.wieght;}
	/**
	 * Returns the remark (meta data) associated with this edge.
	 * @return
	 */
	public String getInfo() {return this.info;}
	/**
	 * Allows changing the remark (meta data) associated with this edge.
	 * @param s
	 */
	public void setInfo(String s) {this.info= s;}
	/**
	 * Temporal data (aka color: e,g, white, gray, black) 
	 * which can be used be algorithms 
	 * @return
	 */
	public int getTag() {return this.tag;}
	/** 
	 * This method allows setting the "tag" value for temporal marking an edge - common
	 * practice for marking by algorithms.
	 * @param t - the new value of the tag
	 */
	public void setTag(int t) {this.tag = t;}

	@Override
	public String toString() {
		return "{s:"+src+", d:"+dest+", w:"+wieght+"}";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (!(obj instanceof Edge))
			return false;

		Edge e = (Edge) obj;

		return e.getSrc() == src && e.getDest() == dest && e.getWeight() == wieght && e.getTag() == tag
				&& e.getInfo().equals(info);
	}
}
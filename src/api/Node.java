package api;

import java.io.Serializable;

public class Node implements node_data, Serializable, Comparable<node_data> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int key;
	private double wieght;
	private String info;
	private int tag; // נשתנש בשביל סכום הערכי פוקימונים סביב
	private geo_location nodelocation;

	
	public Node(int key) {
		this.key= key;
		this.tag=0;
		this.wieght = 0.0;
		this.info = "";
//		this.nodelocation = new nodeLocation(0.0,0.0,0.0);
		this.nodelocation = null;

	}
	
//	public Node(int key, double wieght, String info,int tag,nodeLocation location) {
//		this.key= key;
//		this.wieght = wieght;
//		this.info = info;
//		this.tag = tag;
//		this.nodelocation = new nodeLocation(this.nodelocation.x = location.x,this.nodelocation.y = location.y,	this.nodelocation.z = location.z);
//
//
//	}
//
//	//===============================inner class- for the location of the node===========================================
//	public class nodeLocation implements geo_location{
//		public double x;
//		public double y;
//		public double z;
//
//		public nodeLocation(double x,double y,double z)
//		{
//			this.x=x;
//			this.y=y;
//			this.z=z;
//
//		}
//
//		public double x() {return this.x;}
//		public double y() {return this.y;}
//		public double z() {return this.z;}
//
//		public double distance(geo_location g) {
//
//			 double dis = Math.sqrt( Math.pow(this.x -g.x(), 2) + Math.pow(this.y- g.y(), 2) +Math.pow(this.z- g.z(), 2) );
//			 return dis;
//		}
//	}
	//===========================================================================================================
	
		public int getKey() {return this.key;}

		/** Returns the location of this node, if
		 * none return null.
		 * 
		 * @return
		 */
		public geo_location getLocation() {
			return this.nodelocation;
		}
		/** Allows changing this node's location.
		 * @param p - new new location  (position) of this node.
		 */
		public void setLocation(geo_location p) {

			this.nodelocation = p;
			}
		/**
		 * Returns the weight associated with this node.
		 * @return
		 */
		public double getWeight() {return this.wieght;}
		/**
		 * Allows changing this node's weight.
		 * @param w - the new weight
		 */
		public void setWeight(double w) {this.wieght =w;}
		/**
		 * Returns the remark (meta data) associated with this node.
		 * @return
		 */
		public String getInfo() {return this.info;}
		/**
		 * Allows changing the remark (meta data) associated with this node.
		 * @param s
		 */
		public void setInfo(String s) {this.info = s;}
		/**
		 * Temporal data (aka color: e,g, white, gray, black) 
		 * which can be used be algorithms 
		 * @return
		 */
		public int getTag() {return this.tag;}
		/** 
		 * Allows setting the "tag" value for temporal marking an node - common
		 * practice for marking by algorithms.
		 * @param t - the new value of the tag
		 */
		public void setTag(int t) {this.tag =t;}
		
		public int compareTo(node_data other) {
			
			Double w1 = this.getWeight();
			Double w2 = other.getWeight();
			
			if (w1 > w2) // o1 > o2
				return 1;
			else if (w1 < w2) // o1 < o2
				return -1;
			
			return 0; // o1 = o2
		}

	@Override
	public String toString() {
		return ""+getKey();
	}
}
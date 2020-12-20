
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import api.*;
import org.junit.Test;

public class  TestsGraph {



	    private static Random _rnd = null;

    @Test
    public void nodeSize() {
    	directed_weighted_graph g = new DWGraph_DS();
    	
        g.addNode(new Node(0));
        g.addNode(new Node(1));
        g.addNode(new Node(2));

        g.removeNode(2);
        g.removeNode(1);
        g.removeNode(1);
        int s = g.nodeSize();
        assertEquals(1,s);

    }

    @Test
    public void edgeSize() {
    	directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new Node(0));
        g.addNode(new Node(1));
        g.addNode(new Node(2));
        g.addNode(new Node(3));
        
        g.connect(0,1,1); // 0 -> 1 w: 1
        g.connect(0,2,2); // 0 -> 2 w: 2
        g.connect(0,3,3); // 0 -> 3 w: 3
        
        g.connect(0,1,1); // 0 -> 1 w: change to -> 1 //do nothing
        
        int e_size =  g.edgeSize();
        assertEquals(3, e_size);
        
        edge_data w03 = g.getEdge(0,3);
        edge_data w30 = g.getEdge(3,0);
        
//        assertNotEquals(w03.getWeight(), w30.getWeight(), 0.0001);
        
        assertEquals(w03.getWeight(), 3, 0.0001); // 0 connected to 3 w: 3
        assertEquals(w30, null); // 3 not connected to 0 w: 0
    }

    @Test
    public void getV() {
    	directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new Node(0));
        g.addNode(new Node(1));
        g.addNode(new Node(2));
        g.addNode(new Node(3));
        
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        
        Collection<node_data> v = g.getV();
        Iterator<node_data> iter = v.iterator();
        
        while (iter.hasNext()) {
            node_data n = iter.next();
            assertNotNull(n);
        }
    }

//    @Test
//    public void hasEdge() {
//        int v = 10, e = v*(v-1)/2;
//        api.directed_weighted_graph g = graph_creator(v,e,1);
//        for(int i=0;i<v;i++) {
//            for(int j=i+1;j<v;j++) {
//                boolean b = g.hasEdge(i,j);
//                assertTrue(b);
//                assertTrue(g.hasEdge(j,i));
//            }
//        }
//    }

    @Test
    public void connect() {
    	directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new Node(0));
        g.addNode(new Node(1));
        g.addNode(new Node(2));
        g.addNode(new Node(3));
        
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        
        assertEquals(g.nodeSize(), 4);
        assertEquals(g.edgeSize(), 3);
        
        g.removeEdge(0,1);
        assertEquals(g.getEdge(1,0), null); //no edge 1->0
        
        g.removeEdge(2,1); //do nothing
        g.connect(0,1,1);
        g.connect(1, 0, 2);
        
        assertEquals(g.getEdge(0, 1).getWeight(), 1, 0.0001);
        assertEquals(g.getEdge(1, 0).getWeight(), 2, 0.0001);

    }


    @Test
    public void removeNode() {
    	directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new Node(0));
        g.addNode(new Node(1));
        g.addNode(new Node(2));
        g.addNode(new Node(3));
        
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        
        g.connect(3, 0, 3);
        
        g.connect(1, 2, 1);
        
        g.removeNode(4); //do nothing
        assertEquals(g.nodeSize(), 4);
        
        g.removeNode(0);
        assertEquals(g.getEdge(1,0), null);
        assertEquals(g.edgeSize(), 1);
        assertEquals(g.nodeSize(), 3);
        
        g.connect(1,2,3);
        int e = g.edgeSize();
        assertEquals(1,e);

    }

    @Test
    public void removeEdge() {
    	directed_weighted_graph g = new DWGraph_DS();
        g.addNode(new Node(0));
        g.addNode(new Node(1));
        g.addNode(new Node(2));
        g.addNode(new Node(3));
        
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        
        g.removeEdge(0,3);
        g.removeEdge(0,3);
        edge_data w = g.getEdge(0,3);
        assertEquals(w, null);
    }


    ///////////////////////////////////
    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
    	directed_weighted_graph g = new DWGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
            g.addNode(new Node(i));
        }
        // Iterator<api.node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the api.node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(directed_weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_data> V = g.getV();
        node_data[] nodes = new node_data[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
}



//package api;
//
//import static org.junit.Assert.*;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;
//
//
//import org.junit.Test;
//
//
//
//public class DWGraph_DSTest {
//
//	@Test
//	public final void testDWGraph_DS() {
//		api.DWGraph_DS graph=new api.DWGraph_DS();
//		assertEquals(graph.getMC(),0);
//	}
//
//	@Test
//	public final void testGetNode() {
//		api.DWGraph_DS graph=new api.DWGraph_DS();
//		api.DWGraph_DS.node node=graph.new node(6);
//		graph.addNode(node);
//		assertEquals(graph.getNode(6).getKey(),6);
//	}
//
//	@Test
//	public final void testGetEdge() {
//		api.DWGraph_DS graph=new api.DWGraph_DS();
//		api.DWGraph_DS.node node0=graph.new node(0);
//		graph.addNode(node0);
//		api.DWGraph_DS.node node1=graph.new node(1);
//		graph.addNode(node1);
//		graph.connect(node0.key, node1.key, 3);
//		
//		assertEquals(graph.getEdge(0, 1).getWeight(),3,0.001);
//	}
//
//	@Test
//	public final void testAddNode() {
//		api.DWGraph_DS graph=new api.DWGraph_DS();
//		api.DWGraph_DS.node node=graph.new node(7);
//		graph.addNode(node);
//		api.DWGraph_DS.node node2=graph.new node(8);
//		graph.addNode(node2);
//		api.DWGraph_DS.node node3=graph.new node(6);
//		graph.addNode(node3);
//		assertEquals(graph.getNode(7).getKey(),7);
//	}
//
//	@Test
//	public final void testConnect() {
//		api.DWGraph_DS graph=new api.DWGraph_DS();
//		api.DWGraph_DS.node node=graph.new node(7);
//		graph.addNode(node);
//		api.DWGraph_DS.node node2=graph.new node(8);
//		graph.addNode(node2);
//		api.DWGraph_DS.node node3=graph.new node(9);
//		graph.addNode(node3);
//		api.DWGraph_DS.node node4=graph.new node(5);
//		graph.addNode(node4);
//		graph.connect(7, 8, 25);
//		graph.connect(7, 9, 78);
//		graph.connect(9, 7, 67);
//		graph.connect(5, 9, 61);
//		int edge=graph.edgeSize();
//		assertEquals(edge,4);
//		graph.connect(7, 9, 777);
//		assertEquals(graph.getEdge(7, 9).getWeight(),777,0.001);
//	}
//
//	@Test
//	public final void testGetV() {
//		api.DWGraph_DS graph= new api.DWGraph_DS();
//		api.DWGraph_DS.node node7=graph.new node(7);
//		graph.addNode(node7);
//		api.DWGraph_DS.node node8=graph.new node(8);
//		graph.addNode(node8);
//		api.DWGraph_DS.node node9=graph.new node(9);
//		graph.addNode(node9);
//		api.DWGraph_DS.node node5=graph.new node(5);
//		graph.addNode(node5);
//		graph.connect(7, 8, 25);
//		graph.connect(7, 9, 78);
//		graph.connect(9, 7, 67);
//		graph.connect(5, 9, 61);
//        Collection<api.node_data> v = graph.getV();
//        Iterator<api.node_data> iter = v.iterator();
//        while (iter.hasNext())
//        {
//        api.node_data n = iter.next();
//        assertNotNull(n);
//        }
//	}
//
//	@Test
//	public final void testGetE() {
//		List<api.edge_data> e = new ArrayList<>();
//		api.DWGraph_DS graph= new api.DWGraph_DS();
//		api.DWGraph_DS.node node7=graph.new node(7);
//		graph.addNode(node7);
//		api.DWGraph_DS.node node8=graph.new node(8);
//		graph.addNode(node8);
//		api.DWGraph_DS.node node9=graph.new node(9);
//		graph.addNode(node9);
//		api.DWGraph_DS.node node5=graph.new node(5);
//		graph.addNode(node5);
//		graph.connect(7, 8, 25);
//		graph.connect(7, 9, 78);
//		graph.connect(9, 7, 67);
//		graph.connect(5, 9, 61);
//		graph.connect(5, 7, 25);
//		e=(List<api.edge_data>) graph.getE(node7.getKey());
//		
////		for (api.edge_data edge : e) {
////			//dest=edge.getDest();
////
////		}
//	    assertEquals(e.size(), 2);
//
//	}
//
//	@Test
//	public final void testRemoveNode() {
//		api.DWGraph_DS graph= new api.DWGraph_DS();
//		api.DWGraph_DS.node node1=graph.new node(1);
//		graph.addNode(node1);
//		graph.removeNode(node1.key);
//	    assertEquals(graph.getNode(node1.getKey()), null);
//	}
//
//	@Test
//	public final void testRemoveEdge() {
//		api.DWGraph_DS graph= new api.DWGraph_DS();
//		api.DWGraph_DS.node node7=graph.new node(7);
//		graph.addNode(node7);
//		api.DWGraph_DS.node node8=graph.new node(8);
//		graph.addNode(node8);
//		graph.connect(7, 8, 7751);
//	    api.edge_data edge2= graph.removeEdge(7, 8);
//	    assertEquals(edge2.getWeight(), 7751,0.0001);
//	   
//
//	}
//
//	@Test
//	public final void testEdgeSize() {
//		api.DWGraph_DS graph= new api.DWGraph_DS();
//		api.DWGraph_DS.node node0=graph.new node(0);
//		graph.addNode(node0);
//		api.DWGraph_DS.node node1=graph.new node(1);
//		graph.addNode(node1);
//		api.DWGraph_DS.node node2=graph.new node(2);
//		graph.addNode(node2);
//		api.DWGraph_DS.node node3=graph.new node(3);
//		graph.addNode(node3);
//		api.DWGraph_DS.node node4=graph.new node(4);
//		graph.addNode(node4);
//		api.DWGraph_DS.node node5=graph.new node(5);
//		graph.addNode(node5);
//		api.DWGraph_DS.node node6=graph.new node(6);
//		graph.addNode(node6);
//		graph.connect(0,1, 25);
//		graph.connect(0, 2, 5);
//		graph.connect(1, 3, 6);
//		graph.connect(3, 4, 9);
//		graph.connect(5, 3, 751);
//		graph.removeNode(5);
//	    assertEquals(graph.edgeSize(), 4);
//	}
//
//	@Test
//	public final void testGetMC() {
//		api.DWGraph_DS graph= new api.DWGraph_DS();
//		api.DWGraph_DS.node node7=graph.new node(7);
//		graph.addNode(node7);
//		api.DWGraph_DS.node node8=graph.new node(8);
//		graph.addNode(node8);
//		api.DWGraph_DS.node node9=graph.new node(9);
//		graph.addNode(node9);
//		api.DWGraph_DS.node node5=graph.new node(5);
//		graph.addNode(node5);
//		graph.connect(7, 8, 25);
//		graph.connect(7, 9, 78);
//		graph.connect(9, 7, 67);
//		graph.connect(5, 9, 61);
//		graph.removeEdge(9, 7);
//		api.node_data node=graph.removeNode(7);
//	    assertEquals(graph.getMC(), 12);
//
//	}
//
//	@Test
//	public final void testNodeSize() {
//		api.DWGraph_DS graph= new api.DWGraph_DS();
//		api.DWGraph_DS.node node7=graph.new node(7);
//		graph.addNode(node7);
//		api.DWGraph_DS.node node8=graph.new node(8);
//		graph.addNode(node8);
//		api.DWGraph_DS.node node9=graph.new node(9);
//		graph.addNode(node9);
//		api.DWGraph_DS.node node5=graph.new node(5);
//		graph.addNode(node5);
//		graph.connect(7, 8, 25);
//		graph.connect(7, 9, 78);
//		graph.connect(9, 7, 67);
//		graph.connect(5, 9, 61);
//		graph.removeEdge(9, 7);
//		api.node_data node=graph.removeNode(7);
//	    assertEquals(graph.nodeSize(), 3);
//
//	}
//
//}


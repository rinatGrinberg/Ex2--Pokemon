

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import api.*;
import org.junit.Test;

public class TestAlgo {



	@Test
	public final void testInit() {
		dw_graph_algorithms graph_ = new DWGraph_Algo();
    	directed_weighted_graph g= new DWGraph_DS();
    	graph_.init(g);
    	Node node8 = new Node(8);
    	
		g.addNode(node8);
    	graph_.getGraph().addNode(node8);
    	assertEquals(8, graph_.getGraph().getNode(8).getKey());
	}

	@Test
	public final void testGetGraph() {
		dw_graph_algorithms graph_ =new DWGraph_Algo();
		directed_weighted_graph g = new DWGraph_DS();
    	
    	assertEquals(null, graph_.getGraph());
	}

	@Test
	public final void testCopy() {
		dw_graph_algorithms graph_ =new DWGraph_Algo();
		directed_weighted_graph g=new DWGraph_DS();
    	graph_.init(g);
    	
    	Node node0 = new Node(0);
		g.addNode(node0);
		
		Node node1 = new Node(1);
		g.addNode(node1);
		
		Node node2= new Node(2);
		g.addNode(node2);
		
		directed_weighted_graph g1 = new DWGraph_DS();
    	g1 = (directed_weighted_graph) graph_.copy();
    	assertEquals(2, g1.getNode(2).getKey());	
	}

	@Test
	public final void testDjikstra() {
		dw_graph_algorithms graph_ = new DWGraph_Algo();
		directed_weighted_graph g= new DWGraph_DS();
    	graph_.init(g);
    	
    	g.addNode(new Node(0));
    	g.addNode(new Node(1));
    	g.addNode(new Node(2));
    	g.addNode(new Node(3));
    	g.addNode(new Node(4));
    	g.addNode(new Node(5));
    	g.addNode(new Node(6));
    	g.addNode(new Node(7));
    	g.addNode(new Node(8));
    	
    	graph_.getGraph().connect(1, 2, 8);
    	graph_.getGraph().connect(1, 0, 6);
    	graph_.getGraph().connect(1, 3, 5);
    	graph_.getGraph().connect(2, 3, 2);
    	graph_.getGraph().connect(3, 5, 7);
    	graph_.getGraph().connect(4, 2, 12);
    	graph_.getGraph().connect(5, 6, 1);
    	graph_.getGraph().connect(1, 6, 4);
    	
    	assertEquals(1,1);	
	}

	@Test
	public final void testIsConnected() {
		dw_graph_algorithms graph_ = new DWGraph_Algo();
		directed_weighted_graph g = new DWGraph_DS();
		
    	graph_.init(g);
    	
    	g.addNode(new Node(0));
    	g.addNode(new Node(1));
    	g.addNode(new Node(2));
    	g.addNode(new Node(3));
    	g.addNode(new Node(4));
    	g.addNode(new Node(5));

    	g.connect(3, 4, 6);
    	g.connect(2, 3, 6);

    	assertEquals(graph_.isConnected(), false);	
	}
	

	@Test
	public final void testShortestPathDist() {
		dw_graph_algorithms graph_ = new DWGraph_Algo();
		directed_weighted_graph g = new DWGraph_DS();
    	graph_.init(g);
    	
    	g.addNode(new Node(0));
    	g.addNode(new Node(1));
    	g.addNode(new Node(2));
    	g.addNode(new Node(3));
    	g.addNode(new Node(4));
    	g.addNode(new Node(5));
    	g.addNode(new Node(6));
    	
    	graph_.getGraph().connect(1, 2, 8);
    	graph_.getGraph().connect(1, 0, 6);
    	graph_.getGraph().connect(1, 3, 90);
    	graph_.getGraph().connect(2, 3, 2);
    	graph_.getGraph().connect(1, 4, 23);
    	graph_.getGraph().connect(3, 5, 7);
    	graph_.getGraph().connect(4, 5, 10);
    	graph_.getGraph().connect(2, 4, 3);
    	graph_.getGraph().connect(5, 6, 9);
    	graph_.getGraph().connect(1, 6, 3);
    	
    	assertEquals(graph_.shortestPathDist(1, 4) ,11 ,0.01);
	}

	@Test
	public final void testShortestPath() {
		dw_graph_algorithms graph_ = new DWGraph_Algo();
		directed_weighted_graph g = new DWGraph_DS();
    	graph_.init(g);
    	
    	g.addNode(new Node(0));
    	g.addNode(new Node(1));
    	g.addNode(new Node(2));
    	g.addNode(new Node(3));
    	g.addNode(new Node(4));
    	g.addNode(new Node(5));
    	g.addNode(new Node(6));
    	
    	graph_.getGraph().connect(1, 2, 8);
    	graph_.getGraph().connect(1, 0, 6);
    	graph_.getGraph().connect(1, 3, 5);
    	graph_.getGraph().connect(2, 3, 2);
    	graph_.getGraph().connect(3, 5, 7);
    	graph_.getGraph().connect(4, 2, 12);
    	graph_.getGraph().connect(5, 6, 1);
    	graph_.getGraph().connect(1, 6, 4);
    	
    	List<node_data> sp = new ArrayList<>();
    	sp = graph_.shortestPath(1, 5);
    	
        int[] checkKey = {1, 3, 5};
        int i = 0;
        for(node_data n: sp) {
        	//assertEquals(n.getTag(), checkTag[i]);
        	assertEquals(n.getKey(), checkKey[i]);
        	i++;
        }
    	
//    	String dis_of_node="";
//    	for (api.node_data node : sp) {
//    		dis_of_node=dis_of_node+ node.getKey() +"->";
//    		System.out.println(node.getKey());
//		}
//    	assertEquals(dis_of_node, "1->3->5->");
    	
    	List<node_data> sp2 = graph_.shortestPath(3, 2);

    	assertEquals(sp2, null);
    	
//        int[] checkKey2 = {3, 5};
//        
//        int i2 = 0;
//        
//        for(api.node_data n: sp2) {
//        	//assertEquals(n.getTag(), checkTag[i]);
//        	assertEquals(n.getKey(), checkKey2[i]);
//        	i2++;
//        }
//    	
//    	String dis_of_node2="";
//    	assertEquals(dis_of_node2, "->");
//    	
    	
    	
    	
	}
	
	
	
	@Test
	public final void save_load() {
		
		dw_graph_algorithms graph_ = new DWGraph_Algo();
		directed_weighted_graph g = new DWGraph_DS();
    	graph_.init(g);
    	
    	g.addNode(new Node(0));
    	g.addNode(new Node(1));
    	g.addNode(new Node(2));
    	g.addNode(new Node(3));
    	g.addNode(new Node(4));
    	g.addNode(new Node(5));
    	g.addNode(new Node(6));
    	
    	graph_.getGraph().connect(1, 2, 8);
    	graph_.getGraph().connect(1, 0, 6);
    	graph_.getGraph().connect(1, 3, 5);
    	graph_.getGraph().connect(2, 3, 2);
    	graph_.getGraph().connect(3, 5, 7);
    	graph_.getGraph().connect(4, 2, 12);
    	graph_.getGraph().connect(5, 6, 1);
    	graph_.getGraph().connect(1, 6, 4);
    	
		String str = "gabi-test.json";
		graph_.save(str);
		
		dw_graph_algorithms graph_2 = new DWGraph_Algo();
		directed_weighted_graph g2 = new DWGraph_DS();
    	graph_2.init(g2);
    	
    	g2.addNode(new Node(0));
    	g2.addNode(new Node(1));
    	g2.addNode(new Node(2));
    	g2.addNode(new Node(3));
    	g2.addNode(new Node(4));
    	g2.addNode(new Node(5));
    	g2.addNode(new Node(6));
    	
    	graph_2.getGraph().connect(1, 2, 8);
    	graph_2.getGraph().connect(1, 0, 6);
    	graph_2.getGraph().connect(1, 3, 5);
    	graph_2.getGraph().connect(2, 3, 2);
    	graph_2.getGraph().connect(3, 5, 7);
    	graph_2.getGraph().connect(4, 2, 12);
    	graph_2.getGraph().connect(5, 6, 1);
    	graph_2.getGraph().connect(1, 6, 4);
		
    	graph_.load(str);
    	graph_.save("MyCheck.json");
    	
        assertEquals(g, g2);
        g.removeNode(0);
        assertNotEquals(g, g2);
//        
//    	api.directed_weighted_graph g0 = DWGraph_DSTest.graph_creator(10,30,1);
//    	api.dw_graph_algorithms ag0 = new api.DWGraph_Algo();
//        ag0.init(g0);
//        String str = "g0.obj";
//        ag0.save(str);
//        api.directed_weighted_graph g1 = DWGraph_DSTest.graph_creator(10,30,1);
//        ag0.load(str);
//        assertEquals(g0,g1);
//        g0.removeNode(0);
//        assertNotEquals(g0,g1);
	}

	

}

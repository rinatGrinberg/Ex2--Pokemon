ID:314972902 Rinat Grinberg Gabi Dadashov 


The task was divided into 2 main parts:

The first part was implementation of the directed graph that used for the second part in Java:

The graph contains an implementation of 3 interfaces
node_data.
geo_location
edge_location
edge_data
dw_graph_algorithms
directed_weighted_graph

node_data and geo_location:
In order to implement the interface we built a new class (Node) that contian simple methods.
In addition, we added getLocation() and setLocation(geo_location p) to implement geo_location interface.
the last method that we added to this class was compareTo(node_data other) in order to calculate the distance for the next implementation class(dw_graph_algorithms) that we asked to do.
The complexity of the all methods was O(1)

edge_data:
In this class we created a new class(Edge) and we added src and dest. the class implemented edge_location due to private edge_data e; private double ratio;
The complexity of the all methods was O(1)


In the DWGraph_DS class we defined three hashmaps data structures:

private HashMap<Integer, node_data> nodes; 
private HashMap<Integer, HashMap<Integer, edge_data>> neighbors;
private HashMap<Integer, List<Integer>> connected_to;

The big change in this class was *connected_to* because this hashmap helped us to remove node In both directions successfully.

The getNode(int key) method:
allows to get the requested node in the nodes data structure.
the method checks if the key( the ID of each node) is already exist. if yes the methods return null. esle return the node.
complexity O(1)

The getEdge(int src, int dest) method:
Allows to get the requested edge in the neighbors data structure. 
the method checks if the key( the ID of each node) is already exist, if the nodes are not null. if the edge exists. 
if all the conditions are true. the nethod will return the edge. else retuen null.
complexity O(1)

The addNode(node_data n) method:
Allows to add a new node in the nodes data structure.
the method checks if the node already exist, if yes,the method doesnt add the node. if the node not exist the method add a new node to nodes data structure.
the mode_count Increases by 1
complexity O(1)


The connect(int src, int dest, double w) method:
The method adds a new edge between 2 nodes(src, dest). 
the method checks several conditions,in order to connect the nodes:
if (w >= 0),
if (this.nodes.containsKey(src) && this.nodes.containsKey(dest) && this.neighbors.containsKey(src) && this.neighbors.containsKey(dest)) 
if (this.nodes.get(src) != null && this.nodes.get(dest) != null)
if(!this.neighbors.get(src).containsKey(dest)). 
Existence of all conditions makes it possible to connect the new edge.
the mode_count Increases by 1
complexity O(1)

The getV() method:
Return the all nodes( Collection<node_data>) in the nodes data structure.
complexity O(1)

The getE(int node_id) method:
Return the all edges( Collection<edge_data>) collection representing all the edges getting out of the given node (ID).
The methods uses neighbors.get(node_id).values() data structure to return the edges.
complexity O(1)

The removeNode(int key) method:
The method remove node by ID. 
the method checks 2 conditions,in order to remove the nodes:
if (this.nodes.containsKey(key) && this.neighbors.containsKey(key))
after that the method disconnect edges that related to this node.
edge_count decrease by 1
mode_count Increases by 1
complexity O(|V|)

The removeEdge(int src, int dest) method:
The mothod disconnect edge between src and dest.
The method checks several conditions,in order to disconnect the edge:
if (this.nodes.containsKey(src) && this.nodes.containsKey(dest) && this.neighbors.containsKey(src) && this.neighbors.containsKey(dest)) 
if (this.nodes.get(src) != null && this.nodes.get(dest) != null)			
if (this.neighbors.get(src).containsKey(dest) && src != dest)
Existence of all conditions makes it possible to disconnect necessary edge.
edge_count decrease by 1
mode_count Increases by 1
complexity O(1)

The edgeSize() method:
Return the edgeSize in the graph.
complexity O(1)

The getMC() method:
Return the mode_count in the graph.
complexity O(1)

The nodeSize() method:
Return the node_size in the graph.
complexity O(1)


dw_graph_algorithms
In order to implement the interface we built a new class (DWGraph_Algo) that contian complexity methods.
The class contains:
private directed_weighted_graph graph;
private HashMap<Integer, Integer> prev; // { n0.key = null, n1.key = n0.key, n2.key = n0.key }
private HashMap<Integer, Double> dist; // { n0.key = n0 distance from source }
this data structure will use for the DFS and for the Djikstra	

The init(directed_weighted_graph g) method:
Initialize the graph with directed_weighted_graph g.
In addition declaration on prev and dist to this class.
this.prev = new HashMap<>(); { n0.key = null, n1.key = n0.key, n2.key = n0.key }
this.dist = new HashMap<>(); { n0.key = n0 distance from source }
complexity O(1)

The getGraph() method:
returns the graph that related to the class.
complexity O(1)

The copy() method:
returns directed_weighted_graph that was copied
the method creates a new directed_weighted_graph and copying the graph from the class
the method overs with for on all the nodes and all the edges.
complexity O(|E*V|)

The djikstra() method:
the method calculate the shortest distance between src to dest. use PriorityQueue<node_data> for save the lowest distance 
PriorityQueue<node_data> q = new PriorityQueue<node_data>();
Initialize the distance in the dist to Double.MAX_VALUE, Initialize the prev in the prev to null and setWieght to 0.0 for each node.
and finally q.add(node);
while (!q.isEmpty()) :
the methods calculate the distance :
double alt = this.dist.get(u.getKey()) + edgeNeighbor.getWeight();
if (alt < this.dist.get(edgeNeighbor.getDest()))
this.dist.put(edgeNeighbor.getDest(), alt);
this.prev.put(edgeNeighbor.getDest(), u.getKey());
graph.getNode(edgeNeighbor.getDest()).setWeight(alt);
and finally put in the PriorityQueue the result:
q.remove(graph.getNode(edgeNeighbor.getDest()));
q.add(graph.getNode(edgeNeighbor.getDest()));
complexity O(|E*V|)

The shortestPathDist(int src, int dest) method:
The method operates djikstra in order to return the lowest distance.
complexity O(|E*V|)

The shortestPath(int src, int dest) method:
The method operates djikstra in order to return list of node_data of this Path.
creating List of node_data, and the result of the djikstra 
put on this list and reverse the nodes.
complexity O(|E*V|)

1)The DFS() method:
in order to calculate if the graph is connected we used DFS. 
we declared boolean array:
boolean visited[]=new boolean[graph.nodeSize()];
This array served us to know if from any node it is possible to reach our neighbor.

2)The DFS_init(int vertex,boolean visited[]) method:
Initialize the array: visited[vertex]=true.
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

the complexity of all DFS is O(|E*V|)

The isConnected() method:
The method operates DFS() method:
if DFS>1 the method returns false ,else true.
because (connectedComponents>1)
complexity O(|E*V|)


The load(String file) method:
this method try to read the gson file ti graph. 
the method use GsonBuilder ,GraphJsonDeserializer(), Gson Object and filereader.
the methods convert from json to string in order to read and finally init the graph with the result.
the methids return true if the process succeeded.
complexity O(|E*V|)

The save(String file) method:
save the result into file.
the method use GsonBuilder, Gson Object and FileWriter
Passes about all the nodes and all the edges and insert them to List<edge_data>.
for end the process the methods convert gson.toJson(answerList,fWriter).
if the process was fail the IOException operates and return false.

TESTS:
creating test files and sampled end cases like inserting / lowering a side twice null
And various exceptions.
*************************************************************************************************************************************************************************************************************************************************************************************
The secomd part was implementation of the Pokemon Game that include writing several function in order to play:

void run() punction:
this function allows to run each level that the user wants.
this function uses init() function in order to Initialize the selected graph.

void moveAgants(game_service game, directed_weighted_graph gg) function:




void find_ (List<CL_Pokemon> fruits, CL_Agent agent):


int nextNode(directed_weighted_graph g, int src):


HashMap<Integer,Integer> MaxValue(directed_weighted_graph g,game_service game,int rs):
This function works at the beginning of a game to place the agents at a node around which the highest Pokemons value.
use 3 Hashmaps and PriorityQueue: 
HashMap<Integer,Integer> MaxNodesForAgents = new HashMap<>();-> put each agent in maximum node HashMap<Integer,Double> Nodes_ValueMax = new HashMap<>();-> claculate the max value for each node that has poemon.
HashMap<Double, CL_Pokemon> asd = new HashMap<>();-> save the value of each pokemon in the src node.
PriorityQueue<Double> pQueue = new PriorityQueue<>(rs, Collections.reverseOrder());
The goal of the PriorityQueue is to pull the best values of the pokemons any time.
Some cate that we faced: 
if we have already pokemon in node and we need to add another pokemon.
complexity O(n+m|)
n=pokemon list
m=agent list


build_graph(String g):
Initialize the selected graph:
create a DWGraph_Algo and DWGraph_DS.
declare a  JSONObject in order to read the data 
using Loop for in order to move on each node and edge.
double x = Double.parseDouble(p_[0]);
double y = Double.parseDouble(p_[1]);
double z = Double.parseDouble(p_[2]);
declare a new node and set the location:
n.setLocation(new Point3D(x, y, z));
complexity O(|V+E|)
n=pokemon list
m=agent list


init(game_service game):
Initialize the whole game:
 game.getGraph() , game.getPokemons(), paths = new HashMap<>(), ag_dest = new HashMap<>() and ag_src = new HashMap<>();
complexity O(1))

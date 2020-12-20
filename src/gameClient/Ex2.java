package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class Ex2 implements Runnable {

    private static LoginFrame loginFrame;

    private static int id;
    private static int level;

    private static directed_weighted_graph graph;
    private static dw_graph_algorithms _algo;

    private static MyFrame _win;
    private static Arena _ar;

    // < Agent , Fruit , Path >

    private static HashMap<Integer, List<node_data>> paths; // { agent_id = {shortests path}}

    private static HashMap<Integer, Integer> ag_src; // search, add, remove, random item in o(1)
    private static HashMap<Integer, Integer> ag_dest;

//    private static List<CL_Pokemon> pokemons;

    public static void main(String[] a) {

      if (a.length > 0) {
          id = Integer.parseInt(a[0]);
          level = Integer.parseInt(a[1]);
      }



       else {

//
          loginFrame = new LoginFrame("Welcome to the game");

          boolean flag = loginFrame.getStatus();

          while (!flag) {

              flag = loginFrame.getStatus();

              try {
                  Thread.sleep(50);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }

              if (flag) {

                  level = loginFrame.getLevel();
                  id = loginFrame.getid();
                  System.out.println(id);
                  break;
              }

          }

          Thread client = new Thread(new Ex2());
          client.start();

          System.out.println("you have chosen level: " + level);

//        }


      }


    }

    @Override
    public void run() {



//        int scenario_num = 11;
        game_service game = Game_Server_Ex2.getServer(level); // you have [0,23] games

        //	int id = 999;
//        game_service game = Game_Server_Ex2.getServer(level); // you have [0,23] games
        game.login(id);

        String g = game.getGraph();
        String pks = game.getPokemons();


//        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();

        //load(a)
        System.out.println(g);

        init(game);

        game.startGame();

//        _ar.setAgents(Arena.getAgents(game.getAgents(), gg));
        _ar.setAgents(Arena.getAgents(game.getAgents(), graph)); //update

        for (CL_Agent agent : _ar.getAgents()) {
            paths.put(agent.getID(), new ArrayList<>());
            ag_src.put(agent.getID(), -1);
            ag_dest.put(agent.getID(), -1);
        }

        _win.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString());
        int ind = 0;
        long dt = 100;

        while (game.isRunning()) {
//            moveAgants(game, gg);
            moveAgants(game, graph); //update

            try {
                if (ind % 1 == 0) {

                    List<String> list = new ArrayList<>();

                    for (CL_Agent agent : _ar.getAgents())
                        list.add("Agent: " + String.valueOf(agent.getID()) + ", Speed: " + String.valueOf(agent.getSpeed()));

//                    list.add(_ar.getAgents().toString());
                    list.add("Time to end: " + String.valueOf(game.timeToEnd()) + " ms");

                    _ar.set_info(list);

                    _win.repaint();
                }
                Thread.sleep(dt);
                ind++;

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String res = game.toString();
        System.out.println(res);

        System.exit(0);
    }

    /**
     * Moves each of the agents along the edge,
     * in case the agent is on a node the next destination (next edge) is chosen (randomly).
     *
     * @param game
     * @param gg
     * @param
     */
    private static void moveAgants(game_service game, directed_weighted_graph gg) {


        String lg = game.move();
//        System.out.println(lg);
        List<CL_Agent> log = Arena.getAgents(lg, gg);

        _ar.setAgents(log);

//
//        try {
//            Thread.sleep(50000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//

        //ArrayList<OOP_Point3D> rs = new ArrayList<OOP_Point3D>();

        String fs = game.getPokemons();

        List<CL_Pokemon> ffs = Arena.json2Pokemons(fs);
//        List<CL_Pokemon> ffs = Arena.getPokemons();
        _ar.setPokemons(ffs);

        for (int i = 0; i < log.size(); i++) {

            CL_Agent ag = log.get(i);

            int id = ag.getID();

            int dest = ag.getNextNode();
            int src = ag.getSrcNode();

            double v = ag.getValue();

            if (dest == -1) {

                List<node_data> list = paths.get(id);

//                System.out.println("list: " + paths.get(id));

                if (paths.get(id).size() == 1 || paths.get(id).isEmpty()) {
                    ag_src.put(id, -1);
                    ag_dest.put(id, -1);
                    find_(_ar.getPokemons(), ag);
                }

//                System.out.println("list: " + paths.get(id));

                dest = paths.get(id).remove(1).getKey();

                game.chooseNextEdge(ag.getID(), dest);

                System.out.println("Agent: " + id + ", val: " + v + "   turned to node: " + dest);

            }

//            int src1 = ag.getSrcNode();
//
//
//            int dest_1 = find(ffs, src1, dest, i);
//            game.chooseNextEdge(ag.getID(), dest_1);

//            if(dest_1==-1) {game.stopGame();}


        }
    }


    private static void find_ (List<CL_Pokemon> fruits, CL_Agent agent) {

        PriorityQueue<Double> q = new PriorityQueue<>((x, y) -> Double.compare(y, x));
        HashMap<Double, CL_Pokemon> pr_to_pokemon = new HashMap<>();
        HashMap<Double, List<node_data>> pr = new HashMap<>();

        //f(x) = f.value / shortestPath ** 2

//        System.out.println("find_: " + pokemons_taken);

        for (CL_Pokemon fruit : fruits) {
//            System.out.println("find_ fruit: " + fruit);

            int s = ag_src.get(agent.getID());
            int d = ag_dest.get(agent.getID());

//            if (s != fruit.get_edge().getSrc() && d != fruit.get_edge().getDest()) {
            double a = _algo.shortestPathDist(agent.getSrcNode(), fruit.get_edge().getSrc());
            double res = fruit.getValue() / Math.pow(a, 2);

            q.add(res);
            pr_to_pokemon.put(res, fruit);
//            }
        }

        double d = q.poll();
        CL_Pokemon p = pr_to_pokemon.get(d);

//        HashMap<Integer, Integer> l = pokemons_taken.get(agent);
//        l.put(p.get_edge().getSrc(), p.get_edge().getDest());
//        pokemons_taken.put(agent, l);

        ag_src.put(agent.getID(), p.get_edge().getSrc());
        ag_dest.put(agent.getID(), p.get_edge().getDest());


        List<node_data> path = _algo.shortestPath(agent.getSrcNode(), p.get_edge().getSrc());

        path.add(_algo.getGraph().getNode(p.get_edge().getDest()));

        System.out.println("agent: " + agent.getID() + ", path: " + path);

        paths.put(agent.getID(), path);

//        _ar.setPokemons(fruits);

    }

    /**
     * a very simple random walk implementation!
     * @param g
     * @param src
     * @return
     */
    private static int nextNode(directed_weighted_graph g, int src) {
        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int)(Math.random()*s);
        int i=0;
        while(i<r) {itr.next();i++;}
        ans = itr.next().getDest();
        return ans;
    }

    private static HashMap<Integer,Integer> MaxValue(directed_weighted_graph g,game_service game,int rs)
    {
        String StringJsonPokemon =  game.getPokemons();
        List<CL_Pokemon> PokemonsList = Arena.json2Pokemons(StringJsonPokemon);
        _ar.setPokemons(PokemonsList);

        HashMap<Integer,Integer> MaxNodesForAgents = new HashMap<>();
        HashMap<Integer,Double> Nodes_ValueMax = new HashMap<>();

        int src;
        double a;


        HashMap<Double, CL_Pokemon> asd = new HashMap<>();
        PriorityQueue<Double> pQueue = new PriorityQueue<>(rs, Collections.reverseOrder());


        for (CL_Pokemon P : _ar.getPokemons()) //update
        {
            src=P.get_edge().getSrc();
            if(!Nodes_ValueMax.containsKey(src))
            {
                Nodes_ValueMax.put(src, P.getValue());

                g.getNode(src).setTag((int)P.getValue());

                pQueue.add(P.getValue());
                asd.put(P.getValue(), P);


            }  /////Update The Node
            else
            {
                a = Nodes_ValueMax.get(src);
                Nodes_ValueMax.put(src,a+(int)P.getValue());

                g.getNode(src).setTag((int)a+(int)P.getValue());

                pQueue.add(a + P.getValue());
                asd.put(a + P.getValue(), P);


            }

        }
//        System.out.println(Nodes_ValueMax);

        int counter =-1;

        for (int i=0 ;i < rs;i++) {

            Double x = pQueue.poll();
            CL_Pokemon f = asd.get(x);

//            System.out.println("fruit: " + f);

            edge_data e = f.get_edge();

            MaxNodesForAgents.put(i, e.getSrc());

            /// run over graph edges
//            for (node_data node : g.getV()) {
//
//
//                if (node.getTag() == pQueue.peek()) {
//                    System.out.println(pQueue);
//
//                    pQueue.poll();
//                    counter++;
//                    MaxNodesForAgents.put(counter, node.getKey());
//                }
//
//                if (counter == rs -1) {
//                    break;
//                }
//            }


        }

//        System.out.println(MaxNodesForAgents);
        return MaxNodesForAgents;

    }

    private directed_weighted_graph build_graph(String g) {
//        System.out.println("g: " + g);

        _algo = new DWGraph_Algo(); // NOT EFFICIENT
        directed_weighted_graph graph = new DWGraph_DS();

        JSONObject obj;

        try{
            obj = new JSONObject(g);

            JSONArray nodes = obj.getJSONArray("Nodes");
            JSONArray edges = obj.getJSONArray("Edges");

            for (int i = 0; i < nodes.length(); i++) {

                String posi = nodes.getJSONObject(i).getString("pos");
                int id = nodes.getJSONObject(i).getInt("id");

                node_data n = new Node(id);

                String[] p_ = posi.split(",");

                double x = Double.parseDouble(p_[0]);
                double y = Double.parseDouble(p_[1]);
                double z = Double.parseDouble(p_[2]);

                n.setLocation(new Point3D(x, y, z));

                graph.addNode(n);

            }

            for (int i = 0; i < edges.length(); i++) {

                int source = edges.getJSONObject(i).getInt("src");
                int dest = edges.getJSONObject(i).getInt("dest");
                double weight = edges.getJSONObject(i).getDouble("w");

//				System.out.println(source + "," + dest + ", " + weight);

                graph.connect(source, dest, weight);

            }

        }catch (JSONException e) {
            e.printStackTrace();
        }

        _algo.init(graph);

        return graph;
    }



    private void init(game_service game) {

        String g = game.getGraph();
        String fs = game.getPokemons();

        paths = new HashMap<>();
        ag_dest = new HashMap<>();
        ag_src = new HashMap<>();



//		directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();

//        directed_weighted_graph g2 = build_graph(g);
        graph = build_graph(g); //update

        _ar = new Arena();
//        _ar.setGraph(g2);
        _ar.setGraph(graph); ; //update

        List<CL_Pokemon> fruits = Arena.json2Pokemons(fs);

        _ar.setPokemons(fruits);

        _win = new MyFrame("test Ex2");
        _win.setSize(1000, 700);
        _win.update(_ar);

        _win.show();

        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);

            JSONObject ttt = line.getJSONObject("GameServer");

            int rs = ttt.getInt("agents");
            System.out.println("rs: "+rs);

            System.out.println(info);
            System.out.println(game.getPokemons());

            int src_node = 0;  // arbitrary node, you should start at one of the pokemon

            for(CL_Pokemon fruit : _ar.getPokemons()) {
//                Arena.updateEdge(fruit, g2);
                Arena.updateEdge(fruit, graph);
//                System.out.println("////");

            }

            directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();
            //load(graph)

//            HashMap<Integer,Integer> A = MaxValue(gg,game,rs);
            HashMap<Integer,Integer> A = MaxValue(graph,game,rs); //update

            System.out.println(A);
            for(int i=0; i<A.size();i++)
            {

                paths.put(A.get(i), new ArrayList<>());
                game.addAgent(A.get(i));
                System.out.println(A.get(i));


            }


        }
        catch (JSONException e) {e.printStackTrace();}
    }
}
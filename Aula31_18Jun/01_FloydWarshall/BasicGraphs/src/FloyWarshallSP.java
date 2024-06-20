public class FloyWarshallSP {

    int[][] pathTo;
    double[][] weightTo;
    EdgeWeightedDiGraph g;

    public FloyWarshallSP(EdgeWeightedDiGraph refGraph){

        pathTo = new int[refGraph.V()][refGraph.V()];
        weightTo = new double[refGraph.V()][refGraph.V()];
        g=refGraph;

        // inicialização básica das matrizes
        for(int src=0; src<refGraph.V(); src++)
            for(int tgt=0; tgt<refGraph.V(); tgt++){              
              pathTo[src][tgt]=((src==tgt)?src:-1);
              weightTo[src][tgt]=((src==tgt)?0:Double.POSITIVE_INFINITY);
            }

        //inicialização baseada nas arestas do grafo
        for(DiEdge e: refGraph.edges()){
            pathTo[e.source()][e.target()]=e.source();
            weightTo[e.source()][e.target()]=e.weight();
        }            

        //Operar o floyd-warshall
        for(int k=0; k<refGraph.V(); k++)
            for(int i=0; i<refGraph.V(); i++)
                for(int j=0; j<refGraph.V(); j++){
                    //i e j são um possível caminho conhecido
                    // a alternativa para sair de i e chegar em j é passando por k
                    if(weightTo[i][j] > (weightTo[i][k]+weightTo[k][j]) ){
                        weightTo[i][j] = (weightTo[i][k]+weightTo[k][j]);
                        pathTo[i][j]=pathTo[k][j];
                    }
                }
    }

    public void dump(){
        for(int src=0; src<g.V(); src++){
            System.out.println();
            for(int tgt=0; tgt<g.V(); tgt++)
                if(pathTo[src][tgt]>=0)
                    System.out.println(src+" -> "+tgt+": "+weightTo[src][tgt]);
        }
    }

    public void dumpPath(){
        for(int src=0; src<g.V(); src++){
            System.out.println("\n"+src);
            for(int tgt=0; tgt<g.V(); tgt++){
                System.out.println("  ("+tgt+"): "+ pathTo[src][tgt]);
                int s=src, t=tgt;
            }
        }
    }


    public static void main(String[] args) {
        // equivalente na elaboração do PRIM/KRUSKAL
        In in = new In(args[0]);
        EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(in);

        FloyWarshallSP mst = new FloyWarshallSP(G);

        System.out.println(G.toDot());
        System.out.println();
        //mst.dumpPath();
        mst.dump();

        // FECHAMENTO MOSTRANDO OS CAMINHOS
        /*
        for (Edge e : mst.edges()) {
            StdOut.println(e);
        }
        StdOut.printf("%.5f\n", mst.weight());
        System.out.println(G.toDot());
        */
    }


    
}

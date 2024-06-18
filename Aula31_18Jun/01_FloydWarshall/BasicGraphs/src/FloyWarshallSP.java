public class FloyWarshallSP {

    int[][] pathTo;
    double[][] weightTo;

    public FloyWarshallSP(EdgeWeightedGraph refGraph){

        pathTo = new int[refGraph.V()][refGraph.V()];
        weightTo = new double[refGraph.V()][refGraph.V()];

        for(int src=0; src<refGraph.V(); src++)
            for(int tgt=0; tgt<refGraph.V(); tgt++){
                if(src==tgt){
                    pathTo[src][tgt]=src;
                    weightTo[src][tgt]=0;
                }
            }
    }

    public static void main(String[] args) {
        // equivalente na elaboração do PRIM/KRUSKAL
        In in = new In(args[0]);
        EdgeWeightedGraph G = new EdgeWeightedGraph(in);

        FloyWarshallSP mst = new FloyWarshallSP(G);

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

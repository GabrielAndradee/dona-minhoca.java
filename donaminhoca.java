import java.util.*;

public class DonaMinhoca {
    static int n;
    static List<Integer>[] adj;
    static int[] dist;
    static int[] parent;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        n = sc.nextInt();
        adj = new List[n + 1];
        for (int i = 1; i <= n; i++) {
            adj[i] = new ArrayList<Integer>();
        }

        for (int i = 1; i < n; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            adj[u].add(v);
            adj[v].add(u);
        }

        dist = new int[n + 1];
        parent = new int[n + 1];
        bfs(1); // faz uma busca em largura a partir do nó 1 para encontrar o nó mais distante
        int farthestNode = farthest();
        bfs(farthestNode); // faz uma segunda busca em largura a partir do nó mais distante para encontrar o outro nó mais distante
        int diameter = dist[farthest()];
        int count = countDiameterNodes(); // conta quantos nós estão no diâmetro

        if (count == 2) { // se há apenas dois nós no diâmetro, não é possível construir um ciclo maior
            System.out.println(diameter);
            System.out.println(1);
        } else { // caso contrário, pode-se construir ciclos maiores a partir de qualquer um desses nós
            int maxCycleLength = diameter + 1;
            int waysToBuildCycle = count * (count - 1) / 2; // número de maneiras de escolher dois nós distintos no diâmetro
            System.out.println(maxCycleLength);
            System.out.println(waysToBuildCycle);
        }
    }

    static void bfs(int s) {
        Arrays.fill(dist, -1);
        dist[s] = 0;
        parent[s] = 0;

        Queue<Integer> queue = new LinkedList<Integer>();
        queue.offer(s);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : adj[u]) {
                if (dist[v] == -1) {
                    dist[v] = dist[u] + 1;
                    parent[v] = u;
                    queue.offer(v);
                }
            }
        }
    }

    static int farthest() {
        int farthest = 1;
        for (int i = 2; i <= n; i++) {
            if (dist[i] > dist[farthest]) {
                farthest = i;
            }
        }
        return farthest;
    }

    static int countDiameterNodes() {
        List<Integer> diameter = new ArrayList<Integer>();
        int u = farthest();
        while (u != 0) {
            diameter.add(u);
            u = parent[u];
        }
        return diameter.size();
    }
}

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    private int dim;
    private Cube[] cubesUsed;
    private Letter[][] letterConfiguration;
    private SimpleGraph<Letter, DefaultEdge> graph;
    private Random rand;
    private String cubesFilename;

    public Board(int dim) throws IOException {
        this.dim = dim;
        cubesUsed = new Cube[dim * dim];
        this.rand = new Random();
        this.letterConfiguration = new Letter[dim][dim];
        SimpleGraph<Letter, DefaultEdge> test = new SimpleGraph<>(DefaultEdge.class);
        FileReader fr = new FileReader("core/data/English_5_standard_board_new.csv");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        int i = 0;
        while (line != null) {
            Cube nextCube = new Cube(line.split(","));
            cubesUsed[i] = nextCube;
            i += 1;
            line = br.readLine();
        }
    }

    public void changeCubes() {

    }

    public void rollCubes() {
        for (Cube cube : cubesUsed) {
            cube.roll();
        }
    }

    public SimpleGraph<Letter, DefaultEdge> initializeGraph(Letter[][] letterConfiguration) {
        SimpleGraph<Letter, DefaultEdge> result = new SimpleGraph<>(DefaultEdge.class);
        for (Letter[] row : letterConfiguration) {
            for (Letter elem : row) {
                result.addVertex(elem);
            }
        }
        this.graph = result;
        addNeighbors();
        return result;
    }

    public void placeLetters() {
        List<Cube> tempList = new ArrayList<>(Arrays.asList(cubesUsed));
        int i = 0;
        while (!tempList.isEmpty()) {
            int index = rand.nextInt(0, tempList.size());
            Letter next = tempList.remove(index).toLetter();
            int j = i / dim;
            letterConfiguration[j][Math.floorMod(i, dim)] = next;
            i = i + 1;
        }
    }

    public void addNeighbors() {
        for (int row = 0; row < this.dim; row++) {
            for (int col = 0; col < this.dim; col++) {
                for (int i = -1; i < 2; i++) {
                    for (int j = -1; j < 2; j++) {
                        int row_i = row + i;
                        int col_j = col + j;
                        if (row_i >= 0 && row_i < this.dim && col_j >= 0 && col_j < this.dim && (row_i != row || col_j != col)) {
                           graph.addEdge(this.letterConfiguration[row][col], this.letterConfiguration[row_i][col_j]);
                        }
                    }
                }
            }
        }
    }

    public void populate() {
        rollCubes();
        placeLetters();
        initializeGraph(this.letterConfiguration);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Letter[] row : this.letterConfiguration) {
            for (Letter elem : row) {
                int len = elem.getLetter().length();
                if (len == 1) {
                    result.append(elem.getLetter()).append("  ");
                } else {
                    result.append(elem.getLetter()).append(" ");
                }
            }
            result.append("\n");
        }
        return result.toString();
    }
}

package h1;

import java.util.ArrayList;

public class H1_main {

    public static void main(String[] args) {

        int passed = 0;
        int total = 0;

        // ========= Test 1: Leeres Feld bleibt leer =========
        total++;
        passed += runTest("T1 Empty stays empty",
                new int[][]{
                        {0,0,0},
                        {0,0,0},
                        {0,0,0}
                },
                1,
                new int[][]{
                        {0,0,0},
                        {0,0,0},
                        {0,0,0}
                });

        // ========= Test 2: Einzelne lebende Zelle stirbt =========
        total++;
        passed += runTest("T2 Single cell dies",
                new int[][]{
                        {0,0,0},
                        {0,1,0},
                        {0,0,0}
                },
                1,
                new int[][]{
                        {0,0,0},
                        {0,0,0},
                        {0,0,0}
                });

        // ========= Test 3: Block (Still life) bleibt stabil =========
        total++;
        passed += runTest("T3 Block stays",
                new int[][]{
                        {0,0,0,0},
                        {0,1,1,0},
                        {0,1,1,0},
                        {0,0,0,0}
                },
                3,
                new int[][]{
                        {0,0,0,0},
                        {0,1,1,0},
                        {0,1,1,0},
                        {0,0,0,0}
                });

        // ========= Test 4: Blinker oszilliert (Periode 2) =========
        total++;
        passed += runTest("T4 Blinker after 1 gen",
                new int[][]{
                        {0,0,0,0,0},
                        {0,0,1,0,0},
                        {0,0,1,0,0},
                        {0,0,1,0,0},
                        {0,0,0,0,0}
                },
                1,
                new int[][]{
                        {0,0,0,0,0},
                        {0,0,0,0,0},
                        {0,1,1,1,0},
                        {0,0,0,0,0},
                        {0,0,0,0,0}
                });

        total++;
        passed += runTest("T4b Blinker after 2 gen (back)",
                new int[][]{
                        {0,0,0,0,0},
                        {0,0,1,0,0},
                        {0,0,1,0,0},
                        {0,0,1,0,0},
                        {0,0,0,0,0}
                },
                2,
                new int[][]{
                        {0,0,0,0,0},
                        {0,0,1,0,0},
                        {0,0,1,0,0},
                        {0,0,1,0,0},
                        {0,0,0,0,0}
                });

        // ========= Test 5: Rand/Ecke korrekt (2x2 Block in Ecke bleibt) =========
        total++;
        passed += runTest("T5 Corner block stays",
                new int[][]{
                        {1,1,0},
                        {1,1,0},
                        {0,0,0}
                },
                2,
                new int[][]{
                        {1,1,0},
                        {1,1,0},
                        {0,0,0}
                });

        // ========= Test 6: Überbevölkerung (3-in-line plus extras) =========
        // Zentrum hat zu viele Nachbarn -> stirbt
        total++;
        passed += runTest("T6 Overpopulation example",
                new int[][]{
                        {0,1,0},
                        {1,1,1},
                        {0,1,0}
                },
                1,
                new int[][]{
                        {1,1,1},
                        {1,0,1},
                        {1,1,1}
                });

        // ========= Test 7: computeGeneration(0) ändert nichts =========
        total++;
        passed += runGen0Test("T7 computeGeneration(0) no change",
                new int[][]{
                        {0,0,0,0},
                        {0,1,1,0},
                        {0,1,0,0},
                        {0,0,0,0}
                });

        // ========= Test 8: computeGeneration(n) == n mal computeNextGen =========
        total++;
        passed += runEquivalenceTest("T8 generation(n) equals n steps",
                new int[][]{
                        {0,0,0,0,0},
                        {0,0,1,0,0},
                        {0,0,1,0,0},
                        {0,0,1,0,0},
                        {0,0,0,0,0}
                },
                5);

        System.out.println("\n==============================");
        System.out.println("RESULT: " + passed + " / " + total + " tests passed");
        System.out.println("==============================");
    }

    // --------- Core test runner ----------
    private static int runTest(String name, int[][] start, int gens, int[][] expected) {
        Grid g = buildGridFromMatrix(start);
        g.computeGeneration(gens);

        boolean ok = equalsAliveMatrix(g.getGridArray(), expected);
        System.out.println(name + ": " + (ok ? "PASS" : "FAIL"));
        if (!ok) {
            System.out.println("Expected:");
            printMatrix(expected);
            System.out.println("Got:");
            printAlive(g.getGridArray());
        }
        return ok ? 1 : 0;
    }

    private static int runGen0Test(String name, int[][] start) {
        Grid g = buildGridFromMatrix(start);
        g.computeGeneration(0);

        boolean ok = equalsAliveMatrix(g.getGridArray(), start);
        System.out.println(name + ": " + (ok ? "PASS" : "FAIL"));
        if (!ok) {
            System.out.println("Expected (same as start):");
            printMatrix(start);
            System.out.println("Got:");
            printAlive(g.getGridArray());
        }
        return ok ? 1 : 0;
    }

    private static int runEquivalenceTest(String name, int[][] start, int n) {
        Grid g1 = buildGridFromMatrix(start);
        Grid g2 = buildGridFromMatrix(start);

        g1.computeGeneration(n);
        for (int i = 0; i < n; i++) {
            g2.computeNextGen();
        }

        boolean ok = equalsAliveMatrix(g1.getGridArray(), toAliveIntMatrix(g2.getGridArray()));
        System.out.println(name + ": " + (ok ? "PASS" : "FAIL"));
        if (!ok) {
            System.out.println("Grid via computeGeneration:");
            printAlive(g1.getGridArray());
            System.out.println("Grid via n-times computeNextGen:");
            printAlive(g2.getGridArray());
        }
        return ok ? 1 : 0;
    }

    // --------- Helpers ----------
    private static Grid buildGridFromMatrix(int[][] alive) {
        int rows = alive.length;
        int cols = alive[0].length;

        ArrayList<Cell> list = new ArrayList<>();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (alive[r][c] == 1) {
                    list.add(new Cell(r, c, true)); // alive-Flag ist egal, Grid nutzt nur Index
                }
            }
        }

        Cell[] cells = list.toArray(new Cell[0]);
        return new Grid(cells, rows, cols);
    }

    private static boolean equalsAliveMatrix(Cell[][] grid, int[][] expected) {
        if (grid.length != expected.length || grid[0].length != expected[0].length) return false;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                int got = grid[i][j].isAlive() ? 1 : 0;
                if (got != expected[i][j]) return false;
            }
        }
        return true;
    }

    private static int[][] toAliveIntMatrix(Cell[][] grid) {
        int[][] m = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                m[i][j] = grid[i][j].isAlive() ? 1 : 0;
            }
        }
        return m;
    }

    private static void printAlive(Cell[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.print(grid[i][j].isAlive() ? "1 " : "0 ");
            }
            System.out.println();
        }
    }

    private static void printMatrix(int[][] m) {
        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m[0].length; j++) {
                System.out.print(m[i][j] + " ");
            }
            System.out.println();
        }
    }
}


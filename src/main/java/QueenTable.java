//Guntawit Anurakboonying 6481260
//Pitchapa Phisutpichet   6580065
//Ratchaphon Proongpattanaskul 6580212
//Natnicha Sukchuenanant  6580812
//Chanon Pluemhathaikij   6581103

/*
 * Original code by Abhishek Shankhadhar
 * Source: https://www.geeksforgeeks.org/java-program-for-n-queen-problem-backtracking-3/?ref=next_article
 * Description of modifications: Modified to encode as String and add pre-placed queen functionality
 */


public class QueenTable {

    private final int size;
    private char[][] board;
    public int getBoardSize() {return size;}
    private int placedRow = -1; // Row of pre-placed queen, -1 if none
    private int placedCol = -1; // Column of pre-placed queen, -1 if none

    public QueenTable(int size) {
        this.size = size;
        initializeBoard();
    }

    // Initializes the board with "."
    private void initializeBoard() {
        board = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = '.';
            }
        }
    }

    // Checks if a queen can be safely placed at (row, col)
    private boolean isSafe(int row, int col) {
        if (row == placedRow && col == placedCol) // Ignore pre-placed queen
            return false;

        // Check this row on both sides
        for (int i = 0; i < size; i++) {
            if (i != col && board[row][i] == 'Q') // Ignore the current column, only check other columns
                return false;
        }

        // Check upper diagonal on the left side
        for (int i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 'Q')
                return false;

        // Check lower diagonal on the left side
        for (int i = row, j = col; i < size && j >= 0; i++, j--)
            if (board[i][j] == 'Q')
                return false;

        // Check upper diagonal on the right side
        for (int i = row, j = col; i >= 0 && j < size; i--, j++)
            if (board[i][j] == 'Q')
                return false;

        // Check lower diagonal on the right side
        for (int i = row, j = col; i < size && j < size; i++, j++)
            if (board[i][j] == 'Q')
                return false;

        return true;
    }

    private boolean placeQueens(int col) {
        if (col >= size) return true; // All queens are placed successfully

        // Skip pre-placed queen’s column
        if (col == placedCol) return placeQueens(col + 1);

        for (int i = 0; i < size; i++) {
            if (i == placedRow) continue; // Skip the pre-placed queen’s row
            if (isSafe(i, col)) {
                board[i][col] = 'Q';

                // Recur to place the rest of the queens
                if (placeQueens(col + 1)) return true;

                board[i][col] = '.'; // Backtrack
            }
        }
        return false;
    }


    // Recursive method to solve the N-Queens problem from the first column
    private boolean placeQueensWithDebug(int col) {
        System.out.println("Checking column: " + (col + 1));

        if (col >= size) {
            System.out.println("All queens placed successfully.");
            return true;
        }

        // Skip pre-placed queen’s column
        if (col == placedCol) {
            System.out.println("Skipping pre-placed queen’s column: " + (placedCol + 1));
            return placeQueensWithDebug(col + 1);
        }

        for (int i = 0; i < size; i++) {
            System.out.println("Checking (" + (i + 1) + "," + (col + 1) + ")");

            if (i == placedRow) {
                System.out.println("Skipping pre-placed queen’s row: " + (placedRow + 1));
                continue;
            }

            if (isSafe(i, col)) {
                System.out.println("Placing queen at (" + (i + 1) + "," + (col + 1) + ")");
                board[i][col] = 'Q';
                printBoard(); // Visualize board after placing a queen

                // Recur to place the rest of the queens
                if (placeQueensWithDebug(col + 1)) return true;

                board[i][col] = '.'; // Backtrack
                System.out.println("Backtracking from (" + (i + 1) + "," + (col + 1) + ")");
                printBoard(); // Visualize board after backtracking
            }
        }
        System.out.println("No valid positions found for column: " + (col + 1));
        return false;
    }



    public void solve() {

        System.out.println();
        System.out.println("Searching for a solution...");
        System.out.println();

        if (!placeQueens(0)) {
            System.out.println("Solution does not exist");
            System.out.println();

            return;
        }
        System.out.println("Solution:");
        printTable();
    }

    public void solveWithDebug() {

        System.out.println();
        System.out.println("Searching for a solution...");
        System.out.println();

        if (!placeQueensWithDebug(0)) {
            System.out.println("Solution does not exist");
            System.out.println();

            return;
        }
        System.out.println();
        System.out.println("Solution:");
        printTable();
    }

    // Helper function to place a queen at board[row][col]
    public void placeFirstQueen(int row, int col) {
        if (row >= 0 && row < size && col >= 0 && col < size) {
            board[row][col] = 'Q';
            placedRow = row;
            placedCol = col;
        } else {
            System.out.println("Invalid position for pre-placed queen.");
        }
    }


    // TABLE PRINTING
    public void printTable() {
        System.out.println();
        System.out.print("    ");
        for (int j = 1; j <= size; j++) {
            System.out.printf("%3d", j);
        }
        System.out.println();

        for (int p = 1; p <= size; p++) {
            System.out.printf("%3d | ", p);
            for (int j = 1; j <= size; j++) {
                System.out.print(board[p - 1][j - 1] + "  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}

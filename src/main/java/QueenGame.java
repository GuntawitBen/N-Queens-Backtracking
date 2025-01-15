//Guntawit Anurakboonying 6481260
//Pitchapa Phisutpichet   6580065
//Ratchaphon Proongpattanaskul 6580212
//Natnicha Sukchuenanant  6580812
//Chanon Pluemhathaikij   6581103


import java.util.InputMismatchException;
import java.util.Scanner;

public class QueenGame {
    private QueenTable queenTable;
    private final Scanner scanner;

    // CONSTRUCTOR
    public QueenGame() {
        this.scanner = new Scanner(System.in);
    }

    // GET BOARD SIZE
    private int getUserBoardSize() {
        int n = 0;
        while (n < 4) {
            System.out.println();
            System.out.println("Enter the board size (>= 4):");
            try {
                n = scanner.nextInt();
                if (n < 4) {
                    System.out.println("!!! N must be at least 4 !!!");
                }
            } catch (InputMismatchException e) {
                System.out.println("!!! Please enter a valid integer !!!");
                scanner.next();
            }
        }
        return n;
    }

    // START
    public void start() {
        while (true) {
            queenTable = new QueenTable(getUserBoardSize());
            queenTable.printTable();

            boolean validInput = false;
            while (!validInput) {
                System.out.println("Manually place the first queen?");
                System.out.println("Y: Yes | N: No | Q: Exit Program");
                String answer = scanner.next().toLowerCase();

                switch (answer) {
                    case "y":
                        validInput = true;
                        int x = getCoordinate("row") - 1;
                        int y = getCoordinate("column") - 1;
                        queenTable.placeFirstQueen(x, y);
                        queenTable.printTable();
//                        queenTable.solve();
                        queenTable.solveWithDebug();
                        resetBoard();
                        break;
                    case "n":
                        validInput = true;
//                        queenTable.solve();
                        queenTable.solveWithDebug();
                        resetBoard();
                        break;
                    case "q":
                        System.out.println();
                        System.out.println("Exiting program...");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("!!! Invalid input, please try again. !!!");
                }
            }
        }
    }

    private void resetBoard() {
        boolean validInput = false;
        while (!validInput) {
            System.out.println("Y: Try Again | Q: Exit Program");
            String restart = scanner.next().toLowerCase();

            switch (restart) {
                case "y":
                    validInput = true;
                    break;
                case "q":
                    validInput = true;
                    System.out.println();
                    System.out.println("Exiting program...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("!!! Invalid input, please try again. !!!");
            }
        }
    }


    // GET VALID COORDINATE IF MANUALLY PLACE QUEEN
    private int getCoordinate(String type) {
        int cord;
        while (true) {
            System.out.println();
            System.out.println("Enter " + type + " (1 to " + queenTable.getBoardSize() + "):");
            try {
                cord = scanner.nextInt();
                if (cord < 1 || cord > queenTable.getBoardSize()) {
                    System.out.println("!!! Please enter an integer between 1 and " + queenTable.getBoardSize() + " !!!");
                } else {
                    return cord;
                }
            } catch (InputMismatchException e) {
                System.out.println("!!! Please enter a valid integer !!!");
                scanner.next();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Welcome to the N-Queens Solver!");
        QueenGame game = new QueenGame();
        game.start();
    }
}

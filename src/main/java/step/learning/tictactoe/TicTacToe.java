package step.learning.tictactoe;
import java.util.Scanner;
public class TicTacToe {

    private static final int BOARD_SIZE = 3;
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'O';
    private static final char EMPTY_CELL = ' ';

    private char[][] board;
    private char currentPlayer;
    private int totalGames;
    private int playerXWins;
    private int playerOWins;

      public TicTacToe() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        currentPlayer = PLAYER_X;
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    private void displayBoard() {
        System.out.println("  A B C");
        for (int i = 0; i < BOARD_SIZE; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == PLAYER_X) {
                    System.out.print(ConsoleColors.RED + board[i][j] + " ");
                } else if (board[i][j] == PLAYER_O) {
                    System.out.print(ConsoleColors.BLUE + board[i][j] + " ");
                } else {
                    System.out.print(ConsoleColors.RESET + board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == PLAYER_X) ? PLAYER_O : PLAYER_X;
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < BOARD_SIZE && col >= 0 && col < BOARD_SIZE && board[row][col] == EMPTY_CELL;
    }

    private boolean isGameOver() {
        return checkWin(PLAYER_X) || checkWin(PLAYER_O) || isBoardFull();
    }

    private boolean checkWin(char player) {

        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            return true;
        }
        return false;
    }

    private boolean isBoardFull() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    private void updateBoard(int row, int col) {
        board[row][col] = currentPlayer;
    }

    private void printStats() {
        System.out.println("Рахунок: " + totalGames);
        System.out.println("Ігрок X виграв!: " + playerXWins);
        System.out.println("Ігрок O виграв!: " + playerOWins);
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        boolean playAgain = true;

        while (playAgain) {
            initializeBoard();
            displayBoard();

            while (!isGameOver()) {
                System.out.println("Ваш ход " + currentPlayer + ":");
                System.out.print("Виберіть строку (1-3): ");
                int row = scanner.nextInt() - 1;
                System.out.print("Виберіть колонку (A-C): ");
                String colInput = scanner.next().toUpperCase();
                int col = colInput.charAt(0) - 'A';

                if (isValidMove(row, col)) {
                    updateBoard(row, col);
                    displayBoard();
                    switchPlayer();
                } else {
                    System.out.println(ConsoleColors.RED + "Невірний хід! .");
                }
            }

            if (checkWin(PLAYER_X)) {
                System.out.println(ConsoleColors.YELLOW  + "Гравець X виграв!");
                playerXWins++;
            } else if (checkWin(PLAYER_O)) {
                System.out.println(ConsoleColors.YELLOW  + "Гравець O виграв!");
                playerOWins++;
            } else {
                System.out.println("Нічия!");
            }
            System.out.print(ConsoleColors.RESET);

            totalGames++;
            System.out.println(ConsoleColors.GREEN  + "Бажаєте зіграти ще? (так-1/ні-2)");
            String playAgainInput = scanner.next().toLowerCase();
            playAgain = playAgainInput.equals("1");
        }

        scanner.close();
        System.out.println(ConsoleColors.BLUE + "Гру закінчено!");
        printStats();
    }

}
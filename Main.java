import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        char[][] dimensions = Resetboard();
        CheckTurn checkTurn = new CheckTurn();
        CheckWin checkWin = new CheckWin();
        boolean GameOnGoing = true;

        while (GameOnGoing) {
            if (CheckBoard.IsBoardFull(dimensions)) {
                System.out.println("board is full");
                GameOnGoing = false;
                break;
            }

            String CurrentPlayer = String.valueOf(checkTurn.GetCurrentPlayer());
            System.out.println(CurrentPlayer + "'s turn");
            System.out.println("Please enter the row and col");
            int row = input.nextInt();
            int col = input.nextInt();

            if (row < 0 || row >= 3 || col < 0 || col >= 3) {
                System.out.println("you can only choose rows and columns from 0 to 2");
                continue;
            }

            if (dimensions[row][col] != '_') {
                System.out.println("place occupied, try somewhere else");
                continue;
            }

            dimensions[row][col] = CurrentPlayer.charAt(0);

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(dimensions[i][j] + "    ");
                }
                System.out.println("\t");
            }

            if (checkWin.CheckWin(dimensions, CurrentPlayer.charAt(0))) {
                System.out.println(CurrentPlayer + " WINS!");
                System.out.println("PLAY AGAIN?");
                String Replay = input.next();
                if (Replay.equalsIgnoreCase("yes")) {
                    dimensions = Resetboard();
                    checkTurn = new CheckTurn();
                    continue;
                } else {
                    GameOnGoing = false;
                }
            } else {
                if (CheckBoard.IsBoardFull(dimensions)) {
                    System.out.println("Draw");
                    System.out.println("PLAY AGAIN?");
                    String Replay = input.next();
                    if (Replay.equalsIgnoreCase("yes")) {
                        dimensions = Resetboard();
                        checkTurn = new CheckTurn();
                        continue;
                    } else {
                        GameOnGoing = false;
                    }
                }
            }
            checkTurn.SwitchTurn();  // Switch to the next player after each valid turn
        }
        input.close();
    }

    private static char[][] Resetboard() {
        return new char[][]{
                {'_', '_', '_'},
                {'_', '_', '_'},
                {'_', '_', '_'}
        };
    }
}

class CheckBoard {
    static boolean IsBoardFull(char[][] dimensions) {
        for (char[] dimension : dimensions) {
            for (int j = 0; j < dimensions.length; j++) {
                if (dimension[j] == '_') {
                    return false;
                }
            }
        }
        return true;
    }
}

class CheckTurn {
    private String CurrentPlayer;

    public CheckTurn() {
        this.CurrentPlayer = "x";
    }

    public String GetCurrentPlayer() {
        return CurrentPlayer;
    }

    public void SwitchTurn() {
        CurrentPlayer = (CurrentPlayer.equalsIgnoreCase("x")) ? "o" : "x";
    }
}

class CheckWin {
    public boolean CheckWin(char[][] board, char player) {
        for (int i = 0; i < 3; i++) {
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
}

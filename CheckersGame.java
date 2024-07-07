import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CheckersGame extends JFrame {

    private int[][] board = new int[8][8];
    private JLabel[][] boardSquares = new JLabel[8][8];
    private int currentPlayer = 1; // 1 for player 1, -1 for player 2
    private int selectedRow = -1;
    private int selectedCol = -1;
    private JButton startButton;
    private final JButton quitButton;
    private int player1Kills = 0; // Number of kills for player 1
    private int player2Kills = 0; // Number of kills for player 2

    public CheckersGame() {
        setTitle("Checkers Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 500); // Increased the height to accommodate the new button
        setLocationRelativeTo(null);

        // Create game board panel
        JPanel gameBoard = new JPanel();
        gameBoard.setLayout(new GridLayout(8, 8));

        // Create and add buttons to the game board panel
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JLabel label = new JLabel();
                label.setHorizontalAlignment(SwingConstants.CENTER);
                if ((i + j) % 2 == 0) {
                    label.setBackground(Color.WHITE);
                } else {
                    label.setBackground(Color.BLACK);
                }
                label.setOpaque(true);
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.addMouseListener(new SquareClickListener(i, j)); // Add mouse listener to handle clicks
                gameBoard.add(label);
                boardSquares[i][j] = label;
            }
        }

        // Add game board to frame
        add(gameBoard, BorderLayout.CENTER);

        // Create Start button
        startButton = new JButton("Start");
        startButton.addActionListener(e -> initializeBoard());
        add(startButton, BorderLayout.NORTH); // Moved the start button to the top

        // Create Quit button
        quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> {
            // Close the game window when the Quit button is clicked
            dispose(); // Close the current JFrame
        });
        add(quitButton, BorderLayout.SOUTH); // New quit button placed at the bottom

        setVisible(true);
    }

    // Initialize the game board with pieces
    private void initializeBoard() {
        // Clear the board
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = 0;
                boardSquares[i][j].setIcon(null); // Clear icons
            }
        }

        // Place pieces for player 1
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    board[i][j] = 1;
                     boardSquares[i][j].setIcon(new ImageIcon("C:\\Users\\user\\Documents\\NetBeansProjects\\checkers1\\src\\black_pawn.png")); // Replace with actual image path
                }
            }
        }

        // Place pieces for player 2
        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    board[i][j] = -1;
                     boardSquares[i][j].setIcon(new ImageIcon("C:\\Users\\user\\Documents\\NetBeansProjects\\checkers1\\src\\white_pawn.png")); // Replace with actual image path
                }
            }
        }

        currentPlayer = 1; // Player 1 starts the game
    }

    // Mouse listener for handling square clicks
    private class SquareClickListener extends MouseAdapter {
        private int row;
        private int col;

        public SquareClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (board[row][col] == currentPlayer) {
                selectedRow = row;
                selectedCol = col;
            } else if (selectedRow != -1 && selectedCol != -1) {
                if (isValidMove(selectedRow, selectedCol, row, col)) {
                    movePiece(selectedRow, selectedCol, row, col);
                    currentPlayer *= -1; // Switch player after each move
                    checkWinner();
                }
                selectedRow = -1;
                selectedCol = -1;
            }
        }
    }

    // Check if a move is valid
    private boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol) {
        // Implement your move validation logic here
        return true; // For demonstration purposes
    }

    // Move a piece on the board
    private void movePiece(int fromRow, int fromCol, int toRow, int toCol) {
        // Clear the selected square
        board[fromRow][fromCol] = 0;
        boardSquares[fromRow][fromCol].setIcon(null);

        // Move the piece to the new position
        if (board[toRow][toCol] != 0) {
            // A piece is killed
            int killedPiece = board[toRow][toCol];
            if (killedPiece == 1) {
                player1Kills++;
            } else if (killedPiece == -1) {
                player2Kills++;
            }
        }
        board[toRow][toCol] = currentPlayer;
        boardSquares[toRow][toCol].setIcon(currentPlayer == 1 ? new ImageIcon("C:\\Users\\user\\Documents\\NetBeansProjects\\checkers1\\src\\black_pawn.png") : new ImageIcon("C:\\Users\\user\\Documents\\NetBeansProjects\\checkers1\\src\\white_pawn.png"));

        // Clear the selected square variables
        selectedRow = -1;
        selectedCol = -1;

        // Check for king promotion
        if (currentPlayer == 1 && toRow == 7) {
            board[toRow][toCol] = 2; // Promote to king
            boardSquares[toRow][toCol].setIcon(new ImageIcon("C:\\Users\\user\\Documents\\NetBeansProjects\\checkers1\\src\\black_pawn_king.png")); // Replace with actual king image
        } else if (currentPlayer == -1 && toRow == 0) {
            board[toRow][toCol] = -2; // Promote to king
            boardSquares[toRow][toCol].setIcon(new ImageIcon("C:\\Users\\user\\Documents\\NetBeansProjects\\checkers1\\src\\white_pawn_king.png")); // Replace with actual king image
        }
    }

    // Check if a player has won the game
    private void checkWinner() {
        // Implement your winning condition logic here
        // If a player wins, display a message and reset the game
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CheckersGame();
        });
    }
}

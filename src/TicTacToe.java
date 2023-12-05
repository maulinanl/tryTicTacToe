import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe{
    int boardHeight = 650;
    int boardWidth = 600;

    JFrame frame = new JFrame("Tic Tac Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JButton [][] board = new JButton[3][3];
    String playerX = "X";
    String playerY = "O";
    String currentPlayer = playerX;
    boolean gameOver = false;
    int turns = 0;

    private int playerXScore = 0;
    private int playerYScore = 0;
    private JLabel playerXScoreLabel;
    private JLabel playerYScoreLabel;


    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setText(currentPlayer);
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);


        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);
                tile.setText(currentPlayer);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton clickedTile = (JButton) e.getSource();

                        // Periksa apakah permainan sudah selesai atau kotak sudah diisi
                        if (gameOver || !clickedTile.getText().equals("")) {
                            return;
                        }

                        // Lanjutkan dengan tindakan pemain
                        clickedTile.setText(currentPlayer);
                        turns++;
                        checkWinner();

                        // Jika permainan masih berlanjut, ganti pemain
                        if (!gameOver) {
                            currentPlayer = (currentPlayer == playerX) ? playerY : playerX;
                            textLabel.setText(currentPlayer + "'s turn.");
                        }
                    }
                });
            }
        }

        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetGame();
            }
        });

        JPanel scorePanel = new JPanel(new GridLayout(1, 3));
        JLabel playerXScoreLabel = new JLabel("Player X= " + playerXScore);
        JLabel playerYScoreLabel = new JLabel("Player Y= " + playerYScore);

        scorePanel.add(playerXScoreLabel);
        scorePanel.add(playerYScoreLabel);
        scorePanel.add(newGameButton);

        frame.add(scorePanel, BorderLayout.SOUTH);
    }

    void checkWinner() {
        //horizontal
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "") continue;

            if (board[r][0].getText() == board[r][1].getText() &&
                    board[r][1].getText() == board[r][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "") continue;

            if (board[0][c].getText() == board[1][c].getText() &&
                    board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        //diagonally
        if (board[0][0].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][2].getText() &&
                board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        //anti-diagonally
        if (board[0][2].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][0].getText() &&
                board[0][2].getText() != "") {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.gray);

        if (!gameOver) {
            textLabel.setText(currentPlayer + " is the winner");
            updateScore();
        }
        gameOver = true;
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("Tie!");
        updateScore();
        gameOver = true;
    }

    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setForeground(Color.white);
                board[r][c].setBackground(Color.darkGray);
            }
        }
             currentPlayer = playerX;
             turns = 0;
        gameOver = false;
        textLabel.setText(currentPlayer + "'s turn.");
    }

    void updateScore() {
        if (currentPlayer.equals(playerX)) {
            playerXScore++;
        } else {
            playerYScore++;
        }

        // Update label skor
        playerXScoreLabel.setText("Player X= " + playerXScore);
        playerYScoreLabel.setText("Player Y= " + playerYScore);
    }
    }


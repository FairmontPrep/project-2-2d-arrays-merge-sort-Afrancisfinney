import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

//I used mergesort 

public class GameBoard extends JFrame {
    private static final int SIZE = 8;
    private JPanel[][] squares = new JPanel[SIZE][SIZE];
    private String[][] piecesArray; 

    public GameBoard() {
        setTitle("Chess Board");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(SIZE, SIZE));

        
        initializePieces();

        System.out.println("Unsorted Pieces:");
        printArray(piecesArray);

        mergeSort(piecesArray, 0, piecesArray.length - 1);

        System.out.println("\nSorted Pieces:");
        printArray(piecesArray);

        initializeBoard();
    }

    // Method to initialize chess pieces in random order
    private void initializePieces() {
        ArrayList<String[]> tempPieces = new ArrayList<>();
        
        // Chess piece names with rankings
        String[][] correctSetup = {
            {"B_Rook.png", "1"}, {"B_Knight.png", "2"}, {"B_Bishop.png", "3"}, {"B_Queen.png", "4"},
            {"B_King.png", "5"}, {"B_Bishop.png", "6"}, {"B_Knight.png", "7"}, {"B_Rook.png", "8"},
            {"B_Pawn.png", "9"}, {"B_Pawn.png", "10"}, {"B_Pawn.png", "11"}, {"B_Pawn.png", "12"},
            {"B_Pawn.png", "13"}, {"B_Pawn.png", "14"}, {"B_Pawn.png", "15"}, {"B_Pawn.png", "16"},
            {"W_Pawn.png", "17"}, {"W_Pawn.png", "18"}, {"W_Pawn.png", "19"}, {"W_Pawn.png", "20"},
            {"W_Pawn.png", "21"}, {"W_Pawn.png", "22"}, {"W_Pawn.png", "23"}, {"W_Pawn.png", "24"},
            {"W_Rook.png", "25"}, {"W_Knight.png", "26"}, {"W_Bishop.png", "27"}, {"W_Queen.png", "28"},
            {"W_King.png", "29"}, {"W_Bishop.png", "30"}, {"W_Knight.png", "31"}, {"W_Rook.png", "32"}
        };

        // Copy correct setup into list
        for (String[] piece : correctSetup) {
            tempPieces.add(piece);
        }

        // Shuffle to create disorder
        Collections.shuffle(tempPieces);

        // Convert to 2D array
        piecesArray = new String[tempPieces.size()][2];
        for (int i = 0; i < tempPieces.size(); i++) {
            piecesArray[i] = tempPieces.get(i);
        }
    }

    // Print pieces array
    private void printArray(String[][] array) {
        for (String[] row : array) {
            System.out.println(row[0] + " - Rank: " + row[1]);
        }
    }

    private void initializeBoard() {
        int pieceIndex = 0; // Track piece placement

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                squares[row][col] = new JPanel(new BorderLayout());

                // Checkered board pattern
                if ((row + col) % 2 == 0) {
                    squares[row][col].setBackground(new Color(55, 255, 55)); // Dark green
                } else {
                    squares[row][col].setBackground(new Color(200, 255, 200)); // Light green
                }

                // Place pieces in first two and last two rows
                if (row < 2 || row > 5) {
                    String imagePath = piecesArray[pieceIndex][0];
                    String rank = "Rank: " + piecesArray[pieceIndex][1];

                    // Load image
                    ImageIcon pieceIcon = new ImageIcon(imagePath);
                    Image scaledImage = pieceIcon.getImage().getScaledInstance(40, 70, Image.SCALE_SMOOTH);
                    JLabel pieceLabel = new JLabel(new ImageIcon(scaledImage));
                    JLabel textLabel = new JLabel(rank, SwingConstants.CENTER);

                    squares[row][col].add(pieceLabel, BorderLayout.CENTER);
                    squares[row][col].add(textLabel, BorderLayout.SOUTH);

                    pieceIndex++;
                }

                add(squares[row][col]);
            }
        }
    }

    private void mergeSort(String[][] array, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);
        }
    }

    private void merge(String[][] array, int left, int mid, int right) {
        int leftSize = mid - left + 1;
        int rightSize = right - mid;

        String[][] leftArray = new String[leftSize][2];
        String[][] rightArray = new String[rightSize][2];

        for (int i = 0; i < leftSize; i++) {
            leftArray[i][0] = array[left + i][0];
            leftArray[i][1] = array[left + i][1];
        }
        for (int j = 0; j < rightSize; j++) {
            rightArray[j][0] = array[mid + 1 + j][0];
            rightArray[j][1] = array[mid + 1 + j][1];
        }

        int i = 0, j = 0, k = left;
        while (i < leftSize && j < rightSize) {
            if (Integer.parseInt(leftArray[i][1]) <= Integer.parseInt(rightArray[j][1])) {
                array[k][0] = leftArray[i][0];
                array[k][1] = leftArray[i][1];
                i++;
            } else {
                array[k][0] = rightArray[j][0];
                array[k][1] = rightArray[j][1];
                j++;
            }
            k++;
        }

        while (i < leftSize) {
            array[k][0] = leftArray[i][0];
            array[k][1] = leftArray[i][1];
            i++;
            k++;
        }

        while (j < rightSize) {
            array[k][0] = rightArray[j][0];
            array[k][1] = rightArray[j][1];
            j++;
            k++;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard board = new GameBoard();
            board.setVisible(true);
        });
    }
}

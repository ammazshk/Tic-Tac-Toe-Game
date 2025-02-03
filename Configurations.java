/**
 * The Configurations class manages the state and logic of a Tic Tac Toe game.
 * It includes a game board, win conditions, and methods to track moves, check for wins, and evaluate the game status.
 */
public class Configurations {
    // 2D array representing the Tic Tac Toe game board, with each cell storing 'X', 'O', or ' '
    private char[][] board;       
    // Size of the game board
    private int boardSize;        
    // Number of consecutive symbols needed to win
    private int lengthToWin;      
    // Maximum depth level for the game tree
    private int maxLevels;      

    /**
     * Constructor initializes the game board, board size, win length, and max levels.
     * Each cell in the board is initially set to ' ' (empty).
     * @param boardSize Size of the board.
     * @param lengthToWin Length of consecutive symbols needed to win.
     * @param maxLevels Maximum tree depth for the game tree.
     */
    public Configurations(int boardSize, int lengthToWin, int maxLevels) {
        this.boardSize = boardSize;
        this.lengthToWin = lengthToWin;
        this.maxLevels = maxLevels;
        this.board = new char[boardSize][boardSize];
        
        // Initialize each cell in the board to ' ' (empty)
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = ' ';
            }
        }
    }

    /**
     * Creates and returns an empty HashDictionary with a prime table size
     * (around 8000 entries) to store game board configurations.
     * @return A new HashDictionary instance with a prime table size.
     */
    public HashDictionary createDictionary() {
        return new HashDictionary(7971);
    }

    /**
     * Stores the board configuration into a String and returns the score associated with the board configuration.
     * @param hashTable The hash table storing board configurations and their scores.
     * @return Score associated with the board configuration if it exists in hashTable, or -1 otherwise.
     */
    public int repeatedConfiguration(HashDictionary hashTable) {
        String config = "";
        // Adds all board configurations to config string
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                config += board[i][j];
            }
        }
        // gets the score from based on the configuration
        return hashTable.get(config);
    }

    /**
     * Adds the current board configuration and its score to the provided hashDictionary.
     * @param hashDictionary HashDictionary to store the board configuration and score.
     * @param score The score to associate with the current board configuration.
     */
    public void addConfiguration(HashDictionary hashDictionary, int score) {
        String config = "";
        // Adds all board configurations to config string
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                config += board[i][j];
            }
        }
        // Adds the configuartions and score to the hash dictionary
        hashDictionary.put(new Data(config, score));
    }

    /**
     * Stores the given symbol (either 'X' or 'O') at the specified board location.
     * @param row Row index where the symbol is to be placed.
     * @param col Column index where the symbol is to be placed.
     * @param symbol The symbol ('X' or 'O') to place on the board.
     */
    public void savePlay(int row, int col, char symbol) {
        board[row][col] = symbol;
    }

    /**
     * Checks if a specified board cell is empty.
     * @param row Row index of the cell to check.
     * @param col Column index of the cell to check.
     * @return True if the cell is empty (' '), false otherwise.
     */
    public boolean squareIsEmpty (int row, int col) {
        if (board[row][col] == ' ') {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Checks for a horizontal winning sequence of the specified symbol starting from the given row.
     * @param symbol The symbol ('X' or 'O') to check for.
     * @param row The starting row for the horizontal check.
     * @param col The starting column for the horizontal check.
     * @return True if a horizontal win is found, false otherwise.
     */
    private boolean horzWin(char symbol, int row, int col) {
        int symbolSequence = 0; // Counter for consecutive matching symbols
        // Iterate through each column in the specified row
        for (int k = 0; k < boardSize; k++) {
            if (board[row][k] == symbol) { // Check if current cell matches the symbol
                symbolSequence++; // Increment the sequence counter if there's a match
            } else {
                symbolSequence = 0; // Reset the counter if the sequence is broken
            }
    
            // If the sequence length matches the required length to win
            if (symbolSequence >= lengthToWin) {
                return true; // Return true indicating a horizontal win is found
            }
        }
        // Return false if no winning sequence is found across the entire row
        return false;
    }
    

    /**
     * Checks for a vertical winning sequence of the specified symbol starting from the given column.
     * @param symbol The symbol ('X' or 'O') to check for.
     * @param row The starting row for the vertical check.
     * @param col The starting column for the vertical check.
     * @return True if a vertical win is found, false otherwise.
     */
    private boolean vertWin(char symbol, int row, int col) {
        int symbolSequence = 0; // Counter to track consecutive symbols in the column
        // Loop through each row in the specified column to check for a vertical win
        for (int k = 0; k < boardSize; k++) {
            if (board[k][col] == symbol) {
                symbolSequence++; // Increment if current cell matches the symbol
            } else {
                symbolSequence = 0; // Reset sequence if a different symbol is found
            }
            // Check if consecutive sequence matches the length required to win
            if (symbolSequence >= lengthToWin) {
                return true; // Return true indicating a vertical win is found
            }
        }
        // Return false if no winning sequence is found in the column
        return false;
    }
    
    /**
     * Checks for a right diagonal (top-left to bottom-right) winning sequence.
     * @param symbol The symbol ('X' or 'O') to check for.
     * @param row Starting row for the diagonal check.
     * @param col Starting column for the diagonal check.
     * @return True if a right diagonal win is found, false otherwise.
     */
    private boolean diagRightWin(char symbol, int row, int col) {
        int symbolSequence = 0; // Counter for consecutive symbols in the diagonal
    
        // Check the diagonal from top-left to bottom-right
        for (int k = 0; k < boardSize; k++) {
            int newRow = row + k; // Move downwards by k rows
            int newCol = col + k; // Move rightwards by k columns
            // Ensure the new position is within board boundaries
            if (newRow < boardSize && newCol < boardSize) {
                if (board[newRow][newCol] == symbol) {
                    symbolSequence++; // Increment if the current cell matches the symbol
                } else {
                    symbolSequence = 0; // Reset if a different symbol is encountered
                }
                // Check if the consecutive sequence matches the required length to win
                if (symbolSequence >= lengthToWin) {
                    return true; // Return true if a winning sequence is found
                }
            }
        }
        // Return false if no winning sequence is found on this diagonal
        return false;
    }
    

    /**
     * Checks for a left diagonal (top-right to bottom-left) winning sequence.
     * @param symbol The symbol ('X' or 'O') to check for.
     * @param row Starting row for the diagonal check.
     * @param col Starting column for the diagonal check.
     * @return True if a left diagonal win is found, false otherwise.
     */
    private boolean diagLeftWin(char symbol, int row, int col) {
        int symbolSequence = 0; // Counter for consecutive symbols in the diagonal
        // Check the diagonal from top-right to bottom-left
        for (int k = 0; k < boardSize; k++) {
            int newRow = row + k; // Move downwards by k rows
            int newCol = col - k; // Move leftwards by k columns
            // Ensure the new position is within board boundaries
            if (newRow < boardSize && newCol >= 0) {
                if (board[newRow][newCol] == symbol) {
                    symbolSequence++; // Increment if current cell matches the symbol
                } else {
                    symbolSequence = 0; // Reset if a different symbol is encountered
                }
                // Check if the consecutive sequence matches the required length to win
                if (symbolSequence >= lengthToWin) {
                    return true; // Return true if a win is found
                }
            }
        }
        // Return false if no winning sequence is found on this diagonal
        return false;
    }
    

    /**
     * Checks if the board is completely filled.
     * @return True if there are no empty cells, false otherwise.
     */
    private boolean isFull() {
        // Checks if the game board is full (no empty spaces)
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == ' ') {
                    return false; // Found an empty space
                }
            }
        }
        return true; // No empty spaces found
    }

    /**
     * Checks if the specified player has won by having a required sequence on the board.
     * @param symbol The symbol ('X' or 'O') to check for winning sequences.
     * @return True if the player has won, false otherwise.
     */
    public boolean wins(char symbol) {
        // Iterate through each cell on the board
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Check if the current cell contains the specified symbol
                if (board[i][j] == symbol) {
                    // Check for a win starting from this cell in all possible directions
                    if (horzWin(symbol, i, j) || vertWin(symbol, i, j) ||
                            diagRightWin(symbol, i, j) || diagLeftWin(symbol, i, j)) {
                        return true; // Return true if any direction results in a win
                    }
                }
            }
        }
        // Return false if no win is found after checking all cells
        return false;
    }
    
    /**
     * Determines if the game is a draw by checking if the board is full and no player has won.
     * @return True if the game is a draw, false otherwise.
     */
    public boolean isDraw() {
        return !wins('X') && !wins('O') && isFull(); // Check if no player has won and board is full
    }

    /**
     * Evaluates the game state and returns a score indicating the result.
     * @return 3 if the computer wins, 0 if the human wins, 2 for a draw, 1 if undecided.
     */
    public int evalBoard() {
        if (wins('O')) {
            return 3; // computer wins
        }
        if (wins('X')) {
            return 0; // human wins
        }
        if (isDraw()) {
            return 2; // game is a draw
        }
        return 1; // game is still ongoing
    }

}

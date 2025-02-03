/**
 * Data class represents a record to be stored in the HashDictionary.
 * Each record consists of a configuration string and an associated integer score.
 */
public class Data {
    private String config; // Configuration string representing the board state
    private int score; // Score associated with the configuration

    /**
     * Constructs a Data object with the specified configuration and score.
     *
     * @param config the string representation of the board configuration
     * @param score the score associated with the configuration
     */
    public Data(String config, int score) {
        this.config = config; // Initialize the configuration
        this.score = score; // Initialize the score
    }

    /**
     * Retrieves the configuration stored in this Data object.
     *
     * @return the configuration string
     */
    public String getConfiguration() {
        return this.config; // Return the configuration
    }

    /**
     * Retrieves the score stored in this Data object.
     *
     * @return the score as an integer
     */
    public int getScore() {
        return this.score; // Return the score
    }
}

import java.util.LinkedList;

/**
 * HashDictionary class implements a Dictionary ADT using a hash table with separate chaining.
 * Utilizes a polynomial hash function to map configurations to table indices, ensuring minimal collisions.
 */
public class HashDictionary implements DictionaryADT {
    private LinkedList<Data>[] hashTable; // Array of linked lists for separate chaining
    private int numRecords; // Tracks the number of records in the dictionary

    /**
     * Constructs a HashDictionary of the specified size.
     * Initializes the hash table with empty linked lists.
     *
     * @param size the size of the hash table
     */
    public HashDictionary(int size) {
        hashTable = new LinkedList[size];
        numRecords = 0; // number of records in hash table

        // Initialize each index in the hash table as an empty linked list
        for (int i = 0; i < size; i++) {
            hashTable[i] = new LinkedList<>();
        }
    }

    /**
     * Calculates the hash code for a given key using a polynomial hash function.
     * The function aims to produce a hash value that minimizes collisions.
     *
     * @param key the string key to hash
     * @param M the size of the hash table
     * @return the hash code for the given key
     */
    private int hash(String key, int M) {
        int val = (int) key.charAt(0); // Initialize val with ASCII of first character
        int x = 31; // Constant multiplier in polynomial hash
        for (int i = 0; i < key.length(); i++) {
            val = (val * x + (int) key.charAt(i)) % M; // Polynomial hash function
        }
        return val % M; // Ensure the final hash value is within table bounds
    }

    /**
     * Adds a record to the hash table, handling collisions with separate chaining.
     * If the record already exists, a DictionaryException is thrown.
     *
     * @param record the Data record to add
     * @return 1 if a collision occurs, otherwise 0
     * @throws DictionaryException if the record already exists in the dictionary
     */
    public int put(Data record) throws DictionaryException {
        int position = hash(record.getConfiguration(), hashTable.length); // Get hash position
        LinkedList<Data> list = hashTable[position]; // Access linked list at hashed position

        // Check if record already exists to avoid duplicates
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getConfiguration().equals(record.getConfiguration())) {
                throw new DictionaryException(); // Duplicate found, throw exception
            }
        }
        hashTable[position].add(record); // Add new record to the linked list
        numRecords += 1; // Increment record count

        // Return 1 if a collision occurs, otherwise return 0
        if (hashTable[position].size() - 1 > 0) {
            return 1; // Collision occurred
        } else {
            return 0; // No collision
        }
    }

    /**
     * Removes the record with the given configuration from the hash table.
     * Throws a DictionaryException if no such record exists.
     *
     * @param config the configuration string of the record to remove
     * @throws DictionaryException if no record with the given configuration exists
     */
    public void remove(String config) throws DictionaryException {
        int position = hash(config, hashTable.length); // Get hash position
        LinkedList<Data> list = hashTable[position]; // Access linked list at hashed position

        // Search for the record to remove
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getConfiguration().equals(config)) {
                list.remove(i); // Remove the record
                numRecords -= 1; // Decrement record count
                return; // Exit method after removal
            }
        }
        throw new DictionaryException(); // No record found, throw exception
    }

    /**
     * Retrieves the score associated with the given configuration.
     * Returns -1 if the configuration is not found in the dictionary.
     *
     * @param config the configuration string to search for
     * @return the score associated with the configuration, or -1 if not found
     */
    public int get(String config) {
        int position = hash(config, hashTable.length); // Get hash position
        LinkedList<Data> list = hashTable[position]; // Access linked list at hashed position

        // Search for the configuration and return its score
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getConfiguration().equals(config)) {
                return list.get(i).getScore(); // Return the score of the found record
            }
        }
        return -1; // Configuration not found, return -1
    }

    /**
     * Returns the number of Data objects currently stored in the dictionary.
     *
     * @return the number of records in the dictionary
     */
    public int numRecords() {
        return numRecords; // Return the count of records
    }
}

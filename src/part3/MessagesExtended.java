package part3;

public class MessagesExtended {//start of class

    //declare variables.
    private static final int LIMIT = 100;
    private String[] storedMessages = new String[LIMIT];
    private String[] messageHashes = new String[LIMIT];
    private long[] messageIDs = new long[LIMIT];
    private String[] recipients = new String[LIMIT];
    private String[] messageFlags = new String[LIMIT];

    private int storedCount = 0;

    // Getter to assist with validation testing requirements
    public int getStoredCount() {
        return this.storedCount;
    }

    public void displayStoredReportMenu() {
        System.out.println(" ---Stored Messages Report Manager--- ");
        System.out.println("1. Display Stored messages of Senders and Recipients");
        System.out.println("2. Display Longest Stored Message");
        System.out.println("3. Search by Message ID");
        System.out.println("4. Search by Recipient");
        System.out.println("5. Delete Message by Hash");
        System.out.println("6. Show Full Report");
        System.out.println("7. Return to Main Menu");

    }

    /*
     * Populates the arrays with message data.
     * @param id        Unique message ID
     * @param hash      Message hash value
     * @param recipient Num Recipient's phone number
     * @param msgText   The message content
     * @param flag      Status flag (e.g., "Stored", "Sent", "Failed")
     */
    public void populateArrays(long id, String hash, String recipientNum, String msgText, String flag) {
        if (storedCount < LIMIT) {
            messageIDs[storedCount] = id;
            messageHashes[storedCount] = hash;
            recipients[storedCount] = recipientNum;
            storedMessages[storedCount] = msgText;
            messageFlags[storedCount] = flag;
            storedCount++;
        } else {
            System.out.println("System Report Storage is full!");
        }
    }

    /*
     * Displays the senders and recipients of messages with a "Stored" status.
     * @param None
     * @return void (prints to console)
     */
    public void displaySendersAndRecipients() {
        System.out.println("\n--- SENDER AND RECIPIENT LIST (STORED STATUS ONLY) ---");
        boolean found = false;
        for (int i = 0; i < storedCount; i++) {
            if ("Stored".equalsIgnoreCase(messageFlags[i])) {
                System.out.println("Message Index [" + i + "] -> Recipient: " + recipients[i]);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No messages with a status flag of 'Stored' exist.");
        }
    }

    /*
     * Evaluates the arrays and returns the longest message text body flagged as "Stored"
     * @return String content of the longest message
     */
    public String getLongestStoredMessage() {
        String longest = "";
        boolean foundStored = false;

        for (int i = 0; i < storedCount; i++) {
            // Guard against any null flags or uninitialized array positions
            if (messageFlags[i] != null && messageFlags[i].equalsIgnoreCase("Stored")) {
                if (storedMessages[i] != null) {
                    foundStored = true;
                    if (storedMessages[i].length() > longest.length()) {
                        longest = storedMessages[i];
                    }
                }
            }
        }

        // If no messages have a "Stored" flag, return an informative string rather than null
        if (!foundStored) {
            return "No messages with a status flag of 'Stored' exist.";
        }
        return longest;
    }

    /*
     * Search for a message ID and return the corresponding details.
     * @param targetID The unique 10-digit LONG message ID to search for
     * @return A formatted response string matching the test requirements
     */
    public String searchByMessageID(long targetID) {
        for (int i = 0; i < storedCount; i++) {
            if (messageIDs[i] == targetID) {
                return "\"" + storedMessages[i] + "\"";
            }
        }
        return "Message ID not found.";
    }

    /*
 * Search all messages sent or stored regarding a particular recipient.
 * @param targetRecipient The phone number string to search for
 * @return A concatenated string of all messages associated with the recipient
     */
    public String searchByRecipient(String targetRecipient) {
        StringBuilder results = new StringBuilder();
        boolean found = false;

        for (int i = 0; i < storedCount; i++) {
            if (recipients[i].equals(targetRecipient)) {
                if (found) {
                    results.append(" "); // Space delimiter between messages as requested by PoE
                }
                results.append("\"").append(storedMessages[i]).append("\"");
                found = true;
            }
        }

        if (!found) {
            return "No messages found for this recipient.";
        }
        return results.toString();
    }

    /*
     * Purges a message by its unique hash.
     * @param targetHash The hash of the message to delete
     * @return boolean (true if deleted, false otherwise)
     */
    public boolean deleteMessageByHash(String targetHash) {
        int targetIndex = -1;
        for (int i = 0; i < storedCount; i++) {
            if (messageHashes[i] != null && messageHashes[i].equals(targetHash)) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex == -1) {
            return false;
        }

        // Shift all subsequent elements to the left to fill the gap
        for (int i = targetIndex; i < storedCount - 1; i++) {
            messageIDs[i] = messageIDs[i + 1];
            messageHashes[i] = messageHashes[i + 1];
            recipients[i] = recipients[i + 1];
            storedMessages[i] = storedMessages[i + 1];
            messageFlags[i] = messageFlags[i + 1];
        }

        // Clear the last row after shifting
        messageIDs[storedCount - 1] = 0;
        messageHashes[storedCount - 1] = null;
        recipients[storedCount - 1] = null;
        storedMessages[storedCount - 1] = null;
        messageFlags[storedCount - 1] = null;

        storedCount--;
        return true;
    }

    /*
     * Displays a comprehensive full report of all stored messages.
     * @param None
     * @return void (prints to console)
     */
    public void displayFullReport() {
        System.out.println("---  STORED MESSAGES SYSTEM REPORT  ---");
        if (storedCount == 0) {
            System.out.println("No message data records currently logged.");
        } else {
            for (int i = 0; i < storedCount; i++) {
                System.out.println("Record #" + (i + 1));
                System.out.println("ID:       " + messageIDs[i]);
                System.out.println("Hash:     " + messageHashes[i]);
                System.out.println("To:       " + recipients[i]);
                System.out.println("Status:   [" + messageFlags[i] + "]");
                System.out.println("Message:  \"" + storedMessages[i] + "\"");
                System.out.println("-------------------------------------------------------");
            }
        }
    }
}//end of class

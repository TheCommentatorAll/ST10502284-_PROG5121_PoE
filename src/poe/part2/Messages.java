/*
 *-------------------------------------------------------------------------
 * File: Messages.java
 * Developer: Nicholas Morris
 * Student ID: ST10502284]
 * Date: April 22, 2026
 * Description: Part 2 Logic message class
 * -------------------------------------------------------------------------
 */
package poe.part2;

import java.util.concurrent.ThreadLocalRandom;

public class Messages {

    private long messageID;
    private String message;
    private String msgHashString;
    private String[] sentMessages = new String[100]; // Array to store sent messages, assuming a maximum of 100 messages
    private int globalMessageCounter = 0; // Counter to keep track of the number of messages

    public void setMessageID(long messageID) {
        this.messageID = messageID;
    }

    public long getMessageID() {
        return messageID;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMsgHashString(String msgHashString) {
        this.msgHashString = msgHashString;
    }

    public String getMsgHashString() {
        return msgHashString;
    }

    public void setGlobalMessageCounter(int globalMessageCounter) {
        this.globalMessageCounter = globalMessageCounter;
    }

    public int getGlobalMessageCounter() {
        return globalMessageCounter;
    }

    /*
     * Displays the available options to the user.
     */
    public void displayOptions() {
        System.out.println("""
                           Option 1): Send Messages
                           Option 2): Show recently sent
                           Option 3): Quit""");

    }

    /*
     * Returns a message indicating the user has sent a message.
     * @return A formatted string indicating the message has been sent.
     */
    public String sentMessage() {
        return """
               ---Please select an option---
               1) Send Message
               2) Store Message
               3) Disregard""";
    }

    /*
     * Returns a message indicating the message has been sent.
     * @return A formatted string indicating the message has been sent.
     */
    public String messageSent() {
        return "Message successfully sent.";
    }

    /*
     * Returns a message indicating the user has chosen to disregard the message.
     * @return A formatted string indicating the message has been disregarded.
     */
    public String messageDisregarded() {
        return "Press 0 to delete message.";
    }

    /*
     * Returns a message indicating the message has been stored.
     * @return A formatted string indicating the message has been stored.
     */
    public String messageStored() {
        return "Message successfully stored.";
    }

    /*
     * Generates a unique message ID.
     * @return A randomly generated 10-digit message ID.
     */
    public long generateMessageID() {

        messageID = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);

        return messageID;
    }

    /*
     * Checks if the provided message ID is valid.
     * @param msgID The message ID to check.
     * @return true if the message ID is valid, false otherwise.
     */
    public boolean checkMessageID(long msgID) {
        return msgID >= 1_000_000_000L && msgID < 10_000_000_000L;
    }

    /*
     * Checks if the recipient's cellphone number is valid.
     * @param cellNum The cellphone number to check.
     * @return A message indicating whether the cellphone number is valid.
     */
    public String checkRecipientCell(String cellNum) {
        if (cellNum.matches("^\\+27\\d{9}$")) {
            return "Cell phone number successfully captured.";
        } else {
            return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
        }
    }

    /*
     * Checks if the message length is valid.
     * @param message The message to check.
     * @return A message indicating whether the message length is valid.
     */
    public String checkMessageLength(String message) {
        if (message.length() > 250) {
            int over = message.length() - 250;
            return "Message exceeds 250 characters by " + over + ", please reduce size.";
        } else {
            return "Message ready to send.";
        }
    }

    /*
     * Increments the global message counter.
     * @return The updated message counter.
     */
    public int incrementMessageCounter() {
        return ++globalMessageCounter;
    }

    /*
     * Processes the message metadata to generate a unique hashcode for the message with dependencies.
     * @param msgID The unique identifier for the message, expected to be a 10-digit number.
     * @param messageNumber The sequential number of the message being sent.
     * @param message The text content of the message, which will be used to generate the hash.
     * @return A formatted string containing the message hash.
     */
    public String createMessageHash(long msgID, int messageNumber, String message) {
        String idString = String.format("%010d", msgID);//format msgID ensuring length = 10 digits, padded
        String prefix = idString.substring(0, 2);

        if (message == null || message.isBlank()) {
            return String.format("%s:%d:", prefix, messageNumber);//formatted "prefix:messageNumber:"
        }

        // Split the message into words using whitespace and trim any extra spaces.
        // \\s+ is a regex that matches one or more whitespace characters(spaces, tabs, newlines).
        String[] words = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        String wordHash = (firstWord + lastWord).replaceAll("\\s+", "").toUpperCase();//any space repaced with empty string.

        return String.format("%s:%d:%s", prefix, messageNumber, wordHash);// formatted "prefix:messageNumber:wordHash"

    }

    /*
    * METHOD: storeMessageAsJSON()
    * This method was generated with AI assistance.
    *
    * AI ATTRIBUTION:
    * Tool: Claude (Anthropic)
    * Version: Claude Sonnet 4.6
    * Date: 30 April 2026
    * Prompt used: "I need a method in the Messages class to store messages in JSON,
    *               including the recipient's name"
    * Reference:
    * Anthropic, 2026. Claude Sonnet 4.6 [Large Language Model]. 
    * Available at: https://claude.ai [Accessed 30 April 2026].
    *
    * NOTE: The generated code was reviewed and integrated by the developer.
    * Adaptations made: method parameters adjusted to match existing class fields
    * (msgID, msgHash) and array storage pattern consistent with project structure.
     */

 /*
     * Stores a message as a JSON-formatted string to display in the console and also saves it in the sentMessages array.
     * Generated with AI assistance (Claude, Anthropic, 2026)
     * @param recipientName The name of the recipient.
     * @param message The message content.
     * @param msgHash The message hash.
     * @param msgID The message ID.
     * @return A confirmation string.
     */
    public String storeMessageAsRegular(long msgID, String msgHash, String recipient, String message) {
        int storedMessageCounter = globalMessageCounter - 1;
        if (storedMessageCounter >= 0 && storedMessageCounter < sentMessages.length) {
            String messageData = "{"
                    + "\"messageID\": \"" + msgID + "\", "
                    + "\"recipient\": \"" + recipient + "\", "
                    + "\"message\": \"" + message + "\", "
                    + "\"messageHash\": \"" + msgHash + "\""
                    + "}";
            sentMessages[storedMessageCounter] = messageData;
            return "Message successfully stored as JSON.";
        } else {
            return "Message storage is full.";
        }
    }

    /*
     * Stores a message as a JSON-formatted string inside a file named "messages.json".
     * @param recipientName The name of the recipient.
     * @param message The message content.
     * @param msgHash The message hash.
     * @param msgID The message ID.
     * @return A confirmation string.
     */
    public String storeMessageAsJSON(long msgID, String msgHash, String recipient, String message) {
        
        storeMessageAsRegular(msgID, msgHash, recipient, message);
        
        // Build one complete JSON array from everything in sentMessages[]
        StringBuilder jsonArray = new StringBuilder();
        jsonArray.append("[\n");
        
        int written = 0; // track how many valid entries we've written
        for (int i = 0; i < globalMessageCounter; i++) {
            if (sentMessages[i] != null) {
                if (written > 0) {
                    jsonArray.append(",\n"); // comma BETWEEN objects, not after the last one
                }
                jsonArray.append("  ").append(sentMessages[i]); // append the JSON object from sentMessages[]
                written++;
            }
        }
        jsonArray.append("\n]");

        try (java.io.FileWriter file = new java.io.FileWriter("messages.json", false)) {//false to overwrite each time
            file.write(jsonArray.toString());
            return "Message successfully stored in messages.json";
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return "An error occurred while storing the message." + e.getMessage();
        }

    }

    /*
     * Prints the stored messages in JSON formatted string.
     * @return A formatted string of all stored messages in JSON.
     */
    public String printMessages() {
        if (globalMessageCounter == 0 ) {
            return "No messages sent yet.";
        }
        
        StringBuilder storedMessages = new StringBuilder();
        storedMessages.append("[\n");
        for (int i = 0; i < globalMessageCounter; i++) {
            storedMessages.append("  ").append(sentMessages[i]);
            if (i < globalMessageCounter - 1) {
                storedMessages.append(",");
            }
            storedMessages.append("\n");
        }
        storedMessages.append("]");
        return storedMessages.toString();
    }
}

/*
 * 
 *-------------------------------------------------------------------------
 * File: Messages.java
 * Developer: Nicholas Morris
 * Student ID: ST10502284]
 * Date: April 22, 2026
 * Description: Part 2 Logic message class
 * -------------------------------------------------------------------------
 */
package poe.part2.logic;

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
            return "Cellphone number is valid: " + cellNum;
        } else {
            return "Invalid cellphone number.";
        }
    }

    public void checkMessageLength(String message) {
        if (message.length() > 250) {
            System.out.println("Message exceeds the maximum length of 250 characters.");
        } else {
            System.out.println("Message length is valid.");
        }
    }

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

    /*public String storeMessages(String message) {
        if (globalMessageCounter < sentMessages.length) {
            sentMessages[globalMessageCounter] = message;
            globalMessageCounter++;
            return "Message stored successfully.";
        } else {
            return "Message storage limit reached. Cannot store more messages.";
        }
    }*/

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
     * Stores a message as a JSON-formatted string.
     * Generated with AI assistance (Claude, Anthropic, 2026)
     * @param recipientName The name of the recipient.
     * @param message The message content.
     * @param msgHash The message hash.
     * @param msgID The message ID.
     * @return A confirmation string.
     */
    public String storeMessageRegular(long msgID, String msgHash, String recipient, String message) {
        if (globalMessageCounter < sentMessages.length) {
            String messageData = "{\n"
                    + "\"messageID\": \"" + msgID + "\", "
                    + "\"recipient\": \"" + recipient + "\", "
                    + "\"message\": \"" + message + "\", "
                    + "\"messageHash\": \"" + msgHash + "\""
                    + "}";
            sentMessages[globalMessageCounter] = messageData;
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

        String json = "{\n" +
                "  \"messageID\": \"" + msgID + "\",\n" +
                "  \"recipient\": \"" + recipient + "\",\n" +
                "  \"message\": \"" + message + "\",\n" +
                "  \"messageHash\": \"" + msgHash + "\"\n" +
                "}";

                try (java.io.FileWriter file = new java.io.FileWriter("messages.json", true)) {
                    file.write(json + ",\n");
                    return "Message successfully stored in messages.json";
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                    return "An error occurred while storing the message." + e.getMessage();
                }

    }

    /*public String printMessages(String[] messages){
        if (messages == null || messages.length == 0) {
            return "No messages to display.";
        }else {
        StringBuilder sb = new StringBuilder();
        sb.append("Recently sent messages:\n");
        for (int i = 0; i < globalMessageCounter ; i++) {
            if (sentMessages[i] != null) {
                sb.append((i + 1)).append(".) ").append(sentMessages[i]).append("\n");
            }
        }
        return sb.toString();
    }*/

 /*
     * Prints the stored messages in JSON format.
     * @return A formatted string of all stored messages in JSON.
     */
    public String printMessages() {
        if (globalMessageCounter == 0) {
            return "No messages stored yet.";
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

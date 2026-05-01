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
    private int messageCounter = 0;
    private String message;
    private String[] sentMessages = new String[100]; // Array to store sent messages, assuming a maximum of 100 messages
    private int totalMessages = 0; // Variable to keep track of the total number of messages sent
    
    /*
     * Checks if the final message contains the word "Welcome".
     * @param finalMsg The final message to check.
     * @return true if the message contains "Welcome", false otherwise.
     */
    public boolean checkSendMessage(String finalMsg){
        
        return finalMsg.contains("Welcome");
    }

    /*
     * Displays the available options to the user.
     */
    public void displayOptions(){
        System.out.println("""
                           Option 1): Send Messages
                           Option 2): Show recently sent
                           Option 3): Quit""");
    
    }

    /*
     * Returns a message indicating the user has sent a message.
     * @return A formatted string indicating the message has been sent.
     */
    public String sentMessage(){
        return """
               ---Please select an option---
               1) Send Messages
               2) Store Messages
               3) Disregard""";
    }
    
    /*
     * Generates a unique message ID.
     * @return A randomly generated 10-digit message ID.
     */
    public long generateMessageID(){
        
         messageID = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
        
        return messageID; 
    }

    /*
     * Checks if the provided message ID is valid.
     * @param msgID The message ID to check.
     * @return true if the message ID is valid, false otherwise.
     */
    public boolean checkMessageID(long msgID){
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
        if (totalMessages < sentMessages.length) {
            sentMessages[totalMessages] = message; 
            totalMessages++; 
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
        *
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
    
    public String storeMessageAsJSON(String recipientName, String message, String msgHash, long msgID) {
        if (messageCounter < sentMessages.length) {
            String json = "{"
                    + "\"messageID\": \"" + msgID + "\", "
                    + "\"recipient\": \"" + recipientName + "\", "
                    + "\"message\": \"" + message + "\", "
                    + "\"messageHash\": \"" + msgHash + "\""
                    + "}";
            sentMessages[messageCounter] = json;
            messageCounter++;
            return "Message successfully stored as JSON.";
        } else {
            return "Message storage is full.";
    }
}

    /*public String printMessages(String[] messages){
        if (messages == null || messages.length == 0) {
            return "No messages to display.";
        }else {
        StringBuilder sb = new StringBuilder();
        sb.append("Recently sent messages:\n");
        for (int i = 0; i < messageCounter ; i++) {
            if (messages[i] != null) {
                sb.append((i + 1)).append(".) ").append(messages[i]).append("\n");
            }
        }
        return sb.toString();
    }*/

    public String printJSONMessages() {
    if (messageCounter == 0) {
        return "No messages stored yet.";
    }
    StringBuilder sb = new StringBuilder();
    sb.append("[\n");
    for (int i = 0; i < messageCounter; i++) {
        sb.append("  ").append(sentMessages[i]);
        if (i < messageCounter - 1) sb.append(",");
        sb.append("\n");
    }
    sb.append("]");
    return sb.toString();
}
}

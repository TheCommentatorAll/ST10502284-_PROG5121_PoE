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
    
    
    
    public boolean checkSendMessage(String finalMsg){
        
        return finalMsg.contains("Welcome");
    }
    
    public void displayOptions(){
        System.out.println("""
                           Option 1): Send Messages
                           Option 2): Show recently sent
                           Option 4): Quit""");
    }
    
    public long generateMessageID(){
        
        long numRand = ThreadLocalRandom.current().nextLong(1_000_000_000L, 10_000_000_000L);
        
        return numRand; 
    }

    public boolean checkMessageID(long msgID){
        return msgID >= 1_000_000_000L && msgID < 10_000_000_000L;
    }

    public String checkRecipientCell(String cellNum) {
        if (cellNum.matches("^\\+27\\d{9}$")) {
            return "Cellphone number is valid: " + cellNum;
        } else {
            return "Invalid cellphone number.";
        }
    }

    public String createMessageHash(long msgID, int messageNumber, String message) {
        // Format the message ID as a 10-digit string so we can reliably extract the first two digits.
        String idString = String.format("%010d", msgID);
        // Use only the first two digits of the formatted message ID for the hash prefix.
        String prefix = idString.substring(0, 2);

        // If the message text is null or blank, return only the prefix and message number.
        if (message == null || message.isBlank()) {
            return String.format("%s:%d:", prefix, messageNumber);
        }

        // Split the message into words using whitespace and trim any extra spaces.
        String[] words = message.trim().split("\\s+");
        // The first word in the message.
        String firstWord = words[0];
        // The last word in the message.
        String lastWord = words[words.length - 1];
        // Combine first and last words, remove any spaces, and convert to uppercase.
        String wordHash = (firstWord + lastWord).replaceAll("\\s+", "").toUpperCase();

        // Build the final hash string in the required format.
        return String.format("%s:%d:%s", prefix, messageNumber, wordHash);
    }


    
}

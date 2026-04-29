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


    
}

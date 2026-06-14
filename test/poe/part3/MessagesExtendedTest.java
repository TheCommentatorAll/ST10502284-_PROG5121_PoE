package poe.part3;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import part3.MessagesExtended;

/**
 * JUnit 4 Test Case class for MessagesExtended.
 * Validates PoE requirements against the provided system test data.
 * * @author Nicholas Morris | ST10502284
 */
public class MessagesExtendedTest {

    MessagesExtended msgManager = new MessagesExtended();
    
    // Track dynamic hashes for validation assertions
    private String hash1;
    private String hash2;
    private String hash3;
    private String hash4;
    private String hash5;

    // Helper method to generate the expected message hash based on the provided formula
    private String createMessageHash(long msgID, int messageNumber, String message) {
        String idString = String.format("%010d", msgID);
        String prefix = idString.substring(0, 2);

        if (message == null || message.isBlank()) {
            return String.format("%s:%d:", prefix, messageNumber);
        }

        String[] words = message.trim().split("\\s+");
        String firstWord = words[0];
        String lastWord = words[words.length - 1];
        
        // Remove punctuation marks to ensure words cleanly match raw alphanumeric characters if needed
        // (Adjust regex if your hash generation explicitly strips characters like punctuation)
        String cleanFirst = firstWord.replaceAll("[^a-zA-Z0-9]", "");
        String cleanLast = lastWord.replaceAll("[^a-zA-Z0-9]", "");
        
        String wordHash = (cleanFirst + cleanLast).toUpperCase();
        return String.format("%s:%d:%s", prefix, messageNumber, wordHash);
    }

    // Set up the test environment before each test case
    @Before
    // Initialise the MessagesExtended instance and populate it with test data before each test case
    public void setUp() {
        // Re-instantiate the MessagesExtended object to ensure a clean state for each test
        msgManager = new MessagesExtended();

        // 10-digit IDs established from requirements/developer data
        long id1 = 1000000001L;
        long id2 = 1000000002L;
        long id3 = 1000000003L;
        long id4 = 838884567L; // Derived from Message 4 developer field
        long id5 = 1000000005L;

        // Generate the expected hashes for each message based on the provided data
        hash1 = createMessageHash(id1, 1, "Did you get the cake?");
        hash2 = createMessageHash(id2, 2, "Where are you? You are late! I have asked you to be on time.");
        hash3 = createMessageHash(id3, 3, "Yohoooo, I am at your gate.");
        hash4 = createMessageHash(id4, 4, "It is dinner time !");
        hash5 = createMessageHash(id5, 5, "Ok, I am leaving without you.");

        // Populate arrays with the PoE's official 5 test messages
        msgManager.populateArrays(id1, hash1, "+27834557896", "Did you get the cake?", "Sent");
        msgManager.populateArrays(id2, hash2, "+27838884567", "Where are you? You are late! I have asked you to be on time.", "Stored");
        msgManager.populateArrays(id3, hash3, "+27834484567", "Yohoooo, I am at your gate.", "Disregard");
        msgManager.populateArrays(id4, hash4, "0838884567", "It is dinner time !", "Sent");
        msgManager.populateArrays(id5, hash5, "+27838884567", "Ok, I am leaving without you.", "Stored");
    }

    
    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        assertEquals("The system should contain exactly 5 records.", 5, msgManager.getStoredCount());
    }

    
    @Test
    public void testDisplayLongestMessage() {
        String expectedLongest = "Where are you? You are late! I have asked you to be on time.";
        String actualLongest = msgManager.getLongestStoredMessage();
        assertEquals(expectedLongest, actualLongest);
    }

   
    @Test
    public void testSearchForMessageID() {
        long targetID = 838884567L; 
        String expectedResponse = "\"It is dinner time !\"";
        String actualResponse = msgManager.searchByMessageID(targetID);
        assertEquals(expectedResponse, actualResponse);
    }

    
    @Test
    public void testSearchAllMessagesByRecipient() {
        String targetRecipient = "+27838884567";
        String expectedResponse = "\"Where are you? You are late! I have asked you to be on time.\" \"Ok, I am leaving without you.\"";
        String actualResponse = msgManager.searchByRecipient(targetRecipient);
        assertEquals(expectedResponse, actualResponse);
    }

    
    @Test
    public void testDeleteMessageUsingHash() {
        // Retrieve initial state
        int initialCount = msgManager.getStoredCount();
        
        // Attempt deletion of Message 2 using its computed hash
        boolean isDeleted = msgManager.deleteMessageByHash(hash2);
        
        assertTrue("Deletion routine should return true when hash matches.", isDeleted);
        assertEquals("The stored count should drop by exactly one.", initialCount - 1, msgManager.getStoredCount());
        
        // Edge check: Ensure searching for deleted message contents fails safely
        String checkSearch = msgManager.searchByMessageID(1000000002L);
        assertEquals("Message ID not found.", checkSearch);
    }

    
    @Test
    public void testSearchByMessageIDNotFound() {
        long invalidID = 9999999999L;
        String expected = "Message ID not found.";
        assertEquals(expected, msgManager.searchByMessageID(invalidID));
    }

    
    @Test
    public void testSearchByRecipientNotFound() {
        String invalidRecipient = "+27000000000";
        String expected = "No messages found for this recipient.";
        assertEquals(expected, msgManager.searchByRecipient(invalidRecipient));
    }
}
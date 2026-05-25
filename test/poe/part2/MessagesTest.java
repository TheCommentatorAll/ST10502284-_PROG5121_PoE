
package poe.part2;

/**
 **************************************
 * @author Nicholas Morris | ST10502284
 **************************************
 */

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import part2.Messages;

public class MessagesTest {//start of class

    Messages msg = new Messages();

    @Test
    public void testMessageLengthSuccess() {
        // A short message should return the success string
        String result = msg.checkMessageLength("Hi Mike, can you join us for dinner tonight");
        assertEquals("Message ready to send.", result);
    }

    @Test
    public void testMessageLengthFailure() {
        // Build a string that is definitely over 250 characters
        String longMsg = "A".repeat(260); // 260 A's
        int over = 260 - 250;            // = 10
        String expected = "Message exceeds 250 characters by " + over + ", please reduce size.";
        assertEquals(expected, msg.checkMessageLength(longMsg));
    }

    @Test
    public void testRecipientCellSuccess() {
        // +27718693002 is valid — starts with +27 and has 9 digits after
        String result = msg.checkRecipientCell("+27718693002");
        assertEquals("Cell phone number successfully captured.", result);
    }

    @Test
    public void testRecipientCellFailure() {
        // 08575975889 is invalid — no +27 international code
        String result = msg.checkRecipientCell("08575975889");
        assertEquals("Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.", result);
    }

    @Test
    public void testMessageHashTestCase1() {
        
        long fixedID = 0000000001L; // prefix will be "00"
        String result = msg.createMessageHash(fixedID, 0, "Hi Mike, can you join us for dinner tonight");
        assertEquals("00:0:HITONIGHT", result);
        System.out.println("Message Hash generated: " + result);
    }

    @Test
    public void testMessageIDIsValid() {
        // Generate a random ID and verify it falls in the valid 10-digit range
        long id = msg.generateMessageID();
        assertTrue(msg.checkMessageID(id));
        System.out.println("Message ID generated: " + id);
    }

    @Test
    public void testMessageSentAction() {
        assertEquals("Message successfully sent.", msg.messageSent());
    }

    @Test
    public void testMessageDisregardedAction() {
        assertEquals("Press 0 to delete message.", msg.messageDisregarded());
    }

    @Test
    public void testMessageStoredAction() {
        assertEquals("Message successfully stored.", msg.messageStored());
    }
}


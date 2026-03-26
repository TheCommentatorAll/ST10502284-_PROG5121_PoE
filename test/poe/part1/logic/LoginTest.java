/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package poe.part1.logic;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author nexis
 */
public class LoginTest {

    // Create one instance of Login to use for all tests
    Login login = new Login();

    @Test
    public void testCheckUserName() {
        // From PoE Page 9: "kyl_1" should return true
        assertTrue(login.checkUserName("kyl_1"));

        // From PoE Page 9: "kyle!!!!!!" should return false
        assertFalse(login.checkUserName("kyle!!!!!!"));
    }

    @Test
    public void testCheckPasswordComplexity() {
        // From PoE: "Ch&&sec@ke99!" should return true
        assertTrue(login.checkPasswordComplexity("Ch&&sec@ke99!"));

        // From your PoE: "password" should return false
        assertFalse(login.checkPasswordComplexity("password"));
    }

    @Test
    public void testLoginUser() {
        // Setup: We MUST register a user first so the 'stored' variables aren't empty
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");

        // Test 1: Correct credentials (Expected: true)
        assertTrue(login.loginUser("kyl_1", "Ch&&sec@ke99!"));

        // Test 2: Incorrect credentials (Expected: false)
        assertFalse(login.loginUser("wrong_user", "wrong_pass"));
    }

    @Test
    public void testReturnLoginStatus() {
        // 1. Setup: Register a user with specific names
        login.registerUser("kyl_1", "Ch&&sec@ke99!", "John", "Doe", "+27838968976");

        // 2. Define the EXACT string the PoE expects
        String expected = "Welcome John Doe, it is great to see you again.";

        // 3. Run the login and get the status message
        boolean loginSuccess = login.loginUser("kyl_1", "Ch&&sec@ke99!");
        String actual = login.returnLoginStatus(loginSuccess);

        // 4. Compare them
        assertEquals(expected, actual);
    }
}

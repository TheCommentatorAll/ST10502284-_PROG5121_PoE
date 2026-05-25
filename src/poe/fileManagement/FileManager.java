package poe.fileManagement;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {

    private static final String FILE_PATH = "users.txt";

    private static final String DELIMITER = "[]";

    private static final String STRING_REGEX = "\\[\\]";


    /*
     * Saves a new registered user to the file.
     * @param name The user's name.
     * @param surname The user's surname.
     * @param username The user's username.
     * @param password The user's password.
     * @param cellNumber The user's cell phone number.
     * @return A message indicating the result of the operation.
     */
    public String saveRegisteredUser (String name, String surname, String username, String password, String cellNumber) {

        // Create a string to save to the file
        String userData = username + DELIMITER 
                        + password + DELIMITER 
                        + name + DELIMITER 
                        + surname + DELIMITER 
                        + cellNumber;

        // Save the user data to the file
        try (java.io.FileWriter writer = new java.io.FileWriter(FILE_PATH, true)) {
            writer.write(userData + System.lineSeparator());
            return "User registered successfully.";
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return "An error occurred while registering the user.";
        }
    }

    /*
     * Reads the registered user data from the file.
     * @param username The user's username.
     * @param password The user's password.
     * @return An array containing the user's data, or null if not found.
     * @throws IOException If an error occurs while reading the file.
     */
    public String[] readRegisteredUsers(String username, String password) throws IOException {

         try(BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {

            String line;

            while((line = reader.readLine()) != null) {
                String[] userDataFields = line.split(STRING_REGEX);
                 
                if(userDataFields.length == 5 && userDataFields[0].equals(username) && userDataFields[1].equals(password)) {
                    return userDataFields;
                }
            }
         }catch (IOException e) {
            e.printStackTrace();
            System.out.println("An error occurred while reading the user data: " + e.getMessage());
        }

        return null;

    }

    /*
     * Checks if a user exists in the file.
     * @param username The user's username.
     * @param password The user's password.
     * @return true if the user exists, false otherwise.
     * @throws IOException If an error occurs while reading the file.
     */
    public boolean userExists(String username, String password) throws IOException{
        return readRegisteredUsers(username, password) != null;
    }



}

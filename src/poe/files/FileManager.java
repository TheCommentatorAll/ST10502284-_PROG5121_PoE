package poe.files;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileManager {

    private static final String FILE_PATH = "users.txt";

    private static final String DELIMITER = ":$:";

    private static final String STRING_REGEX = ":\\$:";

    public String saveRegisteredUser (String username, String password, String name, String surname, String cellNumber) {

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

    public boolean userExists(String username, String password) throws IOException{
        return readRegisteredUsers(username, password) != null;
    }



}

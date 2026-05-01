/**
 * Student Name: Nicholas Morris
 * Student Number: ST10502284
 * Assignment: PROG5121 PoE Part 1 - Registration and Login
 * Description: This class handles user input and method calling
 */
package poe.main;

import java.util.Scanner;
import poe.part1.logic.Login;
import poe.part2.logic.Messages;

public class RunApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //create a login object called auth
        Login loginHandler = new Login();
        //create a messages object called inApp
        Messages messagingApp = new Messages();

        String finalMessage = "";
        String registerName = "";
        String registerSurname = "";
        String registerUsername = "";
        String registerPassword = "";
        String registerNumber = "";

        //--- REGISTRATION PHASE ---
        boolean registered = false;
        while (!registered) {
            System.out.println("-- REGISTER NEW ACCOUNT --");
            System.out.print("Enter your Name: ");
            registerName = input.nextLine();

            System.out.print(">" + "\nEnter your surname: ");
            registerSurname = input.nextLine();

            System.out.print(">" + "\nEnter your Username: ");
            registerUsername = input.nextLine();

            System.out.print(">" + "\nEnter your Password: ");
            registerPassword = input.nextLine();

            System.out.print(">" + "\nEnter your Cellphone Number: ");
            registerNumber = input.nextLine();
            System.out.println("-----------------------------------");

            String regStatus = loginHandler.registerUser(registerUsername, registerPassword, registerName, registerSurname, registerNumber);
            System.out.println(regStatus);

            if (regStatus.contains("registered successfully")) {
                registered = true;
                System.out.println("-----------------------------------");
                System.out.println("-- LOGIN DETAILS --");
                System.out.println("Your username is: " + registerUsername + "\nYour password is: " + registerPassword);
                System.out.println("-----------------------------------");
            } else {
                System.out.println("Registration failed. Please try again.");
                System.out.println("-----------------------------------");
            }
        }

        //--- USER LOGIN PHASE ---
        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("-- LOGIN TO ACCOUNT --");
            System.out.print("Please enter your Username: ");
            String username = input.nextLine();

            System.out.print(">" + "\nPlease enter your Password: ");
            String password = input.nextLine();
            System.out.println("-----------------------------------");

            boolean isSuccess = loginHandler.loginUser(username, password);

            if (isSuccess) {
                loggedIn = true;
                finalMessage = loginHandler.returnLoginStatus(isSuccess);
                System.out.println("-- STATUS --");
                System.out.println(finalMessage);
            } else {
                System.out.println("Login failed. Please try again.");
                System.out.println("-----------------------------------");
            }
        }

        boolean isLoggedIn = messagingApp.checkSendMessage(finalMessage);

        if (isLoggedIn) {
            System.out.println("");
            System.out.println("---Welcome to QuickChat---");

            messagingApp.displayOptions();
            System.out.print("\tSelect Option: ");
            int menuSelection = input.nextInt();
            System.out.println("\n-----------------------------------");

            while (menuSelection != 3) {
                String messageOptions = messagingApp.sentMessage();
                System.out.println(messageOptions);
                System.out.print("\tSelect Message Action: ");
                int messageAction = input.nextInt();
                switch (messageAction) {

                    case 1 -> {
                        System.out.print("Enter the number of messages you want to send: ");
                        int numOfMessages = input.nextInt();
                        input.nextLine();

                        for (int i = 0; i < numOfMessages; i++) {

                            System.out.println("===================================");
                            long msgID = messagingApp.generateMessageID();
                            boolean msgIDCheck = messagingApp.checkMessageID(msgID);
                            System.out.println("Generated Message ID: " + msgID);
                            System.out.println("Message ID Valid: " + msgIDCheck);
                            System.out.println("===================================");

                            System.out.println("Please enter your message: ");
                            String message = input.nextLine();

                            int currentMsgCount = messagingApp.incrementMessageCounter();

                            String msgHashString = messagingApp.createMessageHash(msgID, currentMsgCount, message);

                            messagingApp.storeMessageAsJSON(msgID, msgHashString, registerName, message);
                        }
                        System.out.println(messagingApp.printJSONMessages());

                    }

                }


            }
            input.close();

        }
    }
}

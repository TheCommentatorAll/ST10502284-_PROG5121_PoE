/**
 * Student Name: Nicholas Morris
 * Student Number: ST10502284
 * Assignment: PROG5121 PoE Part 1 - Registration and Login
 * Description: Main Class that runs the application, handles user input and output, and calls the necessary methods from the Login and Messages classes
 */
package poe.main;

import java.util.Scanner;
import poe.files.FileManager;
import poe.part1.logic.Login;
import poe.part2.logic.Messages;

public class RunApp {//start of class

    public static void main(String[] args) {//start of main method

        Scanner input = new Scanner(System.in);
        Login loginHandler = new Login();
        Messages messagingApp = new Messages();
        FileManager fileManager = new FileManager();

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

            System.out.print(">" + "\nEnter your Surname: ");
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
                fileManager.saveRegisteredUser(registerName, registerSurname, registerUsername, registerPassword, registerNumber);
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

        if (loggedIn) {

            boolean runApp = true;
            while (runApp) {
                System.out.println("");
                System.out.println("---Welcome to QuickChat---");
                messagingApp.displayOptions();
                System.out.print("\tSelect Option: ");
                int menuSelection = input.nextInt();
                input.nextLine();

                switch (menuSelection) {

                    case 1 -> {

                        System.out.println("You have selected: Send Messages");

                        System.out.print("Enter the number of messages you want to send: ");
                        int numOfMessages = input.nextInt();
                        input.nextLine();

                        for (int i = 0; i < numOfMessages; i++) {

                            System.out.println("Please enter your message: ");
                            System.out.print(":$>> ");
                            String message = input.nextLine();
                            messagingApp.setMessage(message);

                            System.out.println("===================================");
                            long msgID = messagingApp.generateMessageID();
                            messagingApp.setMessageID(msgID);
                            boolean msgIDCheck = messagingApp.checkMessageID(msgID);
                            System.out.println("Generated Message ID: " + msgID);
                            System.out.println("Message ID Valid: " + msgIDCheck);
                            System.out.println("===================================");

                            int currentMsgCount = messagingApp.incrementMessageCounter();
                            messagingApp.setGlobalMessageCounter(currentMsgCount);
                            
                            String msgHashString = messagingApp.createMessageHash(msgID, currentMsgCount, message);
                            messagingApp.setMsgHashString(msgHashString);

                            messagingApp.storeMessageAsRegular(msgID, msgHashString, registerNumber, message);
                        }

                        System.out.println(messagingApp.sentMessage());
                        System.out.print("\tSelect Message Action: ");
                        int messageAction = input.nextInt();

                        switch (messageAction) {

                            case 1 -> {
                                System.out.println("You have selected: Send Message");
                                System.out.println("Sending messages...");
                                System.out.println("Messages sent successfully!");

                                System.out.println(messagingApp.printMessages());
                                System.out.println("Total messages sent: " + messagingApp.getGlobalMessageCounter());

                            }

                            case 2 -> {
                                System.out.println("You have selected: Store Messages");
                                messagingApp.storeMessageAsJSON(messagingApp.getMessageID(), messagingApp.getMsgHashString(), registerNumber, messagingApp.getMessage());
                                System.out.println("Message stored successfully!");

                            }

                            case 3 -> { //goes back to main menu, does not exit the app
                                System.out.println("You have selected: Disregard");
                                System.out.println("Returning to main menu...");

                            }

                        }

                    }
                    case 2 -> {
                        System.out.println("You have selected: Show recently sent");
                        System.out.println("Retrieving recently sent messages...");
                        System.out.println(messagingApp.printMessages());
                        System.out.println("Total messages sent: " + messagingApp.getGlobalMessageCounter());

                    }

                    case 3 -> {
                        System.out.println("You have selected: Quit");
                        System.out.println("Exiting application...");
                        runApp = false;
                    }

                    default -> {
                        System.out.println("Invalid selection, please try again.");
                        messagingApp.displayOptions();
                        System.out.print("\tSelect Option: ");
                        menuSelection = input.nextInt();
                    }

                }

            }

        }

    }//end of main method
}//end of class

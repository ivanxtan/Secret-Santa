// Ivan Tan
// December 14, 2018
// Secret Santa

// This program uses the Participant class and allows a user to:
//    create a list of participants
//    list all the participants names
//    create "Secret Santa" pairings 
//       Each participant is randomly assigned a person to send a gift to. 
//       There should not be two people sending a gift to the same person.
//    view all the "Secret Santa" pairings
//    view the recipient for a specific participant
//    scroll through each recipient for all participants one by one

import java.util.*;

public class SecretSanta {
   // Represents the threshold multiplier to reset the loop when 
   // an impossible matching scenario occurs
   public static final int RETRY_COUNT_MULTIPLIER = 2;

   public static void main(String[] args) {
      Random rand = new Random();
      Scanner console = new Scanner(System.in);
      
      intro();
      
      Participant[] participants = createParticipants(console);
      
      /* 
      
      // Create n filler participants with format "Participant #x"
      
      participants = new Participant[1000];
      for (int i = 0; i < participants.length; i++) {
         participants[i] = new Participant("Participant #" + (i + 1));
      }
      
      */
      
      String choice;
      do {
         printHighlighted("MAIN MENU");
         System.out.println("(C)reate participants list.");
         System.out.println("(L)ist all participants' names.");
         System.out.println("Create (P)airings for the participants.");
         System.out.println("View (A)ll the pairings of participants.");
         System.out.println("View the recipient for (O)ne participant.");
         System.out.println("(S)croll through each recipient for ALL " + 
               "participants one by one.");
         System.out.println("(Q)uit.");
         
         choice = console.nextLine();
         if (choice.equalsIgnoreCase("c")) {
            participants = createParticipants(console);
         } else if (choice.equalsIgnoreCase("l")) {
            listParticipants(participants);
         } else if (choice.equalsIgnoreCase("p")) {
            assignRecipients(participants, rand);
         } else if (choice.equalsIgnoreCase("a")) {
            printAll(participants);
         } else if (choice.equalsIgnoreCase("o")) {
            printSpecific(console, participants);
         } else if (choice.equalsIgnoreCase("s")) {
            scrollAll(console, participants);
         }
         
         System.out.println();
      } while (!choice.equalsIgnoreCase("q"));
   }
   
   // Prints modified text to highlight it
   // Parameters:
   //    String text = the desired text
   public static void printHighlighted(String text) {
      System.out.println("*** " + text + " ***");
   }
   
   // Prints the introduction of the program
   public static void intro() {
      printHighlighted("SECRET SANTA");
      printHighlighted("This program allows you to add the participants of the");
      printHighlighted("game and assign them matches to send and receive gifts.");
   }
   
   // Prompts the user for the number of participants 
   // and the names of each participant.
   // Parameters:
   //    Scanner console = console for user input
   // Returns:
   //    list of Participant objects (Participant[])
   public static Participant[] createParticipants(Scanner console) {
      System.out.println();
      printHighlighted("Enter the number of participants desired");
      int num = promptInt(console);
      while (num <= 0) {
         printHighlighted("Please enter a valid number.");
         num = promptInt(console);
      }
      
      // to get rid of the \n created from nextInt() to fix Scanner's nextLine()
      console.nextLine(); 
      
      Participant[] participants = new Participant[num];
      for (int i = 0; i < participants.length; i++) {
         System.out.println();
         
         printHighlighted("Enter the name for participant #" + (i + 1));
         participants[i] = new Participant(console.nextLine());
      }
      
      System.out.println();
      return participants;
   }
   
   // Lists the names of each participant.
   // Parameters:
   //    Participant[] participants = the desired list of Participant objects
   public static void listParticipants(Participant[] participants) {
      for (int i = 0; i < participants.length; i++) {
         System.out.println((i + 1) + ": " + participants[i].getName());
      }
      
      System.out.println();
   }
   
   // Assigns the recipients of each participant.
   // Parameters:
   //    Participant[] participants = the desired list of Participant objects
   //    Random rand = the random object
   public static void assignRecipients(Participant[] participants, 
         Random rand) {
      System.out.println();
      
      boolean allHaveMatches = false;
      while (!allHaveMatches){
         resetMatches(participants);
         
         for (int i = 0; i < participants.length; i++) {
            printHighlighted("SEARCH FOR: \"" + 
                  participants[i].getName().toUpperCase() + 
                  "\"");
                  
            int retryCount = 0; // to avoid infinite loop when an 
            while (!participants[i].hasRecipient() && 
                  retryCount < participants.length * RETRY_COUNT_MULTIPLIER) {
               // random index of Participant in participants array
               int num = rand.nextInt(participants.length);
               
               // NOT itself AND recipient does NOT have gifter/"Santa"
               if (num != i && !participants[num].hasSender()) {
                  participants[i].setRecipient(participants[num]);
                  participants[num].setSender(participants[i]);
                  System.out.println("Random participant found.");
               } else if (num == i) { // does not count towards retryCount
                  System.out.println("Random participant was themselves..." + 
                        "RETRYING.");
               } else if (participants[num].hasSender()) {
                  System.out.println("Random participant was \"" + 
                        participants[num].getName() + 
                        "\" who already has sender: \"" + 
                        participants[num].getSenderName() + 
                        "\"...RETRYING.");
                  retryCount++;
               } else {
                  System.out.println("Other error...RETRYING");
                  retryCount++;
               }
               
               if (retryCount > participants.length * RETRY_COUNT_MULTIPLIER) {
                  printHighlighted("OVER RETRY THRESHOLD...WILL RESET AFTER LOOP");
               }
            }
            
            System.out.println();
         }
         
         allHaveMatches = allHaveMatches(participants);
         if (allHaveMatches) {
            printHighlighted("ALL PARTICIPANTS HAVE MATCHES");
         } else {
            printHighlighted("NOT ALL PARTICIPANTS HAVE MATCHES");
            System.out.println();
            printHighlighted("RESTARTING LOOP");
         }
         
         System.out.println();
      }
   }
   
   // Checks if all participants have matches/pairings
   // Parameters:
   //    Participant[] participants = the desired list of Participant objects
   // Returns:
   //    whether or not all participants have matches/pairings (boolean)
   public static boolean allHaveMatches(Participant[] participants) {
      for (Participant person : participants) {
         if (!person.hasRecipient() || !person.hasSender()) {
            return false;
         }
      }
      return true;
   }
   
   // Reset all matches/pairings of the participants
   // Parameters:
   //    Participant[] participants = the desired list of Participant objects
   public static void resetMatches(Participant[] participants) {
      for (Participant person : participants) {
         person.resetRecipient();
         person.resetSender();
      }
   }
   
   // Prints all matches/pairings of the participants
   // Parameters:
   //    Participant[] participants = the desired list of Participant objects
   public static void printAll(Participant[] participants) {
      System.out.println();
      printHighlighted("MATCHES OF ALL PARTICIPANTS");
      
      if (allHaveMatches(participants)) {
         for (Participant person : participants) {
            System.out.println(person.getName() + 
                  " will get a gift from " + 
                  person.getSenderName() + 
                  " and send a gift to: " + 
                  person.getRecipientName());
         }
      } else {
         printHighlighted("There are no pairings created for the " + 
               "participants yet.");
      }
      
      System.out.println();
   }
   
   // Lists the names of all participants, prompts the user for a participant,
   // and prints the desired match/pairing of a specific participant
   // Parameters:
   //    Scanner console = console for user input
   //    Participant[] participants = the desired list of Participant objects
   public static void printSpecific(Scanner console, Participant[] participants) {
      System.out.println();
      printHighlighted("VIEW SPECIFIC RECIPIENT OF A PARTICIPANT");
      
      if (allHaveMatches(participants)) {
         printHighlighted("Select the number of a specific participant");
         listParticipants(participants);
         
         int participantChoice = promptInt(console);
         while (participantChoice <= 0 || participantChoice > participants.length) {
            printHighlighted("Please enter a valid number.");
            participantChoice = promptInt(console);
         }
         
         System.out.println();
         
         int participantIndex = participantChoice - 1;
         System.out.println(participants[participantIndex].getName() + 
               " will send a gift to: " + 
               participants[participantIndex].getRecipientName());
               
         System.out.println();
         
         // to get rid of the \n created from nextInt() to fix Scanner's nextLine()
         console.nextLine(); 
         
         printHighlighted("Press enter to clear the output");
         console.nextLine();
         clearScreen(50);
      } else {
         printHighlighted("There are no pairings created for the " + 
               "participants yet.");
         System.out.println();
      }
   }
   
   // Lists the desired match/pairing of each participant one by one
   // Parameters:
   //    Scanner console = console for user input
   //    Participant[] participants = the desired list of Participant objects
   public static void scrollAll(Scanner console, Participant[] participants) {
      System.out.println();
      printHighlighted("VIEW EACH RECIPIENT OF ALL PARTICIPANTS ONE BY ONE");
      
      if (allHaveMatches(participants)) {
         for (int i = 0; i < participants.length; i++) {
            System.out.println();
            System.out.println(participants[i].getName() + 
                  " will send a gift to: " + 
                  participants[i].getRecipientName());
                  
            System.out.println();
            
            if (i + 1 < participants.length) {
               printHighlighted("Press enter to see the recipient for the next " + 
                     "participant: " + participants[i + 1].getName() + " (" + 
                     (participants.length - i - 1) + 
                     " left)");
               printHighlighted("Enter Q to go back to the main menu");
            } else { // i + 1 >= participants.length
               printHighlighted(
                     "There are no more participants.");
               printHighlighted("Press enter or enter Q to go back to the main menu");
               System.out.println();
            }
            
            String scrollChoice = console.nextLine();
            if (scrollChoice.equalsIgnoreCase("Q")) {
               System.out.println();
               break;
            }
            
            clearScreen(50);
         } 
      } else {
         printHighlighted(
               "There are no pairings created for the participants yet.");
         System.out.println();
      }
   }
   
   // Floods the output by printing blank lines
   // Parameters:
   //    int num = the desired number of lines to flood the output
   public static void clearScreen(int num) {
      for (int i = 0; i < num; i++) {
         System.out.println(); 
         System.out.flush();
      }  
   }
   
   // Prompts the user for an integer
   // Parameters:
   //    Scanner console = console for user input
   public static int promptInt(Scanner console) {
      int num = 0;
      try {
         num = console.nextInt();
      } catch (InputMismatchException e) {
         console.next();
      }
      return num;
   }
}
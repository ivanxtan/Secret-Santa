// Ivan Tan
// December 16, 2018
// Secret Santa

// This program takes an output file from the SecretSanta program and sends 
// an email through Gmail that details the specific recipient for a participant.


import java.util.*;
import java.io.*;
// https://javaee.github.io/javamail/
import javax.mail.*;
import javax.mail.internet.*;

public class SendEmail {
   // Represents the Gmail username of the account to send from
   // (just the part before "@gmail.com")
   private static String USERNAME = "";
    
   // Represents the Gmail password of the account to send from
   // https://support.google.com/accounts/answer/185833 for 2FA
   private static String PASSWORD = ""; 
    
   // Represents the last line of the email (the signature)
   private static String SIGNATURE = "https://ivanxtan.com/"; 
    
   public static void main(String[] args) throws FileNotFoundException {
      String from = USERNAME;
      String pass = PASSWORD;
      
      Scanner console = new Scanner(System.in);
      File inputFile = getInputFile(console);
      Scanner input = new Scanner(inputFile);
      
      int count = 0;
      while (input.hasNextLine()) {
         input.nextLine(); // throw away Participant #
         // Participant name
         String nameLine = input.nextLine();
         String name = nameLine.substring(7, nameLine.length() - 1);
         // Participant email
         String emailLine = input.nextLine();
         String email = emailLine.substring(8, emailLine.length() - 1);
         String[] to = { email }; // list of recipient email addresses
         // Participant sender
         String senderLine = input.nextLine();
         String sender = senderLine.substring(30, senderLine.length() - 1);
         // Participant recipient 
         String recipientLine = input.nextLine();
         String recipient = recipientLine.substring(12, recipientLine.length() - 1);
         input.nextLine(); // throw away blank line

         String subject = "Secret Santa: " + name;
         String body = name + "," + "\n\n" + 
               "Your recipient/giftee is " + recipient + ".\n\n" + 
               "Have fun!" + "\n" + 
               SIGNATURE;
         
         sendFromGMail(from, pass, to, subject, body);
         
         System.out.println("Name: \"" + name + "\"" +  " Email: \"" + email + "\"" + " Recipient/Giftee: \"" + recipient + "\"");
         System.out.println();
         count++;
      }
      System.out.println(count + " email(s) sent.");
   }
   
   // Code from: https://stackoverflow.com/questions/46663/
   private static void sendFromGMail(String from, String pass, String[] to, String subject, String body) {
      Properties props = System.getProperties();
      String host = "smtp.gmail.com";
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.user", from);
      props.put("mail.smtp.password", pass);
      props.put("mail.smtp.port", "587");
      props.put("mail.smtp.auth", "true");
      
      Session session = Session.getDefaultInstance(props);
      MimeMessage message = new MimeMessage(session);
      
      try {
         message.setFrom(new InternetAddress(from));
         InternetAddress[] toAddress = new InternetAddress[to.length];
         
         // To get the array of addresses
         for( int i = 0; i < to.length; i++ ) {
            toAddress[i] = new InternetAddress(to[i]);
         }
         
         for( int i = 0; i < toAddress.length; i++) {
            message.addRecipient(Message.RecipientType.TO, toAddress[i]);
         }
         
         message.setSubject(subject);
         message.setText(body);
         Transport transport = session.getTransport("smtp");
         transport.connect(host, from, pass);
         transport.sendMessage(message, message.getAllRecipients());
         transport.close();
      } catch (AddressException ae) {
         ae.printStackTrace();
      } catch (MessagingException me) {
         me.printStackTrace();
      }
   }
   
   // Prompts the user for an input file until they enter an existing file.
   // Throws a FileNotFoundException when the file does not exist.
   // Parameters:
   //    console = console for user input (Scanner)
   // Returns:
   //    the file that the user specified (File)
   public static File getInputFile(Scanner console) throws FileNotFoundException {
      System.out.println("Input file name (without a file extension).");
      String name = console.nextLine() + ".txt";
      File targetFile = new File(name);
      while (!targetFile.exists()) {
         System.out.println("File not found. Try again.");
         name = console.nextLine() + ".txt";
         targetFile = new File(name);
      }
      System.out.println();
      return targetFile;
   }
}
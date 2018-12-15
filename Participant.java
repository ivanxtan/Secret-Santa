// Ivan Tan
// December 14, 2018

// Secret Santa

// This class represents a participant in the Secret Santa game. 
// It outlines and stores the name of the participant and the other 
// participant that this participant is responsible for getting 
// a gift for.

public class Participant {
   // Represents the name of the Participant object (String)
   private String name;
   
   // Represents the recipient/giftee of this particular 
   // Participant object (Participant)
   private Participant recipient;
   
   // Represents the sender/gifter of this particular
   // Participant object (Participant)
   private Participant sender;
   
   // Constructor method
   //    String name = the desired name for the Participant object
   public Participant(String name) {
      this.name = name;
   }
   
   // Accessor/Getter method
   //    Get the name of the Participant object
   // Returns:
   //    name of the Participant object (String)
   public String getName() {
      return name;
   }
   
   // Checks if the Participant object has the recipient set
   // Returns:
   //    whether or not the recipient is set (boolean)
   public boolean hasRecipient() {
      return recipient != null;
   }
   
   // Accessor/Getter method
   //    Get the name of the recipient of the Participant object
   // Returns:
   //    name of the recipient of the Participant object (String)
   public String getRecipientName() {
      return recipient.getName();
   }
   
   // Mutator/Setter method
   //    Sets the recipient of the Participant object
   // Parameters:
   //    Participant recipient = the desired recipient
   public void setRecipient(Participant recipient) {
      this.recipient = recipient;
   }
   
   // Resets the recipient of the Participant object
   public void resetRecipient() {
      this.recipient = null;
   }
   
   // Checks if the Participant object has the sender set
   // Returns:
   //    whether or not the sender is set (boolean)
   public boolean hasSender() {
      return sender != null;
   }
   
   // Accessor/Getter method
   //    Get the name of the sender of the Participant object
   // Returns:
   //    name of the sender of the Participant object (String)
   public String getSenderName() {
      return sender.getName();
   }
   
   // Mutator/Setter method
   //    Sets the sender of the Participant object
   // Parameters:
   //    Participant sender = the desired sender
   public void setSender(Participant sender) {
      this.sender = sender;
   }
   
   // Resets the sender of the Participant object
   public void resetSender() {
      this.sender = null;
   }
}
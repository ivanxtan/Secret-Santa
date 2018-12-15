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
   
   public Participant(String name) {
      this.name = name;
   }
   
   public String getName() {
      return name;
   }
   
   public boolean hasRecipient() {
      return recipient != null;
   }
   
   public String getRecipientName() {
      return recipient.getName();
   }
   
   public void setRecipient(Participant recipient) {
      this.recipient = recipient;
   }
   
   public void resetRecipient() {
      this.recipient = null;
   }
   
   public boolean hasSender() {
      return sender != null;
   }
   
   public String getSenderName() {
      return sender.getName();
   }
   
   public void setSender(Participant sender) {
      this.sender = sender;
   }
   
   public void resetSender() {
      this.sender = null;
   }
}
package dtupay.service;

import customer.service.DTUPayAccount;
import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;

public class AccountManagementService {
    MessageQueue queue;

    private CompletableFuture<DTUPayAccount> registeredMerchant;

    public AccountManagementService(MessageQueue q){
        this.queue = q;
        this.queue.addHandler("AccountCreated", this::handleRegistrationCompleted);
        this.queue.addHandler("DTUPayAccountReturned", this::handleAccountAlreadyExists);
    }

    public DTUPayAccount register(DTUPayAccount account){
        registeredMerchant = new CompletableFuture<>();
        Event event = new Event("RegisterAccountRequested", new Object[] { account });
        System.out.println("Event published MerchantRegistrationRequested");
        queue.publish(event);
        return registeredMerchant.join();
    }
    public void  handleRegistrationCompleted(Event event){
        System.out.println("Event received MerchantAssigned " + event);
        var s = event.getArgument(0, DTUPayAccount.class);
        registeredMerchant.complete(s);
    }

    public void handleAccountAlreadyExists(Event event) {
        System.out.println("Event received AccountAlreadyExists");
        var a = event.getArgument(0, DTUPayAccount.class);
        registeredMerchant.complete(a);
    }
}

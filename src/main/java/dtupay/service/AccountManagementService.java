package dtupay.service;

import merchant.service.Merchant;
import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;

public class AccountManagementService {
    MessageQueue queue;

    private CompletableFuture<Merchant> registeredMerchant;

    public AccountManagementService(MessageQueue q){
        this.queue = q;
        this.queue.addHandler("MerchantAssigned", this::handleRegistrationCompleted);
    }

    public Merchant register(Merchant m){
        registeredMerchant = new CompletableFuture<>();
        Event event = new Event("MerchantRegistrationRequested", new Object[] { m });
        System.out.println("Event published MerchantRegistrationRequested");
        queue.publish(event);
        return registeredMerchant.join();
    }
    public void  handleRegistrationCompleted(Event event){
        System.out.println("Event received MerchantAssigned " + event);
        var s = event.getArgument(0, Merchant.class);
        registeredMerchant.complete(s);
    }
}

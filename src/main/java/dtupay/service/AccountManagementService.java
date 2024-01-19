package dtupay.service;

import customer.service.DTUPayAccount;
import messaging.Event;
import messaging.MessageQueue;

import java.util.concurrent.CompletableFuture;

//@Author: Andreas
public class AccountManagementService {
    private MessageQueue queue;
    private CompletableFuture<DTUPayAccount> registeredAccount;
    private CompletableFuture<DTUPayAccount> returnedAccount;

    public AccountManagementService(MessageQueue q) {
        queue = q;
        queue.addHandler("AccountCreated", this::handleAccountCreated);
        queue.addHandler("DTUPayAccountReturned", this::handleAccountReturned);
        queue.addHandler("AccountAlreadyExists", this::handleAccountAlreadyExists);
        queue.addHandler("DTUPayAccountNotFound", this::handleAccountNotFound);
    }

    public DTUPayAccount register(DTUPayAccount account) {
        registeredAccount = new CompletableFuture<>();
        Event event = new Event("RegisterAccountRequested", new Object[] { account });
        queue.publish(event);
        return registeredAccount.join();
    }

    public DTUPayAccount handleAccountCreated(Event ev) {
        var a = ev.getArgument(0, DTUPayAccount.class);
        registeredAccount.complete(a);
        return a;
    }

    private DTUPayAccount handleAccountAlreadyExists(Event event) {
        var a = event.getArgument(0, DTUPayAccount.class);
        registeredAccount.complete(a);
        return a;
    }

    public DTUPayAccount getAccount(String id) {
        returnedAccount = new CompletableFuture<>();
        Event event = new Event("GetDTUPayAccount", new Object[] { id });
        queue.publish(event);
        return returnedAccount.join();
    }

    public DTUPayAccount handleAccountNotFound(Event event) {
        var a = event.getArgument(0, DTUPayAccount.class);
        returnedAccount.complete(a);
        return a;
    }

    public DTUPayAccount handleAccountReturned(Event ev) {
        var a = ev.getArgument(0, DTUPayAccount.class);
        returnedAccount.complete(a);
        return a;
    }
}

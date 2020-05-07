package com.dbteam.service;

import com.dbteam.model.Person;
import com.dbteam.model.Purchase;

import java.util.List;

public interface PurchaseService {

    void addPurchase(Purchase purchase);

    List<Purchase> getGroupPurchases(Long groupChatId);

    List<Purchase> getPurchasesWithBuyer(Long groupChatId, Person person);

    List<Purchase> getPurchasesWithReceiver(Long groupChatId, Person person);
}
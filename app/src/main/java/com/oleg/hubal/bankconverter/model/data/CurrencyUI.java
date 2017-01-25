package com.oleg.hubal.bankconverter.model.data;

/**
 * Created by User on 25.01.2017.
 */

public class CurrencyUI {

    private String currencyName;
    private String currentAsk;
    private String currentBid;
    private boolean isAskIncreased;
    private boolean isBidIncreased;

    public CurrencyUI() {

    }



    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public String getCurrentAsk() {
        return currentAsk;
    }

    public void setCurrentAsk(String currentAsk) {
        this.currentAsk = currentAsk;
    }

    public String getCurrentBid() {
        return currentBid;
    }

    public void setCurrentBid(String currentBid) {
        this.currentBid = currentBid;
    }

    public boolean isAskIncreased() {
        return isAskIncreased;
    }

    public void setAskIncreased(boolean askIncreased) {
        isAskIncreased = askIncreased;
    }

    public boolean isBidIncreased() {
        return isBidIncreased;
    }

    public void setBidIncreased(boolean bidIncreased) {
        isBidIncreased = bidIncreased;
    }
}

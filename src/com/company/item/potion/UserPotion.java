package com.company.item.potion;

public class UserPotion {
    private Potion potion;
    private int potionNum;

    public UserPotion(Potion potion, int potionNum) {
        this.potion = potion;
        this.potionNum = potionNum;
    }

    public Potion getPotion() {
        return potion;
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
    }

    public int getPotionNum() {
        return potionNum;
    }

    public void setPotionNum(int potionNum) {
        this.potionNum = potionNum;
    }
}

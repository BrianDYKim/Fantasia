package com.company.item.potion;

public class Potion {
    private int price; // 포션의 가격
    private PotionNames potionName; // 포션의 이름(enum)
    private String name; // 포션의 이름(String)
    private RecoveryKind recoveryKind; // 회복되는 수치의 종류
    private int recoveryQuantity; // 회복량

    // 생성자
    public Potion(PotionNames potionName) {
        this.potionName = potionName;

        if(this.potionName == PotionNames.지하수) {
            this.name = "지하수"; // HP
            this.price = 10;
            this.recoveryKind = RecoveryKind.HP;
            this.recoveryQuantity = 60;
        }
        else if(this.potionName == PotionNames.포비돈) {
            this.name = "포비돈";
            this.price = 30;
            this.recoveryKind = RecoveryKind.HP;
            this.recoveryQuantity = 130;
        }
        else if(this.potionName == PotionNames.토니켓) {
            this.name = "토니켓";
            this.price = 50;
            this.recoveryKind = RecoveryKind.HP;
            this.recoveryQuantity = 200;
        }
        else if(this.potionName == PotionNames.생명수) {
            this.name = "생명수";
            this.price = 70;
            this.recoveryKind = RecoveryKind.HP;
            this.recoveryQuantity = 300;
        }
        else if(this.potionName == PotionNames.암반수) {
            this.name = "암반수";
            this.price = 10;
            this.recoveryKind = RecoveryKind.MANA;
            this.recoveryQuantity = 80;
        }
        else if(this.potionName == PotionNames.연양갱) {
            this.name = "연양갱";
            this.price = 30;
            this.recoveryKind = RecoveryKind.MANA;
            this.recoveryQuantity = 150;
        }
        else if(this.potionName == PotionNames.우육포) {
            this.name = "우육포";
            this.price = 50;
            this.recoveryKind = RecoveryKind.MANA;
            this.recoveryQuantity = 300;
        }
        else if(this.potionName == PotionNames.활명수) {
            this.name = "활명수";
            this.price = 70;
            this.recoveryKind = RecoveryKind.MANA;
            this.recoveryQuantity = 500;
        }
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public RecoveryKind getRecoveryKind() {
        return recoveryKind;
    }

    public void setRecoveryKind(RecoveryKind recoveryKind) {
        this.recoveryKind = recoveryKind;
    }

    public int getRecoveryQuantity() {
        return recoveryQuantity;
    }

    public void setRecoveryQuantity(int recoveryQuantity) {
        this.recoveryQuantity = recoveryQuantity;
    }
}

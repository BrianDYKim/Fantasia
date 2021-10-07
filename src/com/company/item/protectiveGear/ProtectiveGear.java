package com.company.item.protectiveGear;

public class ProtectiveGear {
    private int price; // 가격
    private ProtectiveGearNames protectiveGearName; // 갑옷의 이름(enum)
    private String name; // 갑옷의 이름(string)
    private ProtectiveGearSpecialEffects specialEffect; // 갑옷의 특수효과
    private int requirementLevel; // 갑옷의 요구레벨

    // 생성자
    public ProtectiveGear(ProtectiveGearNames protectiveGearName) {
        this.protectiveGearName = protectiveGearName;

        if(this.protectiveGearName == ProtectiveGearNames.기본옷) {
            this.name = "기본옷";
            this.price = 0;
            this.requirementLevel = 1;
        }
        else if(this.protectiveGearName == ProtectiveGearNames.혈웅복) {
            this.name = "혈웅복";
            this.price = 400;
            this.requirementLevel = 5;
            this.specialEffect = ProtectiveGearSpecialEffects.질긴갑옷;
        }
        else if(this.protectiveGearName == ProtectiveGearNames.월령복) {
            this.name = "월령복";
            this.price = 800;
            this.requirementLevel = 10;
            this.specialEffect = ProtectiveGearSpecialEffects.강철갑옷;
        }
        else if(this.protectiveGearName == ProtectiveGearNames.금강복) {
            this.name = "금강복";
            this.price = 1200;
            this.requirementLevel = 15;
            this.specialEffect = ProtectiveGearSpecialEffects.금강;
        }
        else if(this.protectiveGearName == ProtectiveGearNames.천석복) {
            this.name = "천석복";
            this.price = 2000;
            this.requirementLevel = 20;
            this.specialEffect = ProtectiveGearSpecialEffects.가시갑옷;
        }
        else if(this.protectiveGearName == ProtectiveGearNames.멸살복) {
            this.name = "멸살복";
            this.price = 3000;
            this.requirementLevel = 25;
            this.specialEffect = ProtectiveGearSpecialEffects.신의가호;
        }
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public ProtectiveGearSpecialEffects getSpecialEffect() {
        return specialEffect;
    }

    public int getRequirementLevel() {
        return requirementLevel;
    }
}

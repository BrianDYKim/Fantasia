package com.company.item.weapon;

import com.company.unit.character.Job;

public class Weapon {
    private int price; // 무기의 가격
    private WeaponNames weaponName; // 무기의 이름(enum)
    private String name; // 무기의 이름(string)
    private int attackPoint; // 무기가 가진 공격력
    private WeaponSpecialEffects specialEffect; // 무기가 가진 특수효과
    private Job job; // 무기에 부여된 사용가능 직업
    private int requirementLevel; // 무기에 부여된 요구레벨

    // 생성자(이름만 부여하면 공격력, 가격, 요구레벨, 직업군, 특수효과들이 알아서 설정이 됨)
    public Weapon(WeaponNames weaponName) {
        this.weaponName = weaponName;

        // 무기에 부여된 속성
        if(weaponName == WeaponNames.기본검) {
            this.name = "기본검";
            this.attackPoint = 5;
            this.price = 0;
            this.requirementLevel = 1;
            this.job = Job.WARRIOR;
        }
        else if(weaponName == WeaponNames.화룡검) {
            this.name = "화룡검";
            this.attackPoint = 13;
            this.price = 400;
            this.requirementLevel = 5;
            this.job = Job.WARRIOR;
        }
        else if(weaponName == WeaponNames.천지검) {
            this.name = "천지검";
            this.attackPoint = 24;
            this.price = 800;
            this.requirementLevel = 10;
            this.job = Job.WARRIOR;
        }
        else if(weaponName == WeaponNames.유성검) {
            this.name = "유성검";
            this.attackPoint = 40;
            this.price = 1200;
            this.specialEffect = WeaponSpecialEffects.약자멸시;
            this.requirementLevel = 15;
            this.job = Job.WARRIOR;
        }
        else if(weaponName == WeaponNames.흑요검) {
            this.name = "흑요검";
            this.attackPoint = 65;
            this.price = 2000;
            this.specialEffect = WeaponSpecialEffects.핏빛칼날;
            this.requirementLevel = 20;
            this.job = Job.WARRIOR;
        }
        else if(weaponName == WeaponNames.멸살검) {
            this.name = "멸살검";
            this.attackPoint = 100;
            this.price = 3000;
            this.specialEffect = WeaponSpecialEffects.천벌;
            this.requirementLevel = 25;
            this.job = Job.WARRIOR;
        }
        else if(weaponName == WeaponNames.기본장) {
            this.name = "기본장";
            this.attackPoint = 5;
            this.price = 0;
            this.requirementLevel = 1;
            this.job = Job.WIZARD;
        }
        else if(weaponName == WeaponNames.칠흑장) {
            this.name = "칠흑장";
            this.attackPoint = 13;
            this.price = 400;
            this.requirementLevel = 5;
            this.job = Job.WIZARD;
        }
        else if(weaponName == WeaponNames.봉황장) {
            this.name = "봉황장";
            this.attackPoint = 24;
            this.price = 800;
            this.requirementLevel = 10;
            this.job = Job.WIZARD;
        }
        else if(weaponName == WeaponNames.격마장) {
            this.name = "격마장";
            this.attackPoint = 40;
            this.price = 1200;
            this.specialEffect = WeaponSpecialEffects.약자멸시;
            this.requirementLevel = 15;
            this.job = Job.WIZARD;
        }
        else if(weaponName == WeaponNames.비룡장) {
            this.name = "비룡장";
            this.attackPoint = 65;
            this.price = 2000;
            this.specialEffect = WeaponSpecialEffects.핏빛칼날;
            this.requirementLevel = 20;
            this.job = Job.WIZARD;
        }
        else if(weaponName == WeaponNames.멸살장) {
            this.name = "멸살장";
            this.attackPoint = 100;
            this.price = 3000;
            this.specialEffect = WeaponSpecialEffects.천벌;
            this.requirementLevel = 25;
            this.job = Job.WIZARD;
        }
    }

    // getter methods
    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public WeaponSpecialEffects getSpecialEffect() {
        return specialEffect;
    }

    public Job getJob() {
        return job;
    }

    public int getRequirementLevel() {
        return requirementLevel;
    }
}

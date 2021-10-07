package com.company.item.weapon;

public class UserWeapon {
    private Weapon weapon; // 무기
    private int num = 0; // 초기에 user가 가진 무기의 개수는 0개

    public UserWeapon(Weapon weapon, int num) {
        this.weapon = weapon;
        this.num = num;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

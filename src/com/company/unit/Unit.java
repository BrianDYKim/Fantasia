package com.company.unit;

public class Unit {
    private int health; // 유닛의 체력
    private int attackPoint; // 유닛의 공격력

    public Unit(int health, int attackPoint) {
        this.health = health;
        this.attackPoint = attackPoint;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackPoint() {
        return attackPoint;
    }

    public void setAttackPoint(int attackPoint) {
        this.attackPoint = attackPoint;
    }
}

package com.company.unit.character;

public enum Job {
    WARRIOR(70, 40, 5),
    WIZARD(50, 60, 7);

    private int increaseHealthPoint;
    private int increaseManaPoint;
    private int increaseAttackPoint;

    Job(int increaseHealthPoint, int increaseManaPoint, int increaseAttackPoint) {
        this.increaseHealthPoint = increaseHealthPoint;
        this.increaseManaPoint = increaseManaPoint;
        this.increaseAttackPoint = increaseAttackPoint;
    }

    public int getIncreaseHealthPoint() {
        return increaseHealthPoint;
    }

    public int getIncreaseManaPoint() {
        return increaseManaPoint;
    }

    public int getIncreaseAttackPoint() {
        return increaseAttackPoint;
    }
}

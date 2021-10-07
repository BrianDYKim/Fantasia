package com.company.unit.character;

/** 마법사 직업군의 스킬들을 나열한 클래스입니다.
 * 스킬 이름    요구 레벨    마나 소모량    스킬 효과(damage coefficient)
 * 파이어볼       5         80          적에게 (1.4 * 공격력) 만큼의 데미지를 입힙니다.
 * 번개         10         130         적에게 (1.7 * 공격력) 만큼의 데미지를 입힙니다.
 * 눈보라       15         250         적에게 (2.2 * 공격력) 만큼의 데미지를 입힙니다.
 * 슈퍼셀       20         350         적에게 (3.0 * 공격력) 만큼의 데미지를 입힙니다.
 * 메테오       25         500         적에게 (3.9 * 공격력) 만큼의 데미지를 입힙니다.
 * 천지창조      30        700         적에게 (5.0 * 공격력) 만큼의 데미지를 입힙니다.
 * */

public enum WizardSkill {
    파이어볼(80, 1.4, "적에게 (1.4 * 공격력) 만큼의 데미지를 입힙니다."),
    번개(130, 1.7, "적에게 (1.7 * 공격력) 만큼의 데미지를 입힙니다."),
    눈보라(250, 2.2, "적에게 (2.2 * 공격력) 만큼의 데미지를 입힙니다."),
    슈퍼셀(350, 3.0, "적에게 (3.0 * 공격력) 만큼의 데미지를 입힙니다."),
    메테오(500, 3.9, "적에게 (3.9 * 공격력) 만큼의 데미지를 입힙니다."),
    천지창조(700, 5.0, "적에게 (5.0 * 공격력) 만큼의 데미지를 입힙니다.");

    private int cost;
    private double coefficient;
    private String description;

    // 생성자
    WizardSkill(int cost, double coefficient, String description) {
        this.cost = cost;
        this.coefficient = coefficient;
        this.description = description;
    }

    // Getter method
    public int getCost() {
        return cost;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public String getDescription() {
        return description;
    }
}

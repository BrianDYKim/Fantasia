package com.company.unit.character;

/** 전사 직업군의 스킬들을 나열한 클래스입니다.
 * 스킬 이름    요구 레벨    마나 소모량    스킬 효과(damage coefficient)
 * 십자베기       5         60          적에게 (1.4 * 공격력) 만큼의 데미지를 입힙니다.
 * 쾌속난도      10         100         적에게 (1.7 * 공격력) 만큼의 데미지를 입힙니다.
 * 칼날폭풍      15         190         적에게 (2.2 * 공격력) 만큼의 데미지를 입힙니다.
 * 심연의칼날    20         260         적에게 (3.0 * 공격력) 만큼의 데미지를 입힙니다.
 * 멸절검무      25         350         적에게 (3.9 * 공격력) 만큼의 데미지를 입힙니다.
 * 운명봉인      30         550         적에게 (5.0 * 공격력) 만큼의 데미지를 입힙니다.
 * */
public enum WarriorSkill {
    십자베기(60, 1.4, "적에게 (1.4 * 공격력) 만큼의 데미지를 입힙니다."),
    쾌속난도(100, 1.7, "적에게 (1.7 * 공격력) 만큼의 데미지를 입힙니다."),
    칼날폭풍(190, 2.2, "적에게 (2.2 * 공격력) 만큼의 데미지를 입힙니다."),
    심연의칼날(260, 3.0, "적에게 (3.0 * 공격력) 만큼의 데미지를 입힙니다."),
    멸절검무(350, 3.9, "적에게 (3.9 * 공격력) 만큼의 데미지를 입힙니다."),
    운명봉인(550, 5.0, "적에게 (5.0 * 공격력) 만큼의 데미지를 입힙니다.");

    private int cost; // 마나 소모량
    private double coefficient; // 스킬의 계수
    private String description; // 스킬에 관한 설명

    // 생성자
    WarriorSkill(int cost, double coefficient, String description) {
        this.cost = cost;
        this.coefficient = coefficient;
        this.description = description;
    }

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

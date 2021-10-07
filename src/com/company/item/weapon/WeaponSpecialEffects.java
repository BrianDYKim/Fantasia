package com.company.item.weapon;

/** 무기의 특수효과를 나열한 클래스입니다.
 * [특수효과 리스트]
 * 약자멸시 : 체력이 40% 이하인 상대에게 상대 최대 체력의 약 10%에 해당하는 추가 피해를 1회 입힙니다.
 * 핏빛칼날 : 자신이 입힌 데미지의 5%만큼 체력을 흡수합니다.
 * 천벌 : 자신이 입힌 데미지에서 15%만큼 추가 데미지를 상대에게 입힙니다.
 * */
public enum WeaponSpecialEffects {
    약자멸시(10), 핏빛칼날(5), 천벌(15);

    private int coefficient;

    WeaponSpecialEffects(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getCoefficient() {
        return coefficient;
    }
}

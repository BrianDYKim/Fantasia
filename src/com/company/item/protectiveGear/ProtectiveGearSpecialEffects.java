package com.company.item.protectiveGear;

/** 갑옷의 특수효과를 나열한 클래스입니다.
 *
 * [특수효과 리스트]
 * 질긴갑옷 : 입은 피해를 10%만큼 감소시킵니다.
 * 강철갑옷 : 입은 피해를 15%민큼 감소시킵니다.
 * 금강 : 입은 피해를 20%만큼 감소시킵니다.
 * 가시갑옷 : 입은 피해를 15%만큼 감소시키고, 감소피해량만큼 적에게 피해를 추가로 입힙니다.
 * 신의가호 : 입은 피해를 25%만큼 감소시키고, 감소피해량의 50%를 적에게 피해를 추가로 입히며, 감소된 피해량만큼 자신의 마나를 회복합니다.
 * */
public enum ProtectiveGearSpecialEffects {
    질긴갑옷(10), 강철갑옷(15), 금강(20), 가시갑옷(15), 신의가호(25);

    private int protectRatio;

    ProtectiveGearSpecialEffects(int protectRatio) {
        this.protectRatio = protectRatio;
    }

    public int getProtectRatio() {
        return protectRatio;
    }
}
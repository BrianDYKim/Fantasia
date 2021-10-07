package com.company.unit.enemy;

import com.company.item.protectiveGear.ProtectiveGearSpecialEffects;
import com.company.unit.Unit;
import com.company.unit.character.Character;
import org.jetbrains.annotations.NotNull;

public class Monster extends Unit {
    private int level; // 몬스터의 레벨
    private int maxHealth; // 몬스터의 최대 체력
    private int dropExperimentPoint; // 드랍되는 경험치
    private int dropGold; // 드랍되는 골드의 양
    private MonsterNames monsterName;

    // 생성자 todo 보스 몬스터 구현
    public Monster(MonsterNames monsterName, int level) {
        super(140 + 70 * level, 30 + 6 * level); // 몬스터의 체력과 공격력을 초기화
        this.maxHealth = 140 + 70 * level; // 몬스터의 최대 체력
        this.monsterName = monsterName; // 몬스터의 이름 초기화
        this.level = level; // 레벨 초기화
        this.dropExperimentPoint = 10 + 9 * this.level; // 드랍 경험치 : 10 + 9 * 몬스터 레벨
        this.dropGold = 12 * this.level; // 드랍 골드량 : 5 * 몬스터 레벨
    }

    // 몬스터의 정보를 보여주는 메소드
    public void showInfo() {
        System.out.println("======================================");
        System.out.println("[" + this.monsterName + "]");
        System.out.println("몬스터의 레벨 : " + this.getLevel());
        System.out.println("현재 체력 : " + this.getHealth() + "/" + this.getMaxHealth());
        System.out.println("======================================");
    }

    // 캐릭터에게 기본 공격을 시행하는 메소드
    public void basicAttack(@NotNull Character character) {
        int damage = this.getAttackPoint(); // 캐릭터에게 가하는 데미지
        ProtectiveGearSpecialEffects characterSpecialEffect = character.getProtectiveGearSpecialEffects(); // 캐릭터가 가지는 방어구 특수효과

        System.out.println(this.monsterName + "가 캐릭터에게 기본 공격을 가했습니다.");

        // 캐릭터의 방어구 특수효과 적용
        if(characterSpecialEffect == ProtectiveGearSpecialEffects.질긴갑옷) {
            damage = adjustDamage(damage, characterSpecialEffect); // 데미지 조정
            System.out.println("[질긴갑옷] : 입은 피해를 " + ProtectiveGearSpecialEffects.질긴갑옷.getProtectRatio() + "%만큼 감소시킵니다.");
            noticeDamage(damage);
        }
        else if(characterSpecialEffect == ProtectiveGearSpecialEffects.강철갑옷) {
            damage = adjustDamage(damage, characterSpecialEffect); // 데미지 조정
            System.out.println("[강철갑옷] : 입은 피해를 " + ProtectiveGearSpecialEffects.강철갑옷.getProtectRatio() + "%민큼 감소시킵니다.");
            noticeDamage(damage);
        }
        else if(characterSpecialEffect == ProtectiveGearSpecialEffects.금강) {
            damage = adjustDamage(damage, characterSpecialEffect);
            System.out.println("[금강] : 입은 피해를 " + ProtectiveGearSpecialEffects.금강.getProtectRatio() + "%만큼 감소시킵니다.");
            noticeDamage(damage);
        }
        else if(characterSpecialEffect == ProtectiveGearSpecialEffects.가시갑옷) {
            damage = adjustDamage(damage, characterSpecialEffect);
            this.setHealth(this.getHealth() - damage * characterSpecialEffect.getProtectRatio() / 100); // 가시갑옷의 데미지 반사 효과 적용
            System.out.println("[가시갑옷] : 입은 피해를 " + ProtectiveGearSpecialEffects.가시갑옷.getProtectRatio() + "%만큼 감소시키고, 감소피해량만큼 적에게 피해를 추가로 입힙니다.");
            System.out.println("[가시 갑옷] : 흡수한 데미지 양만큼 몬스터에게 반사합니다.");
            noticeDamage(damage);
        }
        else if(characterSpecialEffect == ProtectiveGearSpecialEffects.신의가호) {
            damage = adjustDamage(damage, characterSpecialEffect);
            this.setHealth(this.getHealth() - damage * characterSpecialEffect.getProtectRatio() / 200); // 신의 가호의 데미지 반사 효과 적용
            character.setManaPoint(character.getManaPoint() + damage * characterSpecialEffect.getProtectRatio() / 100); // 신의 가호의 마나 회복 효과

            // 마나가 최대 마나를 넘치는 경우의 예외 처리 --> 최대 마나를 넘지 못하게 막는다.
            if(character.getManaPoint() > character.getMaxManaPoint())
                character.setManaPoint(character.getMaxManaPoint());

            System.out.println("신의가호 : 입은 피해를 " + ProtectiveGearSpecialEffects.신의가호.getProtectRatio() + "%만큼 감소시키고, 감소피해량의 50%를 적에게 피해를 추가로 입히며, 감소된 피해량만큼 자신의 마나를 회복합니다.");
            System.out.println("[신의 가호] : 흡수한 데미지의 절반만큼 몬스터에게 반사합니다.");
            System.out.println("[신의 가호] : 흡수한 데미지만큼 자신의 마나를 회복합니다.");
            noticeDamage(damage);
        }

        // 캐릭터의 체력이 남는 경우
        if(character.getHealth() > damage) {
            character.setHealth(character.getHealth() - damage); // 체력 깎기
        }
        // 캐릭터가 사망하는 경우
        else {
            character.setHealth(0);
        }
    }

    // 데미지 값을 방어구 효과에 따라 조정해주는 함수
    public int adjustDamage(int damage, ProtectiveGearSpecialEffects specialEffect) {
        damage = damage * (100 - specialEffect.getProtectRatio()) / 100;

        return damage;
    }

    // 데미지를 통보하는 메소드
    public void noticeDamage(int damage) {
        System.out.println("몬스터가 캐릭터에게 " + damage + "만큼의 데미지를 가했습니다.");
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getDropExperimentPoint() {
        return dropExperimentPoint;
    }

    public void setDropExperimentPoint(int dropExperimentPoint) {
        this.dropExperimentPoint = dropExperimentPoint;
    }

    public int getDropGold() {
        return dropGold;
    }

    public void setDropGold(int dropGold) {
        this.dropGold = dropGold;
    }

    public MonsterNames getMonsterName() {
        return monsterName;
    }

    public void setMonsterName(MonsterNames monsterName) {
        this.monsterName = monsterName;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
}

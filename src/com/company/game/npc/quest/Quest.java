package com.company.game.npc.quest;

import com.company.item.potion.Potion;
import com.company.item.protectiveGear.ProtectiveGear;
import com.company.item.weapon.Weapon;
import com.company.unit.enemy.Monster;

import java.util.Optional;

public class Quest {
    private QuestNames questName; // 퀘스트의 이름(enum)
    private int requirementLevel; // 퀘스트의 요구 레벨
    private Monster monster; // 퀘스트에서 요구하는 몬스터
    private int object; // 퀘스트의 요구 마리수
    private String questDescription; // 퀘스트에 대한 설명을 저장한 String 변수

    private int exp;
    private int gold;

    // 물건에 관한 보상 객체들 -> 존재할수도, 없을수도 있기 때문에 Optional 제네릭을 이용하여 선언
    private Optional<Weapon> weapon;
    private Optional<ProtectiveGear> protectiveGear;
    private Optional<Potion> potion;

    private boolean isQuestCompleted = false; // 퀘스트가 완료가 되었는지 검사하는 멤버 변수
    private int killedNumber = 0; // 해당 몬스터를 몇 마리 죽였는지 누적하는 스택 변수

    // 생성자 -> enum에 요구 레벨, 요구 마리수를 명시하여 가져옴.
    public Quest(QuestNames questName, Monster monster, int exp, int gold) {
        this.questName = questName;
        this.monster = monster;
        this.exp = exp;
        this.gold = gold;

        this.requirementLevel = questName.getRequirementLevel();
        this.object = questName.getObject();

        makeQuestDescription();
    }

    // 생성자 -> enum에 요구 레벨, 요구 마리수를 명시하여 가져옴.
    public Quest(QuestNames questName, Monster monster, int exp, int gold, Weapon weapon) {
        this.questName = questName;
        this.monster = monster;
        this.exp = exp;
        this.gold = gold;

        this.requirementLevel = questName.getRequirementLevel();
        this.object = questName.getObject();

        this.weapon = Optional.ofNullable(weapon);

        makeQuestDescription();
    }

    // 생성자 -> enum에 요구 레벨, 요구 마리수를 명시하여 가져옴.
    public Quest(QuestNames questName, Monster monster, int exp, int gold, ProtectiveGear protectiveGear) {
        this.questName = questName;
        this.monster = monster;
        this.exp = exp;
        this.gold = gold;

        this.requirementLevel = questName.getRequirementLevel();
        this.object = questName.getObject();

        this.protectiveGear = Optional.ofNullable(protectiveGear);

        makeQuestDescription();
    }

    // 생성자 -> enum에 요구 레벨, 요구 마리수를 명시하여 가져옴.
    public Quest(QuestNames questName, Monster monster, int exp, int gold, Potion potion) {
        this.questName = questName;
        this.monster = monster;
        this.exp = exp;
        this.gold = gold;

        this.requirementLevel = questName.getRequirementLevel();
        this.object = questName.getObject();

        this.potion = Optional.ofNullable(potion);

        makeQuestDescription();
    }

    public void makeQuestDescription() {
        this.questDescription = monster.getMonsterName() + "를 " + questName.getObject() + "마리 처치하십시오.";
    }

    public QuestNames getQuestName() {
        return questName;
    }

    public int getExp() {
        return exp;
    }

    public int getGold() {
        return gold;
    }

    public Optional<Weapon> getWeapon() {
        return weapon;
    }

    public Optional<ProtectiveGear> getProtectiveGear() {
        return protectiveGear;
    }

    public Optional<Potion> getPotion() {
        return potion;
    }

    public String getQuestDescription() {
        return questDescription;
    }

    public Monster getMonster() {
        return monster;
    }

    public void setQuestCompleted(boolean questCompleted) {
        isQuestCompleted = questCompleted;
    }

    public int getKilledNumber() {
        return killedNumber;
    }

    public void setKilledNumber(int killedNumber) {
        this.killedNumber = killedNumber;
    }

    public boolean isQuestCompleted() {
        return isQuestCompleted;
    }
}

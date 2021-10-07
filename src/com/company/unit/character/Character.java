package com.company.unit.character;

import com.company.Main;
import com.company.game.npc.quest.Quest;
import com.company.item.potion.Potion;
import com.company.item.potion.RecoveryKind;
import com.company.item.potion.UserPotion;
import com.company.item.protectiveGear.ProtectiveGear;
import com.company.item.protectiveGear.ProtectiveGearNames;
import com.company.item.protectiveGear.ProtectiveGearSpecialEffects;
import com.company.item.protectiveGear.UserProtectiveGear;
import com.company.item.weapon.UserWeapon;
import com.company.item.weapon.Weapon;
import com.company.item.weapon.WeaponNames;
import com.company.item.weapon.WeaponSpecialEffects;
import com.company.unit.Unit;
import com.company.unit.enemy.Monster;

import java.util.ArrayList;

public class Character extends Unit {

    // 공격력과 체력은 Unit에서 상속받아서 사용함
    private Job job; // 직업
    private int manaPoint; // 마나
    private int level; // 레벨
    private int experiencePoint; // 경험치
    private String name;
    private int gold; // 골드 보유량

    // 체력, 마나, 경험치의 최대치
    private int maxHealth; // 최대 체력
    private int maxManaPoint; // 최대 마나

    // 착용중인 장비들
    private Weapon wearingWeapon; // 착용중인 무기
    private ProtectiveGear wearingProtectiveGear; // 착용중인 갑옷

    // 추가 공격력
    private int summativeAttackPoint;

    // 적용받는 특수효과
    private WeaponSpecialEffects weaponSpecialEffects; // 무기 특수효과
    private ProtectiveGearSpecialEffects protectiveGearSpecialEffects; // 방어구 특수효과

    // 약자멸시 카운트
    private boolean isDespised = false;

    // 가지고 있는 스킬의 목록
    private ArrayList<WarriorSkill> warriorSkillList = new ArrayList<>(); // 전사 스킬의 목록
    private ArrayList<WizardSkill> wizardSkillList = new ArrayList<>(); // 마법사 스킬의 목록

    // 인벤토리
    private ArrayList<UserWeapon> weaponInventory = new ArrayList<>();
    private ArrayList<UserProtectiveGear> protectiveGearInventory = new ArrayList<>();
    private ArrayList<UserPotion> potionInventory = new ArrayList<>();

    // 퀘스트 리스트
    private ArrayList<Quest> unCompletedQuestList = new ArrayList<>(); // 진행중인 퀘스트 리스트
    private ArrayList<Quest> completedQuestList = new ArrayList<>(); // 완료된 퀘스트 리스트

    // 직업은 입력받아서 적용함
    public Character(String job) {
        super(500, 80); // 초반 체력과 공격력은 500, 80으로 설정함
        this.manaPoint = 300; // 초반 최대 마나량은 300 -> debug
        this.level = 1; // 초반 레벨은 1로 시작
        this.experiencePoint = 0; // 초반 경험치는 0
        this.gold = 1000; // 초반 골드는 1000으로 시작

        // 직업 설정
        if(job.equals("0"))
            this.job = Job.WARRIOR;
        else if(job.equals("1"))
            this.job = Job.WIZARD;

        // 최대 체력, 마나, 경험치 조정
        if(this.job == Job.WARRIOR) {
            this.maxHealth = 500 + 70 * (this.level - 1); // 최대 체력
            this.maxManaPoint = 300 + 40 * (level - 1); // 최대 마나량
        }
        else if(this.job == Job.WIZARD) {
            this.maxHealth = 500 + 50 * (this.level - 1); // 최대 체력
            this.maxManaPoint = 300 + 60 * (level - 1); // 최대 마나량
        }

        // 기본 장착 무기, 기본 장착 갑옷 설정
        if(this.job == Job.WARRIOR)
            wearingWeapon = new Weapon(WeaponNames.기본검); // 전사의 기본 무기는 기본검임.
        else if(this.job == Job.WIZARD)
            wearingWeapon = new Weapon(WeaponNames.기본장); // 법사의 기본 무기는 기본장임.

        wearingProtectiveGear = new ProtectiveGear(ProtectiveGearNames.기본옷); // 기본 갑옷은 기본옷임.

        summativeAttackPoint = wearingWeapon.getAttackPoint();

        setAttackPoint(getAttackPoint() + summativeAttackPoint);

        /** 디버깅 코드*/

    }

    // 공격을 수행하는 함수
    /**
     * [공격을 수행하는 함수]
     * @param monster : 현재 캐릭터가 마주하고있는 몬스터의 객체를 매개변수로 전달합니다.
     * @param skillName : 캐릭터가 사용하고자하는 스킬의 이름을 매개변수로 전달합니다. 실행 과정에서 캐릭터의 스킬 목록에 있는지 검사까지 진행함. 전달만 하면됨.
     */
    public void attack(Monster monster, String skillName) {
        int damage = this.getAttackPoint(); // 캐릭터의 공격력 불러오기

        WarriorSkill warriorSkill; // 전사의 스킬
        WizardSkill wizardSkill; // 마법사의 스킬

        WeaponSpecialEffects specialEffect = this.getWeaponSpecialEffects(); // 무기의 특수효과

        // 기본 공격
        if(skillName.equals("기본공격")) {
            System.out.println("캐릭터가 " + monster.getMonsterName() + "에게 기본공격을 가했습니다.");

            // 몬스터가 생존하는 경우
            if(monster.getHealth() > damage)
                monster.setHealth(monster.getHealth() - damage); // 몬스터의 체력 깎기
            else
                monster.setHealth(0); // 몬스터가 죽는 경우 몬스터의 체력을 0으로 설정함
        }
        // 스킬 공격
        else {
            // 직업별로 스킬 공격을 세분화 진행
            if(this.getJob() == Job.WARRIOR) {
                warriorSkill = getWarriorSkillByName(skillName);

                if(warriorSkill == null) {
                    System.out.println("존재하지 않는 스킬입니다. 기본 공격을 가합니다.");
                    System.out.println("캐릭터가 " + monster.getMonsterName() + "에게 기본공격을 가했습니다.");

                    // 몬스터가 생존하는 경우
                    if(monster.getHealth() > damage)
                        monster.setHealth(monster.getHealth() - damage); // 몬스터의 체력 깎기
                    else
                        monster.setHealth(0); // 몬스터가 죽는 경우 몬스터의 체력을 0으로 설정함
                }
                // 스킬 공격 시작
                else {

                    if(this.getManaPoint() >= warriorSkill.getCost()) {
                        System.out.println("캐릭터가 " + monster.getMonsterName() + "에게 " + warriorSkill + "를 시전하였습니다.");

                        // 데미지 조정
                        damage = (int)Math.round(damage * warriorSkill.getCoefficient()); // 데미지는 반올림 계산 처리
                        // 마나 깎기
                        this.setManaPoint(this.getManaPoint() - warriorSkill.getCost());
                    }
                    else {
                        System.out.println("캐릭터의 마나가 부족합니다. 기본 공격을 시행합니다.");
                        System.out.println("캐릭터가 " + monster.getMonsterName() + "에게 기본공격을 가했습니다.");
                    }

                    // 몬스터가 생존하는 경우
                    if(monster.getHealth() > damage)
                        monster.setHealth(monster.getHealth() - damage); // 몬스터의 체력 깎기
                    else
                        monster.setHealth(0); // 몬스터가 죽는 경우 몬스터의 체력을 0으로 설정함
                }
            }
            else if(this.getJob() == Job.WIZARD) {
                wizardSkill = getWizardSkillByName(skillName);

                // 존재하지 않는 스킬을 받아오면 기본 공격을 시행함.
                if(wizardSkill == null) {
                    System.out.println("존재하지 않는 스킬입니다. 기본 공격을 가합니다.");
                    System.out.println("캐릭터가 " + monster.getMonsterName() + "에게 기본공격을 가했습니다.");

                    // 몬스터가 생존하는 경우
                    if(monster.getHealth() > damage)
                        monster.setHealth(monster.getHealth() - damage); // 몬스터의 체력 깎기
                    else
                        monster.setHealth(0); // 몬스터가 죽는 경우 몬스터의 체력을 0으로 설정함
                }
                // 존재하는 스킬인 경우 스킬 공격을 시행함
                else {

                    if(this.getManaPoint() >= wizardSkill.getCost()) {
                        System.out.println("캐릭터가 " + monster.getMonsterName() + "에게 " + wizardSkill + "를 시전하였습니다.");

                        // 데미지 조정
                        damage = (int)Math.round(damage * wizardSkill.getCoefficient()); // 데미지는 반올림 계산 처리
                        // 마나 깎기
                        this.setManaPoint(this.getManaPoint() - wizardSkill.getCost());
                    }
                    else {
                        System.out.println("캐릭터의 마나가 부족합니다. 기본 공격을 시행합니다.");
                        System.out.println("캐릭터가 " + monster.getMonsterName() + "에게 기본공격을 가했습니다.");
                    }

                    // 몬스터가 생존하는 경우
                    if(monster.getHealth() > damage)
                        monster.setHealth(monster.getHealth() - damage); // 몬스터의 체력 깎기
                    else
                        monster.setHealth(0); // 몬스터가 죽는 경우 몬스터의 체력을 0으로 설정함
                }
            }
        }

        // 무기 특수효과 적용 -> null 검사를 하여 null이 아닌 경우에는 무기 특수효과 적용
        if(specialEffect != null) {
            if(specialEffect == WeaponSpecialEffects.약자멸시) {
                // 상대 최대 체력이 40% 이하로 떨어지면 효과 적용
                if(monster.getHealth() <= monster.getMaxHealth() * 4 / 10) {
                    if(isDespised == false) {
                        System.out.println("[약자멸시] : 상대 최대 체력의 10%에 해당하는 추가 피해를 입힙니다.");
                        // 몬스터에게 데미지를 가함
                        monster.setHealth(monster.getHealth() - monster.getMaxHealth() * specialEffect.getCoefficient() / 100);
                        isDespised = true; // 다음에 약자멸시가 적용되지 않게 방지
                    }
                }
            }
            else if(specialEffect == WeaponSpecialEffects.핏빛칼날) {
                System.out.println("[핏빛칼날] : 자신이 입힌 데미지의 5%만큼 체력을 화복합니다.");

                this.setHealth(this.getHealth() + damage * specialEffect.getCoefficient() / 100);

                // 체력 흡수 결과 최대 체력을 넘게 되는경우.
                if(this.getHealth() > this.getMaxHealth())
                    this.setHealth(this.getMaxHealth());
            }
            else if(specialEffect == WeaponSpecialEffects.천벌) {
                System.out.println("[천벌] : 추가 피해 15%를 입힙니다.");
                monster.setHealth(monster.getHealth() - damage * specialEffect.getCoefficient() / 100);
            }
        }

        if(monster.getHealth() <= 0) {
            monster.setHealth(0);
        }

        monster.showInfo(); // 몬스터의 정보를 출력
    }

    // 스킬 이름을 기반으로 전사의 스킬을 캐릭터의 스킬 목록에서 탐색하는 메소드
    // 캐릭터가 가지고있지 않은 스킬이면 null을 반환함.
    public WarriorSkill getWarriorSkillByName(String skillName) {
        WarriorSkill targetSkill = null;

        // 실존하는 스킬 이름인지 검사하는 코드
        for(WarriorSkill skill : WarriorSkill.values()) {
            if(skillName.equals(skill.toString()))
                targetSkill = skill;
        }

        // 현재 캐릭터가 이 스킬을 가지고있는지 검사하는 메소드
        for(WarriorSkill skill : warriorSkillList) {
            if(targetSkill == skill) {
                targetSkill = skill;
                break; // 실제로 캐릭터가 가진 스킬이면 반복문 탈출
            }
            else
                targetSkill = null;
        }

        return targetSkill;
    }

    // 스킬 이름을 기반으로 마법사의 스킬을 탐색하는 메소드
    public WizardSkill getWizardSkillByName(String skillName) {
        WizardSkill targetSkill = null;

        // 실존하는 스킬 이름인지 검사하는 코드
        for(WizardSkill skill : WizardSkill.values()) {
            if(skillName.equals(skill.toString()))
                targetSkill = skill;
        }

        // 현재 이 캐릭터가 이 스킬을 가지고있는지 검사하는 코드
        for(WizardSkill skill : wizardSkillList) {
            if(targetSkill == skill) {
                targetSkill = skill;
                break; // 실제로 캐릭터가 가진 스킬이면 반복문 탈출
            }
            else
                targetSkill = null;
        }

        return targetSkill;
    }

    // 몬스터로부터 경험치를 얻어오는 메소드 -> 경험치가 최대치를 넘어서면 레벨업까지 한번에 처리함.
    public void receiveExpFromMonster(int exp) {
        // 경험치를 받아온다. 경험치를 받아오고 경험치 표를 초과하는지 검사 -> 초과하면 경험치를 경험치표에서 값만큼 깎는다.
        this.setExperiencePoint(this.getExperiencePoint() + exp); // 경험치 수령
    }

    // 캐릭터의 레벨업을 처리하는 메소드
    public void levelUp() {
        if(this.level == 30)
            return; // 만렙인 경우에는 레벨업을 하지 않습니다.

        // 경험치 깎기 처리
        this.setExperiencePoint(this.getExperiencePoint() - Main.gameModule.getExpList()[this.level]);

        this.setLevel(this.getLevel() + 1);

        // 최대 체력, 최대 마나 조정
        this.setMaxHealth(this.getMaxHealth() + this.job.getIncreaseHealthPoint());
        this.setMaxManaPoint(this.getMaxManaPoint() + this.job.getIncreaseManaPoint());

        // 레벨업을 하면 최대 체력, 최대 마나까지 회복함
        this.setHealth(this.getMaxHealth());
        this.setManaPoint(this.getMaxManaPoint());

        // 공격력 조정
        this.setAttackPoint(this.getAttackPoint() + this.job.getIncreaseAttackPoint());

        System.out.println("캐릭터의 레벨이 올랐습니다.");

        // 스킬 획득 처리
        if(this.level % 5 == 0) {
            if(this.job == Job.WARRIOR) {
                switch (this.level / 5) {
                    case 1 :
                        warriorSkillList.add(WarriorSkill.십자베기);
                        System.out.println("[스킬 획득] 십자베기 스킬을 획득하셨습니다.");
                        break;

                    case 2 :
                        warriorSkillList.add(WarriorSkill.쾌속난도);
                        System.out.println("[스킬 획득] 쾌속난도 스킬을 획득하셨습니다.");
                        break;

                    case 3 :
                        warriorSkillList.add(WarriorSkill.칼날폭풍);
                        System.out.println("[스킬 획득] 칼날폭풍 스킬을 획득하셨습니다.");
                        break;

                   case 4 :
                        warriorSkillList.add(WarriorSkill.심연의칼날);
                        System.out.println("[스킬 획득] 심연의칼날 스킬을 획득하셨습니다.");
                        break;

                    case 5 :
                        warriorSkillList.add(WarriorSkill.멸절검무);
                        System.out.println("[스킬 획득] 멸절검무 스킬을 획득하셨습니다.");
                        break;

                    case 6 :
                        warriorSkillList.add(WarriorSkill.운명봉인);
                        System.out.println("[스킬 획득] 운명봉인 스킬을 획득하셨습니다.");
                        break;
                }
            }
            else if(this.job == Job.WIZARD) {
                switch (this.level / 5) {
                    case 1 :
                        wizardSkillList.add(WizardSkill.파이어볼);
                        System.out.println("[스킬 획득] 파이어볼 스킬을 획득하셨습니다.");
                        break;

                    case 2 :
                        wizardSkillList.add(WizardSkill.번개);
                        System.out.println("[스킬 획득] 번개 스킬을 획득하셨습니다.");
                        break;

                    case 3 :
                        wizardSkillList.add(WizardSkill.눈보라);
                        System.out.println("[스킬 획득] 눈보라 스킬을 획득하셨습니다.");
                        break;

                    case 4 :
                        wizardSkillList.add(WizardSkill.슈퍼셀);
                        System.out.println("[스킬 획득] 슈퍼셀 스킬을 획득하셨습니다.");
                        break;

                    case 5 :
                        wizardSkillList.add(WizardSkill.메테오);
                        System.out.println("[스킬 획득] 메테오 스킬을 획득하셨습니다.");
                        break;

                    case 6 :
                        wizardSkillList.add(WizardSkill.천지창조);
                        System.out.println("[스킬 획득] 천지창조 스킬을 획득하셨습니다.");
                        break;
                }
            }
        }

        this.showInfo();
    }

    public void showInfo() {
        System.out.println("======================================");
        System.out.println("캐릭터 이름 : " + this.name);

        if(job == Job.WARRIOR)
            System.out.println("현재 직업 : 전사");
        else
            System.out.println("현재 직업 : 마법사");

        System.out.println("현재 체력 : " + this.getHealth() + "/" + getMaxHealth());
        System.out.println("현재 마나포인트 : " + this.getManaPoint() + "/" + getMaxManaPoint());
        System.out.println("현재 공격력 : " + getAttackPoint());
        System.out.println("현재 레벨 : " + this.getLevel());
        System.out.println("현재 경험치 : " + this.getExperiencePoint());
        System.out.println("현재 보유 골드 : " + this.gold + " gold");

        if(weaponSpecialEffects != null)
            System.out.println("무기 특수효과 : " + weaponSpecialEffects);

        if(protectiveGearSpecialEffects != null)
            System.out.println("갑옷 특수효과 : " + protectiveGearSpecialEffects);
        System.out.println("======================================");
    }

    // 캐릭터의 골드를 차감하는 메소드
    public void subtractGold(int gold) {
        this.gold -= gold;
    }

    // 인벤토리에 추가하려는 무기가 이미 존재하는지 검사하는 메소드
    public boolean isAlreadyExistWeapon(Weapon weapon) {
        boolean isExist = false;
        for(UserWeapon userWeapon : weaponInventory) {
            if(userWeapon.getWeapon().getName().equals(weapon.getName()))
                isExist = true;
        }

        return isExist;
    }

    // 무기를 인벤토리에 추가하는 메소드
    public void addWeaponInInventory(UserWeapon userWeapon) {
        // 이미 존재하는 무기를 중복 구매하는 경우에는 그 무기를 인벤토리에서 탐색하여 개수만 추가함.
        if(isAlreadyExistWeapon(userWeapon.getWeapon())) {
            for(UserWeapon weapon : weaponInventory) {
                if(weapon.getWeapon().getName().equals(userWeapon.getWeapon().getName()))
                    weapon.setNum(weapon.getNum() + userWeapon.getNum());
            }
        }
        else
            this.weaponInventory.add(userWeapon); // 무기가 이미 존재하지 않았던 경우에는 인벤토리에 그저 추가하면됨.
    }

    // 인벤토리에 있는 무기를 착용하는 메소드
    public void putOnWeapon(String weaponName) {
        int index = 0;

        // 인벤토리를 검색하여 해당하는 무기를 착용한 후, 개수가 0이 되면 배열에서 제거하기
        // 첫번째 케이스 : 아무 무기도 장착하지 않은 상태
        if(wearingWeapon == null) {
            for(UserWeapon userWeapon : weaponInventory) {
                if(userWeapon.getWeapon().getName().equals(weaponName)) {
                    // 착용 레벨이 되지 않는 경우에는 메소드를 탈출해야함.
                    if(userWeapon.getWeapon().getRequirementLevel() > this.level) {
                        System.out.println("착용 불가능(착용 요구 레벨 : " + userWeapon.getWeapon().getRequirementLevel() + ")");
                        return;
                    }

                    this.wearingWeapon = userWeapon.getWeapon(); // 무기 장착
                    // 공격력 조정
                    setAttackPoint(getAttackPoint() - summativeAttackPoint); // 원래 공격력으로 복원
                    summativeAttackPoint = wearingWeapon.getAttackPoint();
                    setAttackPoint(getAttackPoint() + summativeAttackPoint);

                    // 특수효과 적용
                    this.weaponSpecialEffects = wearingWeapon.getSpecialEffect();

                    userWeapon.setNum(userWeapon.getNum() - 1);
                    break; // 착용이 끝났으면 반복문 탈출
                }
                else {
                    index++;
                }
            }
        }

        // 이미 장착중인 무기가 존재하는 경우
        else {
            Weapon tempWeapon = wearingWeapon; // 장착중인 무기를 임시 저장

            // 장착 시도
            for(UserWeapon userWeapon : weaponInventory) {
                if(userWeapon.getWeapon().getName().equals(weaponName)) {
                    // 착용 레벨이 되지 않는 경우에는 메소드를 탈출해야함.
                    if(userWeapon.getWeapon().getRequirementLevel() > this.level) {
                        System.out.println("착용 불가능(착용 요구 레벨 : " + userWeapon.getWeapon().getRequirementLevel() + ")");
                        return;
                    }

                    this.wearingWeapon = userWeapon.getWeapon(); // 무기 장착
                    // 공격력 조정
                    setAttackPoint(getAttackPoint() - summativeAttackPoint); // 원래 공격력으로 복원
                    summativeAttackPoint = wearingWeapon.getAttackPoint();
                    setAttackPoint(getAttackPoint() + summativeAttackPoint);

                    // 특수효과 적용
                    this.weaponSpecialEffects = wearingWeapon.getSpecialEffect();

                    userWeapon.setNum(userWeapon.getNum() - 1);

                    // 장착 해제한 무기를 인벤토리에 추가
                    addWeaponInInventory(new UserWeapon(tempWeapon, 1));

                    break; // 착용이 끝났으면 반복문 탈출
                }
                else {
                    index++;
                }
            }
        }

        // 잘못 입력한 경우의 예외 처리
        if(index == weaponInventory.size()) {
            System.out.println("존재하지 않는 아이템입니다.");
            return;
        }

        if(weaponInventory.get(index).getNum() == 0)
            weaponInventory.remove(index);
    }

    // 인벤토리에 추가하려는 갑옷이 이미 존재하는지 검사하는 메소드
    public boolean isAlreadyExistProtectiveGear(ProtectiveGear protectiveGear) {
        boolean isExist = false;

        for(UserProtectiveGear userProtectiveGear : protectiveGearInventory) {
            if(userProtectiveGear.getProtectiveGear().getName().equals(protectiveGear.getName()))
                isExist = true;
        }

        return isExist;
    }

    // 갑옷을 인벤토리에 추가하는 메소드
    public void addProtectiveGearInInventory(UserProtectiveGear userProtectiveGear) {
        // 이미 존재하는 갑옷을 구매하는 경우에는 개수만 추가함
        if(isAlreadyExistProtectiveGear(userProtectiveGear.getProtectiveGear())) {
            System.out.println("이미 존재하는 갑옷을 구매하셨습니다.");

            for(UserProtectiveGear protectiveGear : protectiveGearInventory) {
                if(protectiveGear.getProtectiveGear().getName().equals(userProtectiveGear.getProtectiveGear().getName()))
                    protectiveGear.setNum(protectiveGear.getNum() + userProtectiveGear.getNum());
            }
        }
        else {
            this.protectiveGearInventory.add(userProtectiveGear);
        }
    }

    // 인벤토리에 있는 갑옷을 착용하는 메소드
    public void putOnProtectiveGear(String protectiveGearName) {
        int index = 0;

        // 인벤토리를 검색하여 해당하는 갑옷을 착용한 후, 개수가 0이 되면 배열에서 제거하기
        // 첫번째 케이스 : 아무 갑옷도 장착하지 않은 상태
        if(wearingProtectiveGear == null) {
            for(UserProtectiveGear userProtectiveGear : protectiveGearInventory) {
                if(userProtectiveGear.getProtectiveGear().getName().equals(protectiveGearName)) {
                    // 착용 레벨이 되지 않는 경우에는 메소드 탈출
                    if(userProtectiveGear.getProtectiveGear().getRequirementLevel() > this.level) {
                        System.out.println("착용 불가능(착용 요구 레벨 : " +
                                userProtectiveGear.getProtectiveGear().getRequirementLevel() + ")");
                        return;
                    }

                    this.wearingProtectiveGear = userProtectiveGear.getProtectiveGear();

                    // 특수효과 적용
                    protectiveGearSpecialEffects = wearingProtectiveGear.getSpecialEffect();

                    userProtectiveGear.setNum(userProtectiveGear.getNum() - 1);
                    break;
                }
                else
                    index++;
            }
        }

        // 이미 장착중인 갑옷이 존재하는 경우
        else {
            ProtectiveGear tempProtectiveGear = wearingProtectiveGear;

            // 장착 시도
            for(UserProtectiveGear userProtectiveGear : protectiveGearInventory) {
                if(userProtectiveGear.getProtectiveGear().getName().equals(protectiveGearName)) {
                    // 착용 레벨이 되지 않는 경우에는 메소드 탈출
                    if(userProtectiveGear.getProtectiveGear().getRequirementLevel() > this.level) {
                        System.out.println("착용 불가능(착용 요구 레벨 : " +
                                userProtectiveGear.getProtectiveGear().getRequirementLevel() + ")");
                        return;
                    }

                    this.wearingProtectiveGear = userProtectiveGear.getProtectiveGear(); // 갑옷 장착

                    // 특수효과 적용
                    this.protectiveGearSpecialEffects = wearingProtectiveGear.getSpecialEffect();

                    userProtectiveGear.setNum(userProtectiveGear.getNum() - 1);

                    addProtectiveGearInInventory(new UserProtectiveGear(tempProtectiveGear, 1));

                    break; // 착용 완료
                }
                else
                    index++;
            }
        }

        // 잘못 입력한 경우의 예외 처리
        if(index == protectiveGearInventory.size()) {
            System.out.println("존재하지 않는 아이템입니다.");
            return;
        }

        if(protectiveGearInventory.get(index).getNum() == 0)
            protectiveGearInventory.remove(index);
    }

    // 인벤토리에 추가하려는 포션이 이미 존재하는지 검사하는 메소드
    public boolean isAlreadyExistPotion(Potion potion) {
        boolean isExist = false;

        for(UserPotion userPotion : potionInventory) {
            if(userPotion.getPotion().getName().equals(potion.getName()))
                isExist = true;
        }

        return isExist;
    }

    // 포션을 인벤토리에 추가하는 메소드
    public void addPotionInInventory(UserPotion userPotion) {
        // 이미 존재하는 포션을 구매하는 경우에는 개수만 추가함
        if(isAlreadyExistPotion(userPotion.getPotion())) {
            System.out.println("이미 존재하는 포션을 구매하셨습니다.");

            for(UserPotion potion : potionInventory) {
                if(potion.getPotion().getName().equals(userPotion.getPotion().getName()))
                    potion.setPotionNum(potion.getPotionNum() + userPotion.getPotionNum());
            }
        }
        else {
            this.potionInventory.add(userPotion);
        }
    }

    // 포션을 복용하는 메소드
    public void drinkPotion(String potionName, int num) {
        int index = 0;
        int drinkPotionNum = 0; // 마신 포션의 개수

        for(UserPotion userPotion : potionInventory) {
            if(userPotion.getPotion().getName().equals(potionName)) {
                // 보유한 개수보다 많은 포션을 요구하면 메소드 탈출
                if(userPotion.getPotionNum() < num) {
                    System.out.println("포션 복용 요류(보유한 포션의 수보다 많은 포션을 요구).");
                    return;
                }

                // 체력을 회복하는가? 마나를 회복하는가?
                if(userPotion.getPotion().getRecoveryKind() == RecoveryKind.HP) {
                    // 최대체력인데 포션을 섭취할 필요는 없음. -> 최대체력에서 포션 복용을 시도시 메소드를 바로 탈출해야함
                    if(this.getHealth() == this.getMaxHealth()) {
                        System.out.println("포션 복용 불가능.");
                        return;
                    }
                    // 최대 체력이 아닌 경우 -> 최대 체력에 도달할 때 까지만 포션을 복용해야함.
                    else if(this.getHealth() < this.getMaxHealth()) {
                        for(drinkPotionNum = 1; drinkPotionNum <= num; drinkPotionNum++) {
                            // 마시고 바로 풀피가 되는 경우
                            if(this.getHealth() + userPotion.getPotion().getRecoveryQuantity() >= this.getMaxHealth()) {
                                this.setHealth(this.getMaxHealth()); // 최대 체력까지만 채움
                                break; // 반복문 탈출
                            }

                            // 마셔도 풀피까지 안 채워지는 경우
                            else
                                this.setHealth(this.getHealth() + userPotion.getPotion().getRecoveryQuantity());
                        }
                    }

                    if(this.getHealth() == this.getMaxHealth())
                        userPotion.setPotionNum(userPotion.getPotionNum() - drinkPotionNum); // 포션 개수 조정
                    else
                        userPotion.setPotionNum(userPotion.getPotionNum() - drinkPotionNum + 1); // 포션 개수 조정

                    if(userPotion.getPotionNum() == 0)
                        potionInventory.remove(index); // 포션 개수가 0개가 되면 인벤토리에서 제거

                    return; // 회복 활동을 끝냈으므로 메소드 종료
                }
                else if(userPotion.getPotion().getRecoveryKind() == RecoveryKind.MANA) {
                    // 최대 마나인데 포션을 복용할 필요가 없음. -> 최대 마나에서 포션 복용 시도시 즉시 메소드 탈출
                    if(this.getManaPoint() == this.getMaxManaPoint()) {
                        System.out.println("포션 복용 불가능.");
                        return;
                    }
                    // 최대 마나가 아닌 경우 -> 최대 마나에 도달하면 복용을 멈춰야함
                    else if(this.getManaPoint() < this.getMaxManaPoint()) {
                        for(drinkPotionNum = 1; drinkPotionNum <= num; drinkPotionNum++) {
                            // 마시고 바로 풀마나가 되는 경우
                            if(this.getManaPoint() + userPotion.getPotion().getRecoveryQuantity() >= this.getMaxManaPoint()) {
                                this.setManaPoint(this.getMaxManaPoint()); // 최대 마나까지만 회복
                                break; // 반복문 탈출
                            }

                            // 마셔도 풀마나에 도달하지 못하는 경우
                            else
                                this.setManaPoint(this.getManaPoint() + userPotion.getPotion().getRecoveryQuantity());
                        }
                    }

                    if(this.getManaPoint() == this.getMaxManaPoint())
                        userPotion.setPotionNum(userPotion.getPotionNum() - drinkPotionNum); // 포션 개수 조정
                    else
                        userPotion.setPotionNum(userPotion.getPotionNum() - drinkPotionNum + 1); // 포션 개수 조정

                    if(userPotion.getPotionNum() == 0)
                        potionInventory.remove(index); // 포션 개수가 0개가 되면 인벤토리에서 제거

                    return; // 회복 활동을 끝냈으므로 메소드 종료.
                }
            }
            else // 포션 탐색 실패시 다음 인덱스로 넘어감
                index++;
        }

        // 코드가 여기까지 도달했다는 뜻은, 잘못 입력을 했다는 뜻임.
        System.out.println("인벤토리에 존재하지 않는 포션입니다.");
        return;
    }

    // 무기 인벤토리를 조회하는 메소드
    public void openWeaponInventory() {
        System.out.println("[무기 인벤토리 조회]");

        weaponInventory.stream().forEach(userWeapon -> {
            System.out.println(userWeapon.getWeapon().getName() + " : " + userWeapon.getNum() + "개");
        });
    }

    // 갑옷 인벤토리를 조회하는 메소드
    public void openProtectiveGearInventory() {
        System.out.println("[갑옷 인벤토리 조회]");

        protectiveGearInventory.stream().forEach(userProtectiveGear -> {
            System.out.println(userProtectiveGear.getProtectiveGear().getName() + " : " + userProtectiveGear.getNum() + "개");
        });
    }

    // 포션 인벤토리를 조회하는 메소드
    public void openPotionInventory() {
        System.out.println("[포션 인벤토리 조회]");

        potionInventory.stream().forEach(userPotion -> {
            System.out.println(userPotion.getPotion().getName() + " : " + userPotion.getPotionNum() + "개");
        });
    }

    // 캐릭터의 스킬 목록을 조회하는 메소드
    public void openSkillList() {
        System.out.println("======================================");

        // 직업군 별로 출력 결과를 달리해야함
        if(this.job == Job.WARRIOR) {
            if(this.warriorSkillList.size() == 0)
                System.out.println("보유한 스킬이 없습니다.");

            else if(this.warriorSkillList.size() > 0) {
                warriorSkillList.stream().forEach(warriorSkill -> {
                    System.out.println(warriorSkill + " : " + warriorSkill.getDescription()); // 스킬의 이름과 스킬의 설명을 가져옴.
                });
            }
        }
        else if(this.job == Job.WIZARD) {
            if(this.wizardSkillList.size() == 0)
                System.out.println("보유한 스킬이 없습니다.");

            else if(this.wizardSkillList.size() > 0) {
                wizardSkillList.stream().forEach(wizardSkill -> {
                    System.out.println(wizardSkill + " : " + wizardSkill.getDescription());
                });
            }
        }

        System.out.println("======================================");
    }

    // 몬스터를 잡을때마다 관련 퀘스트의 목표수가 증가하는 메소드
    public void updateKilledMonster(Monster monster) {
        for(Quest quest : unCompletedQuestList) {
            if(quest.getMonster().getMonsterName() == monster.getMonsterName()) {
                quest.setKilledNumber(quest.getKilledNumber() + 1); // 죽인 마리수를 1마리 늘림.

                if(quest.getKilledNumber() == quest.getQuestName().getObject()) {
                    System.out.println("[" + quest.getQuestName().toString() + "] 완료.");
                    quest.setQuestCompleted(true); // 퀘스트 완료 처리를 함.
                }
            }
        }
    }

    public int getManaPoint() {
        return manaPoint;
    }

    public void setManaPoint(int manaPoint) {
        this.manaPoint = manaPoint;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getExperiencePoint() {
        return experiencePoint;
    }

    public void setExperiencePoint(int experiencePoint) {
        this.experiencePoint = experiencePoint;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }

    public int getMaxManaPoint() {
        return maxManaPoint;
    }

    public void setMaxManaPoint(int maxManaPoint) {
        this.maxManaPoint = maxManaPoint;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Job getJob() {
        return job;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public WeaponSpecialEffects getWeaponSpecialEffects() {
        return weaponSpecialEffects;
    }

    public void setWeaponSpecialEffects(WeaponSpecialEffects weaponSpecialEffects) {
        this.weaponSpecialEffects = weaponSpecialEffects;
    }

    public ProtectiveGearSpecialEffects getProtectiveGearSpecialEffects() {
        return protectiveGearSpecialEffects;
    }

    public void setProtectiveGearSpecialEffects(ProtectiveGearSpecialEffects protectiveGearSpecialEffects) {
        this.protectiveGearSpecialEffects = protectiveGearSpecialEffects;
    }

    public boolean isDespised() {
        return isDespised;
    }

    public void setDespised(boolean despised) {
        isDespised = despised;
    }

    public ArrayList<Quest> getCompletedQuestList() {
        return completedQuestList;
    }

    public ArrayList<Quest> getUnCompletedQuestList() {
        return unCompletedQuestList;
    }
}

package com.company.game.npc;

import com.company.Main;
import com.company.game.npc.quest.Quest;
import com.company.game.npc.quest.QuestNames;
import com.company.item.potion.UserPotion;
import com.company.item.protectiveGear.ProtectiveGear;
import com.company.item.protectiveGear.ProtectiveGearNames;
import com.company.item.protectiveGear.UserProtectiveGear;
import com.company.item.weapon.UserWeapon;
import com.company.item.weapon.Weapon;
import com.company.item.weapon.WeaponNames;
import com.company.unit.character.Character;
import com.company.unit.enemy.Monster;
import com.company.unit.enemy.MonsterNames;

import java.util.ArrayList;
import java.util.Scanner;

public class VillageHead {
    private static VillageHead villageHead = null;

    private Scanner scanner = new Scanner(System.in);

    // 이장이 가진 퀘스트의 목록
    private ArrayList<Quest> questList = new ArrayList<>();

    // 생성자(private)
    private VillageHead() {
        initializeQuestList(); // 이장이 가지고있는 퀘스트 목록을 초기화함.
    }

    // Singleton pattern 적용
    public static VillageHead getInstance() {
        if(villageHead == null) {
            villageHead = new VillageHead();
            return villageHead;
        }
        else
            return villageHead;
    }

    // 퀘스트 목록을 초기화하는 메소드
    public void initializeQuestList() {
        questList.add(new Quest(QuestNames.들개_소탕, new Monster(MonsterNames.들개, 1), 20, 30, new Weapon(WeaponNames.화룡검)));
        questList.add(new Quest(QuestNames.파리지옥_박멸, new Monster(MonsterNames.파리지옥, 4), 80, 200, new ProtectiveGear(ProtectiveGearNames.혈웅복)));
        questList.add(new Quest(QuestNames.까마귀_소탕, new Monster(MonsterNames.삼족오, 6), 100, 400));
        questList.add(new Quest(QuestNames.불청객이_싫어요, new Monster(MonsterNames.화등장수, 8), 300, 500, new Weapon(WeaponNames.천지검)));
        questList.add(new Quest(QuestNames.공포스러운_불청객, new Monster(MonsterNames.외족망나니, 9), 450, 600, new ProtectiveGear(ProtectiveGearNames.월령복)));
        questList.add(new Quest(QuestNames.끔찍한_비명, new Monster(MonsterNames.설녀, 11), 450, 800));
        questList.add(new Quest(QuestNames.얼음세상_개척1, new Monster(MonsterNames.에스키모, 12), 450, 800));
        questList.add(new Quest(QuestNames.얼음세상_개척2, new Monster(MonsterNames.설원검사, 14), 450, 800));
        questList.add(new Quest(QuestNames.지옥의_관문, new Monster(MonsterNames.지옥수, 16), 600, 1000));
        questList.add(new Quest(QuestNames.원한_풀어주기, new Monster(MonsterNames.처녀귀신, 18), 800, 1000));
        questList.add(new Quest(QuestNames.지옥_해방, new Monster(MonsterNames.외눈강시, 19), 800, 1000));
        questList.add(new Quest(QuestNames.저승의_무질서, new Monster(MonsterNames.저승경찰, 22), 1000, 1100));
        questList.add(new Quest(QuestNames.마을_부적_만들기, new Monster(MonsterNames.저승사자, 23), 1200, 1300));
        questList.add(new Quest(QuestNames.저승_해방, new Monster(MonsterNames.저승기사, 24), 1300, 2500));
        questList.add(new Quest(QuestNames.공허의_저항, new Monster(MonsterNames.공허수문장, 26), 1800, 2500));
        questList.add(new Quest(QuestNames.공허_청소, new Monster(MonsterNames.공허간수, 27), 2100, 2700));
        questList.add(new Quest(QuestNames.전략적_침투, new Monster(MonsterNames.공허마녀, 28), 3300, 3000));
        questList.add(new Quest(QuestNames.마을_구원, new Monster(MonsterNames.공허기사, 29), 0, 0));
    }

    // 캐릭터의 레벨 기준으로 미완료된 퀘스트를 보여주고(이건 메소드로 따로 구현), 받을 퀘스트를 결정하는 메소드
    public void assignQuest(Character character) {
        ArrayList<Quest> questCandidates = new ArrayList<>(); // 퀘스트 후보군들
        String inputQuestName; // 입력한 퀘스트의 이름

        for(Quest quest : questList) {
            // 완료되지 않았으며, 퀘스트의 요구 레벨보다 캐릭터가 레벨이 높은 경우에만 퀘스트 후보군에 퀘스트를 추가함.
            if(!isCompletedQuest(Main.gameModule.getCharacter(), quest)
                    && Main.gameModule.getCharacter().getLevel() >= quest.getQuestName().getRequirementLevel())
                questCandidates.add(quest);
        }

        showQuestList(questCandidates); // 퀘스트 후보군들을 출력함

        while(true) {
            System.out.print("받고싶은 퀘스트의 이름을 입력하십시오(0을 입력하면 퀘스트를 받지 않음) : ");
            inputQuestName = scanner.nextLine();

            // 퀘스트를 받지 않으면 메소드를 탈출
            if(inputQuestName.equals("0")) {
                System.out.println("퀘스트를 받지 않습니다.");
                return;
            }

            for(Quest quest : questCandidates) {
                if(quest.getQuestName().toString().equals(inputQuestName)) {
                    character.getUnCompletedQuestList().add(quest);
                    return; // 퀘스트를 정상 등록하였으면 메소드 탈출
                }
            }

            // 코드가 여기에 도달하는 경우에는 잘못 입력한 경우임.
            System.out.println("퀘스트 이름을 잘못 입력하셨습니다. 다시 입력해주세요.");
            System.out.println();
        }
    }

    // 미완료된 퀘스트를 출력하는 메소드
    /**
     * 퀘스트의 목록을 조회하는 메소드
     * 퀘스트의 정보를 출력한 뒤, 마지막에는 퀘스트의 완료 조건(목표 몬스터, 목표 마리 수)을 명시해야합니다.
     *
     * @param questList 조회를 하려고 하는 퀘스트 리스트
     */
    public void showQuestList(ArrayList<Quest> questList) {
        System.out.println("===========================================================================");

        questList.stream().forEach(quest -> {
            System.out.print("[" + quest.getQuestName().toString() + "] " + "요구 레벨 : " + quest.getQuestName().getRequirementLevel()
             + ", 골드 보상 : " + quest.getGold() + ", 경험치 보상 : " + quest.getExp());

            if(quest.getWeapon() != null) {
                quest.getWeapon().ifPresent(weapon -> {
                    System.out.print(", 무기 보상 : " + weapon.getName());
                });
            }

            if(quest.getProtectiveGear() != null) {
                quest.getProtectiveGear().ifPresent(protectiveGear -> {
                    System.out.print(", 갑옷 보상 : " + protectiveGear.getName());
                });
            }

            if(quest.getPotion() != null) {
                quest.getPotion().ifPresent(potion -> {
                    System.out.print(", 포션 보상 : " + potion.getName());
                });
            }

            // 퀘스트에 관한 내용 출력
            System.out.println();
            System.out.println(quest.getQuestDescription());
            System.out.println();
        });

        System.out.println("===========================================================================");

    }

    // 완료된 퀘스트인지 판별하는 메소드
    public boolean isCompletedQuest(Character character, Quest quest) {
        boolean isCompleted = false;

        ArrayList<Quest> alreadyCompletedQuestList = character.getCompletedQuestList(); // 캐릭터의 이미 완료된 퀘스트 리스트를 가져옴

        // 퀘스트 검색
        for(Quest userQuest : alreadyCompletedQuestList) {
            if(userQuest.getQuestName() == quest.getQuestName())
                isCompleted = true;
        }

        return isCompleted; // 검색 결과 for문에 걸리는게 없으면 이미 완료된 퀘스트가 아니라는 뜻임.
    }

    // todo 퀘스트를 완료 여부를 처리하는 메소드
    public void checkQuest(Character character) {
        String name; // 입력 받을 퀘스트의 이름
        int index = 0;

        System.out.println("===========================================================================");
        character.getUnCompletedQuestList().stream().forEach(quest -> {
            if(quest.isQuestCompleted()) {
                System.out.println("[" + quest.getQuestName().toString() + "] : 완료");
            }
            else {
                System.out.println("[" + quest.getQuestName().toString() + "] : 미완료 (" + quest.getKilledNumber() + "/"
                + quest.getQuestName().getObject() + ")");
            }
        });
        System.out.println("===========================================================================");

        while(true) {
            System.out.print("완료하고싶은 퀘스트의 이름을 입력하세요(0 : 잘못 찾아왔어요) : ");
            name = scanner.nextLine();

            if(name.equals("0"))
                return;

            for(Quest userQuest : character.getUnCompletedQuestList()) {
                if(userQuest.getQuestName().toString().equals(name)) {
                    // 탐색 완료
                    if(userQuest.isQuestCompleted()) {
                        System.out.println("퀘스트를 완료하였습니다.");

                        character.setGold(character.getGold() + userQuest.getGold()); // 골드 수령
                        character.receiveExpFromMonster(userQuest.getExp()); // 경험치 수령

                        if(Main.gameModule.getExpList()[character.getLevel()] < character.getExperiencePoint()) {
                            character.levelUp(); // 경험치를 깎는 것도 레벨업 메소드에 포함이 되어있음.
                        }

                        if(userQuest.getWeapon() != null) {
                            userQuest.getWeapon().ifPresent(weapon -> {
                                character.addWeaponInInventory(new UserWeapon(userQuest.getWeapon().get(), 1));
                            });
                        }

                        if(userQuest.getProtectiveGear() != null) {
                            userQuest.getProtectiveGear().ifPresent(protectiveGear -> {
                                character.addProtectiveGearInInventory(new UserProtectiveGear(userQuest.getProtectiveGear().get(), 1));
                            });
                        }

                        if(userQuest.getPotion() != null) {
                            userQuest.getPotion().ifPresent(potion -> {
                                character.addPotionInInventory(new UserPotion(userQuest.getPotion().get(), 1));
                            });
                        }

                        // 완료된 퀘스트 목록에 퀘스트를 추가하고, 진행중인 퀘스트 목록에서 퀘스트를 삭제하는 처리
                        character.getCompletedQuestList().add(userQuest);
                        character.getUnCompletedQuestList().remove(character.getUnCompletedQuestList().indexOf(userQuest)); // 수정
                        return;
                    }
                    else {
                        System.out.println("퀘스트 미완료.");
                        return;
                    }
                }
            }
            System.out.println("잘못 입력하셨습니다.");
        }
    }
}

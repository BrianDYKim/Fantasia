package com.company.game.hunting;

import com.company.Main;
import com.company.unit.character.Character;
import com.company.unit.enemy.Monster;
import com.company.unit.enemy.MonsterNames;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class HuntingThread extends Thread {
    private Scanner scanner = new Scanner(System.in);
    private StringTokenizer stringTokenizer;

    private int select; // 어느 사냥터를 갈지 결정하는 변수

    private Character character = Main.gameModule.getCharacter(); // game Module의 character를 끌어다 사용
    private Monster fieldMonster; // 현재 캐릭터가 대치중인 몬스터

    private HuntingFieldNames huntingField; // 현재 입장된 사냥터

    // 필드별 몬스터 배열
    private ArrayList<Monster> abandonedForestMonsterList = new ArrayList<>(); // 황폐화된 숲의 몬스터 리스트
    private ArrayList<Monster> abandonedVillageMonsterList = new ArrayList<>(); // 버려진 도시의 몬스터 리스트
    private ArrayList<Monster> iceMountainMonsterList = new ArrayList<>(); // 설산의 몬스터 리스트
    private ArrayList<Monster> hellMonsterList = new ArrayList<>(); // 지옥의 몬스터 리스트
    private ArrayList<Monster> afterLifeMonsterList = new ArrayList<>(); // 저승의 몬스터 리스트
    private ArrayList<Monster> voidWorldMonsterList = new ArrayList<>(); // 공허 세계의 몬스터 리스트

    private boolean isEnd = false; // 사냥이 끝났는지 여부를 저장하는 변수

    // 생성자
    public HuntingThread() {
        // 몬스터 리스트에 몬스터들을 추가
        initializeMonsterList(HuntingFieldNames.ABANDONED_FOREST);
        initializeMonsterList(HuntingFieldNames.ABANDONED_VILLAGE);
        initializeMonsterList(HuntingFieldNames.ICE_MOUNTAIN);
        initializeMonsterList(HuntingFieldNames.HELL);
        initializeMonsterList(HuntingFieldNames.AFTER_LIFE);
        initializeMonsterList(HuntingFieldNames.VOID_WORLD);

        enterHuntingField(); // 사냥터에 접속을 요청
    }

    @Override
    public void run() {
        int huntingOptionSelect;

        synchronized (this) {
            while(!isEnd) {
                // 사냥터가 지정이 되지 않은 경우에는 쓰레드를 종료함
                if(huntingField == null) {
                    notify();
                    break;
                }
                // 사냥터가 지정이 된 경우에는 사냥을 시작함
                else {
                    if(huntingField == HuntingFieldNames.ABANDONED_FOREST) {
                        System.out.print("선택(1 : 몬스터 사냥, 2 : 포션 섭취, 3 : 사냥 종료) : ");
                        huntingOptionSelect = scanner.nextInt();

                        if(huntingOptionSelect == 1) {
                            callAndHunting(); // 몬스터를 소환하여 싸움

                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }
                        else if(huntingOptionSelect == 2) {
                            drinkPotionAtHuntingField(); // 포션을 섭취
                        }
                        else if(huntingOptionSelect == 3) {
                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }

                    }
                    else if(huntingField == HuntingFieldNames.ABANDONED_VILLAGE) {
                        System.out.print("선택(1 : 몬스터 사냥, 2 : 포션 섭취, 3 : 사냥 종료) : ");
                        huntingOptionSelect = scanner.nextInt();

                        if(huntingOptionSelect == 1) {
                            callAndHunting();

                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }
                        else if(huntingOptionSelect == 2) {
                            drinkPotionAtHuntingField(); // 포션을 섭취
                        }
                        else if(huntingOptionSelect == 3) {
                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }
                    }

                    else if(huntingField == HuntingFieldNames.ICE_MOUNTAIN) {
                        System.out.print("선택(1 : 몬스터 사냥, 2 : 포션 섭취, 3 : 사냥 종료) : ");
                        huntingOptionSelect = scanner.nextInt();

                        if(huntingOptionSelect == 1) {
                            callAndHunting();

                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }
                        else if(huntingOptionSelect == 2) {
                            drinkPotionAtHuntingField(); // 포션을 섭취
                        }
                        else if(huntingOptionSelect == 3) {
                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }
                    }
                    else if(huntingField == HuntingFieldNames.HELL) {
                        System.out.print("선택(1 : 몬스터 사냥, 2 : 포션 섭취, 3 : 사냥 종료) : ");
                        huntingOptionSelect = scanner.nextInt();

                        if(huntingOptionSelect == 1) {
                            callAndHunting();

                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }
                        else if(huntingOptionSelect == 2) {
                            drinkPotionAtHuntingField(); // 포션을 섭취
                        }
                        else if(huntingOptionSelect == 3) {
                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }
                    }
                    else if(huntingField == HuntingFieldNames.AFTER_LIFE) {
                        System.out.print("선택(1 : 몬스터 사냥, 2 : 포션 섭취, 3 : 사냥 종료) : ");
                        huntingOptionSelect = scanner.nextInt();

                        if(huntingOptionSelect == 1) {
                            callAndHunting();

                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }
                        else if(huntingOptionSelect == 2) {
                            drinkPotionAtHuntingField(); // 포션을 섭취
                        }
                        else if(huntingOptionSelect == 3) {
                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }
                    }
                    else if(huntingField == HuntingFieldNames.VOID_WORLD) {
                        System.out.print("선택(1 : 몬스터 사냥, 2 : 포션 섭취, 3 : 사냥 종료) : ");
                        huntingOptionSelect = scanner.nextInt();

                        if(huntingOptionSelect == 1) {
                            callAndHunting();

                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }
                        else if(huntingOptionSelect == 2) {
                            drinkPotionAtHuntingField(); // 포션을 섭취
                        }
                        else if(huntingOptionSelect == 3) {
                            System.out.println("사냥을 종료합니다.");
                            notify();
                            break;
                        }
                    }
                }
            }
        }
    }

    // 사냥터의 정보를 보여주는 메소드
    public void showHuntingFieldsInfo() {
        System.out.println("===========================================================================");
        System.out.println("0. 사냥 종료");
        System.out.println("1. 황폐된 숲(Abandoned forest) : 권장레벨 1~5");
        System.out.println("2. 버려진 마을(Abandoned village) : 권장레벨 6~10");
        System.out.println("3. 설산(Ice mountain) : 권장레벨 11~15");
        System.out.println("4. 지옥(Hell) : 권장레벨 16~20");
        System.out.println("5. 저승(After life) : 권장레벨 21~25");
        System.out.println("6. 공허 세계(Void world) : 권장레벨 26~30");
        System.out.println("===========================================================================");
    }

    // 사냥터에 입장을 요청하는 메소드
    public void enterHuntingField() {
        boolean isPermitted = false; // 사냥터의 입장이 허락되었는지 검사하는 변수
        int minLevel; // 권장레벨의 최소치

        while(!isPermitted) {
            // 입장할 사냥터를 선택
            showHuntingFieldsInfo();
            System.out.print("입장할 사냥터를 입력하십시오 >> ");
            select = scanner.nextInt() - 1;

            if(select * 5 + 1 > character.getLevel()) {
                System.out.println("입장 불가능(레벨 미달)");
                continue;
            }
            else {
                switch (select) {
                    case -1 : // 사냥을 하지 않음
                        System.out.println("마을로 복귀합니다.");

                        try {
                            Thread.sleep(1500);
                        }catch(InterruptedException e) {
                            e.printStackTrace();
                        }

                        return;

                    case 0 :
                        huntingField = HuntingFieldNames.ABANDONED_FOREST;
                        isPermitted = true;
                        break;
                    case 1 :
                        huntingField = HuntingFieldNames.ABANDONED_VILLAGE;
                        isPermitted = true;
                        break;

                    case 2 :
                        huntingField = HuntingFieldNames.ICE_MOUNTAIN;
                        isPermitted = true;
                        break;

                    case 3 :
                        huntingField = HuntingFieldNames.HELL;
                        isPermitted = true;
                        break;

                    case 4 :
                        huntingField = HuntingFieldNames.AFTER_LIFE;
                        isPermitted = true;
                        break;

                    case 5 :
                        huntingField = HuntingFieldNames.VOID_WORLD;
                        isPermitted = true;
                        break;

                    default:
                        System.out.println("입장 불가능(존재하지 않는 선택)");
                        break;
                }
            }
        }
    }

    // 몬스터들을 등록하는 메소드
    public void initializeMonsterList(HuntingFieldNames huntingFieldName) {
        if(huntingFieldName == HuntingFieldNames.ABANDONED_FOREST) {
            abandonedForestMonsterList.add(new Monster(MonsterNames.들개, 1));
            abandonedForestMonsterList.add(new Monster(MonsterNames.늑대, 2));
            abandonedForestMonsterList.add(new Monster(MonsterNames.두꺼비, 3));
            abandonedForestMonsterList.add(new Monster(MonsterNames.파리지옥, 4));

            // 보스 몬스터 추가
            abandonedForestMonsterList.add(new Monster(MonsterNames.식인초, 5));
        }
        else if(huntingFieldName == HuntingFieldNames.ABANDONED_VILLAGE) {
            abandonedVillageMonsterList.add(new Monster(MonsterNames.삼족오, 6));
            abandonedVillageMonsterList.add(new Monster(MonsterNames.흑수리, 7));
            abandonedVillageMonsterList.add(new Monster(MonsterNames.화등장수, 8));
            abandonedVillageMonsterList.add(new Monster(MonsterNames.외족망나니, 9));

            // 보스 몬스터 추가
            abandonedVillageMonsterList.add(new Monster(MonsterNames.타락한_부리미, 10));
        }
        else if(huntingFieldName == HuntingFieldNames.ICE_MOUNTAIN) {
            iceMountainMonsterList.add(new Monster(MonsterNames.설녀, 11));
            iceMountainMonsterList.add(new Monster(MonsterNames.에스키모, 12));
            iceMountainMonsterList.add(new Monster(MonsterNames.설원화살병, 13));
            iceMountainMonsterList.add(new Monster(MonsterNames.설원검사, 14));

            // 보스 몬스터 추가
            iceMountainMonsterList.add(new Monster(MonsterNames.폴라_이그나이트, 15));
        }
        else if(huntingFieldName == HuntingFieldNames.HELL) {
            hellMonsterList.add(new Monster(MonsterNames.지옥수, 16));
            hellMonsterList.add(new Monster(MonsterNames.도깨비, 17));
            hellMonsterList.add(new Monster(MonsterNames.처녀귀신, 18));
            hellMonsterList.add(new Monster(MonsterNames.외눈강시, 19));

            // 보스 몬스터 추가
            hellMonsterList.add(new Monster(MonsterNames.플레어, 20));
        }
        else if(huntingFieldName == HuntingFieldNames.AFTER_LIFE) {
            afterLifeMonsterList.add(new Monster(MonsterNames.저승수문장, 21));
            afterLifeMonsterList.add(new Monster(MonsterNames.저승경찰, 22));
            afterLifeMonsterList.add(new Monster(MonsterNames.저승사자, 23));
            afterLifeMonsterList.add(new Monster(MonsterNames.저승기사, 24));

            // 보스 몬스터 추가
            afterLifeMonsterList.add(new Monster(MonsterNames.저승_군단장, 25));
        }
        else if(huntingFieldName == HuntingFieldNames.VOID_WORLD) {
            voidWorldMonsterList.add(new Monster(MonsterNames.공허수문장, 26));
            voidWorldMonsterList.add(new Monster(MonsterNames.공허간수, 27));
            voidWorldMonsterList.add(new Monster(MonsterNames.공허마녀, 28));
            voidWorldMonsterList.add(new Monster(MonsterNames.공허기사, 29));

            // 보스 몬스터 추가
            voidWorldMonsterList.add(new Monster(MonsterNames.그림자_군단장, 30));
        }
    }

    // 사냥터에서 포션을 마시는 역할을 수행하는 메소드
    public void drinkPotionAtHuntingField() {
        System.out.println("===========================================================================");
        character.openPotionInventory();
        System.out.println("===========================================================================");

        System.out.print("어떤 포션을 마시겠습니까?(포션이름과 수량은 ,로 구분) >>");
        stringTokenizer = new StringTokenizer(scanner.next(), ",");
        character.drinkPotion(stringTokenizer.nextToken(), Integer.parseInt(stringTokenizer.nextToken()));
    }

    // 일반몬스터를 소환하는 함수
    public void callMonster() {
        // 나머지가 4가 아니게 설정해야함 --> 나머지가 4이면 보스 몬스터가 소환됨.
        int randomNumber = (int)(Math.random() * 100) % 5; // 0부터 4까지의 난수 부여

        // 난수가 4이면 반복하는 반복문임.
        while(randomNumber == 4)
            randomNumber = (int)(Math.random() * 100) % 5; // 0부터 3까지의 난수 부여

        if(huntingField == HuntingFieldNames.ABANDONED_FOREST) {
            for(Monster monster : abandonedForestMonsterList) {
                if(monster.getMonsterName().getNum() == randomNumber)
                    fieldMonster = new Monster(monster.getMonsterName(), monster.getLevel());
            }
        }
        else if(huntingField == HuntingFieldNames.ABANDONED_VILLAGE) {
            for(Monster monster : abandonedVillageMonsterList) {
                if(monster.getMonsterName().getNum() == randomNumber)
                    fieldMonster = new Monster(monster.getMonsterName(), monster.getLevel());
            }
        }
        else if(huntingField == HuntingFieldNames.ICE_MOUNTAIN) {
            for(Monster monster : iceMountainMonsterList) {
                if(monster.getMonsterName().getNum() == randomNumber)
                    fieldMonster = new Monster(monster.getMonsterName(), monster.getLevel());
            }
        }
        else if(huntingField == HuntingFieldNames.HELL) {
            for(Monster monster : hellMonsterList) {
                if(monster.getMonsterName().getNum() == randomNumber)
                    fieldMonster = new Monster(monster.getMonsterName(), monster.getLevel());
            }
        }
        else if(huntingField == HuntingFieldNames.AFTER_LIFE) {
            for(Monster monster : afterLifeMonsterList) {
                if(monster.getMonsterName().getNum() == randomNumber)
                    fieldMonster = new Monster(monster.getMonsterName(), monster.getLevel());
            }
        }
        else if(huntingField == HuntingFieldNames.VOID_WORLD) {
            for(Monster monster : voidWorldMonsterList) {
                if(monster.getMonsterName().getNum() == randomNumber)
                    fieldMonster = new Monster(monster.getMonsterName(), monster.getLevel());
            }
        }

        System.out.println(fieldMonster.getMonsterName() + "를 마주쳤습니다.");
    }

    // todo callBossMonster() 메소드 구현

    // 몬스터와의 전투를 시행하는 메소드
    public void fightWithMonster() {
        // 선공권은 무조건 캐릭터가 가져간다.
        FightTurn fightTurn = FightTurn.TURN_CHARACTER;
        boolean isFightEnd = false;

        while(!isFightEnd) {

            if(fightTurn == FightTurn.TURN_CHARACTER) {
                System.out.print("선택(1 : 기본공격, 2 : 스킬공격, 3 : 포션 마시기) : ");

                /*
                [스킬 공격]
                몬스터에게 스킬 공격을 수행. 몬스터가 죽지 않으면 턴을 넘기고, 죽으면 isFightEnd를 true로 변환.
                공격을 수행함에 있어, 무기의 특수효과를 고려하여 데미지를 계산해야함.

                <-중점 사항->
                enum과 string을 비교할 때는 enum 변수를 toString()을 통해 String으로 비교하여 equals를 시행해야함!!
                 */
                switch(scanner.nextInt()) {
                    case 1 :
                        character.attack(fieldMonster, "기본공격");

                        if(fieldMonster.getHealth() == 0) {
                            // 경험치 처리
                            character.receiveExpFromMonster(fieldMonster.getDropExperimentPoint());

                            if(Main.gameModule.getExpList()[character.getLevel()] < character.getExperiencePoint()) {
                                character.levelUp(); // 경험치를 깎는 것도 레벨업 메소드에 포함이 되어있음.
                            }

                            // 골드 처리
                            character.setGold(character.getGold() + fieldMonster.getDropGold()); // 골드 획득

                            isFightEnd = true;
                        }
                        break;

                    case 2 :
                        character.openSkillList(); // 캐릭터의 스킬 목록을 조회함
                        System.out.print("사용하고자 하는 스킬의 이름을 입력하세요 : ");
                        character.attack(fieldMonster, scanner.next()); // 공격 시행

                        if(fieldMonster.getHealth() == 0) {
                            // 경험치 처리
                            character.receiveExpFromMonster(fieldMonster.getDropExperimentPoint());

                            if(Main.gameModule.getExpList()[character.getLevel()] < character.getExperiencePoint()) {
                                character.levelUp(); // 경험치를 깎는 것도 레벨업 메소드에 포함이 되어있음.
                            }

                            // 골드 처리
                            character.setGold(character.getGold() + fieldMonster.getDropGold()); // 골드 획득

                            isFightEnd = true;
                        }
                        break;

                    case 3 :
                        drinkPotionAtHuntingField(); // 포션 마시기
                        break;

                    default:
                        break;
                }

                fightTurn = FightTurn.TURN_MONSTER; // 턴을 몬스터에게 넘김
            }
            else if(fightTurn == FightTurn.TURN_MONSTER) {
                fieldMonster.basicAttack(character);

                if(character.getHealth() == 0) {
                    System.out.println("캐릭터가 사망하였습니다.");
                    System.exit(0); // 게임 자체를 종료시켜버림.
                }
                else {
                    System.out.println("캐릭터의 공격 차례입니다.");
                    fightTurn = FightTurn.TURN_CHARACTER;
                }

                character.showInfo();
            }

        }
    }

    // 몬스터와의 싸움 종합 메소드
    public void callAndHunting() {
        // 일반 몬스터와는 전투를 10회 치름
        for(int i = 0; i < 10; i++) {
            callMonster(); // 몬스터 호출
            // 몬스터와의 전투
            fightWithMonster();
            character.setDespised(false);
            character.updateKilledMonster(fieldMonster); // 죽인 몬스터 마리 수를 갱신하는 메소드
        }
    }
}
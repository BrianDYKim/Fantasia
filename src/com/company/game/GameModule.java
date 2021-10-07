package com.company.game;

import com.company.game.hunting.HuntingThread;
import com.company.game.inventory.InventoryThread;
import com.company.game.npc.VillageHead;
import com.company.game.npc.VillageHeadThread;
import com.company.game.shopping.ShoppingThread;
import com.company.unit.character.Character;

import java.util.Scanner;

public class GameModule extends Thread {

    private Scanner scanner  = new Scanner(System.in);
    private int behaviorSelect; // 행동을 입력받는 int형 변수
    private Character character; // 캐릭터 객체
    private CharacterBehavior characterBehavior = CharacterBehavior.RESTING;

    private String characterName; // 캐릭터의 이름

    // NPC 객체
    private VillageHead villageHead = VillageHead.getInstance(); // 마을 이장

    private int[] expList = {0, 15, 34, 57, 92, 135, 180, 280, 420, 620, 620,
                            620, 620, 620, 620, 745, 800, 850, 900, 1000, 1200,
                            1450, 1700, 1950, 2200, 2500, 2800, 3500, 4300, 5000}; // 경험치를 보관하는 배열

    // 게임이 끝났는지 여부를 결정하는 boolean 변수
    private boolean isEnd = false;

    public GameModule() {

        System.out.println("게임을 시작합니다.");

        System.out.println("===========================================================================");
        // 캐릭터의 설정
        System.out.print("직업군 선택(전사 : 0, 마법사 : 1) : ");

        character = new Character(scanner.next());

        System.out.print("캐릭터의 닉네임을 입력해주세요 : ");
        characterName = scanner.next();
        character.setName(characterName);
        System.out.println("===========================================================================");

        // 개행
        for(int i = 0; i < 10; i++)
            System.out.println();

        // 게임의 초반 설명
        System.out.println("===========================================================================");
        // 캐릭터의 대사
        System.out.println("나 : '..................여긴 어디지?........'");
        System.out.println();

        try {
            Thread.sleep(1000);
        }catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("눈을 떠보니 나는 낯선 곳에 떨어져있었다. 그리고 세상은 온통 아비규환에 가득차있었다.");
        System.out.println();

        try {
            Thread.sleep(1000);
        }catch(Exception e) {
            e.printStackTrace();
        }

        // npc의 대사(이장)
        System.out.println("?? : 자네 이제 눈을 떴는가?");
        System.out.println();

        try {
            Thread.sleep(1000);
        }catch(Exception e) {
            e.printStackTrace();
        }

        //캐릭터의 대사
        System.out.println("나 : 당신은 누구시죠?");
        System.out.println();

        try {
            Thread.sleep(1000);
        }catch(Exception e) {
            e.printStackTrace();
        }

        // npc의 대사(이장)
        System.out.println("?? : 나는 이 마을의 이장이라네. 당신의 이름은 무엇이오?");
        System.out.println();

        try {
            Thread.sleep(1000);
        }catch(Exception e) {
            e.printStackTrace();
        }

        // 캐릭터의 대사
        System.out.println("나 : 제 이름은 " + characterName + " 인데, 지금 어찌된 상황입니까?");
        System.out.println();

        try {
            Thread.sleep(1000);
        }catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("이장 : 본디 이 마을에는 신수가 존재하여 우리 마을을 이세계의 마물로부터 보호를 받고 있었는데...갑자기 어느날 대마물이 나타나 신수를 제압하여 " +
                "신수를 강탈해갔소. \n이제는 마물로부터 신수를 다시 되찾아올 용사들도 없소. 자네가 희망이오. 도와줄 수 있겠소?");
        System.out.println();

        try {
            Thread.sleep(1000);
        }catch(Exception e) {
            e.printStackTrace();
        }

        System.out.println("---------------------------------------------------");
        System.out.println("| 1 : 마을이 위험해졌는데 당연히 도와드려야죠!            |");
        System.out.println("| 2 : 저랑 무슨 상관이죠?                             |");
        System.out.println("---------------------------------------------------");

        try {
            Thread.sleep(1000);
        }catch(Exception e) {
            e.printStackTrace();
        }

        System.out.print("선택 : ");

        if(scanner.next().equals("2")) {
            System.out.println();
            System.out.println("이장 : 실망이네. 자네 마음대로 하게.");
            System.out.println("===========================================================================");
            isEnd = true;
            System.exit(0);
        }

        System.out.println();
        System.out.println("이장 : 고맙네, 고마워. 자네가 이 마을을 구해줄 수 있을거라 믿네.");


        System.out.println("===========================================================================");

        try {
            Thread.sleep(1000);
        }catch(Exception e) {
            e.printStackTrace();
        }

        for(int i = 0; i < 10; i++)
            System.out.println();

        character.showInfo();
    }

    // todo NPC 쓰레드 작성

    @Override
    public void run() {
        while(!isEnd) {
            showBehaviorList();
            System.out.print(">>");
            behaviorSelect = scanner.nextInt();

            // 캐릭터의 상태 변경
            if(behaviorSelect == 1)
                characterBehavior = CharacterBehavior.TALKING_WITH_NPC;
            else if(behaviorSelect == 2)
                characterBehavior = CharacterBehavior.SHOP;
            else if(behaviorSelect == 3)
                characterBehavior = CharacterBehavior.CHECK_STATUS;
            else if(behaviorSelect == 4)
                characterBehavior = CharacterBehavior.INVENTORY;
            else if(behaviorSelect == 5)
                characterBehavior = CharacterBehavior.HUNTING;

            // 캐릭터에 상태에 따른 행동
            if(characterBehavior == CharacterBehavior.TALKING_WITH_NPC) {
                VillageHeadThread villageHeadThread = new VillageHeadThread();
                villageHeadThread.start();

                synchronized (villageHeadThread) {
                    try {
                        villageHeadThread.wait();
                    }catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                characterBehavior = CharacterBehavior.RESTING;
            }
            else if(characterBehavior == CharacterBehavior.SHOP) {
                ShoppingThread shoppingThread = new ShoppingThread();
                shoppingThread.start();

                synchronized (shoppingThread) {
                    try {
                        shoppingThread.wait(); // shopping을 마칠때까지 기다림
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                characterBehavior = CharacterBehavior.RESTING; // 마을로 돌아온 상태로 변경
            }
            else if(characterBehavior == CharacterBehavior.CHECK_STATUS) {
                for(int i = 0; i < 10; i++)
                    System.out.println();

                character.showInfo();

                try {
                    Thread.sleep(1500);
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for(int i = 0; i < 10; i++)
                    System.out.println();

                characterBehavior = CharacterBehavior.RESTING;
            }

            else if(characterBehavior == CharacterBehavior.INVENTORY) {
                InventoryThread inventoryThread = new InventoryThread();
                inventoryThread.start();

                synchronized (inventoryThread) {
                    try {
                        inventoryThread.wait();
                    }catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                characterBehavior = CharacterBehavior.RESTING; // 마을로 돌아온 상태로 변경
            }
            else if(characterBehavior == CharacterBehavior.HUNTING) {
                HuntingThread huntingThread = new HuntingThread();
                huntingThread.start();

                synchronized (huntingThread) {
                    try {
                        huntingThread.wait();
                    }catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                characterBehavior = CharacterBehavior.RESTING; // 마을로 돌아온 상태로 변경
            }

        }
    }

    // 캐릭터가 행할 행동들의 목록을 보여주는 메소드
    public void showBehaviorList() {
        System.out.println();

        System.out.println("===========================================================================");
        System.out.println("1 : 이장님과의 대화"); // 이장님과의 대화를 통해 퀘스트 수용 및 완료를 할수있음. 또한 전직 가능. 특수스킬 획득도 가능.
        System.out.println("2 : 상점");
        System.out.println("3 : 캐릭터 스테이터스 확인");
        System.out.println("4 : 인벤토리 확인");
        System.out.println("5 : 사냥터 이동"); // 레벨에 맞는 사냥터에 입장하여 원할때까지 사냥을 수행할 수 있다. 사냥터에서 소환되는 마물은 랜덤이다.
        System.out.println("===========================================================================");
    }

    public Character getCharacter() {
        return character;
    }

    public int[] getExpList() {
        return expList;
    }
}
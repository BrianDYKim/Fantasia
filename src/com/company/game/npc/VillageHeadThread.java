package com.company.game.npc;

import com.company.Main;
import com.company.unit.character.Character;

import java.util.Scanner;

// 마을 이장과의 대화에 관한 쓰레드입니다.
public class VillageHeadThread extends Thread {
    private Scanner scanner = new Scanner(System.in);

    private Character character;
    private VillageHead villageHead;

    // 생성자
    public VillageHeadThread() {
        character = Main.gameModule.getCharacter();
        villageHead = VillageHead.getInstance(); // 마을 이장을 데려옴
    }

    @Override
    public void run() {
        synchronized (this) {
            System.out.print("선택(1 : 퀘스트 수령, 2 : 퀘스트 완료, 3 : 대화 종료) : ");

            switch (scanner.nextInt()) {
                case 1 :
                    villageHead.assignQuest(character);
                    notify();
                    break;

                case 2 :
                    villageHead.checkQuest(character);
                    notify();
                    break;

                case 3 :
                    notify();
                    break;
            }
        }
    }
}

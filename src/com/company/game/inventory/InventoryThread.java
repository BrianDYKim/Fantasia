package com.company.game.inventory;

import com.company.Main;
import com.company.unit.character.Character;

import java.util.Scanner;
import java.util.StringTokenizer;

public class InventoryThread extends Thread {
    private Scanner scanner = new Scanner(System.in);
    private StringTokenizer stringTokenizer; // 문자열을 delimeter를 기준으로 쪼개는 기능을 가지는 객체

    private Character character;

    private int select;

    // 생성자
    public InventoryThread() {
        character = Main.gameModule.getCharacter();
    }

    @Override
    public void run() {
        synchronized (this) {
            while(true) {
                System.out.println("===========================================================================");

                character.openWeaponInventory();
                System.out.println();
                character.openProtectiveGearInventory();
                System.out.println();
                character.openPotionInventory();
                System.out.println();

                System.out.println("===========================================================================");

                System.out.print("선택(1 : 무기 장착, 2 : 갑옷 장착, 3 : 포션 마시기, 4 : 인벤토리 나가기) : ");
                select = scanner.nextInt();

                if(select == 1) {
                    System.out.print("어떤 무기를 장착하시겠습니까? >>");
                    character.putOnWeapon(scanner.next());
                }
                else if(select == 2) {
                    System.out.print("어떤 갑옷을 장착하시겠습니까? >>");
                    character.putOnProtectiveGear(scanner.next());
                }
                else if(select == 3) {
                    System.out.print("어떤 포션을 마시겠습니까?(포션이름과 수량은 ,로 구분) >>");
                    stringTokenizer = new StringTokenizer(scanner.next(), ",");
                    character.drinkPotion(stringTokenizer.nextToken(), Integer.parseInt(stringTokenizer.nextToken()));
                }
                else if(select == 4) {
                    notify();
                    break;
                }
            }
        }
    }
}

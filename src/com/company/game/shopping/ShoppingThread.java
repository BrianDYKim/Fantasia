package com.company.game.shopping;

import com.company.Main;
import com.company.item.potion.Potion;
import com.company.item.potion.PotionNames;
import com.company.item.potion.UserPotion;
import com.company.item.protectiveGear.ProtectiveGear;
import com.company.item.protectiveGear.ProtectiveGearNames;
import com.company.item.protectiveGear.ProtectiveGearSpecialEffects;
import com.company.item.protectiveGear.UserProtectiveGear;
import com.company.item.weapon.UserWeapon;
import com.company.item.weapon.Weapon;
import com.company.item.weapon.WeaponNames;
import com.company.item.weapon.WeaponSpecialEffects;
import com.company.unit.character.Character;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ShoppingThread extends Thread{
    private Scanner scanner = new Scanner(System.in);
    private StringTokenizer stringTokenizer; // String을 delimeter 기준으로 분리하는 기능을 가짐

    // 캐릭터 관련 변수
    private Character character = Main.gameModule.getCharacter(); // gameModule에서 캐릭터를 끌어다옴
    private ShoppingCategory shoppingCategory;

    // 상점의 물품 목록
    private ArrayList<Weapon> weaponList = new ArrayList<>();
    private ArrayList<ProtectiveGear> protectiveGearList = new ArrayList<>();
    private ArrayList<Potion> potionList = new ArrayList<>();

    private int select = 0;

    // 생성자
    public ShoppingThread() {
        // 무기 상점의 물품 목록
        weaponList.add(new Weapon(WeaponNames.화룡검));
        weaponList.add(new Weapon(WeaponNames.천지검));
        weaponList.add(new Weapon(WeaponNames.유성검));
        weaponList.add(new Weapon(WeaponNames.흑요검));
        weaponList.add(new Weapon(WeaponNames.멸살검));
        weaponList.add(new Weapon(WeaponNames.칠흑장));
        weaponList.add(new Weapon(WeaponNames.봉황장));
        weaponList.add(new Weapon(WeaponNames.격마장));
        weaponList.add(new Weapon(WeaponNames.비룡장));
        weaponList.add(new Weapon(WeaponNames.멸살장));

        // 갑옷 상점의 물품 목록
        protectiveGearList.add(new ProtectiveGear(ProtectiveGearNames.혈웅복));
        protectiveGearList.add(new ProtectiveGear(ProtectiveGearNames.월령복));
        protectiveGearList.add(new ProtectiveGear(ProtectiveGearNames.금강복));
        protectiveGearList.add(new ProtectiveGear(ProtectiveGearNames.천석복));
        protectiveGearList.add(new ProtectiveGear(ProtectiveGearNames.멸살복));

        // 소비 아이템 상점의 물품 목록
        potionList.add(new Potion(PotionNames.지하수));
        potionList.add(new Potion(PotionNames.포비돈));
        potionList.add(new Potion(PotionNames.토니켓));
        potionList.add(new Potion(PotionNames.생명수));
        potionList.add(new Potion(PotionNames.암반수));
        potionList.add(new Potion(PotionNames.연양갱));
        potionList.add(new Potion(PotionNames.우육포));
        potionList.add(new Potion(PotionNames.활명수));

        shoppingCategory = ShoppingCategory.WEAPON; // 초기에는 무기 상점에서 시작
    }


    // 쓰레드의 동작을 기술하는 run 메소드
    @Override
    public void run() {
        synchronized (this) {

            while(true) {
                if(shoppingCategory == ShoppingCategory.WEAPON) {
                    showWeaponList();

                    System.out.print("선택(1 : 물품 구매, 2 : 다른 상점, 3 : 상점 나가기) : ");
                    select = scanner.nextInt();

                    if(select == 1) {
                        System.out.print("구매할 물품과 수량을 입력하십시오(물품이름과 수량은 ,으로 구분) : ");
                        stringTokenizer = new StringTokenizer(scanner.next(), ",");
                        String weaponName = stringTokenizer.nextToken(); // 구매하려는 무기의 이름
                        int weaponNum = Integer.parseInt(stringTokenizer.nextToken()); // 구매하려는 무기의 개수

                        // 구매 시도
                        if(isExistWeapon(weaponName)) {
                            if(findWeaponByName(weaponName).getPrice() * weaponNum > character.getGold()) {
                                System.out.println("구매 불가능.");
                            }
                            else {
                                System.out.println("구매 완료.");
                                character.addWeaponInInventory(new UserWeapon(findWeaponByName(weaponName), weaponNum)); // 인벤토리에 무기를 추가 처리
                                character.subtractGold(findWeaponByName(weaponName).getPrice() * weaponNum); // 캐릭터에서 골드 차감
                                character.showInfo();
                            }
                        }
                        else {
                            System.out.println("존재하지 않는 무기입니다.");
                        }
                    }
                    else if(select == 2) {
                        System.out.print("선택(1 : 갑옷 상점, 2 : 포션 상점) : ");
                        int anotherShopSelect = scanner.nextInt();

                        if(anotherShopSelect == 1)
                            shoppingCategory = ShoppingCategory.PROTECTIVE_GEAR;
                        else if(anotherShopSelect == 2)
                            shoppingCategory = ShoppingCategory.POTION;
                    }
                    else if(select == 3) {
                        notify(); // 모니터링 락의 권한을 game Module로 넘김
                        break;
                    }
                }
                else if(shoppingCategory == ShoppingCategory.PROTECTIVE_GEAR) {
                    showProtectiveGearList();

                    System.out.print("선택(1 : 물품 구매, 2 : 다른 상점, 3 : 상점 나가기) : ");
                    select = scanner.nextInt();

                    // 물품 구매
                    if(select == 1) {
                        System.out.print("구매할 물품과 수량을 입력하십시오(물품이름과 수량은 ,으로 구분) : ");
                        stringTokenizer = new StringTokenizer(scanner.next(), ",");
                        String protectiveGearName = stringTokenizer.nextToken();
                        int protectiveGearNum = Integer.parseInt(stringTokenizer.nextToken());

                        if(isExistProtectiveGear(protectiveGearName)) {
                            if(findProtectiveGearByName(protectiveGearName).getPrice() * protectiveGearNum > character.getGold()) {
                                System.out.println("구매 불가능.");
                            }
                            else {
                                System.out.println("구매 완료.");
                                character.addProtectiveGearInInventory(new UserProtectiveGear(findProtectiveGearByName(protectiveGearName),
                                        protectiveGearNum));
                                character.subtractGold(findProtectiveGearByName(protectiveGearName).getPrice() * protectiveGearNum);
                                character.showInfo();
                            }
                        }
                        else {
                            System.out.println("존재하지 않는 갑옷입니다.");
                        }
                    }
                    else if(select == 2) {
                        System.out.print("선택(1 : 무기 상점, 2 : 포션 상점) : ");
                        int anotherShopSelect = scanner.nextInt();

                        if(anotherShopSelect == 1)
                            shoppingCategory = ShoppingCategory.WEAPON;
                        else if(anotherShopSelect == 2)
                            shoppingCategory = ShoppingCategory.POTION;
                    }
                    else if(select == 3) {
                        notify(); // 모니터링 락의 권한을 game Module로 넘김
                        break;
                    }
                }
                else if(shoppingCategory == ShoppingCategory.POTION) {
                    showPotionList();

                    System.out.print("선택(1 : 물품 구매, 2 : 다른 상점, 3 : 상점 나가기) : ");
                    select = scanner.nextInt();

                    if(select == 1) {
                        System.out.print("구매할 물품과 수량을 입력하십시오(물품이름과 수량은 ,으로 구분) : ");
                        stringTokenizer = new StringTokenizer(scanner.next(), ",");
                        String potionName = stringTokenizer.nextToken();
                        int potionNum = Integer.parseInt(stringTokenizer.nextToken());

                        // 구매 시도
                        if(isExistPotion(potionName)) {
                            if(findPotionByName(potionName).getPrice() * potionNum > character.getGold()) {
                                System.out.println("구매 불가능");
                            }
                            else {
                                System.out.println("구매 완료");
                                character.addPotionInInventory(new UserPotion(findPotionByName(potionName), potionNum));
                                character.subtractGold(findPotionByName(potionName).getPrice() * potionNum);
                                character.showInfo();
                            }
                        }
                        else {
                            System.out.println("존재하지 않는 포션입니다.");
                        }
                    }
                    else if(select == 2) {
                        System.out.print("선택(1 : 무기 상점, 2 : 갑옷 상점) : ");
                        int anotherShopSelect = scanner.nextInt();

                        if(anotherShopSelect == 1)
                            shoppingCategory = ShoppingCategory.WEAPON;
                        else if(anotherShopSelect == 2)
                            shoppingCategory = ShoppingCategory.PROTECTIVE_GEAR;
                    }
                    else if(select == 3) {
                        notify(); // 모니터링 락의 권한을 game Module로 넘김
                        break;
                    }
                }
            }

        }
    }

    // 무기 목록의 보여주는 메소드(상점에서 무기 목록 조회를 위함)
    public void showWeaponList() {
        System.out.println("===========================================================================");
        System.out.println("무기이름    추가공격력    특수효과   요구레벨    가격");

        weaponList.stream().forEach(weapon -> {
            // 캐릭터의 직업과 맞는 무기들만 조회하도록 함
            if(character.getJob() == weapon.getJob()) {
                System.out.print(weapon.getName() + "       " + weapon.getAttackPoint() + "        ");

                if(weapon.getSpecialEffect() == WeaponSpecialEffects.약자멸시)
                    System.out.print(" 약자멸시    ");
                else if(weapon.getSpecialEffect() == WeaponSpecialEffects.핏빛칼날)
                    System.out.print(" 핏빛칼날    ");
                else if(weapon.getSpecialEffect() == WeaponSpecialEffects.천벌)
                    System.out.print("천벌       ");
                else
                    System.out.print("            ");

                System.out.println(weapon.getRequirementLevel() + "        " + weapon.getPrice());
            }
        });

        System.out.println();
        System.out.println("[특수효과 설명]");
        System.out.println("약자멸시 : 체력이 40% 이하인 상대에게 상대 최대 체력의 약 8%에 해당하는 추가 피해를 1회 입힙니다.");
        System.out.println("핏빛칼날 : 자신이 입힌 데미지의 5%만큼 체력을 흡수합니다.");
        System.out.println("천벌 : 자신이 입힌 데미지에서 15%만큼 추가 데미지를 상대에게 입힙니다.");
        System.out.println("===========================================================================");
    }

    // 상점의 갑옷 목록을 보여주는 메소드(상점에서 갑옷 목록 조회를 위함)
    public void showProtectiveGearList() {
        System.out.println("===========================================================================");
        System.out.println("갑옷이름    특수효과   요구레벨    가격");

        protectiveGearList.stream().forEach(protectiveGear -> {
            System.out.print(protectiveGear.getName() + "    ");

            if(protectiveGear.getSpecialEffect() == ProtectiveGearSpecialEffects.질긴갑옷)
                System.out.print("  질긴갑옷    ");
            else if(protectiveGear.getSpecialEffect() == ProtectiveGearSpecialEffects.강철갑옷)
                System.out.print("  강철갑옷    ");
            else if(protectiveGear.getSpecialEffect() == ProtectiveGearSpecialEffects.금강)
                System.out.print("  금강       ");
            else if(protectiveGear.getSpecialEffect() == ProtectiveGearSpecialEffects.가시갑옷)
                System.out.print("  가시갑옷    ");
            else if(protectiveGear.getSpecialEffect() == ProtectiveGearSpecialEffects.신의가호)
                System.out.print("  신의가호    ");

            System.out.println(protectiveGear.getRequirementLevel() + "      " + protectiveGear.getPrice());
        });

        System.out.println();
        System.out.println("[특수효과 설명]");
        System.out.println("질긴갑옷 : 입은 피해를 " + ProtectiveGearSpecialEffects.질긴갑옷.getProtectRatio() + "%만큼 감소시킵니다.");
        System.out.println("강철갑옷 : 입은 피해를 " + ProtectiveGearSpecialEffects.강철갑옷.getProtectRatio() + "%민큼 감소시킵니다.");
        System.out.println("금강 : 입은 피해를 " + ProtectiveGearSpecialEffects.금강.getProtectRatio() + "%만큼 감소시킵니다.");
        System.out.println("가시갑옷 : 입은 피해를 " + ProtectiveGearSpecialEffects.가시갑옷.getProtectRatio() + "%만큼 감소시키고, 감소피해량만큼 적에게 피해를 추가로 입힙니다.");
        System.out.println("신의가호 : 입은 피해를 " + ProtectiveGearSpecialEffects.신의가호.getProtectRatio() + "%만큼 감소시키고, 감소피해량의 50%를 적에게 피해를 추가로 입히며, 감소된 피해량만큼 자신의 마나를 회복합니다.");
        System.out.println("===========================================================================");
    }

    // 상점의 소비 아이템 목록을 보여주는 메소드
    public void showPotionList() {
        System.out.println("===========================================================================");
        System.out.println("포션이름  가격   효과");

        potionList.stream().forEach(potion -> {
            System.out.print(potion.getName() + "    " + potion.getPrice() + "   ");

            if(potion.getName().equals("지하수"))
                System.out.println("체력 " + potion.getRecoveryQuantity() + " 회복");
            else if(potion.getName().equals("포비돈"))
                System.out.println("체력 " + potion.getRecoveryQuantity() + " 회복");
            else if(potion.getName().equals("토니켓"))
                System.out.println("체력 " + potion.getRecoveryQuantity() + " 회복");
            else if(potion.getName().equals("생명수"))
                System.out.println("체력 " + potion.getRecoveryQuantity() + " 회복");
            else if(potion.getName().equals("암반수"))
                System.out.println("마나 " + potion.getRecoveryQuantity() + " 회복");
            else if(potion.getName().equals("연양갱"))
                System.out.println("마나 " + potion.getRecoveryQuantity() + " 회복");
            else if(potion.getName().equals("우육포"))
                System.out.println("마나 " + potion.getRecoveryQuantity() + " 회복");
            else if(potion.getName().equals("활명수"))
                System.out.println("마나 " + potion.getRecoveryQuantity() + " 회복");
        });
        System.out.println("===========================================================================");
    }

    // 무기의 이름을 검색하는 메소드 (무기가 존재하면 무기를 반환, 무기가 존재하지 않으면 null 객체를 반환)
    public Weapon findWeaponByName(String weaponName) {
        Weapon searchedWeapon = null;

        for(Weapon weapon : weaponList) {
            if(weapon.getName().equals(weaponName))
                searchedWeapon = weapon;
        }
        
        return searchedWeapon;
    }
    
    // 무기가 존재하는지 여부를 반환하는 메소드(쇼핑 리스트에서의 검색기능)
    public boolean isExistWeapon(String weaponName) {
        boolean isExistWeapon = false;

        for(Weapon weapon : weaponList) {
            if(weapon.getName().equals(weaponName))
                isExistWeapon = true;
        }

        return isExistWeapon;
    }

    // 갑옷의 이름을 검색하는 메소드
    public ProtectiveGear findProtectiveGearByName(String protectiveGearName) {
        ProtectiveGear searchProtectiveGear = null;

        for(ProtectiveGear protectiveGear : protectiveGearList) {
            if(protectiveGear.getName().equals(protectiveGearName))
                searchProtectiveGear = protectiveGear;
        }

        return searchProtectiveGear;
    }

    // 갑옷이 존재하는지 여부를 반환하는 메소드(쇼핑 리스트에서의 검색)
    public boolean isExistProtectiveGear(String protectiveGearName) {
        boolean isExistProtectiveGear = false;

        for(ProtectiveGear protectiveGear : protectiveGearList) {
            if(protectiveGear.getName().equals(protectiveGearName))
                isExistProtectiveGear = true;
        }

        return isExistProtectiveGear;
    }

    // 포션의 이름을 검색하는 메소드
    public Potion findPotionByName(String potionName) {
        Potion searchPotion = null;

        for(Potion potion : potionList) {
            if(potion.getName().equals(potionName))
                searchPotion = potion;
        }

        return searchPotion;
    }

    // 포션이 존재하는지 여부를 반환하는 메소드
    public boolean isExistPotion(String potionName) {
        boolean isExistPotion = false;

        for(Potion potion : potionList) {
            if(potion.getName().equals(potionName))
                isExistPotion = true;
        }

        return isExistPotion;
    }
}
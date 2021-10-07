package com.company.game;

/** 현재 캐릭터가 하고 있는 행위(ex. 상점 방문, npc와의 대화, 사냥터)를 나타내는 enum class입니다.
 * TALKING_WITH_NPC : npc와의 대화 중인 상태(퀘스트 수용, 전직, 특수스킬 획득..)
 * SHOP : 상점 방문(소비 아이템, 무기, 방어구 구매 가능)
 * CHECK_STATUS : 캐릭터의 상태 확인(캐릭터의 능력치 정보, 착용중인 무기, 착용중인 방어구)
 * INVENTORY : 캐릭터의 인벤토리를 확인하는 중인 상태
 * RESTING : 딱히 아무것도 하고 있지 않은 상태
 * HUNTING : 캐릭터가 사냥터에 있는 상태
 * */
public enum CharacterBehavior {
    TALKING_WITH_NPC,
    SHOP,
    CHECK_STATUS,
    INVENTORY,
    RESTING,
    HUNTING
}

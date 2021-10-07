package com.company.game.npc.quest;

public enum QuestNames {
    들개_소탕(1, 3, "들개 소탕"), 파리지옥_박멸(4, 4, "파리지옥 박멸"),
    까마귀_소탕(6, 4, "까마귀 소탕"), 불청객이_싫어요(8, 5, "불청객이 싫어요"), 공포스러운_불청객(9, 5, "공포스러운 불청객"),
    끔찍한_비명(11, 5, "끔찍한 비명"), 얼음세상_개척1(12, 5, "얼음세상 개척1"), 얼음세상_개척2(14, 5, "얼음세상 개척2"),
    지옥의_관문(16, 5, "지옥의 관문"), 원한_풀어주기(18, 5, "원한 풀어주기"), 지옥_해방(19, 5, "지옥 해방"),
    저승의_무질서(22, 5, "저승의 무질서"), 마을_부적_만들기(23, 5, "마을 부적 만들기"), 저승_해방(24, 5, "저승 해방"),
    공허의_저항(26, 5, "공허의 저항"), 공허_청소(27, 5, "공허 청소"), 전략적_침투(28, 5, "전략적 침투"), 마을_구원(30, 10, "마을 구원");

    private int requirementLevel; // 퀘스트의 요구 레벨
    private int object; // 퀘스트의 목표 마리 수
    private String questName;

    // 생성자
    QuestNames(int requirementLevel, int object, String questName) {
        this.requirementLevel = requirementLevel;
        this.object = object;
        this.questName = questName;
    }

    public int getRequirementLevel() {
        return requirementLevel;
    }

    public int getObject() {
        return object;
    }

    // 기존의 toString method를 Override 하여서 사용
    @Override
    public String toString() {
        return questName;
    }
}

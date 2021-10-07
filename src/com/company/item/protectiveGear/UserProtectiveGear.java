package com.company.item.protectiveGear;

public class UserProtectiveGear {
    private ProtectiveGear protectiveGear;
    private int num = 0;

    public UserProtectiveGear(ProtectiveGear protectiveGear, int num) {
        this.protectiveGear = protectiveGear;
        this.num = num;
    }

    public ProtectiveGear getProtectiveGear() {
        return protectiveGear;
    }

    public void setProtectiveGear(ProtectiveGear protectiveGear) {
        this.protectiveGear = protectiveGear;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

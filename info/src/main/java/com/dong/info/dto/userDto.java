package com.dong.info.dto;

import com.dong.info.entity.User;

public class userDto extends User {

    private String modifyTime;

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }
}

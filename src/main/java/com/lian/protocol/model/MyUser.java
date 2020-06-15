package com.lian.protocol.model;

import lombok.Data;
import net.sf.json.JSONObject;

/**
 * @author Ted
 * @date 2020/6/15 14:45
 */
@Data
public class MyUser {
    private Long birthday;
    private String country;
    private String zoneCode;
    private String email;
    private String userName;
    private String mobile;
    private Integer gender;


    public static void main(String[] args) {
        MyUser myUser = new MyUser();
        myUser.setBirthday(801989587000L);
        myUser.setCountry("china");
        myUser.setZoneCode("86");
        myUser.setUserName("纷飞落叶");
        myUser.setMobile("123456789");
        myUser.setGender(1);
        myUser.setEmail("luoyefubuki@163.com");

        JSONObject jsonObject = JSONObject.fromObject(myUser);
        System.out.println(jsonObject.toString());
        System.out.println(jsonObject.toString().length());
    }
}



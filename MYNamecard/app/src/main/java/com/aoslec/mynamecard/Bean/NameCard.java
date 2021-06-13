package com.aoslec.mynamecard.Bean;

import java.util.Date;

public class NameCard {
    int namecardNo;
    Date insertDate;
    Date deleteDate;
    int favorite;
    int trashcan;
    int group_groupNo;
    String user_id;
    String namecardFilePath;
    String name;
    String company;
    String dept;
    String jobPosition;
    String mobile;
    String tel;
    String fax;
    String email;
    String address;
    String memo;
    Date updateDate;
    String groupName;

    // nameCardList
    public NameCard(int namecardNo, int group_groupNo, String namecardFilePath,
                    String name, String jobPosition, String groupName, String company,
                    String dept, String mobile, String tel, String fax, String email,
                    String address, String memo) {
        this.namecardNo = namecardNo;
        this.group_groupNo = group_groupNo;
        this.namecardFilePath = namecardFilePath;
        this.name = name;
        this.company = company;
        this.dept = dept;
        this.jobPosition = jobPosition;
        this.mobile = mobile;
        this.tel = tel;
        this.fax = fax;
        this.email = email;
        this.address = address;
        this.memo = memo;
        this.groupName = groupName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getNamecardNo() {
        return namecardNo;
    }

    public void setNamecardNo(int namecardNo) {
        this.namecardNo = namecardNo;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getTrashcan() {
        return trashcan;
    }

    public void setTrashcan(int trashcan) {
        this.trashcan = trashcan;
    }

    public int getGroup_groupNo() {
        return group_groupNo;
    }

    public void setGroup_groupNo(int group_groupNo) {
        this.group_groupNo = group_groupNo;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNamecardFilePath() {
        return namecardFilePath;
    }

    public void setNamecardFilePath(String namecardFilePath) {
        this.namecardFilePath = namecardFilePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}

package com.example.linkopener.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pet")
public class Pet {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "p_id")
    private Integer pId;

    @ColumnInfo(name = "owner_id")
    private Integer ownerId;

    @ColumnInfo(name = "nickname")
    private String nickname;

    @ColumnInfo(name = "species")
    private String species;

    public Integer getPId() {
        return pId;
    }

    public void setPId(Integer pId) {
        this.pId = pId;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
}

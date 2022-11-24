package com.example.linkopener.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ticket")
public class Ticket {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "t_id")
    private Integer tId;

    @ColumnInfo(name = "pet_id")
    private Integer petId;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "checked")
    private Boolean checked;

    public Integer getTId() {
        return tId;
    }

    public void setTId(Integer tId) {
        this.tId = tId;
    }

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}

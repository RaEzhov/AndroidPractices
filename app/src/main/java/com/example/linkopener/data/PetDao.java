package com.example.linkopener.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Map;

@Dao
public interface PetDao {

    @Query("SELECT * FROM pet WHERE pet.owner_id == :ownerId")
    List<Pet> getAllByOwnerId(Integer ownerId);

    @Query("SELECT * FROM pet JOIN user ON pet.owner_id = user.u_id")
    Map<Pet, User> getAllWithUser();

    @Insert
    void insert(Pet pet);

    @Update
    void update(Pet pet);
}

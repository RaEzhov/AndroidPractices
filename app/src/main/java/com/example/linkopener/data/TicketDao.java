package com.example.linkopener.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.Map;

@Dao
public interface TicketDao {

    @Query("SELECT * FROM ticket WHERE ticket.t_id == :tId")
    Ticket getById(String tId);

    @Query("SELECT * FROM ticket JOIN pet ON ticket.pet_id = pet.p_id")
    Map<Ticket, Pet> getAllWithPet();

    @Insert
    void insert(Ticket user);

    @Delete
    void delete(Ticket user);

    @Update
    void update(Ticket user);
}

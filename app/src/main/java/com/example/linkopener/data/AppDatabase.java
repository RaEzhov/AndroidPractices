package com.example.linkopener.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class, Pet.class, Ticket.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract PetDao petDao();

    public abstract TicketDao ticketDao();
}

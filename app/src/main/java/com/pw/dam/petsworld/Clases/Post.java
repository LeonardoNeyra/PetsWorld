package com.pw.dam.petsworld.Clases;

/**
 * Created by Leonardo on 21/06/2018.
 */

public class Post {
   public Mascota mascota;
   public String user;
   public String id;

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

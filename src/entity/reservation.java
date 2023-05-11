/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.sql.Date;
import java.time.LocalDate;

/**
 *
 * @author zakar
 */
public class reservation {
    
    private int id_res;
    private LocalDate date_res;
    private evenement id_event;
    private user id_user1;

    public reservation(int id_res, LocalDate date_res, evenement id_event, user id_user1) {
        this.id_res = id_res;
        this.date_res = date_res;
        this.id_event = id_event;
        this.id_user1 = id_user1;
    }

    public reservation(LocalDate date_res, evenement id_event, user id_user1) {
        this.date_res = date_res;
        this.id_event = id_event;
        this.id_user1 = id_user1;
    }

    public reservation() {
    }
    

   

    public int getId_res() {
        return id_res;
    }

    public void setId_res(int id_res) {
        this.id_res = id_res;
    }

    public evenement getId_event() {
        return id_event;
    }

    public LocalDate getDate_res() {
        return date_res;
    }

    public void setDate_res(LocalDate date_res) {
        this.date_res = date_res;
    }

    public user getId_user1() {
        return id_user1;
    }

    public void setId_user1(user id_user1) {
        this.id_user1 = id_user1;
    }

    public void setId_event(evenement id_event) {
        this.id_event = id_event;
    }

    @Override
    public String toString() {
        return "reservation{" + "id_res=" + id_res + ", date_res=" + date_res + ", id_event=" + id_event + ", id_user1=" + id_user1 + '}';
    }

   public void setUser(user u) {
    this.id_user1 = u;
}


    public void setEvenement(evenement e) {
        this.id_event = e;
    }


    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;



public class reactions {
    private int id;
    private annonce id_annonce;
    private int id_user;
    private String type_react;

    public reactions() {
    }

    public reactions(int id, annonce id_annonce, int id_user, String type_react) {
        this.id = id;
        this.id_annonce = id_annonce;
        this.id_user = id_user;
        this.type_react = type_react;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public annonce getId_annonce() {
        return id_annonce;
    }

    public void setId_annonce(annonce id_annonce) {
        this.id_annonce = id_annonce;
    }

  

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getType_react() {
        return type_react;
    }

    public void setType_react(String type_react) {
        this.type_react = type_react;
    }


}
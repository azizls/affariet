/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.services;


import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Evenement;
import com.mycompany.utils.Statics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
/**
 *
 * @author ASUS
 */
public class ServiceEvenement {
    
    public static ServiceEvenement instance = null;
    
    public static boolean resultOk = true;
    
    private ConnectionRequest req;
    
    public static ServiceEvenement getInstance(){
        if(instance == null)
            instance = new ServiceEvenement();
        return instance;            
    }
    
    public ServiceEvenement(){
        req = new ConnectionRequest();
    }
    
    public void AjouterEvenement(Evenement e){

        String nomEvent= e.getNomEvent();
        String emplacement= e.getEmplacement();
        String descriptionEvent=e.getDescriptionEvent();
        int prix = e.getPrix();
        String affiche = e.getAffiche();
        int nbrMax = e.getNbrMax();

        String url = Statics.BASE_URL+"/evenement/addEvent/"+nomEvent+"/"+emplacement+"/"+descriptionEvent+"/"+prix+"/"+affiche+"/"+nbrMax+"/";
        ConnectionRequest req = new ConnectionRequest(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        
    }
    
    
   
    
    
    
    public ArrayList<Evenement>afficherEvenement(){
        ArrayList<Evenement> result = new ArrayList<>();
        
        String url = Statics.BASE_URL+"/evenement/listEvents/aa";
        req.setUrl(url);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt){
                JSONParser jsonp;
                jsonp = new JSONParser();
                
                try{
                    Map<String,Object> mapMatchs = jsonp.parseJSON(new CharArrayReader(new String(req.getResponseData()).toCharArray()));
                    List<Map<String,Object>> listofmaps = (List<Map<String,Object>>) mapMatchs.get("root");
                    for (Map<String,Object> obj : listofmaps){
                        Evenement e = new Evenement();
                        
                        float id =Float.parseFloat(obj.get("idEvent").toString());
                        String nom_event=obj.get("nomEvent").toString();
                        String emplacement=obj.get("emplacement").toString();
                        String description_event = obj.get("descriptionEvent").toString();
                        String affiche = obj.get("affiche").toString();
                        float prix =Float.parseFloat(obj.get("prix").toString());
                        float nbr =Float.parseFloat(obj.get("nbrMax").toString());


    
                        
                        
                        e.setIdEvent((int) id);
                        e.setNomEvent(nom_event);
                        e.setEmplacement(emplacement);
                        e.setDescriptionEvent(description_event);
                        e.setAffiche(affiche);
                        e.setNbrMax((int)nbr);
                       e.setPrix((int)prix);
                       
                        
                        result.add(e);
                        
                       
                    }
                }catch(Exception ex){
                    ex.printStackTrace();
                }
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return result;
    }    
    
    public boolean supprimerEvenement(int id ) {
        String url = Statics.BASE_URL +"/evenement/supprimerE/"+id;
        
        ConnectionRequest req = new ConnectionRequest(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
        return  resultOk;
    }
    
    //Update 
    public boolean modifierEvenement(Evenement e) {
        String url = Statics.BASE_URL +"/evenement/updateEvent/"+e.getIdEvent()+"/"+e.getNomEvent()+"/"+e.getEmplacement()+"/"+e.getDescriptionEvent()+"/"+e.getPrix()+"/"+e.getAffiche()+"/"+e.getNbrMax();
        ConnectionRequest req = new ConnectionRequest(url);
        req.setPost(false);

        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOk = req.getResponseCode() == 200;
                req.removeResponseListener(this);
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(req);
    return resultOk;
        
    }
    
}

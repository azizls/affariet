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
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.Commande;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.mycompany.entities.Produit;
import java.util.Date;


/**
 *
 * @author bhk
 */
public class ServiceCommande {

    public ArrayList<Commande> tasks;

    public static ServiceCommande instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceCommande() {
        req = new ConnectionRequest();
    }

    public static ServiceCommande getInstance() {
        if (instance == null) {
            instance = new ServiceCommande();
        }
        return instance;
    }

  

    public ArrayList<Commande> parseTasks(String jsonText) {
    try {
        tasks = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
        for (Map<String, Object> obj : list) {
            Commande t = new Commande();
            float id = Float.parseFloat(obj.get("id").toString());
            t.setId_commande((int) id);

            // Extract the Produit data
            
            float iduser = Float.parseFloat(obj.get("user").toString());
            t.setUser((int) iduser);
                
                
                t.setNom_produit((String) obj.get("nom_produit"));
                t.setChargeId((String) obj.get("chargeId"));
                float prix = Float.parseFloat(obj.get("prix_produit").toString());
                t.setPrix_produit((int)prix);
                
            

            // Set the Date object for the Commande
            String dateStr = (String) obj.get("date_commande");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            t.setDate_commande(format.parse(dateStr));
             System.out.println(t);
            tasks.add(t);
        }

    } catch (IOException | ParseException ex) {
        System.out.println(ex.getMessage());
    }
    return  tasks;
}

  
  public boolean addCommande(Commande t) {

       int  produit = t.getId_produit();
       String charge_id =  t.getChargeId();
              //String url = Statics.BASE_URL + "/commande/addjson?produit=" + t.getName() + "&status=" + t.getStatus();
        String url = Statics.BASE_URL + "/commande/addjson/json?produit=" + produit + "&charge_id=" + charge_id;

        req.setUrl(url);
        req.setPost(false);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

   public ArrayList<Commande> getAllCommandes() {
    String url = Statics.BASE_URL + "/commande/showjson/";
    req.setUrl(url);
    req.setPost(false);
    
   
    
    req.addResponseListener(new ActionListener<NetworkEvent>() {
        @Override
        public void actionPerformed(NetworkEvent evt) {
            tasks = parseTasks(new String(req.getResponseData()));
            System.out.println("tasks is null: " + (tasks == null));

            req.removeResponseListener(this);
        }
    });
    
    NetworkManager.getInstance().addToQueueAndWait(req);
    
    return tasks;
}

}

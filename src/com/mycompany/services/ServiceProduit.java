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
public class ServiceProduit {

    public ArrayList<Produit> tasks;

    public static ServiceProduit instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServiceProduit() {
        req = new ConnectionRequest();
    }

    public static ServiceProduit getInstance() {
        if (instance == null) {
            instance = new ServiceProduit();
        }
        return instance;
    }

  

    public ArrayList<Produit> parseTasks(String jsonText) {
    try {
        tasks = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");
        for (Map<String, Object> obj : list) {
            Produit t = new Produit();
            float id = Float.parseFloat(obj.get("id_produit").toString());
            t.setId_produit((int) id);

            // Extract the Produit data
            
            
                
                
                t.setNom_produit((String) obj.get("nom_produit"));
                t. setDescription_produit((String) obj.get("description_produit"));
                float prix = Float.parseFloat(obj.get("prix_produit").toString());
                t.setPrix_produit((int)prix);
                
            

            // Set the Date object for the Commande
            
             System.out.println(t);
            tasks.add(t);
        }

    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return  tasks;
}

  
  

   public ArrayList<Produit> getAllProduits() {
    String url = Statics.BASE_URL + "/produit/showjson/json";
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

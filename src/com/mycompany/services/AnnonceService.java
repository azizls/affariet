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
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionListener;
import com.mycompany.entities.annonce;
//import static com.mycompany.services.AnnonceServic;
import com.mycompany.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;

import java.util.List;
import java.util.Map;





public class AnnonceService {
    
    public static boolean resultOk = true;
  


    
    
    private ArrayList<annonce> annonces;
    public static AnnonceService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    public AnnonceService() {
        req = new ConnectionRequest();
    }

    public static AnnonceService getInstance() {
        if (instance == null) {
            instance = new AnnonceService();
        }
        return instance;
    }

    public void addAnnonce(annonce a ,int idUser) {
           String type= a.getType();
        String description= a.getDescription();
        
      

        String url = Statics.BASE_URL+"/api/annonces/add?type="+type+"&description="+description+"&idUser="+idUser;
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

public ArrayList<annonce> parseAnnonces(String jsonText) {
    try {
        annonces = new ArrayList<>();
        JSONParser j = new JSONParser();
        Map<String, Object> annoncesListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

        List<Map<String, Object>> list = (List<Map<String, Object>>) annoncesListJson.get("root");
       for (Map<String, Object> obj : list) {
    annonce a = new annonce();
    float id = Float.parseFloat(obj.get("idAnnonce").toString());
    a.setId_annonce((int) id);
    a.setType(obj.get("type").toString());
    a.setDescription(obj.get("description").toString());
   
    annonces.add(a);
}

    } catch (IOException ex) {
        System.out.println(ex.getMessage());
    }
    return annonces;
}





    public ArrayList<annonce> getAllAnnonces() {
        String url = Statics.BASE_URL + "/api/annonces";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                annonces = parseAnnonces(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return annonces;
    }
    
    
    
    public boolean deleteannonce(int idAnnonce ) {
        String url = Statics.BASE_URL +"/api/annonces/delete/"+idAnnonce;
        
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
}

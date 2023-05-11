/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import entity.annonce;
import java.util.List;

/**
 *
 * @author lando
 * @param <T>
 */
public interface IService<T> {
    void insert(T t);
    void delete(T t);
    void update(T t);
    List<T> readAll();
    //List<T> readAll(annonce id_annonce);
    T readById(int id);
    T get_annonce(int id);


    
}

package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.ToDo;

import java.util.List;

public interface TodoDao {

    void add(ToDo todo) throws DaoException;

    void update(ToDo todo) throws DaoException;

    void delete(int id) throws DaoException;


    List<ToDo> findAll();

    ToDo findById(int id);
}

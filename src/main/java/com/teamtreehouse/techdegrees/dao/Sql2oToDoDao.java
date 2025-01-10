package com.teamtreehouse.techdegrees.dao;

import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.ToDo;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

public class Sql2oToDoDao implements TodoDao{

    private final Sql2o sql2o;

    public Sql2oToDoDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }


    @Override
    public void add(ToDo todo) throws DaoException {
        String sql = "INSERT INTO tododb(name, is_completed) VALUES (:name, :is_completed)";
        try (Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql)
                    .addParameter("name", todo.getName())
                    .addParameter("is_completed", todo.getIsCompleted())
                    .executeUpdate()
                    .getKey();
            todo.setId(id);

        } catch (Sql2oException ex){
            throw new DaoException(ex, "Problem adding toDo");
        }

    }

    @Override
    public void update(ToDo todo) throws DaoException {
        String sql = "UPDATE tododb SET name = :name, is_completed = :is_completed WHERE id = :id";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("name",todo.getName())
                    .addParameter("is_completed",todo.getIsCompleted())
                    .addParameter("id",todo.getId())
                    .executeUpdate()
                    .getResult();

        }catch (Sql2oException ex){
            throw new DaoException(ex, "Problem updating toDo");
        }
    }

    @Override
    public void delete(int id) throws DaoException{
        String sql = "DELETE FROM tododb WHERE id = :id";
        try (Connection con = sql2o.open()){
            con.createQuery(sql)
                    .addParameter("id",id)
                    .executeUpdate()
                    .getResult();

        }catch (Sql2oException ex){
            throw  new DaoException(ex, "Problem deleting toDo");
        }

    }

    @Override
    public List<ToDo> findAll() {
        try (Connection con = sql2o.open()){
            return con.createQuery("SELECT id, name, is_completed AS isCompleted FROM tododb")
                    .executeAndFetch(ToDo.class);

        }
    }

    @Override
    public ToDo findById(int id) {
        try(Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM tododb WHERE id = :id")
                    .addParameter("id", id)
                    .executeAndFetchFirst(ToDo.class);

        }
    }
}

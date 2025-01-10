package com.teamtreehouse.todo.dao;

import com.teamtreehouse.techdegrees.dao.Sql2oToDoDao;
import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.ToDo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class Sql2oToDoDaoTest {
    private Sql2oToDoDao dao;
    private Connection conn;

    @BeforeEach
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/init.sql'";
        Sql2o sql2o = new Sql2o(connectionString,"","");
        dao = new Sql2oToDoDao(sql2o);
        conn = sql2o.open();
    }

    @AfterEach
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingToDoSetsId() throws Exception {
        ToDo todo = newTestToDo();
        int originalToDoId = todo.getId();

        dao.add(todo);
        assertNotEquals(originalToDoId, todo.getId());
    }

    @Test
    void addedToDoAreReturnedFromFindAll() throws DaoException {
        ToDo toDo = newTestToDo();
        dao.add(toDo);
        assertEquals(1,dao.findAll().size());
    }
    
    private static ToDo newTestToDo() {
        return new ToDo(true, "Dance");
    }


}

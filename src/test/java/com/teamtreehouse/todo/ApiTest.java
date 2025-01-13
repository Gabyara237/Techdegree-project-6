package com.teamtreehouse.todo;

import com.google.gson.Gson;
import com.teamtreehouse.techdegrees.App;
import com.teamtreehouse.techdegrees.dao.Sql2oToDoDao;
import com.teamtreehouse.techdegrees.exc.DaoException;
import com.teamtreehouse.techdegrees.model.ToDo;
import com.teamtreehouse.testing.ApiClient;
import com.teamtreehouse.testing.ApiResponse;
import org.junit.jupiter.api.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import spark.Spark;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTest {
    public static final String PORT = "4568";
    public static final String TEST_DATASOURCE = "jdbc:h2:mem:testing";
    private Connection coon;
    private ApiClient client;
    private Gson gson;
    private Sql2oToDoDao todoDao;

    @BeforeAll
    static void startServer() {
        String[] args = {PORT, TEST_DATASOURCE};
        App.main(args);
    }

    @AfterAll
    static void stopServer() {
        Spark.stop();
    }

    @BeforeEach
    void setUp() throws Exception {
        Sql2o sql2o = new Sql2o(TEST_DATASOURCE + ";INIT=RUNSCRIPT from 'classpath:db/init.sql'", "", "");
        todoDao = new Sql2oToDoDao(sql2o);
        coon = sql2o.open();
        client = new ApiClient("http://localhost:" + PORT);
        gson = new Gson();
    }

    @AfterEach
    void tearDown() {
        coon.close();
    }

    @Test
    void addingToDoReturnsCreatedStatus() {
        Map<Boolean, String> values = new HashMap<>();
        values.put(true, "Test");
        ApiResponse res = client.request("POST", "/api/v1/todos", gson.toJson(values));

        assertEquals(201, res.getStatus());
    }

    @Test
    void deletingToDoReturnDeletedStatus() throws DaoException {

        ToDo toDo = newTestToDo();
        todoDao.add(toDo);
        ApiResponse res = client.request("DELETE", "/api/v1/todos/" + toDo.getId());

        assertEquals(204, res.getStatus());
    }

    @Test
    void updatingToDoReturnEditedStatus() throws DaoException {
        ToDo toDo = newTestToDo();
        todoDao.add(toDo);
        Map<String, Object> values = new HashMap<>();
        values.put("name", "UpdatingTest");
        values.put("isCompleted", false);
        ApiResponse res = client.request("PUT", "/api/v1/todos/" + toDo.getId(), gson.toJson(values));

        assertEquals(200, res.getStatus());
    }

    private static ToDo newTestToDo() {
        return new ToDo(true, "Dance");
    }
}

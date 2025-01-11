package com.teamtreehouse.techdegrees;


import com.google.gson.Gson;
import com.teamtreehouse.techdegrees.dao.Sql2oToDoDao;
import com.teamtreehouse.techdegrees.dao.TodoDao;
import com.teamtreehouse.techdegrees.exc.ApiError;
import com.teamtreehouse.techdegrees.model.ToDo;
import org.sql2o.Sql2o;

import static spark.Spark.*;

public class App {

    public static void main(String[] args) {
        staticFileLocation("/public");

        String datasource = "jdbc:h2:~/tododb.db";
        if(args.length > 0){
            if (args.length !=2){
                System.out.println("java Api <port> <datasource>");
                System.exit(0);
            }
            port(Integer.parseInt(args[0]));
            datasource = args[1];
        }

        Sql2o sql2o = new Sql2o(
                String.format("%s;INIT=RUNSCRIPT from 'classpath:db/init.sql'", datasource)
                ,"","");
        TodoDao todoDao =  new Sql2oToDoDao(sql2o);
        Gson gson = new Gson();

        get("/api/v1/todos", "application/json", (req, resp) -> todoDao.findAll(),gson::toJson);


        post("/api/v1/todos", "application/json",(req, res) ->{

            ToDo toDo = gson.fromJson(req.body(), ToDo.class);
            todoDao.add(toDo);
            res.status(201);
            return toDo;
        }, gson::toJson);


        put("/api/v1/todos/:id", "application/json",(req, res) -> {

            int id = Integer.parseInt(req.params("id"));
            ToDo newData;

            ToDo toDo = todoDao.findById(id);

            if ( toDo == null){
                throw new ApiError(404,"Could not find toDo with id: " + id);
            }

            newData = gson.fromJson(req.body(),ToDo.class);

            if(newData.getName() !=null){
              toDo.setName(newData.getName());
            }
            toDo.setCompleted(newData.getIsCompleted());

            todoDao.update(toDo);

            res.status(200);
            return toDo;

        },gson::toJson);

    }

}

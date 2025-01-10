package com.teamtreehouse.techdegrees;


import com.google.gson.Gson;
import com.teamtreehouse.techdegrees.dao.Sql2oToDoDao;
import com.teamtreehouse.techdegrees.dao.TodoDao;
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


    }

}

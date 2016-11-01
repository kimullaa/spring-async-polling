package com.example.controllers;

import com.example.Task;
import com.example.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/tasks")
public class TaskRestController {
    @Autowired
    TaskService service;

    @GetMapping
    public List<Task> showAll(){
        return service.status();
    }

    @PostMapping
    public void execute() {
        int id = service.register();
        service.execute(id);
    }

}

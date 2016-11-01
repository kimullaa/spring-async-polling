package com.example.services;

import com.example.Task;
import com.example.model.RequestBean;
import com.example.model.SessionBean;
import com.example.repositories.TaskMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class TaskServiceImpl implements TaskService {
    final TaskMapper taskMapper;
    final ExecuteService service;
    final RequestBean request;
    final SessionBean session;

    final Random random = new Random(System.currentTimeMillis());

    @Override
    @Transactional
    public Task register() {
        Task task = new Task();
        task.setAmount(random.nextInt(10));
        taskMapper.insert(task);

        return task;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> status() {
        return taskMapper.findAll();
    }

    @Override
    @Async
    public void execute(int id) {
        //Threadに紐づく値は利用できない
//        log.info(session.toString());
//        log.info(request.toString());
        Task task = taskMapper.findOne(id);
        log.info(task.toString());

        // 1sごとにtaskを1こずつ消化していく
        while (task.getDone() < task.getAmount()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            service.execute(task);
        }
    }
}

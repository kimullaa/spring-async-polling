package com.example.services;

import com.example.Task;
import com.example.repositories.TaskMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskMapper taskMapper;
    Random random = new Random(System.currentTimeMillis());

    @Override
    public int register() {
        Task task = new Task();
        task.setAmount(random.nextInt(10));
        taskMapper.insert(task);

        return task.getId();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Task> status() {
        return taskMapper.findAll();
    }

    @Override
    @Async
    public void execute(int id) {
        Task task = taskMapper.findOne(id);
        log.info(task.toString());

//        // 1sごとにtaskを1こずつ消化していく
        while (task.getDone() < task.getAmount()) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            task.setDone(task.getDone() + 1);
            taskMapper.update(task);
            log.info(task.toString());
        }
    }
}

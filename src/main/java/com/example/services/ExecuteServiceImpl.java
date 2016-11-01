package com.example.services;

import com.example.Task;
import com.example.repositories.TaskMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class ExecuteServiceImpl implements ExecuteService {
    final TaskMapper taskMapper;

    @Override
    @Transactional
    public void execute(Task task) {
        task.setDone(task.getDone() + 1);
        taskMapper.update(task);
        log.info(task.toString());
    }
}

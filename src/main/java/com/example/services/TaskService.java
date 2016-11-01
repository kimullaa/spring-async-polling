package com.example.services;

import com.example.Task;

import java.util.List;

public interface TaskService {
    /**
     * 1から10までのタスクを設定し、タスクIDを返す
     * @return タスクID
     */
    public int register();

    /**
     * 与えられたタスクIDを非同期に実行する
     * 1sごとに1タスク消化する
     * @param id タスクID
     */
    public void execute(int id);

    /**
     * 全てのタスクを返す
     * @return タスク一覧
     */
    public List<Task> status();
}

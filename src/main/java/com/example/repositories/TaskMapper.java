package com.example.repositories;

import com.example.Task;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Insert("INSERT INTO TASKS (amount,done) VALUES (#{task.amount},0)")
    @SelectKey(statement = "CALL IDENTITY()", keyProperty = "task.id", before = false, resultType = int.class)
    public int insert(@Param("task") Task task);

    @Update("UPDATE TASKS SET done = #{task.done} WHERE id = #{task.id}")
    public int update(@Param("task")Task task);

    @Select("SELECT * FROM TASKS WHERE id =#{id}")
    public Task findOne(@Param("id") int id);

    @Select("SELECT * FROM TASKS")
    public List<Task> findAll();
}

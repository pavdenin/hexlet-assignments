package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskMapper taskMapper;

    @GetMapping("")
    public List<TaskDTO> getTaskList() {
        var tasks = taskRepository.findAll();
        return tasks.stream().map(t -> taskMapper.map(t)).toList();
    }

    @GetMapping("/{id}")
    public TaskDTO getTask(@PathVariable Long id) {
        Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " is not found"));

        return taskMapper.map(task);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO createTask(@Valid @RequestBody TaskCreateDTO newTask) {
        Task task = taskRepository.save(taskMapper.map(newTask));
        return taskMapper.map(task);
    }

    @PutMapping("/{id}")
    public TaskDTO updateTask(@Valid @RequestBody TaskUpdateDTO updatedTask, @PathVariable Long id) {
        Task task = taskRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Task with id " + id + " is not found"));
        taskMapper.update(updatedTask, task);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        taskRepository.deleteById(id);
    }
    // END
}

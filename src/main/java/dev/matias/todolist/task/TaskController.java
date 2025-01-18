package dev.matias.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.matias.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping("/")
    public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        if (taskModel.getStartAt() == null || taskModel.getEndDate() == null) {
            return ResponseEntity.badRequest().body("Start date and end date are required.");
        }

        var idUser = request.getAttribute("idUser");
        taskModel.setIdUser((UUID) idUser);

        LocalDateTime currentDate = LocalDateTime.now();

        // Verification vaariables

        Boolean startIsBefore = currentDate.isAfter(taskModel.getStartAt());
        Boolean endIsBefore = currentDate.isAfter(taskModel.getEndDate());
        Boolean endIsBeforeOfStart = taskModel.getStartAt().isAfter(taskModel.getEndDate());

        if (startIsBefore || endIsBefore) {
            return ResponseEntity.badRequest().body(
                    "You can't attribute a past date.");
        }

        if (endIsBeforeOfStart) {
            return ResponseEntity.badRequest().body(
                    "You can't finish a task with a past date.");
        }

        var task = this.taskRepository.save(taskModel);
        return ResponseEntity.ok().body(task);
    }

    @GetMapping("/")
    public List<TaskModel> list(HttpServletRequest request) {
        var idUser = request.getAttribute("idUser");
        return this.taskRepository.findByIdUser((UUID) idUser);
    }

    // Path variable
    @PutMapping("/{id}")
    public TaskModel update(@RequestBody TaskModel taskModel, @PathVariable UUID id, HttpServletRequest request) {

        var task = this.taskRepository.findById(id).orElse(null);

        Utils.copyNonNullProperties(taskModel, task);

        var saved = this.taskRepository.save(task);
        return saved;
    }
}

package com.uplog.uplog.domain.task.api;

import com.uplog.uplog.domain.member.api.TestController;
import com.uplog.uplog.domain.task.application.TaskService;
import com.uplog.uplog.domain.task.dto.TaskDTO.*;
import com.uplog.uplog.domain.task.model.Task;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final TaskService taskService;

    //나중에 토큰 구현하면 bareer로 받을 예정이라 일단 pathvariable로 뺌
    @PostMapping(value="/tasks/{member_id}")
    public ResponseEntity<TaskInfoDTO> createTask(@PathVariable(name = "member_id") Long id,@RequestBody CreateTaskRequest createTaskRequest) {
        Task createdTask = taskService.createTask(id,createTaskRequest);
        TaskInfoDTO taskInfoDTO = createdTask.toTaskInfoDTO();
        return new ResponseEntity<>(taskInfoDTO, HttpStatus.CREATED);
    }

    //조회
    @GetMapping("/tasks/{task_id}")
    public ResponseEntity<TaskInfoDTO> findTaskById(@PathVariable(name="task_id") Long id) {
        Task task = taskService.findTaskById(id);
        TaskInfoDTO taskInfoDTO = task.toTaskInfoDTO();
        return ResponseEntity.ok(taskInfoDTO);
    }

//    //수정
//    @PutMapping("/task/{id}")
//    public ResponseEntity<TaskInfoDTO> updateTask(@PathVariable Long id, @RequestBody UpdateTaskData updateTaskData) {
//        Task updatedTask = taskService.updateTask(id,updateTaskData);
//        TaskInfoDTO taskInfoDTO = updatedTask.toTaskInfoDTO();
//        return ResponseEntity.ok(taskInfoDTO);
//    }



    //수정부분에서 path에 task_id가 있어서 dto에 id빼도 되는데 혹시 몰라서 일단 넣어둠
    //이름 수정
    @PatchMapping("/tasks/{task_id}/title")
    public ResponseEntity<TaskInfoDTO> updateTaskName(@PathVariable(name="task_id") Long id, @RequestBody UpdateTaskNameRequest updateTaskNameRequest) {
        Task updatedTask = taskService.updateTaskName(id,updateTaskNameRequest);
        TaskInfoDTO taskInfoDTO = updatedTask.toTaskInfoDTO();
        return ResponseEntity.ok(taskInfoDTO);
    }

    //날짜 수정(시작날짜,종료날짜)
    @PatchMapping("/tasks/{task_id}/date")
    public ResponseEntity<TaskInfoDTO> updateTaskDate(@PathVariable(name="task_id") Long id, @RequestBody UpdateTaskDateRequest updateTaskDateRequest) {
        Task updatedTask = taskService.updateTaskDate(id,updateTaskDateRequest);
        TaskInfoDTO taskInfoDTO = updatedTask.toTaskInfoDTO();
        return ResponseEntity.ok(taskInfoDTO);
    }

    //상세 내용 수정
    @PatchMapping("/tasks/{task_id}/content")
    public ResponseEntity<TaskInfoDTO> updateTaskContent(@PathVariable(name="task_id") Long id, @RequestBody UpdateTaskContentRequest updateTaskContentRequest) {
        Task updatedTask = taskService.updateTaskContent(id,updateTaskContentRequest);
        TaskInfoDTO taskInfoDTO = updatedTask.toTaskInfoDTO();
        return ResponseEntity.ok(taskInfoDTO);
    }

    //target멤버 수정
    @PatchMapping("/tasks/{task_id}/target-Member")
    public ResponseEntity<TaskInfoDTO> updateTaskStatus(@PathVariable(name="task_id") Long id, @RequestBody UpdateTaskMemberRequest updateTaskMemberRequest) {
        Task updatedTask = taskService.updateTaskMember(id,updateTaskMemberRequest);
        TaskInfoDTO taskInfoDTO = updatedTask.toTaskInfoDTO();
        return ResponseEntity.ok(taskInfoDTO);
    }

    //Menu수정
    @PatchMapping("/tasks/{task_id}/menu")
    public ResponseEntity<TaskInfoDTO> updateTaskMenu(@PathVariable(name="task_id") Long id, @RequestBody UpdateTaskMenuRequest updateTaskMenuRequest) {
        Task updatedTask = taskService.updateTaskMenu(id,updateTaskMenuRequest);
        TaskInfoDTO taskInfoDTO = updatedTask.toTaskInfoDTO();
        return ResponseEntity.ok(taskInfoDTO);
    }


    //테스크 팀 수정
    @PatchMapping("/tasks/{task_id}/taskTeam")
    public ResponseEntity<TaskInfoDTO> updateTaskTeam(@PathVariable(name="task_id") Long id, @RequestBody UpdateTaskTeamRequest updateTaskTeamRequest) {
        Task updatedTask = taskService.updateTaskProjectTeam(id,updateTaskTeamRequest);
        TaskInfoDTO taskInfoDTO = updatedTask.toTaskInfoDTO();
        return ResponseEntity.ok(taskInfoDTO);
    }


    //상태 수정
    @PatchMapping("/tasks/{task_id}/status")
    public ResponseEntity<TaskInfoDTO> updateTaskStatus(@PathVariable(name="task_id") Long id, @RequestBody UpdateTaskStatusRequest UpdateTaskStatusRequest) {
        Task updatedTask = taskService.updateTaskStatus(id,UpdateTaskStatusRequest);
        TaskInfoDTO taskInfoDTO = updatedTask.toTaskInfoDTO();
        return ResponseEntity.ok(taskInfoDTO);
    }

    //삭제
    @DeleteMapping("/tasks/{task_id}")
    public ResponseEntity<Void> deleteTask(@PathVariable(name="task_id") Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    //menu id status task list (query stirng)
    ///menus/{menu-id}/task?status=/tasks


    //target member menu id task list
    //members/{member-id}/menus/{menu-id}/tasks


    //projectTeam id menu id task list
    //project-teams/{project-tema-id}/menus/{menu- id}/tasks


}

package com.uplog.uplog.domain.task.dto;

import com.uplog.uplog.domain.member.dto.MemberDTO;
import com.uplog.uplog.domain.member.model.Member;
import com.uplog.uplog.domain.menu.dto.MenuDTO;
import com.uplog.uplog.domain.task.model.Task;
import com.uplog.uplog.domain.task.model.TaskStatus;
import com.uplog.uplog.domain.menu.model.Menu;
import com.uplog.uplog.domain.team.model.ProjectTeam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TaskDTO {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateTaskRequest{
        private String taskName;
        private Long menuId;
        private Long projectTeamId;
        private String taskDetail;
        private LocalDateTime startTime;
        private LocalDateTime endTime;

        public Task toEntity(Member targetMember,Menu menu, ProjectTeam projectTeam) {
            return Task.builder()
                    .targetMember(targetMember)
                    .menu(menu)
                    .taskStatus(TaskStatus.PROGRESS_BEFORE)
                    .projectTeam(projectTeam)
                    .taskDetail(taskDetail)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build();
        }
    }

//    @Getter
//    @Builder
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class TaskSimpleDTO{
//        private Long id;
//        private String taskName;
//        private S
//
//
//    }



    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TaskInfoDTO{
        private Long id;
        private String taskName;
        private MemberDTO.PowerMemberInfoDTO targetMemberInfoDTO;
        //private Long targetMemberId;
        //private String targetmemberName;
        private Long menuId;
        private String menuName;
        private Long projectTeamId;
        private String projectTeamName;
        private TaskStatus taskStatus;
        private String taskDetail;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }



    @Getter
//    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTaskData{
        private Long id;
        private String taskName;
        private Member targetmember;
        private Menu menu;
        private ProjectTeam projectTeam;
        private TaskStatus taskStatus;
        private String taskDetail;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTaskNameRequest{
        private Long id;
        private String updatetaskName;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTaskDateRequest{
        private Long id;
        private LocalDateTime updateStartTime;
        private LocalDateTime updateEndTime;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTaskMenuRequest{
        private Long id;
        private Menu updateMenu;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTaskTeamRequest{
        private Long id;
        private ProjectTeam updateTeam;

    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTaskMemberRequest{
        private Long id;
        private Member updateTargetMember;

    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTaskContentRequest{
        private Long id;
        private String updateContent;

    }
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTaskStatusRequest{
        private Long id;
        private TaskStatus taskStatus;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateTaskDTO{
        private Long id;
        private String taskName;
        private Member targetmember;
        private ProjectTeam projectTeam;
        private Menu menu;
        private TaskStatus taskStatus;
        private String taskDetail;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }



}

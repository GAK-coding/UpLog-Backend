package com.uplog.uplog.domain.team.dto;

import com.uplog.uplog.domain.member.model.Member;
import com.uplog.uplog.domain.member.model.Position;
import com.uplog.uplog.domain.team.model.MemberTeam;
import com.uplog.uplog.domain.team.model.PowerType;
import com.uplog.uplog.domain.team.model.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class memberTeamDTO {
    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CreateMemberTeamRequest{
        private String memberEmail;
        private Long memberId;
        private Long teamId;
        private PowerType powerType;
        private String link;
        private int mailType;
        //private int type;//0이면 프로덕트 생성 1이면 프로젝트 그룹 생성 -> 아이디, 이메일로 멤버 찾는거 구분때문에 만듬.

        public MemberTeam toMemberTeam(Team team, Member member, PowerType powerType){
            return MemberTeam.builder()
                    .team(team)
                    .member(member)
                    .powerType(powerType)
                    .build();
        }
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberTeamInfoDTO{
        private Long id;
        private Long memberId;
        private String memberName;
        private Long teamId;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberPowerDTO{
        private String memberEmail;
        private PowerType powerType;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberPowerListDTO{
        private Long poductId;
        private String productName;
        private String master;
        private int leaderCnt;
        private List<String> leaderList;
        private int workerCnt;
        private List<String> workerList;
        private int clientCnt;
        private List<String> clientList;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UpdateMemberPowerTypeRequest{
        private PowerType newPowerType;
        private Long memberId;
        private Long teamId; //여기서는 프로덕트팀, 프로젝트팀 다 같이 이걸로 씀.
    }
}

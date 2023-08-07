package com.uplog.uplog.domain.changedIssue.api;

import com.uplog.uplog.domain.changedIssue.application.ChangedIssueService;
import com.uplog.uplog.domain.changedIssue.dto.ChangedIssueDTO;
import com.uplog.uplog.domain.changedIssue.model.AccessProperty;
import com.uplog.uplog.domain.comment.api.CommentController;
import com.uplog.uplog.domain.member.dao.MemberRepository;
import com.uplog.uplog.domain.member.exception.NotFoundMemberByEmailException;
import com.uplog.uplog.domain.member.model.Member;
import com.uplog.uplog.domain.product.dao.ProductRepository;
import com.uplog.uplog.domain.product.model.Product;
import com.uplog.uplog.domain.team.dao.MemberTeamRepository;
import com.uplog.uplog.domain.team.dao.TeamRepository;
import com.uplog.uplog.domain.team.model.MemberTeam;
import com.uplog.uplog.domain.team.model.PowerType;
import com.uplog.uplog.domain.team.model.Team;
import com.uplog.uplog.global.method.AuthorizedMethod;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.uplog.uplog.domain.changedIssue.dto.ChangedIssueDTO.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ChangedIssueController {

    private final ChangedIssueService changedIssueService;
    private final AuthorizedMethod authorizedMethod;

    // summary -> api 내용(기능) description -> 세부 설명 tag -> 그룹 (도메인 별 컨트롤러 이름)
    @Operation(summary = "ChangedIssue", description = "ChangedIssue", tags = { "ChangedIssue Controller" })
    // response 코드 별로 응답 시 내용(설명) 작성
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = CommentController.class))),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })





    @PostMapping(value="/changedIssues/{project-id}/{member-id}")
    public ResponseEntity<IssueInfoDTO> createIssue(@RequestBody @Validated CreateChangedIssueRequest CreateChangedIssueRequest,
                                                                 @PathVariable("project-id")Long projId,
                                                                 @PathVariable("member-id")Long memberId) {

        IssueInfoDTO issueInfoDTO =changedIssueService.createIssue(CreateChangedIssueRequest,projId,memberId);

        return new ResponseEntity<>(issueInfoDTO, HttpStatus.CREATED);
    }

    @GetMapping(value="/changedIssues/{issue-id}")
    public ResponseEntity<IssueInfoDTO> findByIssueId(@PathVariable("issue-id")Long issueId){

        IssueInfoDTO IssueInfoDTO =changedIssueService.findByIssueId(issueId);

        return new ResponseEntity<>(IssueInfoDTO,HttpStatus.OK);
    }


    //변경이력 추가, 생성, 수정, 클릭 시 진행 중 or 접근 권한 확인.
    //global method
    @GetMapping(value="/changedIssues/{member-id}/{project-id}/validate")
    public String checkAuthorized(@PathVariable("member-id")Long memberId,
                                  @PathVariable("project-id")Long projectId){
        authorizedMethod.checkProjectProgress(projectId);
        authorizedMethod.powerValidateByMemberId(memberId);

        return AccessProperty.ACCESS_OK.toString();
    }

    @PatchMapping(value="/changedIssues/{issue-id}/issue")
    public ResponseEntity<SimpleIssueInfoDTO> updateChangedIssue(@RequestBody @Validated UpdateChangedIssueRequest UpdateChangedIssueRequest,
                                                                        @PathVariable("issue-id")Long issueId
                                                                ){

        ChangedIssueDTO.SimpleIssueInfoDTO simpleIssueInfoDTO =changedIssueService.updateChangedIssue(UpdateChangedIssueRequest,issueId);

        return new ResponseEntity<>(simpleIssueInfoDTO,HttpStatus.OK);

    }

    //삭제 시에는 클릭만으로 삭제 여부가 결정되니 메서드 안에 권한 검증 서비스만 삽입.
    @DeleteMapping(value="/changedIssues/{issue-id}/{member-id}")
    public String deleteChangedIssue(@PathVariable("issue-id")Long issueId,
                                     @PathVariable("member-id")Long memberId){


        authorizedMethod.powerValidateByMemberId(memberId);

        return changedIssueService.deleteChangedIssue(issueId);


    }


}

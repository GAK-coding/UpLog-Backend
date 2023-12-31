package com.uplog.uplog.domain.post.application;

import com.uplog.uplog.domain.comment.dao.CommentRepository;
import com.uplog.uplog.domain.like.dao.PostLikeRepository;
import com.uplog.uplog.domain.member.dao.MemberRepository;
import com.uplog.uplog.domain.member.model.Member;
import com.uplog.uplog.domain.member.model.Position;
import com.uplog.uplog.domain.menu.dao.MenuRepository;
import com.uplog.uplog.domain.menu.model.Menu;
import com.uplog.uplog.domain.post.dao.PostRepository;
import com.uplog.uplog.domain.post.dto.PostDTO.*;
import com.uplog.uplog.domain.post.model.Post;
import com.uplog.uplog.domain.post.model.PostType;
import com.uplog.uplog.domain.product.dao.ProductRepository;
import com.uplog.uplog.domain.product.model.Product;
import com.uplog.uplog.domain.project.dao.ProjectRepository;
import com.uplog.uplog.domain.project.model.Project;
import com.uplog.uplog.domain.task.exception.NotFoundTaskByIdException;
import com.uplog.uplog.global.exception.AuthorityException;
import com.uplog.uplog.global.exception.NotFoundIdException;
import com.uplog.uplog.global.method.AuthorizedMethod;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ProjectRepository projectRepository;
    private final MenuRepository menuRepository;
    private final AuthorizedMethod authorizedMethod;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;

    /*
    Create
     */
    @Transactional
    public PostInfoDTO createPost(Long id, CreatePostRequest createPostRequest) {
        Member author = memberRepository.findMemberById(id)
                .orElseThrow(() -> new NotFoundIdException("해당 멤버는 존재하지 않습니다."));

        //현재 프로젝트 팀 내에 존재하는 멤버,기업이 아닌 회원,클라이언트가 아닌 멤버 확인
        authorizedMethod.CreatePostTaskValidateByMemberId(author);

        Menu menu = menuRepository.findById(createPostRequest.getMenuId())
                .orElseThrow(() -> new NotFoundIdException("해당 메뉴는 존재하지 않습니다."));

        Project project = projectRepository.findById(createPostRequest.getProjectId())
                .orElseThrow(() -> new NotFoundIdException("해당 프로젝트는 존재하지 않습니다."));

        Product product = productRepository.findById(createPostRequest.getProductId())
                .orElseThrow(() -> new NotFoundIdException("해당 제품은 존재하지 않습니다."));



        // Post post = createPostRequest.toEntity(author, menu, product, project);
        PostType postType = PostType.DEFAULT; // 기본값으로 설정

        String requestType = createPostRequest.getPostType();
        if (requestType != null) {
            // requestType이 null이 아닐 때만 비교
            if (requestType.equals(PostType.REQUEST_READ.name())) {
                postType = PostType.REQUEST_READ;
            } else if (requestType.equals(PostType.REQUEST_REQUIREMENT.name())) {
                postType = PostType.REQUEST_REQUIREMENT;
            } else {
                throw new IllegalArgumentException("Invalid PostType: " + requestType);
            }
        }

        //TODO 해당 프로젝트사람들이면
        Post post = createPostRequest.toEntity(author, menu, product, project, postType);
        postRepository.save(post);

        return toPostInfoDTO(post);

    }


    /*
    Delete
     */
    @Transactional
    public String deletePost(Long id,Long currentUserId) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundIdException::new);

        if(post.getAuthor().getId().equals(currentUserId)){
            postRepository.delete(post);
            return "delete 완료";
        }
        else{
            throw new AuthorityException("작성자와 일치하지 않아 삭제 권한이 없습니다.");
        }
    }

    /*
        update
    */
    //TODO update 권한 설정해야,==으로 바꾸기
    @Transactional
    public PostInfoDTO updatePostTitle(Long id, UpdatePostTitleRequest updatePostTitleRequest, Long currentUserId) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundTaskByIdException::new);
        if(post.getAuthor().getId().equals(currentUserId)){
            post.updatePostTitle(updatePostTitleRequest.getUpdateTitle());
            return toPostInfoDTO(post);
        }
        else{
            throw new AuthorityException("작성자와 일치하지 않아 수정 권한이 없습니다.");
        }

    }

    @Transactional
    public PostInfoDTO updatePostContent(Long id, UpdatePostContentRequest updatePostContentRequest, Long currentUserId) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundTaskByIdException::new);
        if(post.getAuthor().getId().equals(currentUserId)){
            post.updatePostContent(updatePostContentRequest.getUpdateContent());
            return toPostInfoDTO(post);
        }
        else{
            throw new AuthorityException("작성자와 일치하지 않아 수정 권한이 없습니다.");
        }

    }

    //TODO Enum수정
    @Transactional
    public PostInfoDTO updatePostType(Long id, UpdatePostTypeRequest updatePostTypeRequest, Long currentUserId) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundTaskByIdException::new);
        PostType updatepostType = PostType.DEFAULT; // 기본값으로 설정

        String requestType = updatePostTypeRequest.getUpdatePostType();
        if(post.getAuthor().getId().equals(currentUserId)) {
            if (requestType != null) {
                // requestType이 null이 아닐 때만 비교
                if (requestType.equals(PostType.REQUEST_READ.name())) {
                    updatepostType = PostType.REQUEST_READ;
                } else if (requestType.equals(PostType.REQUEST_REQUIREMENT.name())) {
                    updatepostType = PostType.REQUEST_REQUIREMENT;
                } else {
                    throw new IllegalArgumentException("Invalid PostType: " + requestType);
                }
            }
            post.updatePostType(updatepostType);
            return toPostInfoDTO(post);
        }
        else{
            throw new AuthorityException("작성자와 일치하지 않아 수정 권한이 없습니다.");
        }

    }

    @Transactional
    public PostInfoDTO updatePostMenu(Long id, UpdatePostMenuRequest updatePostMenuRequest, Long currentUserId) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundTaskByIdException::new);
        Menu menu = menuRepository.findById(updatePostMenuRequest.getUpdateMenuId())
                .orElseThrow(() -> new NotFoundIdException("해당 메뉴는 존재하지 않습니다."));
        if(post.getAuthor().getId().equals(currentUserId)){
            post.updatePostMenu(menu);
            return toPostInfoDTO(post);
        }
        else{
            throw new AuthorityException("작성자와 일치하지 않아 수정 권한이 없습니다.");
        }

    }


    //TODO 이건 나중에 제품 수정할때 같이 불러야하는 서비스
    @Transactional
    public PostInfoDTO updateProductName(Long id, UpdatePostProductRequest updatePostProductRequest, Long currentUserId) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundTaskByIdException::new);
        post.updatePostProductName(updatePostProductRequest.getUpdateProductName());
        return toPostInfoDTO(post);


    }

    //TODO 이건 나중에 프로젝트 수정할때 같이 불러야하는 서비스임
    @Transactional
    public PostInfoDTO updateVersion(Long id, UpdatePostVersionRequest updatePostVersionRequest, Long currentUserId) {
        Post post = postRepository.findById(id).orElseThrow(NotFoundTaskByIdException::new);
        post.updatePostVersion(updatePostVersionRequest.getUpdateVersion());
        return toPostInfoDTO(post);

    }

    /*
    Get
     */

    @Transactional(readOnly = true)
    public PostInfoDTO findById(Long Id){
        Post post=postRepository.findById(Id).orElseThrow(NotFoundIdException::new);;
        return toPostInfoDTO(post);
    }
    @Transactional(readOnly = true)
    public List<PostInfoDTO> findPostInfoByMenuId(Long menuId){
        List<Post> postList=postRepository.findByMenuId(menuId);
        List<PostInfoDTO> postInfoDTOs=new ArrayList<>();
        for(Post post:postList){
            PostInfoDTO postInfoDTO=toPostInfoDTO(post);
            postInfoDTOs.add(postInfoDTO);
        }
        return postInfoDTOs;

    }
    //이건 안쓰는거->메뉴에서 씀
    public List<Post> findPostsByMenuId(Long menuId) {
        List<Post> postList = postRepository.findByMenuId(menuId);
        return postList;
    }

    public PostInfoDTO toPostInfoDTO(Post post) {
        int likeCount = postLikeRepository.countByPostId(post.getId());
        int commentCount = commentRepository.countByPostId(post.getId());

        return PostInfoDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .authorInfoDTO(post.getAuthor().powerMemberInfoDTO())
                .menuId(post.getMenu().getId())
                .menuName(post.getMenu().getMenuName())
                .productName(post.getProductName())
                .projectName(post.getVersion())
                .postType(post.getPostType())
                .content(post.getContent())
                .createTime(post.getCreateTime())
                .likeCount(likeCount)
                .commentCount(commentCount)
                .build();
    }


}

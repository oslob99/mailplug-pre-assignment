package kr.co.oslob.oslob.post.service;

import kr.co.oslob.oslob.common.exception.ErrorCode;
import kr.co.oslob.oslob.common.exception.NotFoundPostException;
import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.page.PageResponseDTO;
import kr.co.oslob.oslob.post.dto.request.PostRequestModifyDTO;
import kr.co.oslob.oslob.post.dto.request.PostRequestWriteDTO;
import kr.co.oslob.oslob.post.dto.response.PostListResponseDTO;
import kr.co.oslob.oslob.post.dto.response.PostResponseDTO;
import kr.co.oslob.oslob.post.entity.Post;
import kr.co.oslob.oslob.post.repository.PostRepository;
import kr.co.oslob.oslob.reply.dto.response.ReplyResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * @param pageDTO 게시글 페이징 처리 정보
     * @param keyword 게시글 제목,내용,작성자를 조회하는 키워드
     * @return 페이징 처리와 키워드를 조회해 게시글 전체 목록을 반환한다
     */
    public PostListResponseDTO getList(PageDTO pageDTO, String keyword) {


        Pageable pageable = PageRequest.of(
                pageDTO.getOffset() - 1,
                pageDTO.getLimit(),
                Sort.by(Sort.Direction.DESC,"postDate")
        );

        Page<Post> posts;

        if (keyword.isEmpty()){
            posts = postRepository.findAll(pageable);
        }else {
            posts = postRepository.findByKeyword(keyword, pageable);
        } // 동적 쿼리문 사용하기

        List<PostResponseDTO> responseDTOList = posts.stream().map(
                post -> new PostResponseDTO().toEntity(post)
        ).toList();

        return PostListResponseDTO.builder()
                .value(responseDTOList)
                .pageInfo(new PageResponseDTO<Post>(posts))
                .count(responseDTOList.size())
                .build();

    }

    /**
     * @param postId 상세보기할 게시글 번호
     * @return 상세보기에 필요한 게시글 정보를 반환한다
     */
    public PostResponseDTO detail(Long postId) {
        return new PostResponseDTO().toEntity(findByPost(postId));
    }

    /**
     * @param writeDTO 작성에 필요한 정보
     * @return 작성한 게시글을 반환한다
     */
    public PostResponseDTO write(PostRequestWriteDTO writeDTO) {
        return new PostResponseDTO().toEntity(postRepository.save(writeDTO.toEntity()));
    }

    /**
     * @param modifyDTO 수정에 필요한 정보
     * @return 수정된 게시글을 반환한다
     */
    public PostResponseDTO modify(PostRequestModifyDTO modifyDTO) {
        Post findByPost = findByPost(modifyDTO.getPostId());

        findByPost.setPostTitle(modifyDTO.getPostTitle());
        findByPost.setPostContent(modifyDTO.getPostContent());
        findByPost.setPostWriter(modifyDTO.getPostWriter());

        return new PostResponseDTO().toEntity(postRepository.save(findByPost));
    }

    /**
     * @param postId 삭제할 게시글 번호
     */
    public void delete(Long postId) {
        postRepository.delete(findByPost(postId));
    }


    /**
     * @param postId 조회할 게시글 번호
     * @return 예외처리를 포함해 게시글을 반환한다
     */
    private Post findByPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> {
                    throw new NotFoundPostException(ErrorCode.NOT_FOUND_POST, postId);
                }
        );
    }
}

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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

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

    public PostResponseDTO detail(Long postId) {

        Post post = findByPost(postId);

        return new PostResponseDTO().toEntity(post);
    }

    public void write(PostRequestWriteDTO writeDTO) {

        Post savedPost = postRepository.save(writeDTO.toEntity());

    }

    public void modify(PostRequestModifyDTO modifyDTO) {
        Post findByPost = findByPost(modifyDTO.getPostId());

        findByPost.setPostTitle(modifyDTO.getPostTitle());
        findByPost.setPostContent(modifyDTO.getPostContent());
        findByPost.setPostWriter(modifyDTO.getPostWriter());

        postRepository.save(findByPost);

    }

    public void delete(Long postId) {

        postRepository.delete(findByPost(postId));
    }


    /**
     * @param postId
     * @return postId의 게시글 찾아 Post를 반환하는 메서드
     */
    private Post findByPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(
                () -> {
                    throw new NotFoundPostException(ErrorCode.NOT_FOUND_POST, postId);
                }
        );
    }
}

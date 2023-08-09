package kr.co.oslob.oslob.post.service;

import kr.co.oslob.oslob.page.PageDTO;
import kr.co.oslob.oslob.page.SearchDTO;
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

@Service
@Slf4j
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public void getList(PageDTO pageDTO, SearchDTO searchDTO) {

        List<String> typeList = searchDTO.getTypes() != null ? Arrays.asList(searchDTO.getTypes()) : Collections.emptyList();

        Pageable pageable = PageRequest.of(
                pageDTO.getOffset() - 1,
                pageDTO.getLimit(),
                Sort.by(Sort.Direction.DESC,"postDate")
        );

        Page<Post> posts;
        if (typeList.isEmpty()){
            posts = postRepository.findAll(pageable);
        }else {
//            posts = postRepository.findbyPostType
        }
    }
}

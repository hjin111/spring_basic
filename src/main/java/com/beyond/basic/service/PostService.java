package com.beyond.basic.service;

import com.beyond.basic.domain.Post;
import com.beyond.basic.domain.PostResDto;
import com.beyond.basic.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    @Autowired // 생성자가 1개 밖에 없을 때는 Autowired 생략 가능
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public List<PostResDto> postList(){
        List<Post> postList = postRepository.findAll();
        List<PostResDto> postResDtoList = new ArrayList<>();
        for(Post p : postList){
            postResDtoList.add(p.fromEntity());
            System.out.println("저자의 이름은 " + p.getMember().getEmail());
        }

        return postResDtoList;
    }

}

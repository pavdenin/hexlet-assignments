package exercise.controller;

import exercise.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import exercise.model.Post;
import exercise.repository.PostRepository;
import exercise.exception.ResourceNotFoundException;
import exercise.dto.PostDTO;
import exercise.dto.CommentDTO;

// BEGIN
@RestController
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    CommentRepository commentRepository;

    @GetMapping("")
    public List<PostDTO> getPosts() {
        var postList = postRepository.findAll();

        return postList.stream().map(this::listToPostDtoConverter).toList();
    }

    @GetMapping("/{id}")
    public PostDTO getPostById(@PathVariable Long id) {
        Post post = postRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Post with id " + id + " not found"));

        return listToPostDtoConverter(post);
    }

    private PostDTO listToPostDtoConverter(Post post) {

        List<CommentDTO> commentDtoList = commentRepository.findByPostId(post.getId()).
        stream().map(c -> {
            CommentDTO commentDTO = new CommentDTO();
            commentDTO.setId(c.getId());
            commentDTO.setBody(c.getBody());
            return commentDTO;
        }).toList();

        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setBody(post.getBody());
        postDTO.setComments(commentDtoList);

        return postDTO;
    } 
    
}
// END

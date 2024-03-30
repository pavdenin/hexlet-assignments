package exercise.controller.users;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

import exercise.model.Post;
import exercise.Data;

// BEGIN
@RestController
@RequestMapping("/api/users")
public class PostsController {

    private List<Post> posts = Data.getPosts();

    @GetMapping("/{id}/posts")
    public List<Post> getUserPosts(@PathVariable Integer id) {
        List<Post> result = posts.stream()
        .filter(p -> Integer.valueOf(p.getUserId()).equals(id))
        .toList();

        return result;
    }

    @PostMapping("/{id}/posts")
    @ResponseStatus(HttpStatus.CREATED)
    public Post newUserPost(@PathVariable Integer id, @RequestBody Post newPost) {
        
        newPost.setUserId(id);
        posts.add(newPost);

        return newPost;
    }
}
// END

package exercise;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import exercise.model.Post;

@SpringBootApplication
@RestController
public class Application {
    // Хранилище добавленных постов
    private List<Post> posts = Data.getPosts();

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    // BEGIN
    @GetMapping("/posts")
    public List<Post> postList(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        if (page < 1) { page = 1; }
        if (size < 1) { size = 10; }
        return posts.stream().skip((page - 1) * size).limit(size).toList();
    }

    @GetMapping("/posts/{id}")
    public Optional<Post> getPost(@PathVariable String id) {
        return posts.stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @PostMapping("/posts")
    public Post newPost(@RequestBody Post data) {
        posts.add(data);
        return data;
    }

    @PutMapping("/posts/{id}")
    public Post updatePost(@PathVariable String id, @RequestBody Post data) {
        var findedPost = posts.stream().filter(p -> p.getId().equals(id)).findFirst();

        if (findedPost.isPresent()) {
            Post updatedPost = findedPost.get();
            updatedPost.setTitle(data.getTitle());
            updatedPost.setBody(data.getBody());
            return updatedPost;
        }

        return null;
    }

    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable String id){
        posts.removeIf(p -> p.getId().equals(id));
    }
    // END
}

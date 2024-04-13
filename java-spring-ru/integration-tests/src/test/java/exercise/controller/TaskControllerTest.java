package exercise.controller;

import org.junit.jupiter.api.Test;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import org.instancio.Instancio;
import org.instancio.Select;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import java.util.HashMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.datafaker.Faker;
import exercise.repository.TaskRepository;
import exercise.model.Task;

// BEGIN
@SpringBootTest
@AutoConfigureMockMvc
// END
class ApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Faker faker;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TaskRepository taskRepository;


    @Test
    public void testWelcomePage() throws Exception {
        var result = mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThat(body).contains("Welcome to Spring!");
    }

    @Test
    public void testIndex() throws Exception {
        var result = mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andReturn();

        var body = result.getResponse().getContentAsString();
        assertThatJson(body).isArray();
    }


    private Task generateTask() {
        return Instancio.of(Task.class)
                .ignore(Select.field(Task::getId))
                .supply(Select.field(Task::getTitle), () -> faker.lorem().word())
                .supply(Select.field(Task::getDescription), () -> faker.chuckNorris().fact())
                .create();
    }

    // BEGIN
    @Test
    public void testShow() {
        var task = generateTask();
        taskRepository.save(task);

        try {
            var result = mockMvc.perform(get("/tasks/"+task.getId()))
            .andExpect(status().isOk()).andReturn();

            var body = result.getResponse().getContentAsString();
            assertThatJson(body)
            .and(a -> a.node("title").isEqualTo(task.getTitle()),
            a -> a.node("description").isEqualTo(task.getDescription()));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Test
    public void testShowNegative() {
        Long taskId = 9999L;

        try {
            mockMvc.perform(get("/tasks/" + taskId))
            .andExpect(status().isNotFound());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void testCreate() throws Exception {
        String title = "Chuck Norris task";
        String description = faker.chuckNorris().fact();

        HashMap<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("description", description);

        var request = post("/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsString(map));

        var result = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();

        String body = result.getResponse().getContentAsString();

        Task savedTask = om.readValue(body, Task.class);

        assertThat(savedTask.getTitle()).isEqualTo(title);
        assertThat(savedTask.getDescription()).isEqualTo(description);
    }

    @Test
    public void testUpdate() throws Exception {
        var task = generateTask();
        taskRepository.save(task);

        String newTitle = "Chuck Norris task 2";
        String newDescription = "Chuck Norris has a polar bear carpet at home. It's not dead; it's just afraid to move.";

        HashMap<String, String> map = new HashMap<>();
        map.put("title", newTitle);
        map.put("description", newDescription);

        var request = put("/tasks/" + task.getId())
        .contentType(MediaType.APPLICATION_JSON)
        .content(om.writeValueAsString(map));

        mockMvc.perform(request)
        .andExpect(status().isOk());

        Task updatedTask = taskRepository.findById(task.getId()).get();

        assertThat(updatedTask.getTitle()).isEqualTo(newTitle);
        assertThat(updatedTask.getDescription()).isEqualTo(newDescription);
    }

    @Test
    public void testDelete() throws Exception {
        var task = generateTask();
        taskRepository.save(task);

        mockMvc.perform(delete("/tasks/" + task.getId()))
        .andExpect(status().isOk());

        var deletedTask = taskRepository.findById(task.getId());
        assertThat(!deletedTask.isPresent());
    }
    // END
}

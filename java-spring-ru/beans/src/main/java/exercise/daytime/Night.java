package exercise.daytime;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Night implements Daytime {
    private String name = "night";

    public String getName() {
        return name;
    }

    // BEGIN
    @PostConstruct
    public void init() {
        System.out.println("Night bean is created");
    }
    // END
}

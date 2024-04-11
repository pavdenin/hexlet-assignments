package exercise.daytime;

import jakarta.annotation.PostConstruct;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Day implements Daytime {
    private String name = "day";

    public String getName() {
        return name;
    }

    // BEGIN
    @PostConstruct
    public void init() {
        System.out.println("Day bean is created");
    }
    // END
}

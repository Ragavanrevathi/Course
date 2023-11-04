package courses.dao.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class Questions {

    private String question;
    private List<String> options = new ArrayList<>();
    private String answer;
}

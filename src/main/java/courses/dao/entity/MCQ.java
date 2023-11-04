package courses.dao.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class MCQ {

    private String category;
    private String type;
    private List<Questions> listOfQuestions;
    private int markPerQuestion;
}

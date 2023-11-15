package courses.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Module {

    @Id
    @Column(name = "Module_Id")
    private int id;

    @Column(name = "Module_Name")
    private String moduleName;

    @Column(name = "Module_Type")
    private String moduleType;

    @Column(name = "No_Of_Questions")
    private int noOfQuestions;

    @Column(name = "Mark_per_Question")
    private int markPerQuestion;

    @Column(name = "Duration_Of_Test")
    private int duration;


}


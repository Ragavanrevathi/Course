package courses.dao.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TestModule")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "Module_Test_Id",referencedColumnName = "Test_Id",
            foreignKey = @ForeignKey(name = "Module_Test_Id",value = ConstraintMode.NO_CONSTRAINT))
    @JsonbTransient
    private Test moduleTest;
}

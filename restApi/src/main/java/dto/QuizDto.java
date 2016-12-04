package dto;

import embeddable.Question;
import entity.Subsubcategory;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Tommy on 26/11/2016.
 */
public class QuizDto {
    @ApiModelProperty("The quiz id")
    public String id;

    @ApiModelProperty("The quiz name")
    public String name;

    @ApiModelProperty("The quiz subsubcategory")
    public Subsubcategory subsubcategory;

    @ApiModelProperty("The quiz questions")
    public List<Question> questions;

    public QuizDto(){}

    public QuizDto(String id, String name, List<Question> questions, Subsubcategory subsubcategory){
        this.id = id;
        this.name = name;
        this.questions = questions;
        this.subsubcategory = subsubcategory;
    }
}

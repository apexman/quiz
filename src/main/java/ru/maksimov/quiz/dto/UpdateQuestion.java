package ru.maksimov.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.maksimov.quiz.entity.Question;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateQuestion {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("question_text")
	private String questionText;

	public UpdateQuestion(Question question) {
		this.setQuestionText(question.getQuestionText());
	}

}

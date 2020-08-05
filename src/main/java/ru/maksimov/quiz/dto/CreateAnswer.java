package ru.maksimov.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.maksimov.quiz.entity.Answer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAnswer {

	@JsonProperty("answer_text")
	private String answerText;

	@JsonProperty("is_correct")
	private Boolean isCorrect;

	@JsonProperty("question_id")
	private Long questionId;

	public CreateAnswer(Answer answer) {
		this(answer.getAnswerText(), answer.getIsCorrect(), answer.getQuestionId());
	}
}

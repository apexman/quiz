package ru.maksimov.quiz.controllerobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.maksimov.quiz.entity.Answer;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnswerView {

	private Long id;

	private String answerText;

	private Boolean isCorrect;

	public AnswerView(Answer answer) {
		this(answer.getId(), answer.getAnswerText(), answer.getIsCorrect());
	}
}

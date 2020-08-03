package ru.maksimov.quiz.controllerobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.maksimov.quiz.entity.Answer;
import ru.maksimov.quiz.entity.Question;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionView {

	private Long id;

	private String questionText;

	private List<AnswerView> answers;

	public QuestionView(Question question, List<Answer> answers) {
		List<AnswerView> answerViews = new ArrayList<>();
		answers.forEach(answer -> {
			AnswerView answerView = new AnswerView(answer);
			answerViews.add(answerView);
		});
		this.setId(question.getId());
		this.setQuestionText(question.getQuestionText());
		this.setAnswers(answerViews);
	}

}

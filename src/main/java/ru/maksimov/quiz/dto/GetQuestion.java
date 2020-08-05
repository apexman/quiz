package ru.maksimov.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.maksimov.quiz.entity.Answer;
import ru.maksimov.quiz.entity.Question;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetQuestion {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("question_text")
	private String questionText;

	@JsonProperty("answers")
	private List<GetAnswer> answers;

	@JsonProperty("local_date_time")
	private LocalDateTime localDateTime;

	public GetQuestion(Question question) {
		this(question, new ArrayList<>());
	}

	public GetQuestion(Question question, List<Answer> answers) {
		List<GetAnswer> getAnswers = new ArrayList<>();
		answers.forEach(answer -> {
			GetAnswer getAnswer = new GetAnswer(answer);
			getAnswers.add(getAnswer);
		});
		this.setQuestionText(question.getQuestionText());
		this.setAnswers(getAnswers);
	}

}

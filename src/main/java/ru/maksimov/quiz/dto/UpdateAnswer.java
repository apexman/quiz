package ru.maksimov.quiz.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.maksimov.quiz.entity.Answer;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAnswer {

	@JsonProperty("id")
	private Long id;

	@JsonProperty("answer_text")
	private String answerText;

	@JsonProperty("is_correct")
	private Boolean isCorrect;

	@JsonProperty("local_date_time")
	private LocalDateTime localDateTime;

	public UpdateAnswer(Answer answer) {
		this(answer.getId(), answer.getAnswerText(), answer.getIsCorrect(), answer.getLocalDateTime());
	}
}

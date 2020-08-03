package ru.maksimov.quiz.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "ANSWER")
public class Answer extends AbstractEntity {

	@Column
	private String answerText;

	@Column
	private Boolean isCorrect = false;

	@JoinColumn(name = "question_id", insertable = false, updatable = false)
	@ManyToOne(targetEntity = Question.class, fetch = FetchType.LAZY)
	private Question question;

	@Column(name = "question_id")
	private Long questionId;

}

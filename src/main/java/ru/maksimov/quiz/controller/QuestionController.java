package ru.maksimov.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.maksimov.quiz.controllerobjects.AnswerView;
import ru.maksimov.quiz.controllerobjects.QuestionView;
import ru.maksimov.quiz.entity.Answer;
import ru.maksimov.quiz.entity.Question;
import ru.maksimov.quiz.repository.AnswerRepository;
import ru.maksimov.quiz.repository.QuestionRepository;

import java.util.*;

@Controller
@RequestMapping(path = "/question")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

	@GetMapping(path = "/{id}")
	public ResponseEntity<QuestionView> get(@PathVariable("id") Long id) {
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		if (optionalQuestion.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Question question = optionalQuestion.get();
		List<AnswerView> answerViews = new ArrayList<>();
		List<Answer> answers = answerRepository
				.findAllByQuestionId(id)
				.orElse(new ArrayList<>());
		answers.forEach(answer -> {
			AnswerView answerView = new AnswerView(answer.getId(), answer.getAnswerText(), answer.getIsCorrect());
			answerViews.add(answerView);
		});

		QuestionView questionView = new QuestionView(question.getId(), question.getQuestionText(), answerViews);
		return ResponseEntity.ok(questionView);
	}

	@PostMapping
	public ResponseEntity create(@RequestBody QuestionView questionView) {
		if (questionView == null
				|| questionView.getQuestionText() == null
				|| questionView.getQuestionText().isEmpty()
				|| questionView.getAnswers() == null
				|| questionView.getAnswers().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		if (questionView.getAnswers().stream().noneMatch(AnswerView::getIsCorrect)) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build();
		}

		Question question = new Question();
		question.setQuestionText(questionView.getQuestionText());
		questionRepository.save(question);

		List<Answer> answers = new ArrayList<>();
		questionView.getAnswers()
				.forEach(answerView -> {
					Answer answer = new Answer();
					answer.setAnswerText(answerView.getAnswerText());
					answer.setIsCorrect(answerView.getIsCorrect());
					answer.setQuestion(question);
					answer.setQuestionId(question.getId());
					answers.add(answer);
				});

		answerRepository.saveAll(answers);

		QuestionView questionViewResult = new QuestionView(question, answers);

		return ResponseEntity.ok(questionViewResult);
	}

}

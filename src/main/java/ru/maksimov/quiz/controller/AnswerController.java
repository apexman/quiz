package ru.maksimov.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.maksimov.quiz.dto.CreateAnswer;
import ru.maksimov.quiz.dto.GetAnswer;
import ru.maksimov.quiz.dto.UpdateAnswer;
import ru.maksimov.quiz.entity.Answer;
import ru.maksimov.quiz.entity.Question;
import ru.maksimov.quiz.repository.AnswerRepository;
import ru.maksimov.quiz.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/answer")
public class AnswerController {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

	@GetMapping(path = "/{id}")
	public ResponseEntity<GetAnswer> get(@PathVariable("id") Long id) {
		Optional<Answer> optionalAnswer = answerRepository.findById(id);

		if (optionalAnswer.isEmpty()) {
			return ResponseEntity.notFound().build();
		}

		Answer answer = optionalAnswer.get();
		GetAnswer getAnswer = new GetAnswer(answer);

		return ResponseEntity.ok(getAnswer);
	}

	@GetMapping(path = "/question/{id}")
	public ResponseEntity<List<GetAnswer>> getQuestionAnswers(@PathVariable("id") Long id) {
		Optional<List<Answer>> optionalAnswers = answerRepository.findAllByQuestionId(id);
		List<Answer> answers = optionalAnswers.orElse(new ArrayList<>());

		List<GetAnswer> getAnswers = new ArrayList<>();
		answers.forEach(answer -> getAnswers.add(new GetAnswer(answer)));

		return ResponseEntity.ok(getAnswers);
	}

	@PostMapping
	public ResponseEntity<CreateAnswer> create(@RequestBody CreateAnswer createAnswer) {
		if (createAnswer == null
				|| createAnswer.getAnswerText() == null
				|| createAnswer.getAnswerText().isEmpty()
				|| createAnswer.getQuestionId() == null) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Question> optionalQuestion = questionRepository.findById(createAnswer.getQuestionId());
		if (optionalQuestion.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		Answer answer = new Answer();
		answer.setAnswerText(createAnswer.getAnswerText());
		answer.setIsCorrect(createAnswer.getIsCorrect());
		answer.setQuestionId(createAnswer.getQuestionId());

		Answer savedAnswer = answerRepository.save(answer);
		CreateAnswer result = new CreateAnswer(savedAnswer);

		return ResponseEntity.ok(result);
	}

	@PutMapping
	public ResponseEntity<UpdateAnswer> update(@RequestBody UpdateAnswer updateAnswer) {
		if (updateAnswer == null
				|| updateAnswer.getId() == null
				|| updateAnswer.getAnswerText() == null
				|| updateAnswer.getAnswerText().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Answer> optionalAnswer = answerRepository.findById(updateAnswer.getId());
		if (optionalAnswer.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Question> optionalQuestion = questionRepository.findById(updateAnswer.getId());
		if (optionalQuestion.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		Question question = optionalQuestion.get();

		Answer answer = optionalAnswer.get();
		answer.setAnswerText(updateAnswer.getAnswerText());
		answer.setIsCorrect(updateAnswer.getIsCorrect());
		answer.setQuestionId(question.getId());
		Answer savedAnswer = answerRepository.save(answer);

		return ResponseEntity.ok(new UpdateAnswer(savedAnswer));
	}

}

package ru.maksimov.quiz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.maksimov.quiz.dto.CreateQuestion;
import ru.maksimov.quiz.dto.GetAnswer;
import ru.maksimov.quiz.dto.GetQuestion;
import ru.maksimov.quiz.dto.UpdateQuestion;
import ru.maksimov.quiz.entity.Answer;
import ru.maksimov.quiz.entity.Question;
import ru.maksimov.quiz.repository.AnswerRepository;
import ru.maksimov.quiz.repository.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(path = "/question")
public class QuestionController {

	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private AnswerRepository answerRepository;

	@GetMapping(path = {"/", ""})
	public ResponseEntity<List<GetQuestion>> get() {
		List<GetQuestion> getQuestions = new ArrayList<>();
		questionRepository.findAll().forEach(question -> getQuestions.add(new GetQuestion(question)));

		List<Long> questionIds = getQuestions.stream().map(GetQuestion::getId).collect(Collectors.toList());
		List<Answer> answers = answerRepository.findAllByQuestionIds(questionIds).orElse(new ArrayList<>());
		Map<Long, GetQuestion> questionMap = getQuestions.stream().collect(Collectors.toMap(GetQuestion::getId, question -> question));
		answers.forEach(answer -> questionMap.get(answer.getQuestionId()).getAnswers().add(new GetAnswer(answer)));

		return ResponseEntity.ok(getQuestions);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<GetQuestion> get(@PathVariable("id") Long id) {
		Optional<Question> optionalQuestion = questionRepository.findById(id);
		if (optionalQuestion.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		Question question = optionalQuestion.get();
		List<Answer> answers = answerRepository
				.findAllByQuestionId(id)
				.orElse(new ArrayList<>());
		GetQuestion createQuestion = new GetQuestion(question, answers);
		return ResponseEntity.ok(createQuestion);
	}

	@PostMapping
	public ResponseEntity create(@RequestBody CreateQuestion createQuestion) {
		if (createQuestion == null
				|| createQuestion.getQuestionText() == null
				|| createQuestion.getQuestionText().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		Question question = new Question();
		question.setQuestionText(createQuestion.getQuestionText());
		questionRepository.save(question);
		return ResponseEntity.ok().build();
	}

	@PutMapping
	public ResponseEntity update(@RequestBody UpdateQuestion updateQuestion) {
		if (updateQuestion == null
				|| updateQuestion.getId() == null
				|| updateQuestion.getQuestionText() == null
				|| updateQuestion.getQuestionText().isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		Optional<Question> optionalQuestion = questionRepository.findById(updateQuestion.getId());
		if (optionalQuestion.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		Question question = optionalQuestion.get();
		question.setQuestionText(updateQuestion.getQuestionText());
		questionRepository.save(question);
		return ResponseEntity.ok().build();
	}

}

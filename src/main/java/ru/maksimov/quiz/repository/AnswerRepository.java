package ru.maksimov.quiz.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.maksimov.quiz.entity.Answer;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Long> {

	@Query("SELECT a FROM Answer as a WHERE a.questionId = :questionId")
	Optional<List<Answer>> findAllByQuestionId(@Param("questionId") Long questionId);

	@Query("SELECT a FROM Answer as a WHERE a.questionId not null and in :questionIds")
	Optional<List<Answer>> findAllByQuestionIds(@Param("questionIds") List<Long> questionIds);
}

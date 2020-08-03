package ru.maksimov.quiz.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.maksimov.quiz.entity.Question;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {
}

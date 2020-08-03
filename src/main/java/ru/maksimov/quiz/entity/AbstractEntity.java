package ru.maksimov.quiz.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(columnDefinition = "TIMESTAMP")
	private LocalDateTime localDateTime = LocalDateTime.now();

	public Long getId() {
		return id;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}
}

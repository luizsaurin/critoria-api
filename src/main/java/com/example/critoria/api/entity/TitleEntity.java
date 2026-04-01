package com.example.critoria.api.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "titles")
@ToString(onlyExplicitlyIncluded = true)
public class TitleEntity extends BaseEntity {

	@Id
	@ToString.Include
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "titles_seq")
	@SequenceGenerator(name = "titles_seq", sequenceName = "titles_id_seq", allocationSize = 10)
	private Long id;

	@ToString.Include
	@Column(unique = true)
	private String name;

	@ToString.Include
	@Column(name = "release_year")
	private Integer releaseYear;

	@OneToMany(mappedBy = "title")
	private List<ReviewEntity> reviews;

}

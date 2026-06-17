<h1 align="center"><strong>NOTES</strong></h1>

Personal notes on various subjects about this project, its features and development phase.

&nbsp;

## Table of contents

1. [ID generation](#id-generation)
1. [Async validation](#async-validation)
1. [Jpa Auditing](#jpa-auditing)
1. [JaCoCo report](#jacoco-report)

&nbsp;

## ID generation

PostgreSQL is optimized to use sequences instead of sequencial auto increment generated ids. Sequences are also recommended to increase performance when executing queries in bulk. So instead of generating like this:

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

Prefer using sequences like this:

```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "titles_seq")
@SequenceGenerator(name = "titles_seq", sequenceName = "titles_id_seq", allocationSize = 50)
private Long id;
```

Its more verbose but it will deliver higher performance in high usage. This is the database configuration step, obviously it is required to properly use java to get the most out of sequences.

&nbsp;

## Async validation

Validator from Jakarta can be useful to asynchronously validate payloads and split between valid and invalid records.

```java
import jakarta.validation.Validator;

private final Validator validator;

Map<Boolean, List<CreateTitleRequestDTO>> partitioned = requestList.stream()
		.collect(Collectors.partitioningBy(r -> r != null && validator.validate(r).isEmpty()));

Set<CreateTitleRequestDTO> valid = new HashSet<>(partitioned.get(true));
Set<CreateTitleRequestDTO> invalid = new HashSet<>(partitioned.get(false));
```

Is this example, we are partitioning the request list input in two parts, valid and invalid. The evaluation is the snipped:

```java
r -> r != null && validator.validate(r).isEmpty()
```

validator.validate() process the bean validation annotation inside the DTO. note that before we a checking is the evaluated object is not null, since validator doesnt do that by default.

&nbsp;

## Jpa Auditing

Spring enabled for a more consistent way of defining the classic columns of `created_at` and `updated_at`. Instead of declaring them on every entity class, they can be declared at a base entity, then extended to other entity classes. JpaAuditing will take care of managing those fields.

&nbsp;

## JaCoCo report

This project is configured with JaCoco to generate test's report. Run tests using:
```
mvn clean test
```

JaCoCo will generate the report as a `index.html` file inside *target/site/jacoco* directory.
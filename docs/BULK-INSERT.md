# Bulk insert observations

Personal comments and considerations on how to properly code bulk inserts on database.

## The easy way

Using JPA extended repositories and calling saveAll() method is the easiest way, but not the most performatic and customizable way. There are other factors to actually make a bulk insert work properly, with performance and fail safes.

## ID generation

Database configuration plays a major part. Of course it differs from each database, for now we are talking PostgreSQL. Its very common to go for a easy sequencial autoincrement id generation, like this:

```java
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;
```

For big bulk inserts, this is *slow*. For each insert, Hibernate need to ask the database for a id, then execute the insert. Actually, PostgreSQL is optimized to work with `sequences`. And to make it even better, is it possible to allocate a number of ids, lowering the number os trips Hibernate has to do.

```java
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "titles_seq")
@SequenceGenerator(name = "titles_seq", sequenceName = "titles_id_seq", allocationSize = 50)
private Long id;
```

## Async validation

Its realistic to expect bulk inserts to be big, so its generally a good ideia to make it async. Unfurtonally we lose the ability to apply bean validation, commonly used when the request arrives at the controller. Fortunally, we can call the bean validation any time using `jakarta.validation.Validator`.

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


## Silent insert

Its possible to configure a silent insert. JPA's saveAll() by default fails the batch if one insert fails. To get around, we can use JdbcTemplate and run a custom insert query, one that ignores failed inserts and does not compromise the whole batch.

```sql
INSERT INTO titles (name, release_year) VALUES (?, ?) ON CONFLICT (name) DO NOTHING
```

## Application config

Beyond configuring the database table and java entity with allocation size, it does help to also configure the application with batch size and ordering for better networking performance.

```yaml
spring:
  jpa:
    properties:
      hibernate:
        jdbc:
          batch_size: 50
        order_inserts: true
```

`batch_size` groupd sql statements in a given number os batches, lowering network round trips. `order_inserts` makes Hibernate reorder SQL statements to maximize batching.
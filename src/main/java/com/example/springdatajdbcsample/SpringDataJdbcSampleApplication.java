package com.example.springdatajdbcsample;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringDataJdbcSampleApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataJdbcSampleApplication.class, args);
	}

	private final PersonRepository personRepository;

	public SpringDataJdbcSampleApplication(PersonRepository personRepository) {
		this.personRepository = personRepository;
	}

	public void run(String... args) {

		// エンティティの保存
		Person person1 = new Person();
		person1.setName("Taro");
		personRepository.save(person1);

		Person person2 = new Person();
		person2.setName("Jiro");
		personRepository.save(person2);

		// PrimaryKeyでエンティティを取得
		Optional<Person> savedPerson = personRepository.findById((long) 1);
		System.out.println("ID: " + savedPerson.get().getId() + " Name: " + savedPerson.get().getName());

		// すべてのエンティティの取得
		Iterable<Person> allPersons = personRepository.findAll();
		allPersons.forEach(person -> System.out.println(person.getName()));

		// 保存されているエンティティの数を取得
		System.out.println(personRepository.count());

		// 保存されているエンティティを削除
		personRepository.delete(savedPerson.get());
		Optional<Person> deletedIdPerson = personRepository.findById((long) 1);
		try {
			deletedIdPerson.get();
		} catch (Exception e) {
			System.out.println("取得エラー: " + e.getMessage());
		}

		// PrimaryKeyで指定したエンティティの存在確認
		if(personRepository.existsById(person1.getId())){
			System.out.println(person1.getId() + "は存在");
		}else{
			System.out.println(person1.getId() + "は存在しません");
		}

	}

}

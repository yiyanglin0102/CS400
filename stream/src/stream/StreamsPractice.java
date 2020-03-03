package stream;

import java.util.stream.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.*;

public class StreamsPractice {
	enum Level {K12, UNDERGRAD, CAPSTONE, GRADUATE};
	static class Student {
		String name;
		int age;
		Level level;
		Student(String name, int age, Level level) {
			this.name = name;
			this.age = age;
			this.level = level;
		}
		public String toString() { return name + "(" + age + ")" ; }
	}

	/**
	 * @param args UNUSED
	 * @throws IOException if StreamsPractice.java is not in current directory
	 */
	public static void main(String[] args) throws IOException {
		// create a collection (data structure) to be used as a source
		List<String> words = Arrays.asList("the", "Quick", "Brown", "the", "THE",
				"fox", "jumped", "jUmped", "over", "the", "lAzy", "dog");

		// Use Stream operations to create a list of words with more than 3 characters
		List<String> result = words.stream()
				.map(thing -> thing.toLowerCase())
				.filter(w->w.length() > 5)       // must have more than 3 characters
				.distinct()                      // no duplicates
				//				.limit(2)                        // stop after two words
				.sorted()
				.collect(Collectors.toList());
		System.out.println(result);

		// for each word in the stream
		words.stream()                           // get Stream of the sords
		.map(x ->x.toLowerCase())
		.filter(n -> n.contains("e"))            // only include words with letter 'e'
		.forEach(word -> {                       // print blank or letter e (when found)
			for (int i=0; i < word.length(); i++) { 
				if (word.charAt(i)=='e') System.out.print("e ");
				else System.out.print("_ ");
			} // for
			System.out.println();
		}); //forEach

		// count the words with letter 'o' that are in this file
		String filename = "StreamsPractice.java";
		long counter = Files.lines(Paths.get(filename)) 
				.map(String::trim)
				.map(String::toLowerCase)
				.filter(n -> n.contains("o")) 
				.count();
		System.out.println(counter + " items contain o");

		// Create a collection (List) of Students to use as a source for a Stream
		List<Student> students = Arrays.asList(
				new Student("Stevie",  10, Level.K12),
				new Student("Meghan",  21, Level.UNDERGRAD),
				new Student("Josh",    18, Level.UNDERGRAD),
				new Student("Pratham", 25, Level.GRADUATE),
				new Student("Alice",   28, Level.CAPSTONE),
				new Student("Sam",     12, Level.K12),
				new Student("Andy",    25, Level.GRADUATE),
				new Student("Sam",     12, Level.K12)        // duplicate
				);

		// Remove duplicates from Student stream
		List<Student> stuResult = students.stream()
				.distinct()
				.collect(Collectors.toList());
		System.out.println(stuResult);

		// Get sorted list of ages of students (no dups)
		List<Integer> distinctAges = students.stream()
				.map(s -> s.age)
				.sorted()
				.distinct()
				.collect(Collectors.toList());
		System.out.println(distinctAges);

		// Count the number of students older than 20
		long count = students.stream()
				.filter(stu -> stu.age > 20)
				.count();
		System.out.println("there are " + count + " students who are older than 20");
		
	}

}
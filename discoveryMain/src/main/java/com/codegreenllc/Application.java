package com.codegreenllc;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Set;

import org.scannotation.AnnotationDB;
import org.scannotation.ClasspathUrlFinder;

public class Application {

	public static void main(String[] args) throws IOException, ClassNotFoundException {
		URL[] urls = ClasspathUrlFinder.findClassPaths();
		AnnotationDB db = new AnnotationDB();
		db.scanArchives(urls);
		Set<String> goodPets = db.getAnnotationIndex().get(GoodPet.class.getName());
		printAnnotatedClasses(goodPets);
		System.out.println("All annotated classes discovered. Starting to read annotated properties");
		printAnnotatedClassesWithAttributes(goodPets);
	}

	private static void printAnnotatedClassesWithAttributes(Set<String> goodPets) throws ClassNotFoundException {
		for (String klazzName : goodPets) {
			Class<?> klass = Class.forName(klazzName);
			for (Annotation annotation : klass.getAnnotations()) {
				System.out.println(annotation.annotationType().getName());
				if (annotation.annotationType().equals(GoodPet.class)) {
					GoodPet gp = (GoodPet) annotation;
					System.out.println(String.format("Found GoodPet annotation in class %s with eats = %s", klazzName,asString(gp.eats())));
				}
			}
		}
		
	}

	private static void printAnnotatedClasses(Set<String> goodPets) {
		for (String klazzName : goodPets) {
			System.out.println(String.format("Found GoodPet annotation in class %s", klazzName));
		}
	}

	private static String asString(String[] eats) {
		String result = "[";
		boolean first = true;
		for (String e : eats) {
			if (first) {
				first = false;
			} else {
				result += ", ";
			}
			result += e;
		}
		result += "]";
		return result;
	}

}

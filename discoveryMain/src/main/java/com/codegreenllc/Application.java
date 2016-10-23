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

		for (String klazzName : goodPets) {
			System.out.println(klazzName);
			Class<?> klass = Class.forName(klazzName);
			for (Annotation annotation : klass.getAnnotations()) {
				System.out.println(annotation.annotationType().getName());
				if (annotation.annotationType().equals(GoodPet.class)) {
					GoodPet gp = (GoodPet) annotation;
					System.out.println(asString(gp.eats()));

				}
			}
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

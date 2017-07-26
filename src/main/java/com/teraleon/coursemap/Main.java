package com.teraleon.coursemap;

import java.io.IOException;

public class Main {

	public static void main(String[] args) {
		String url = "http://www.ucsd.edu/catalog/courses/BIOL.html";
		try {
			new CourseMap(url);
		} catch (IOException e) {
			System.err.println("Could not connect to " + url);
			e.printStackTrace();
		}
	}

}

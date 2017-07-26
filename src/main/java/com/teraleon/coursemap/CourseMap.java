package com.teraleon.coursemap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CourseMap {

	class Course {
		private String id;
		private String name;
		private String desc;
		private String prereqs;

		Course(Element course_name, Element course_description) {
			int dotCount = StringUtils.countMatches(course_name.text(), ".");

			if (dotCount == 0)
				throw new IllegalArgumentException(course_name.text() + " is not a valid course-name");

			String[] nmArr = course_name.text().split("\\.");
			this.id = nmArr[0];
			this.name = (dotCount == 1) ? nmArr[1].trim()
					: String.join("", Arrays.copyOfRange(nmArr, 1, nmArr.length)).trim();

			Elements prereqHeader = course_description.select("strong");
			if (!prereqHeader.isEmpty()) {
				this.desc = "";
				int n = course_description.childNodeSize();
				for (int i = 0; i < n - 2; i++)
					this.desc += course_description.childNode(i);
				this.prereqs = course_description.childNode(n - 1).toString().split("\\.")[0].trim();
			} else
				this.desc = course_description.text();
		}

		@Override
		public String toString() {
			return String.format("[ID] %s%n[NAME] %s%n[DESC] %s%n[REQS] %s", id, name, desc, prereqs);
		}
	}

	private Document catalog;

	public CourseMap(String url) throws IOException {
		catalog = Jsoup.connect(url).get();

		ArrayList<Course> courses = new ArrayList<>();
		catalog.select("[class=\"course-name\"]").forEach(n -> courses.add(new Course(n, n.nextElementSibling())));

		for (Course c : courses)
			System.out.println(c + "\n");
	}
}

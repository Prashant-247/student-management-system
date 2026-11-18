
package com.kiet.student.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;

import com.kiet.student.model.Student;

@RestController
@RequestMapping("/students")
public class StudentController {

    // In-memory collection
    List<Student> students = new ArrayList<>();

    // Constructor: Adding initial student data
    public StudentController() {

        students.add(new Student(101, "Rahul Sharma",
                "rahul@kiet.edu", "CSE", 2, 8.65));

        students.add(new Student(102, "Amit Kumar",
                "amit@kiet.edu", "CSE", 2, 9.10));

        students.add(new Student(103, "Priya Singh",
                "priya@kiet.edu", "IT", 3, 9.25));

        students.add(new Student(104, "Neha Verma",
                "neha@kiet.edu", "ECE", 2, 8.40));

        students.add(new Student(105, "Rohan Gupta",
                "rohan@kiet.edu", "CSE", 3, 9.50));
    }

    // 1. GET all students
    @GetMapping
    public List<Student> getAllStudents() {
        return students;
    }

    // 2. GET student by ID
    @GetMapping("/{studentId}")
    public Object getStudentById(@PathVariable int studentId) {

        for (Student s : students) {
            if (s.getStudentId() == studentId) {
                return s;
            }
        }

        return Map.of("message", "Student not found");
    }

    // 3. POST add new student
    @PostMapping
    public Object addStudent(@RequestBody Student student) {

        for (Student s : students) {
            if (s.getStudentId() == student.getStudentId()) {
                return Map.of("message", "Student ID already exists");
            }
        }

        students.add(student);

        return student;
    }

    // 4. PUT update student
    @PutMapping("/{studentId}")
    public Object updateStudent(@PathVariable int studentId,
                                @RequestBody Student student) {

        for (Student s : students) {
            if (s.getStudentId() == studentId) {

                s.setName(student.getName());
                s.setEmail(student.getEmail());
                s.setBranch(student.getBranch());
                s.setYear(student.getYear());
                s.setCgpa(student.getCgpa());

                return s;
            }
        }

        return Map.of("message", "Student not found");
    }

    // 5. DELETE student
    @DeleteMapping("/{studentId}")
    public Map<String, String> deleteStudent(
            @PathVariable int studentId) {

        for (Student s : students) {
            if (s.getStudentId() == studentId) {

                students.remove(s);

                return Map.of("message",
                        "Student deleted successfully");
            }
        }

        return Map.of("message", "Student not found");
    }

    // 6. Search by branch and year
    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam String branch,
            @RequestParam int year) {

        List<Student> result = new ArrayList<>();

        for (Student s : students) {

            if (s.getBranch().equalsIgnoreCase(branch)
                    && s.getYear() == year) {

                result.add(s);
            }
        }

        return result;
    }

    // 7. Find students by minimum CGPA
    @GetMapping("/topper")
    public List<Student> getToppers(
            @RequestParam double cgpa) {

        List<Student> result = new ArrayList<>();

        for (Student s : students) {

            if (s.getCgpa() >= cgpa) {
                result.add(s);
            }
        }

        return result;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package fi.tuni.prog3.studentregister;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


/**
 *
 * @author Sakari Ollikainen
 *  Student id H300314
 */
public class StudentRegister {
    
    private ArrayList<Student> studentList = new ArrayList<>();
    private ArrayList<Course> CourseList = new ArrayList<>();
    private ArrayList<Attainment> AttainmentList = new ArrayList<>();
    
    public void addStudent(Student student) {
        studentList.add(student);   
    }
    
     public void addCourse(Course course) {
        CourseList.add(course);   
    }
     
     public void addAttainment(Attainment att) {
        AttainmentList.add(att);   
    }
    
    public ArrayList<Student> getStudents() {
        
        Collections.sort(studentList, new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getName().compareTo(s2.getName());
            }
        });

        return studentList;
    }
        
    public ArrayList<Course> getCourses() {
         Collections.sort(CourseList, new Comparator<Course>() {
            @Override
            public int compare(Course c1, Course c2) {
                return c1.getName().compareTo(c2.getName());
            }
        });

        return CourseList;
    }
    
    private Student findStudentByNumber(String studentNumber) {
        for (Student student : studentList) {
            if (student.getStudentNumber().equals(studentNumber)) {
                return student;
            }
        }
        return null; // Student not found
    }
    
     private Course findCourseByCode(String courseCode) {
        for (Course course : CourseList) {
            if (course.getCode().equals(courseCode)) {
                return course;
            }
        }
        return null; // Course not found
    }

    
    public void printStudentAttainments(String studentNumber, String order){
        
       Student student = findStudentByNumber(studentNumber);

        if (student == null) {
            System.out.println("Unknown student number: " + studentNumber);
            return;
        } 
        
         System.out.println(student.getName() + " (" + student.getStudentNumber() + "):");

        
        ArrayList<Attainment> studentAttainments = new ArrayList<>();
        for (Attainment attainment : AttainmentList) {
            if (attainment.getStudentNumber().equals(studentNumber)) {
                studentAttainments.add(attainment);
            }
        }
        
        if ("by name".equalsIgnoreCase(order)) {
            Collections.sort(studentAttainments, new Comparator<Attainment>() {
                @Override
                public int compare(Attainment a1, Attainment a2) {
                    Course course1 = findCourseByCode(a1.getCourseCode());
                    Course course2 = findCourseByCode(a2.getCourseCode());
                    return course1.getName().compareTo(course2.getName());
                }
            });
        } else if ("by code".equalsIgnoreCase(order)) {
            Collections.sort(studentAttainments, new Comparator<Attainment>() {
                @Override
                public int compare(Attainment a1, Attainment a2) {
                    return a1.getCourseCode().compareTo(a2.getCourseCode());
                }
            });
        }
        
        for (Attainment attainment : studentAttainments) {
            Course course = findCourseByCode(attainment.getCourseCode());
            System.out.println("  " + course.getCode() + " " + course.getName() + ": " + attainment.getGrade());
        }
    }
    
    public void printStudentAttainments(String studentNumber) {
        printStudentAttainments(studentNumber, ""); // Call the method with an empty order parameter
    }
}
        
    


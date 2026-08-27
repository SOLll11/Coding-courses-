/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package fi.tuni.prog3.junitattainment;

import java.util.Comparator;

public class Attainment implements Comparable<Attainment>, Comparator<Attainment> {

    private String courseCode;
    private String studentNumber;
    private int grade;

    public Attainment(String courseCode, String studentNumber, int grade) {
        this.courseCode = courseCode;
        this.studentNumber = studentNumber;
        this.grade = grade;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public int getGrade() {
        return grade;
    }

    public String toString() {
        return courseCode + " " + studentNumber + " " + grade;
    }

    
    public static final Comparator<Attainment> CODE_STUDENT_CMP = new Comparator<Attainment>() {
        @Override
        public int compare(Attainment attainment1, Attainment attainment2) {
            
            int codeComparison = attainment1.getCourseCode().compareTo(attainment2.getCourseCode());

            if (codeComparison != 0) {
                return codeComparison; 
            } else {
                
                return attainment1.getStudentNumber().compareTo(attainment2.getStudentNumber());
            }
        }
    };

   
    public static final Comparator<Attainment> CODE_GRADE_CMP = new Comparator<Attainment>() {
        @Override
        public int compare(Attainment attainment1, Attainment attainment2) {
            
            int codeComparison = attainment1.getCourseCode().compareTo(attainment2.getCourseCode());

            if (codeComparison != 0) {
                return codeComparison; 
            } else {
                
                return Integer.compare(attainment2.getGrade(), attainment1.getGrade());
            }
        }
    };

    
    @Override
    public int compareTo(Attainment other) {
        int studentNumberComparison = this.studentNumber.compareTo(other.studentNumber);

        if (studentNumberComparison != 0) {
            return studentNumberComparison; 
        } else {
            
            return this.courseCode.compareTo(other.courseCode);
        }
    }

    
    @Override
    public int compare(Attainment attainment1, Attainment attainment2) {
        return Integer.compare(attainment1.grade, attainment2.grade);
    }
}

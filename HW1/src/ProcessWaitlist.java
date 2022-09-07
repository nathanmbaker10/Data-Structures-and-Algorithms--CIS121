import java.util.Iterator;
import java.util.LinkedList;

public class ProcessWaitlist {

    /**
     * Explanation and algorithm for this method is in the write-up. Make sure to read that in
     * its entirety.
     * 
     * Be sure to make use of the willAcceptStudent(student) and acceptStudent(student) methods you
     * implemented in Course.java.
     * 
     * Note: on execution, the courses LinkedList's accepted list should be populated correctly.
     * This is what we will be testing.
     *
     * Hint: you are allowed to modify the Student objects in the students list. See the comment
     * in Student.java for the getPreferences() method. You may find that doing so helps with
     * book-keeping in the makeAssignments() method. 
     * 
     * @param students list of students to be matched
     * @param courses list of courses to be matched
     *
     * You may assume that the students and courses lists are both non-null and that there 
     * are no null students nor null courses in either of the lists. 
     *
     * Note, students and courses may remain unmatched at the end of the algorithm. You should
     * not throw any exceptions in either case.
     */
    public static void makeAssignments(LinkedList<Student> students, LinkedList<Course> courses) {
        while (students.size() > 0) {
            Iterator<Student> studentsIterator = students.iterator();
            Student nextStudent = studentsIterator.next();
            
            LinkedList<Course> nextStudentsPreferences = nextStudent.getPreferences();
            
            Iterator<Course> preferenceIterator = nextStudentsPreferences.iterator();
            
            while (preferenceIterator.hasNext()) {
                Course nextCourse = preferenceIterator.next();
                
                if (nextCourse.willAcceptStudent(nextStudent)) {
                    Student removedStudent = nextCourse.acceptStudent(nextStudent);
                    if (removedStudent != null) {
                        students.addLast(removedStudent);
                    }
                    nextStudent.getPreferences().remove(nextCourse);
                    
                    break;
                    
                }
            }
            students.remove(nextStudent);
            
        }
    }
}

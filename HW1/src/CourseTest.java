import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import org.junit.Test;
import java.util.LinkedList;

public class CourseTest {
    
    @Test
    public void studentNotInCoursePreferenceList() {
        Student james = new Student("James", new LinkedList<Course>());
        Student mikey = new Student("Mikey", new LinkedList<Course>());
        
        Course cis520 = new Course("CIS520", 1);
        
        LinkedList<Student> cis520List = new LinkedList<Student>();
        cis520List.add(mikey);
        cis520.setPreferences(cis520List);
        
        
        assertFalse(cis520.willAcceptStudent(james));
        assertTrue(cis520.willAcceptStudent(mikey));
        
        assertNull(cis520.acceptStudent(james));
        assertNull(cis520.acceptStudent(mikey));
        
        Iterator<Student> acceptedStudentsIterator = cis520.getAccepted().iterator();
        assertTrue(acceptedStudentsIterator.hasNext());
        assertEquals(mikey, acceptedStudentsIterator.next());
        assertFalse(acceptedStudentsIterator.hasNext());
        
        
    }
    
    
    
    @Test
    public void addStudentThenRemove() {
        Student james = new Student("James", new LinkedList<Course>());
        Student mikey = new Student("Mikey", new LinkedList<Course>());
        
        Course cis520 = new Course("CIS520", 1);
        
        LinkedList<Student> cis520List = new LinkedList<Student>();
        cis520List.add(mikey);
        cis520List.add(james);
        cis520.setPreferences(cis520List);
        
        
        assertTrue(cis520.willAcceptStudent(james));
        assertTrue(cis520.willAcceptStudent(mikey));
        
        assertNull(cis520.acceptStudent(james));
        assertEquals(james, cis520.acceptStudent(mikey));
        
        
        
        
        
        Iterator<Student> acceptedStudentsIterator = cis520.getAccepted().iterator();
        assertTrue(acceptedStudentsIterator.hasNext());
        assertEquals(mikey, acceptedStudentsIterator.next());
        assertFalse(acceptedStudentsIterator.hasNext());
    }
    
    
    @Test
    public void studentAlreadyAccepted() {
        Student james = new Student("James", new LinkedList<Course>());
        
        
        Course cis520 = new Course("CIS520", 1);
        
        LinkedList<Student> cis520List = new LinkedList<Student>();
        
        cis520List.add(james);
        cis520.setPreferences(cis520List);
        
        
        assertTrue(cis520.willAcceptStudent(james));
        
        
        assertNull(cis520.acceptStudent(james));
        assertFalse(cis520.willAcceptStudent(james));
        
    }
    
    
    @Test
    public void addToEndOfList() {
        Student james = new Student("James", new LinkedList<Course>());
        Student mikey = new Student("Mikey", new LinkedList<Course>());
        
        LinkedList<Student> cis520List = new LinkedList<Student>();
        cis520List.add(mikey);
        cis520List.add(james);
        
        Course cis520 = new Course("CIS520", 2, cis520List);
        
       
        
        
        assertTrue(cis520.willAcceptStudent(james));
        assertTrue(cis520.willAcceptStudent(mikey));
        
        assertNull(cis520.acceptStudent(mikey));
        assertTrue(cis520.willAcceptStudent(james));
        
        assertNull(cis520.acceptStudent(james));
        
        
        
        
        
        Iterator<Student> acceptedStudentsIterator = cis520.getAccepted().iterator();
        assertTrue(acceptedStudentsIterator.hasNext());
        assertEquals(mikey, acceptedStudentsIterator.next());
        assertTrue(acceptedStudentsIterator.hasNext());
        assertEquals(james, acceptedStudentsIterator.next());
        assertFalse(acceptedStudentsIterator.hasNext());
    }
    
    
    @Test
    public void gettersAndSetters() {
        Student james = new Student("James", new LinkedList<Course>());
        Student mikey = new Student("Mikey", new LinkedList<Course>());
        
        LinkedList<Student> cis520List = new LinkedList<Student>();
        cis520List.add(mikey);
        cis520List.add(james);
        
        Course cis520 = new Course("CIS520", 2, cis520List);
        
        cis520.setCapacity(5);
        assertEquals("CIS520", cis520.getCourseCode());
        assertEquals("CIS520", cis520.toString());
        assertEquals(cis520List, cis520.getPreferences());
        
        
        
    }
    
    
    
    
}

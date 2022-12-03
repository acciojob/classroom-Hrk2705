package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class StudentRepository {

    private HashMap<String, Student> studentMap;
    private HashMap<String, Teacher> teacherMap;
    private HashMap<String, List<String>> teacherStudentMapping;

    public StudentRepository(HashMap<String, Student> studentMap, HashMap<String, Teacher> teacherMap, HashMap<String, List<String>> teacherStudentMapping) {
        this.studentMap = studentMap;
        this.teacherMap = teacherMap;
        this.teacherStudentMapping = teacherStudentMapping;
    }

    public void saveStudent(Student student){
        studentMap.put(student.getName(), student);
    }

    public void saveTeacher(Teacher teacher){
        teacherMap.put(teacher.getName(), teacher);
    }

    public void saveStudentTeacherPair(String student, String teacher){
        if(studentMap.containsKey(student) && teacherMap.containsKey(teacher)){
            List<String> currentStudents = new ArrayList<String>();
            if(teacherStudentMapping.containsKey(teacher)) currentStudents = teacherStudentMapping.get(teacher);
            currentStudents.add(student);
            teacherStudentMapping.put(teacher, currentStudents);
        }
    }

    public Student findStudent(String student){
        return studentMap.get(student);
    }

    public Teacher findTeacher(String teacher){
        return teacherMap.get(teacher);
    }

    public List<String> findStudentsFromTeacher(String director){
        List<String> studentList = new ArrayList<String>();
        if(teacherStudentMapping.containsKey(director)) studentList = teacherStudentMapping.get(director);
        return studentList;
    }

    public List<String> findAllStudents(){
        return new ArrayList<>(studentMap.keySet());
    }

    public void deleteTeacher(String teacher){
        ArrayList<String> students = new ArrayList<String>();
        if(teacherStudentMapping.containsKey(teacher)){
            students = (ArrayList<String>) teacherStudentMapping.get(teacher);
            for(String student: students){
                if(studentMap.containsKey(student)){
                    studentMap.remove(student);
                }
            }

            teacherStudentMapping.remove(teacher);
        }

        if(teacherMap.containsKey(teacher)){
            teacherMap.remove(teacher);
        }
    }

    public void deleteAllTeachers(){
        HashSet<String> studentSet = new HashSet<String>();

        for(String teacher: teacherStudentMapping.keySet()){
            for(String student: teacherStudentMapping.get(teacher)){
                studentSet.add(student);
            }
        }

        for(String student: studentSet){
            if(studentMap.containsKey(student)){
                studentMap.remove(student); //add
            }
        }
    }
}


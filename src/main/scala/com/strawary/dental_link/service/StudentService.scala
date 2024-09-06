package com.strawary.dental_link.service

import com.strawary.dental_link.dto.{StudentRequestDto, StudentResponseDto}
import com.strawary.dental_link.model.{Role, Student}
import com.strawary.dental_link.repository.StudentRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import scala.jdk.CollectionConverters.{CollectionHasAsScala, SeqHasAsJava}
import scala.jdk.OptionConverters.RichOptional

@Service
class StudentService @Autowired()(val studentRepository: StudentRepository) {

  def getAllStudents: List[StudentResponseDto] = {
    studentRepository.findAll().asScala.toList.map(mapToStudentDto)
  }

  def getStudentById(id: Long): Option[Student] = {
    studentRepository.findById(id).toScala
  }


  def createStudent(studentDto: StudentRequestDto): StudentResponseDto = {
    val student = mapToStudentEntity(studentDto)
    val savedStudent = studentRepository.save(student)
    mapToStudentDto(savedStudent) // Return StudentDto after saving
  }

  def updateStudent(id: Long, updatedStudent: Student): Option[Student] = {
    val studentOpt = studentRepository.findById(id)
    studentOpt.map(student => {
      student.university = updatedStudent.university
      student.currentYear = updatedStudent.currentYear
      studentRepository.save(student)
    }).toScala
  }

  def deleteStudent(id: Long): Unit = {
    studentRepository.deleteById(id)
  }

  // Generic method to map a request DTO to a Student entity
  private def mapToEntity[T](dto: T, entitySetter: (T, Student) => Unit): Student = {
    val student = new Student
    entitySetter(dto, student) // Set the specific fields using the passed function
    student
  }

  // Generic method to map a Student entity to a response DTO
  private def mapToDto[R](student: Student, dtoMapper: Student => R): R = {
    dtoMapper(student) // Create the response DTO using the passed function
  }

  // Specific mapping for StudentRequestDto to Student
  def mapToStudentEntity(studentDto: StudentRequestDto): Student = {
    mapToEntity[StudentRequestDto](studentDto, (dto, student) => {
      student.fullName = dto.fullName
      student.email = dto.email.orNull
      student.phoneNumber = dto.phoneNumber
      student.university = dto.university
      student.currentYear = dto.currentYear
      student.specialization = dto.specialization.orNull
      student.role = Role.STUDENT // Always set to STUDENT in this case
      student.coursesCurrent = dto.coursesCurrent.asJava
      student.skills = dto.skills.asJava
      student.neededCases = dto.neededCases.asJava
      student.location = dto.location
    })
  }

  // Specific mapping for Student to StudentResponseDto
  def mapToStudentDto(student: Student): StudentResponseDto = {
    mapToDto(student, student => {
      StudentResponseDto(
        userId = Some(student.userId),
        fullName = student.fullName,
        email = Option(student.email),
        phoneNumber = student.phoneNumber,
        profilePicture = Option(student.profilePicture),
        university = student.university,
        currentYear = student.currentYear,
        specialization = Option(student.specialization),
        coursesCurrent = Option(student.coursesCurrent).map(_.asScala.toList).getOrElse(List.empty),
        skills = Option(student.skills).map(_.asScala.toList).getOrElse(List.empty),
        neededCases = Option(student.neededCases).map(_.asScala.toList).getOrElse(List.empty),
        location = student.location,
        role = Role.toString(student.role) // Include role in the response
      )
    })
  }
}

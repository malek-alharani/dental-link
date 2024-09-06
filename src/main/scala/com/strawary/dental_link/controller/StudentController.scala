package com.strawary.dental_link.controller

import com.strawary.dental_link.dto.{StudentRequestDto, StudentResponseDto}
import com.strawary.dental_link.model.Student
import com.strawary.dental_link.service.StudentService
import org.springframework.web.bind.annotation._
import org.springframework.http.ResponseEntity
import org.springframework.beans.factory.annotation.Autowired
import play.api.libs.json.Json
import play.api.libs.json.OFormat.oFormatFromReadsAndOWrites

@RestController
@RequestMapping(Array("/students"))
class StudentController @Autowired()(val studentService: StudentService) {

  @GetMapping(Array("/"))
  def getAllStudents: ResponseEntity[String] = {
    val students = studentService.getAllStudents
    ResponseEntity.ok(Json.toJson(students).toString())
  }

  @GetMapping(Array("/{id}"))
  def getStudentById(@PathVariable id: Long): ResponseEntity[String] = {
    studentService.getStudentById(id) match {
      case Some(student) =>
        val studentDto = studentService.mapToStudentDto(student)
        ResponseEntity.ok(Json.toJson(studentDto).toString())
      case None => ResponseEntity.notFound().build()
    }
  }

  @PostMapping(Array("/"))
  def createStudent(@RequestBody studentDtoJson: String): ResponseEntity[String] = {
    // Parse the JSON string into StudentDto
    val studentDto = Json.parse(studentDtoJson).as[StudentRequestDto]
    val createdStudent = studentService.createStudent(studentDto)
    ResponseEntity.ok(Json.toJson(createdStudent).toString())
  }

  @PutMapping(Array("/{id}"))
  def updateStudent(@PathVariable id: Long, @RequestBody updatedStudent: Student): ResponseEntity[Student] = {
    studentService.updateStudent(id, updatedStudent) match {
      case Some(student) => ResponseEntity.ok(student)
      case None => ResponseEntity.notFound().build()
    }
  }

  @DeleteMapping(Array("/{id}"))
  def deleteStudent(@PathVariable id: Long): ResponseEntity[Void] = {
    studentService.deleteStudent(id)
    ResponseEntity.noContent().build()
  }
}

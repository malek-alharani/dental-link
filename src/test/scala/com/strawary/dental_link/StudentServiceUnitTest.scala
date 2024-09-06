package com.strawary.dental_link

import com.strawary.dental_link.dto.StudentRequestDto
import com.strawary.dental_link.model.Student
import com.strawary.dental_link.repository.StudentRepository
import com.strawary.dental_link.service.StudentService
import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.should.Matchers

import scala.jdk.CollectionConverters.SeqHasAsJava

class StudentServiceUnitTest extends AnyFunSuite with Matchers {

  // Mock the repository directly using Mockito
  val studentRepository: StudentRepository = mock(classOf[StudentRepository])

  // Inject the mocked repository into the service
  val studentService = new StudentService(studentRepository)

  test("createStudent should save and return StudentDto") {
    // Create a mock input DTO
    val studentRequestDto = StudentRequestDto(
      fullName = "John Doe",
      email = Some("johndoe@example.com"),
      phoneNumber = "+123456789",
      university = "Medical University",
      currentYear = 4,
      specialization = Some("Orthodontics"),
      coursesCurrent = List("Advanced Dentistry"),
      skills = List("Root Canal"),
      neededCases = List("ROOT_CANAL"),
      location = "New York"
    )

    // Create a mock student entity
    val studentEntity = new Student
    studentEntity.userId = 1L
    studentEntity.fullName = studentRequestDto.fullName
    studentEntity.email = studentRequestDto.email.getOrElse("")
    studentEntity.skills = studentRequestDto.skills.asJava

    // Mock the repository's save method
    when(studentRepository.save(any[Student])).thenReturn(studentEntity)

    // Call the service method
    val result = studentService.createStudent(studentRequestDto)
    print(result.toString)
    // Assertions
    result.fullName shouldBe "John Doe"
    result.email shouldBe Some("johndoe@example.com")
    result.skills shouldBe List("Root Canal")

    // Verify that the repository's save method was called
    verify(studentRepository).save(any[Student])
  }
}

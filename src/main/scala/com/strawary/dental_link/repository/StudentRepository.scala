package com.strawary.dental_link.repository

import com.strawary.dental_link.model.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
trait StudentRepository extends JpaRepository[Student, Long] {
  def findByUniversity(university: String): List[Student]
}

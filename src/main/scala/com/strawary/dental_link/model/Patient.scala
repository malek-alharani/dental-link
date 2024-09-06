package com.strawary.dental_link.model

import jakarta.persistence._
import java.util.List

@Entity
class Patient extends User {
  @Column(nullable = false)
  var age: Int = _

  @Column(nullable = false)
  var gender: String = _

  @Column(nullable = false)
  var location: String = _

  @ElementCollection
  var chronicConditions: List[String] = _

  @ElementCollection
  var specialNeeds: List[String] = _

  @ElementCollection
  var medicalReports: List[String] = _
}

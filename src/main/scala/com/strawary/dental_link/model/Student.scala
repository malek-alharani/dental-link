package com.strawary.dental_link.model

import jakarta.persistence._
import java.util.List

@Entity
class Student extends User {
  @Column(nullable = false)
  var university: String = _

  @Column(nullable = false)
  var currentYear: Int = _

  @Column(nullable = true)
  var specialization: String = _

  @ElementCollection
  var coursesCurrent: List[String] = _

  @ElementCollection
  var skills: List[String] = _

  @ElementCollection
  var neededCases: List[String] = _

  @Column(nullable = false)
  var location: String = _
}


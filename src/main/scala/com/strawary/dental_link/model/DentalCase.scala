package com.strawary.dental_link.model

import com.strawary.dental_link.model.CaseType.{CROWN, FILLING, GUM_TREATMENT, ROOT_CANAL}
import jakarta.persistence._

import java.time.LocalDateTime

@Entity
class DentalCase {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var caseId: Long = _

  @Convert(converter = classOf[CaseTypeConverter])
  var caseType: CaseType = _

  @Column(nullable = true)
  var description: String = _

  @Column(nullable = true)
  var symptoms: String = _ // Make sure this field exists

  @ManyToOne
  @JoinColumn(name = "patient_id", nullable = false)
  var patient: Patient = _

  @Column(nullable = false)
  var createdAt: LocalDateTime = LocalDateTime.now()

  @Column(nullable = false)
  var updatedAt: LocalDateTime = LocalDateTime.now()
}

sealed trait CaseType

object CaseType {
  case object ROOT_CANAL extends CaseType

  case object FILLING extends CaseType

  case object CROWN extends CaseType

  case object GUM_TREATMENT extends CaseType

  def fromString(caseType: String): CaseType = caseType match {
    case "ROOT_CANAL" => ROOT_CANAL
    case "FILLING" => FILLING
    case "CROWN" => CROWN
    case "GUM_TREATMENT" => GUM_TREATMENT
    case _ => throw new IllegalArgumentException(s"Unknown case type: $caseType")
  }

  def toString(caseType: CaseType): String = caseType match {
    case ROOT_CANAL => "ROOT_CANAL"
    case FILLING => "FILLING"
    case CROWN => "CROWN"
    case GUM_TREATMENT => "GUM_TREATMENT"
  }
}


@Converter(autoApply = true)
class CaseTypeConverter extends AttributeConverter[CaseType, String] {


  // Convert CaseType to a String to store in the database
  override def convertToDatabaseColumn(caseType: CaseType): String = caseType match {
    case CaseType.ROOT_CANAL => "ROOT_CANAL"
    case CaseType.FILLING => "FILLING"
    case CaseType.CROWN => "CROWN"
    case CaseType.GUM_TREATMENT => "GUM_TREATMENT"
    case _ => throw new IllegalArgumentException(s"Unknown case type: $caseType")
  }

  // Convert String from the database back to the CaseType
  override def convertToEntityAttribute(caseTypeString: String): CaseType = caseTypeString match {
    case "ROOT_CANAL" => CaseType.ROOT_CANAL
    case "FILLING" => CaseType.FILLING
    case "CROWN" => CaseType.CROWN
    case "GUM_TREATMENT" => CaseType.GUM_TREATMENT
    case _ => throw new IllegalArgumentException(s"Unknown case type string: $caseTypeString")
  }
}
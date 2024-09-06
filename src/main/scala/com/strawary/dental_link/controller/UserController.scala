package com.strawary.dental_link.controller

import com.strawary.dental_link.dto.UserDto
import com.strawary.dental_link.service.UserService
import org.springframework.web.bind.annotation._
import org.springframework.http.ResponseEntity
import org.springframework.beans.factory.annotation.Autowired
import play.api.libs.json.Json

@RestController
@RequestMapping(Array("/users"))
class UserController @Autowired()(val userService: UserService) {

  @GetMapping(Array("/"))
  def getAllUsers: ResponseEntity[String] = {
    val users = userService.getAllUsers
    ResponseEntity.ok(Json.toJson(users).toString())
  }

  @GetMapping(Array("/{id}"))
  def getUserById(@PathVariable id: Long): ResponseEntity[String] = {
    userService.getUserById(id) match {
      case Some(user) => ResponseEntity.ok(Json.toJson(user).toString())
      case None => ResponseEntity.notFound().build()
    }
  }

  @PostMapping(Array("/"))
  def createUser(@RequestBody userDto: UserDto): ResponseEntity[String] = {
    val createdUser = userService.createUser(userDto)
    ResponseEntity.ok(Json.toJson(createdUser).toString())
  }

  @PutMapping(Array("/{id}"))
  def updateUser(@PathVariable id: Long, @RequestBody updatedUserDto: UserDto): ResponseEntity[String] = {
    userService.updateUser(id, updatedUserDto) match {
      case Some(user) => ResponseEntity.ok(Json.toJson(user).toString())
      case None => ResponseEntity.notFound().build()
    }
  }

  @DeleteMapping(Array("/{id}"))
  def deleteUser(@PathVariable id: Long): ResponseEntity[Void] = {
    userService.deleteUser(id)
    ResponseEntity.noContent().build()
  }
}

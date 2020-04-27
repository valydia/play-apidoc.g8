package service

import java.util.UUID

import domain.{CreateUser, User, UserUpdate}

class UserService {

  var users: List[User] = List()

  def save(user: CreateUser): Either[String, String] = {
    if (users.exists(u => u.firstname == user.firstname &&  u.lastname == user.lastname))
      Left("user already exist")
    else {
      val id = UUID.randomUUID().toString
      users = users :+ User(id, user.firstname, user.lastname)
      Right(id)
    }
  }

  def findUserById(id: String): Option[User] = {
    users.find(_.id == id)
  }

  def update(id: String, userUpdate: UserUpdate): Option[Either[String, Unit]] = {
    users.find(_.id == id).map { user =>
      val firstname = userUpdate.firstname orElse user.firstname
      val lastname = userUpdate.lastname.getOrElse(user.lastname)
      val updatedUser = user.copy(firstname = firstname, lastname = lastname)
      if (users.exists(u => u.firstname == user.firstname &&  u.lastname == user.lastname)) {
        Left("user already exist")
      } else {
        users = users.map { u => if (u.id == updatedUser.id) updatedUser else u}
        Right(())
      }
    }
  }

  def delete(id: String): Option[Unit] = {
    users.find(_.id == id).map { _ =>
      users = users.filterNot(_.id == id)
    }
  }



}

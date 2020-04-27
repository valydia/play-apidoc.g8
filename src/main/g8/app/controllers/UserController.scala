package controllers

import domain.{CreateUser, UserError, UserUpdate}
import javax.inject.{Inject, Singleton}
import play.api.libs.json.Json
import play.api.mvc.{BaseController, ControllerComponents}
import service.UserService



@Singleton
class UserController @Inject()(userService: UserService, val controllerComponents: ControllerComponents) extends BaseController {


  /**
   * @api {post} /users Create a user
   * @apiName CreateUser
   * @apiGroup User
   *
   * @apiHeader {String} Content-Type application/json.
   *
   * @apiParam {String} [firstname] Firstname of the User.
   * @apiParam {String} lastname  Lastname of the User.
   *
   * @apiSuccessExample Success-Response:
   *     HTTP/1.1 201 Created
   *     {
   *        "id": "d1efdad6-e3a5-4efe-b938-a253673d9e6f"
   *     }
   * @apiUse UserBadRequestError
   */
  def create() = Action(parse.json) { req =>
    val newUser = req.body.validate[CreateUser]
    newUser.fold(
      _ => {
        BadRequest(Json.toJson(UserError("UserBadRequest")))
      },
      user => {
        userService.save(user).fold(
          error =>
            Conflict(Json.toJson(UserError(error))),
          id =>
            Created(Json.obj("id" -> id))
        )
      }
    )
  }

  /**
   * @api {get} /users/:id Request User information
   * @apiName GetUser
   * @apiGroup User
   *
   * @apiParam {Number} id Users unique ID.
   *
   * @apiSuccess {String} firstname Firstname of the User.
   * @apiSuccess {String} lastname  Lastname of the User.
   *
   * @apiSuccessExample Success-Response:
   *     HTTP/1.1 200 OK
   *     {
   *       "id": "d1efdad6-e3a5-4efe-b938-a253673d9e6f",
   *       "firstname": "John",
   *       "lastname": "Doe"
   *     }
   *
   * @apiUse UserNotFoundError
   */
  def find(id: String) = Action { _ =>
      userService.findUserById(id) match {
        case Some(user) => Ok(Json.toJson(user))
        case _ => NotFound(Json.toJson(UserError("User not found")))
      }
  }

  /**
   * @api {patch} /users/:id Update User information
   * @apiName UpdateUser
   * @apiGroup User
   *
   * @apiHeader {String} Content-Type application/json.
   *
   * @apiParam {Number} id Users unique ID.
   *
   * @apiParam {String} [firstname] Firstname of the User.
   * @apiParam {String} [lastname]  Lastname of the User.
   *
   * @apiSuccessExample Success-Response:
   *     HTTP/1.1 204 OK
   *
   * @apiUse UserNotFoundError
   * @apiUse UserAlreadyExistsError
   * @apiUse UserBadRequestError
   * @apiSampleRequest off
   */
  def update(id: String)= Action(parse.json) { req =>
    val userUpdates = req.body.validate[UserUpdate]
    userUpdates.fold(
      _ => BadRequest(Json.toJson(UserError("UserBadRequest"))),
      updates =>
        userService.update(id, updates) match {
          case None => NotFound(Json.toJson(UserError("User not found")))
          case Some(Left(error)) => Conflict(Json.toJson(UserError(error)))
          case Some(Right(_)) => NoContent
        }
    )

  }

  /**
   * @api {delete} /users/:id Delete a User
   * @apiName DeleteUser
   * @apiGroup User
   *
   * @apiParam {Number} id Users unique ID.
   *
   * @apiSuccessExample Success-Response:
   *     HTTP/1.1 204 OK
   *
   * @apiUse UserNotFoundError
   */
  def delete(id: String)= Action(parse.empty) { _ =>
    userService.delete(id) match {
      case None => NotFound(Json.toJson(UserError("User not found")))
      case _ => NoContent
    }

  }
}

package controller

import skinny._
import skinny.validator._
import _root_.controller._
import model.Student

class StudentsController extends SkinnyResource with ApplicationController {
  protectFromForgery()

  override def model = Student
  override def resourcesName = "students"
  override def resourceName = "student"

  override def resourcesBasePath = s"/${toSnakeCase(resourcesName)}"
  override def useSnakeCasedParamKeys = true

  override def viewsDirectoryPath = s"/${resourcesName}"

  override def createParams = Params(params)
  override def createForm = validation(createParams,
    paramKey("name") is required & maxLength(512),
    paramKey("enrollment") is required & numeric & intValue
  )
  override def createFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "enrollment" -> ParamType.Int
  )

  override def updateParams = Params(params)
  override def updateForm = validation(updateParams,
    paramKey("name") is required & maxLength(512),
    paramKey("enrollment") is required & numeric & intValue
  )
  override def updateFormStrongParameters = Seq(
    "name" -> ParamType.String,
    "enrollment" -> ParamType.Int
  )

}

package controller

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.BeforeAndAfterAll
import skinny._
import skinny.test._
import org.joda.time._
import model._

// NOTICE before/after filters won't be executed by default
class StudentsControllerSpec extends AnyFunSpec with Matchers with BeforeAndAfterAll with DBSettings {

  override def afterAll(): Unit = {
    super.afterAll()
    Student.deleteAll()
  }

  def createMockController = new StudentsController with MockController
  def newStudent = FactoryGirl(Student).create()

  describe("StudentsController") {

    describe("shows students") {
      it("shows HTML response") {
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/students/index"))
        controller.contentType should equal("text/html; charset=utf-8")
      }

      it("shows JSON response") {
        implicit val format = Format.JSON
        val controller = createMockController
        controller.showResources()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/students/index"))
        controller.contentType should equal("application/json; charset=utf-8")
      }
    }

    describe("shows a student") {
      it("shows HTML response") {
        val student = newStudent
        val controller = createMockController
        controller.showResource(student.id)
        controller.status should equal(200)
        controller.getFromRequestScope[Student]("item") should equal(Some(student))
        controller.renderCall.map(_.path) should equal(Some("/students/show"))
      }
    }

    describe("shows new resource input form") {
      it("shows HTML response") {
        val controller = createMockController
        controller.newResource()
        controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/students/new"))
      }
    }

    describe("creates a student") {
      it("succeeds with valid parameters") {
        val controller = createMockController
        controller.prepareParams(
          "name" -> "dummy",
          "enrollment" -> Int.MaxValue.toString())
        controller.createResource()
        controller.status should equal(200)
      }

      it("fails with invalid parameters") {
        val controller = createMockController
        controller.prepareParams() // no parameters
        controller.createResource()
        controller.status should equal(400)
        controller.errorMessages.size should be >(0)
      }
    }

    it("shows a resource edit input form") {
      val student = newStudent
      val controller = createMockController
      controller.editResource(student.id)
      controller.status should equal(200)
        controller.renderCall.map(_.path) should equal(Some("/students/edit"))
    }

    it("updates a student") {
      val student = newStudent
      val controller = createMockController
      controller.prepareParams(
        "name" -> "dummy",
        "enrollment" -> Int.MaxValue.toString())
      controller.updateResource(student.id)
      controller.status should equal(200)
    }

    it("destroys a student") {
      val student = newStudent
      val controller = createMockController
      controller.destroyResource(student.id)
      controller.status should equal(200)
    }

  }

}

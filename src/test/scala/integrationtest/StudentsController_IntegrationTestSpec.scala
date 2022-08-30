package integrationtest

import org.scalatest._
import skinny._
import skinny.test._
import org.joda.time._
import _root_.controller.Controllers
import model._

class StudentsController_IntegrationTestSpec extends SkinnyFlatSpec with SkinnyTestSupport with BeforeAndAfterAll with DBSettings {
  addFilter(Controllers.students, "/*")

  override def afterAll(): Unit = {
    super.afterAll()
    Student.deleteAll()
  }

  def newStudent = FactoryGirl(Student).create()

  it should "show students" in {
    get("/students") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/students/") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/students.json") {
      logBodyUnless(200)
      status should equal(200)
    }
    get("/students.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show a student in detail" in {
    get(s"/students/${newStudent.id}") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/students/${newStudent.id}.xml") {
      logBodyUnless(200)
      status should equal(200)
    }
    get(s"/students/${newStudent.id}.json") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "show new entry form" in {
    get(s"/students/new") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "create a student" in {
    post(s"/students",
      "name" -> "dummy",
      "enrollment" -> Int.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      post(s"/students",
        "name" -> "dummy",
        "enrollment" -> Int.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
        val id = header("Location").split("/").last.toLong
        Student.findById(id).isDefined should equal(true)
      }
    }
  }

  it should "show the edit form" in {
    get(s"/students/${newStudent.id}/edit") {
      logBodyUnless(200)
      status should equal(200)
    }
  }

  it should "update a student" in {
    put(s"/students/${newStudent.id}",
      "name" -> "dummy",
      "enrollment" -> Int.MaxValue.toString()) {
      logBodyUnless(403)
      status should equal(403)
    }

    withSession("csrf-token" -> "valid_token") {
      put(s"/students/${newStudent.id}",
        "name" -> "dummy",
        "enrollment" -> Int.MaxValue.toString(),
        "csrf-token" -> "valid_token") {
        logBodyUnless(302)
        status should equal(302)
      }
    }
  }

  it should "delete a student" in {
    delete(s"/students/${newStudent.id}") {
      logBodyUnless(403)
      status should equal(403)
    }
    withSession("csrf-token" -> "valid_token") {
      delete(s"/students/${newStudent.id}?csrf-token=valid_token") {
        logBodyUnless(200)
        status should equal(200)
      }
    }
  }

}

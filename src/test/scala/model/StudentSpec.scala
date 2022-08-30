package model

import skinny.DBSettings
import skinny.test._
import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scalikejdbc._
import scalikejdbc.scalatest._
import org.joda.time._

class StudentSpec extends FixtureAnyFlatSpec with Matchers with DBSettings with AutoRollback {
}

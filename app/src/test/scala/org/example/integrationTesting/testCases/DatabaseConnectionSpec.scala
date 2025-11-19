package org.example.integrationTesting.testCases

import org.example.integrationTesting.configsAndConnections.H2DBConnection
import org.mockito.scalatest.MockitoSugar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DatabaseConnectionSpec extends AnyFlatSpec with MockitoSugar with Matchers{
  val db = new H2DBConnection
  val con = db.getConnection

  it should "establish connection" in {
    con should not be null
    con.isClosed should be (false)
  }

}
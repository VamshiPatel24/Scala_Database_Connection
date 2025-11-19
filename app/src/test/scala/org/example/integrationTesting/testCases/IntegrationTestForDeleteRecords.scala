package org.example.integrationTesting.testCases

import org.example.integrationTesting.InsertIntegrationTest
import org.example.topArtist.repository.DBConnection
import org.example.topArtist.service.Artist

import java.sql.Connection
import scala.util.Using

class IntegrationTestForDeleteRecords extends InsertIntegrationTest {
  it should "delete record with given rank" in {
    Using.resource(connection.createStatement()) { stmt =>


      val artistOp = new DBConnection {
        override def getConnection = connection
      }

      val artist = new Artist {
        override val getConnection: Connection = artistOp.getConnection
      }


      artist.delete("2")

      val rs = stmt.executeQuery("SELECT COUNT(*) FROM Artists where rank = '2' ")
      rs.next()
      rs.getInt(1) shouldBe 0
    }
  }

}

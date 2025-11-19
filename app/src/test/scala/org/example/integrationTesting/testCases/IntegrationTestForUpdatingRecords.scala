package org.example.integrationTesting.testCases

import org.example.integrationTesting.InsertIntegrationTest
import org.example.topArtist.repository.DBConnection
import org.example.topArtist.service.Artist

import java.sql.Connection
import scala.util.Using

class IntegrationTestForUpdatingRecords extends InsertIntegrationTest{
  it should "update playCount for rank = 2" in {
    Using.resource(connection.createStatement()) { stmt =>


      val artistOp = new DBConnection {
        override def getConnection = connection
      }

      val artist = new Artist {
        override val getConnection: Connection = artistOp.getConnection
      }


      artist.update("9999")


      val rs = stmt.executeQuery("SELECT playCount FROM Artists WHERE rank = '2'")
      rs.next() shouldBe true
      rs.getString("playCount") shouldBe "9999"
    }
  }
  it should "it shoul" in {
    Using.resource(connection.createStatement()) { stmt =>


      val artistOp = new DBConnection {
        override def getConnection = connection
      }

      val artist = new Artist {
        override val getConnection: Connection = artistOp.getConnection
      }


      artist.update("9999")


      val rs = stmt.executeQuery("SELECT playCount FROM Artists WHERE rank = '2'")
      rs.next() shouldBe true
      rs.getString("playCount") shouldBe "9999"
    }
  }
}

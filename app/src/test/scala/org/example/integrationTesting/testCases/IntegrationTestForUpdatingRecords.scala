package org.example

import org.example.integrationTesting.InsertIntegrationTest
import org.example.topArtist.repository.DBConnection
import org.example.topArtist.service.Artist

import java.sql.{Connection}
import scala.util.Using

class ArtistUpdateIT extends InsertIntegrationTest {

  it should "update record with given rank" in {
    Using.resource(connection.createStatement()) { stmt =>


      val artistOp = new DBConnection {
        override def getConnection = connection
      }

      val artist = new Artist {
        override val getConnection: Connection = artistOp.getConnection
      }

      val ps = connection.prepareStatement(
        "update Artists set playCount = '555' where rank = 99"
      )

      val rowsUpdated = ps.executeUpdate()

      assert(rowsUpdated == 0) // <--- failure scenario

    }
  }
}
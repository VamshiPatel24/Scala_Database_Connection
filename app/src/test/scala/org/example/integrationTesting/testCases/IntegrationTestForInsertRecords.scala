package org.example.integrationTesting

import org.example.integrationTesting.configsAndConnections.H2DBConnection
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import com.github.tototoshi.csv.{CSVWriter}
import org.example.topArtist.repository.DBConnection
import org.example.topArtist.service.Artist

import java.io.File
import java.sql.{Connection, Statement}
import scala.util.Using

class InsertIntegrationTest extends AnyFlatSpec with Matchers {

  val db = new H2DBConnection
  val connection: Connection = db.getConnection

  def createArtistsTable(stmt: Statement): Unit = {
    stmt.execute(
      """

         CREATE TABLE Artists(
          userId VARCHAR(255),
          rank VARCHAR(255),
          artistName VARCHAR(500),
          playCount VARCHAR(500),
          mbid VARCHAR(500)
        );
      """
    )
  }

  behavior of "Artist.insert()"

  it should "insert a single record using List input" in {
    Using.resource(connection.createStatement()) { stmt =>
      createArtistsTable(stmt)

      val artistOp = new DBConnection {
        override def getConnection = connection
      }
      val artist = new Artist {
        override val getConnection: Connection = artistOp.getConnection
      }
      val dataList = List("U100", "1", "Adele", "1000", "MB123")

      noException shouldBe thrownBy {
        artist.insert(null, dataList)
      }

      val rs = stmt.executeQuery("SELECT * FROM Artists")
      rs.next() shouldBe true
      rs.getString("userId") shouldBe "U100"
      rs.getString("rank") shouldBe "1"
      rs.getString("artistName") shouldBe "Adele"
      rs.getString("playCount") shouldBe "1000"
      rs.getString("mbid") shouldBe "MB123"
    }
  }
  it should "insert records from CSV file" in {
    Using.resource(connection.createStatement()) { stmt =>
      // createArtistsTable(stmt)

      // Create temp CSV file
      val tempCsv = File.createTempFile("artists", ".csv")
      val writer = CSVWriter.open(tempCsv)

      writer.writeRow(List("user_id", "rank", "artist_name", "playcount", "mbid"))
      writer.writeRow(List("U1", "1", "Artist1", "500", "MB1"))
      writer.writeRow(List("U2", "2", "Artist2", "900", "MB2"))


      val artistOp = new DBConnection {
        override def getConnection = connection
      }
      val artist = new Artist {
        override val getConnection: Connection = artistOp.getConnection
      }
      noException shouldBe thrownBy {
        artist.insert(tempCsv.getAbsolutePath)
      }

      val rs = stmt.executeQuery("SELECT COUNT(*) FROM Artists")
      rs.next()
      rs.getInt(1) shouldBe 3
    }
  }

  it should "fail when CSV file does not exist" in {
    val artistOp = new DBConnection {
      override def getConnection = connection
    }
    val artist = new Artist {
      override val getConnection: Connection = artistOp.getConnection
    }

    an[Exception] shouldBe thrownBy(artist.insert(null))

  }

}
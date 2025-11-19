package org.example.topArtist.service

import com.github.tototoshi.csv.CSVReader
import com.typesafe.scalalogging.Logger
import org.example.topArtist.repository.DBConnection
import org.slf4j.LoggerFactory

class Artist {
  val logger = LoggerFactory.getLogger(getClass)
  val getConnection = new DBConnection().getConnection
  def create: Unit = {
    val stmt = getConnection.createStatement()
    val create = "CREATE TABLE Artists(userId varchar(1000), rank varchar(1000), artistName varchar(1000), playCount varchar(500), mbid varchar(500))"
    logger.info("table created successfully")
    stmt.execute(create)
  }
 def update(rank: String): Unit = {
  val ps = getConnection.prepareStatement(s"update Artists set playCount = '555' where rank = $rank ")
  ps.executeUpdate()
   logger.info("record updated successfully")
 }

  def delete(rank: String): Unit = {
    val stmt = getConnection.createStatement()
    val delete = s"delete from Artists where rank = $rank"
    stmt.executeUpdate(delete)
    logger.info("record deleted successfully")
  }
def insert(filePath:String = null,l:List[String] = List() ):Unit= {

  def readFile(path:String = filePath): Unit = {
    if (filePath != null || !l.isEmpty) {
      try {
        val con = getConnection


        val insertq = "insert into Artists(userId, rank, artistName, playCount, mbid) values (?,?,?,?,?)"
        con.setAutoCommit(false)
        try {
          val ps = con.prepareStatement(insertq)
          var count = 0
          var total = 0
          if (!l.isEmpty) {
            logger.info("list is not empty")
            for (i <- 0 to l.length - 1) {
              ps.setString(i + 1, l(i))
              logger.info(l(i) + " inserted at" + (i + 1))
            }
            con.commit()
            ps.execute()
          }
          if (path != null) {
            try {
              val reader = CSVReader.open(filePath)
              val records = reader.iteratorWithHeaders
              records.foreach {
                row =>
                  val top = Artists(
                    userId = row.getOrElse("user_id", ""),
                    rank = row.getOrElse("rank", ""),
                    artistName = row.getOrElse("artist_name", ""),
                    playCount = row.getOrElse("playcount", ""),
                    mbid = row.getOrElse("mbid", "")
                  )
                  ps.setString(1, top.userId)
                  ps.setString(2, top.rank)
                  ps.setString(3, top.artistName)
                  ps.setString(4, top.playCount)
                  ps.setString(5, top.mbid)
                  ps.addBatch()
                  count += 1
                  total += 1
                  if (count >= 30000) {
                    ps.executeBatch()
                    count = 0
                    logger.info(s"$total records inserted")
                  }
              }

            }
            catch {
              case e: Exception => e.printStackTrace()
            }
          }

          ps.execute()
          con.commit()
        }
        catch {
          case e: Exception => e.printStackTrace()
        }
      }
      catch {
        case e: Exception => e.printStackTrace()
      }
    }
    else {
      throw new NullPointerException()
    }
  }

    readFile(filePath)
}
}

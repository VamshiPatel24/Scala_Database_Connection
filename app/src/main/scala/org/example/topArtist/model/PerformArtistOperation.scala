package org.example.topArtist.model
import com.typesafe.config.ConfigFactory
import org.example.topArtist.service.{Artist, TopArtistImplementation}
object PerformArtistOperation extends App{
  val path = ConfigFactory.load("app").getConfig("adapters").getString("path")
  val artist = new Artist
  //artist.create
  //artist.insert(path)
  //artist.delete("25")
  artist.update("50")

}
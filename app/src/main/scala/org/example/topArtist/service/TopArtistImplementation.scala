package org.example.topArtist.service

import com.typesafe.config.{Config, ConfigFactory}

object TopArtistImplementation extends TopArtistOperation {
  override def msurl: String = config.getString("msurl")

  override def username: String = config.getString("username")

  override def config = loader.getConfig("adapters")

  override def loader: Config = ConfigFactory.load("app.conf")

  override def password: String = config.getString("password")

  override def path: String = config.getString("path")

}
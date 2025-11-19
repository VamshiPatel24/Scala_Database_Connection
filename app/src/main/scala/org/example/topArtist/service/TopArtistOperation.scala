package org.example.topArtist.service

import com.typesafe.config.Config

trait TopArtistOperation {
  def loader: Config = ???

  def config: Config = ???

  def msurl: String = ???

  def username: String = ???

  def password: String = ???

  def path: String = ???
}

name := "Portfolio Analyzer"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0-RC1" % Test
)

libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.0.3",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3",
  "com.h2database" % "h2" % "1.4.192",
  "ch.qos.logback" % "logback-classic" % "1.1.2"
)

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"





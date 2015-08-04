javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

name := "scala-sns-slack"

scalaVersion := "2.11.7"

version := "1.0"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-lambda-java-core" % "1.0.0",
  "com.amazonaws" % "aws-lambda-java-events" % "1.0.0",
  "org.scalatest" %% "scalatest" % "2.1.3" % "test"
)


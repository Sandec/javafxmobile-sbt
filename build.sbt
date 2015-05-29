sbtPlugin := true

addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.12.0")

name := "javafxmobile-sbt"
organization := "SANDEC"
version := "0.1.2"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:_")


publishMavenStyle := false
bintrayRepository := "repo"
licenses += ("MIT", url("http://opensource.org/licenses/MIT"))
bintrayOrganization := Some("sandec")
name := """$name$"""
organization := "$organization$"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.1"

apidocVersion := Some("0.1.0")
apidocOutputDir := (classDirectory in Compile).value / "apidoc"
apidocVersionFile := (resourceDirectory in Compile).value / "apidocDefinitions"
apidocOrder := List("CreateUser", "GetUser", "UpdateUser", "DeleteUser")
apidocTitle := Some("""play with sbt-apidoc Demo""")
apidocSampleURL := Some("http://localhost:9000")
// Make the apidoc run before the run command
run := (run in Compile).dependsOn(apidoc).evaluated
unmanagedResourceDirectories in Assets += apidocOutputDir.value

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test

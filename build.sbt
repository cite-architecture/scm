name := "Scala CITE manager"

//crossScalaVersions := Seq("2.10.6","2.11.8", "2.12.1")
//crossScalaVersions := Seq("2.10.6","2.11.8")

//crossScalaVersions := Seq("2.11.8", "2.12.1")
crossScalaVersions := Seq("2.11.8")
scalaVersion := "2.11.8"

lazy val root = project.in(file(".")).
    aggregate(crossedJVM, crossedJS).
    settings(
      publish := {},
      publishLocal := {}

    )

lazy val crossed = crossProject.in(file(".")).
    settings(
      name := "scm",
      organization := "edu.holycross.shot",
      version := "3.6.0",
      licenses += ("GPL-3.0",url("https://opensource.org/licenses/gpl-3.0.html")),
      resolvers += Resolver.jcenterRepo,
      resolvers += "beta" at "http://beta.hpcc.uh.edu/nexus/content/repositories/releases",
      resolvers += Resolver.bintrayRepo("neelsmith", "maven"),
      libraryDependencies ++= Seq(
        "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided",
        "org.scalatest" %%% "scalatest" % "3.0.1" % "test",

        "edu.holycross.shot.cite" %%% "xcite" % "2.3.2",
        "edu.holycross.shot" %%% "ohco2" % "8.0.1",
        "edu.holycross.shot" %%% "citeobj" % "3.1.1",
        "edu.holycross.shot" %%% "citerelations" % "1.1.1",
        "edu.holycross.shot" %%% "cex" % "4.1.0"
      )
    ).
    jvmSettings(
      libraryDependencies ++= Seq(
        "edu.holycross.shot" % "hocuspocus" % "2.1.5"
      )
    ).
    jsSettings(
      skip in packageJSDependencies := false,
      persistLauncher in Compile := true,
      persistLauncher in Test := false

    )

lazy val crossedJVM = crossed.jvm
lazy val crossedJS = crossed.js.enablePlugins(ScalaJSPlugin)

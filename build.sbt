name := "LiftExample"

scalaVersion := "2.9.1"

scalacOptions += "-deprecation"

seq(webSettings :_*)

// If using JRebel
scanDirectories in Compile := Nil

logLevel := Level.Info

EclipseKeys.withSource := true

libraryDependencies ++= {
  val liftVersion = "2.4"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile->default"  withSources(),
    "net.liftweb" %% "lift-mapper" % liftVersion % "compile->default"  withSources(),
    "net.liftweb" %% "lift-widgets" % liftVersion % "compile->default"  withSources(),
    "net.liftweb" %% "lift-json" % liftVersion % "compile->default"  withSources(),
    "net.liftweb" %% "lift-util" % liftVersion % "compile->default"  withSources(),
    "net.liftweb" %% "lift-actor" % liftVersion % "compile->default"  withSources(),
    "net.liftweb" %% "lift-wizard" % liftVersion % "compile->default"  withSources(),
    "net.liftweb" %% "lift-testkit" % liftVersion % "compile->default"  withSources()
  )
}

libraryDependencies ++= Seq(
  "joda-time" % "joda-time" % "1.6.2"  withSources(),
  "junit" % "junit" % "4.5" % "test->default",
  "org.mortbay.jetty" % "jetty" % "6.1.22" % "container,test",
  "javax.servlet" % "servlet-api" % "2.5" % "provided->default",
  "com.h2database" % "h2" % "1.2.138",
  "org.apache.httpcomponents" % "httpclient" % "4.1" % "compile->default"  withSources(),
  "org.scala-tools.testing" %% "specs" % "1.6.9" % "test->default",
  "ch.qos.logback" % "logback-classic" % "0.9.26" % "compile->default"
)

resolvers ++= Seq(
    "apache.repo" at "https://repository.apache.org/content/repositories/snapshots/",
    "sonatype.repo" at "https://oss.sonatype.org/content/repositories/public/"
)


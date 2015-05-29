# javafxmobile-sbt (early version)
This projects allows Scala-Developers build [SimpleFX](http://sandec.de)/[ScalaFX](http://www.scalafx.org/)/JavaFX-Apps for android or iOS from sbt.
It creates a gradle project with the [javafxmobile-plugin](https://bitbucket.org/javafxports/javafxmobile-plugin) and calls it from sbt.

## Requirements
 - [Gradle](http://gradle.org/) Installed
 - AndroidSDK Installed
 
## How To
Add the following to your project/plugins.sbt:
```scala
resolvers += Resolver.url("SANDEC", url("http://sandec.de/repo/"))(Resolver.ivyStylePatterns)
"SANDEC" % "javafxmobile-sbt" % "0.1.2-SNAPSHOT"
```
Add the following to your build.sbt:
```scala
enablePlugins(plugin.JavaFXMobilePlugin)
```

## Customize
Will follow soon

## Commands
take a look here, for a complete list of all available commands: http://javafxports.org/page/Getting_Started
- create android apk: `sbt "javafx android"`
- install android apk: `sbt "javafx androidInstall"`
- start on atached iOS-device: `sbt "javafx launchIOSDevice"`


# Sample Projects
 - [SimpleFX-Samples](https://github.com/Sandec/SimpleFX-Samples/blob/master/project/plugins.sbt)
 - JavaFX Applictation - coming soon
 - ScalaFX Applictation - coming soon

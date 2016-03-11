# javafxmobile-sbt
This projects allows Scala-Developers build [SimpleFX](http://sandec.de)/[ScalaFX](http://www.scalafx.org/)/JavaFX-Apps for android or iOS from sbt.
It creates a gradle project with the [javafxmobile-plugin](https://bitbucket.org/javafxports/javafxmobile-plugin) and calls it from sbt. This version is an early but useful version!

## Requirements
 - [Gradle](http://gradle.org/) Installed
 - AndroidSDK Installed
 
## How To
Add the following to your project/plugins.sbt:
```scala
resolvers += Resolver.url("SANDEC", url("http://dl.bintray.com/sandec/repo"))(Resolver.ivyStylePatterns)
"SANDEC" % "javafxmobile-sbt" % "0.1.3"
```
Add the following to your build.sbt:
```scala
enablePlugins(plugin.JavaFXMobilePlugin)
```

## Customize
Will follow soon

## Commands
Some available commands:
- create android apk: `sbt "javafx android"`
- install android apk: `sbt "javafx androidInstall"`
- start on atached iOS-device: `sbt "javafx launchIOSDevice"`

[A list with all commands](http://javafxports.org/page/Getting_Started)


# Sample Projects
 - [SimpleFX-Samples](https://github.com/Sandec/SimpleFX-Samples/blob/master/project/plugins.sbt)
 - JavaFX Applictation - coming soon
 - ScalaFX Applictation - coming soon

package plugin

import sbt._
import sbt.Keys._
import sbtassembly.AssemblyPlugin
import sbtassembly.AssemblyPlugin.autoImport._
import sbt.complete.Parsers._

object JavaFXMobilePlugin extends AutoPlugin{

  object autoImport {
    lazy val createGradleProject    = taskKey[File]  ("Produces a gradle project, which uses the JavaFX-Mobile plugin. The result is the folder of the project.")
    lazy val javafx                 = inputKey[Unit] ("Calls the gradle project with the given arguments.")
    lazy val appName                = taskKey[String]("The name of the generated application.")
    lazy val gradleBuildContent     = taskKey[String]("The content of the build.gradle file.")
    lazy val javafx_mobile_version  = taskKey[String]("Version of the javafx-mobile-plugin")
    lazy val ios_forceLinkClasses   = taskKey[String]("forceLinkClasses in the gradle project")
    lazy val iosSignIdentity        = taskKey[Option[String]]("The SignIdentity used to build the iOS-App")
    lazy val iosProvisioningProfile = taskKey[Option[String]]("The ProvisioningProfile used to build the iOS-App")

  }
  import autoImport._

  override def requires = AssemblyPlugin

  val utf8 = java.nio.charset.Charset.forName("UTF-8")

  override def projectSettings = List(
    javafx_mobile_version  := "org.javafxports:jfxmobile-plugin:1.0.0-b9",
    ios_forceLinkClasses   := "['ensemble.**.*']",
    iosSignIdentity        := None,
    iosProvisioningProfile := None,
    appName                := name.value,
    createGradleProject := {
      val folder = new File(target.value, "gradleProj")
      folder.mkdir

      val buildFile = new File(folder, "build.gradle")
      IO.write(buildFile, gradleBuildContent.value, utf8)

      val settingsFile = new File(folder, "settings.gradle")
      IO.write(settingsFile, s"rootProject.name = '${appName.value}'", utf8)

      assembly.value
      folder
    },

    javafx := {
      val args: Seq[String] = spaceDelimited("<arg>").parsed
      val cmd = args.toList

      val commandUnix = Process("gradle" :: cmd, createGradleProject.value)
      val commandCMD  = Process("cmd.exe" :: "/c" :: "gradle.bat" :: cmd, createGradleProject.value)
      val exitCode = try {
        commandUnix !
      } catch {
        case e: java.io.IOException => //couldn't found gradle, now trying cmd for windows users
          commandCMD !
      }
      if(exitCode != 0) {
        sys.error(s"the command gradle ${cmd.reduce(_ + " " + _)} returned the exitcode $exitCode !")
      }
    },

    gradleBuildContent := {
      val main = (mainClass in assembly).value.getOrElse(
        sys.error("You have to specify mainClass in sbt (mainClass := Some(\"my.Main\")) or declare only 1 object with a main method.")
      )
s"""System.setProperty("file.encoding", "UTF-8")

buildscript {
    repositories {
        jcenter()
    }

    dependencies {
        classpath '${javafx_mobile_version.value}'
    }
}

apply plugin: 'org.javafxports.jfxmobile'

mainClassName = '${main}'

dependencies {
  compile files('../scala-2.11/${(assemblyJarName in assembly).value}')
}

repositories {
    jcenter()
}


jfxmobile {
    ios {
        forceLinkClasses = ${ios_forceLinkClasses.value}
        ${iosSignIdentity       .value.map{"iosSignIdentity        = '" + _ + "'"}.getOrElse("")}
        ${iosProvisioningProfile.value.map{"iosProvisioningProfile = '" + _ + "'"}.getOrElse("")}
    }
    android {
        applicationPackage = '${main}'
    }
}
"""
  })
}

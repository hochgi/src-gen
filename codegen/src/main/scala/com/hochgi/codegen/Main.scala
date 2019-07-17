package com.hochgi.codegen

import java.io.File

object Main extends App {

  implicit class FileOps(file: File) {
    def /(child: String): File =
      if (child == ".") new File(child)
      else new File(file, child)
  }

  args.headOption match {
    case None => System.err.println("You must supply a directory path to generate scala files under.")
    case Some(dirName) => {

      val basedir = new java.io.File(dirName)
      val canContinue = {
        if (basedir.exists()) basedir.isDirectory
        else basedir.mkdirs()
      }

      if(!canContinue) System.err.println("invalid path supplied")
      else {

        val packageDir = basedir / "com" / "hochgi" / "codegen"
        if(!packageDir.mkdirs() && (!packageDir.exists() || !packageDir.isDirectory))
          System.err.println(s"unable to mkdirs(${packageDir.getAbsolutePath})")
        else {

          val main = packageDir / "Main.scala"
          val pw = new java.io.PrintWriter(main)
          pw.write("""package com.hochgi.codegen
                     |
                     |object Main extends App {
                     |
                     |  def add(l: Int, r: Int): Int = l + r
                     |
                     |  println("Hello Scala Codegen!")
                     |}""".stripMargin)
          pw.close()
          println("SUCCESS")
        }
      }
    }
  }
}

val generateScalaCodegen = taskKey[Unit]("run codegen to generate sources")
val listScalaCodegenFiles = taskKey[Seq[File]]("list generated sources")


def listFilesRec(f: File, acc: List[File] = List.empty[File]): List[File] = {
  IO.listFiles(f).foldLeft(acc)((list,file) => {
    if(file.isDirectory) listFilesRec(file,list)
    else file :: list
  })
}


val codegen = project in file("codegen")

val usedgen = (project in file("usedgen"))
  .settings(
  libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.8" % Test,
  generateScalaCodegen := {
    val logger = streams.value.log
    val generatedCodedir = (sourceManaged.value / "main" / "scala").getAbsolutePath
//    (codegen / Compile / runMain).fullInput(s"com.hochgi.codegen.Main $generatedCodedir").evaluated
    val cp = Attributed.data((codegen / Compile / fullClasspath).value)
    (codegen / Compile / runner).value.run("com.hochgi.codegen.Main",cp,Seq(generatedCodedir),logger)
  },
  listScalaCodegenFiles := {
    val logger = streams.value.log
    generateScalaCodegen.value
    val generatedCodedir = sourceManaged.value / "main" / "scala"
    listFilesRec(generatedCodedir)
  },
  Compile / sourceGenerators += listScalaCodegenFiles.taskValue,
  mainClass := Some("com.hochgi.codegen.Main")
)



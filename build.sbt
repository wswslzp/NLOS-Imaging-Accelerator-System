name := "nlos_system"
version := "1.0"
scalaVersion := "2.12.10"
val spinalVersion = "1.4.1"

libraryDependencies ++= Seq(
  //"org.scalatest" % "scalatest_2.12" % "2.2.1",
  "com.github.spinalhdl" % "spinalhdl-core_2.12" % spinalVersion,
  "com.github.spinalhdl" % "spinalhdl-lib_2.12" % spinalVersion,
  compilerPlugin("com.github.spinalhdl" % "spinalhdl-idsl-plugin_2.12" % spinalVersion)
)

fork := true

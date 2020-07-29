name := "nlos_system"
version := "1.0"
scalaVersion := "2.11.12"
val spinalVersion = "1.4.2"

libraryDependencies ++= Seq(
  //"org.scalatest" %% "scalatest_2.12" % "2.2.1",
  "com.github.spinalhdl" %% "spinalhdl-core" % spinalVersion,
  "com.github.spinalhdl" %% "spinalhdl-lib" % spinalVersion,
  compilerPlugin("com.github.spinalhdl" %% "spinalhdl-idsl-plugin" % spinalVersion)
)

fork := true

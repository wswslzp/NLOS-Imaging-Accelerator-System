name := "nlos_system"
version := "1.0"
scalaVersion := "2.12.13"
val spinalVersion = "1.7.0"

libraryDependencies ++= Seq(
  "org.bytedeco" % "javacv-platform" % "1.5.7",
  "org.scalanlp" %% "breeze" % "1.1",
  "org.scalanlp" %% "breeze-natives" % "1.1",
  "org.scalanlp" %% "breeze-viz" % "1.1",
  "com.github.spinalhdl" %% "spinalhdl-core" % spinalVersion,
  "com.github.spinalhdl" %% "spinalhdl-lib" % spinalVersion,
  compilerPlugin("com.github.spinalhdl" %% "spinalhdl-idsl-plugin" % spinalVersion)
)

//libraryDependencies += "com.sksamuel.scrimage" % "scrimage-core" % "4.0.30"
//libraryDependencies += "com.sksamuel.scrimage" %% "scrimage-scala" % "4.0.31"
//
fork := true
javaOptions := Seq("-Xmx16G")

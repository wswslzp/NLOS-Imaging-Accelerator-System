name := "nlos_system"
version := "1.0"
scalaVersion := "2.12.13"
val spinalVersion = "1.6.1"

libraryDependencies ++= Seq(
  "org.bytedeco" % "javacv-platform" % "1.5.3",
  "org.scalanlp" %% "breeze" % "1.1",
  "org.scalanlp" %% "breeze-natives" % "1.1",
  "org.scalanlp" %% "breeze-viz" % "1.1",
  "com.github.spinalhdl" %% "spinalhdl-core" % spinalVersion,
  "com.github.spinalhdl" %% "spinalhdl-lib" % spinalVersion,
  compilerPlugin("com.github.spinalhdl" %% "spinalhdl-idsl-plugin" % spinalVersion)
)

//libraryDependencies ++= Seq(
//  "org.jgrapht" % "jgrapht-core" % "1.4.0",
//  "org.jgrapht" % "jgrapht-ext" % "1.4.0",
//  "org.jgrapht" % "jgrapht-io" % "1.4.0",
//)

fork := true
javaOptions := Seq("-Xmx16G")

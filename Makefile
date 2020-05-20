DESIGN := RsdKernelGen

all: compile run

compile: 
	sbt "compile" | tee compile.log

run:
	sbt "runMain ${DESIGN}Main" | tee runMain.log

DESIGN := RsdKernelGen

all: compile run

gitup: 
	git add -A
	git commit -m "update daily"
	git push vsp

compile: 
	sbt "compile" | tee compile.log

run:
	sbt "runMain ${DESIGN}Main" | tee runMain.log

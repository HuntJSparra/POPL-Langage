ANTLR4=java -Xmx500M -cp "/usr/local/lib/antlr-4.7.1-complete.jar:$CLASSPATH" org.antlr.v4.Tool
AFLAGS=-no-listener -visitor
JC=javac
BOOLLANG_OUTPUT = boollangVisitor.java boollangParser.java boollangLexer.java boollangBaseVisitor.java

default: main

$(BOOLLANG_OUTPUT): boollang.g4
	$(ANTLR4) boollang.g4 $(AFLAGS)

%.class: %.java
	$(JC) *.java

main: Main.java $(BOOLLANG_OUTPUT)
	$(JC) $^

run: main
	java Main

clean:
	rm -f *.class
	rm -f boollang*.interp
	rm -f boollang*.tokens
	rm -f boollang*.java
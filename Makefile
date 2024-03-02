# Makefile pour compiler le squelette de code distribué

# Organisation:
#  1) Les sources (*.java) se trouvent dans le répertoire src
#     Les classes d'un package toto sont dans src/toto
#
#  2) Les bytecodes (*.class) sont générés dans le répertoire bin
#     La hiérarchie des sources (par package) est conservée.
#
#  3) Une librairie objbc6.jar est distribuée pour utiliser l'API JDBC. 
#     Elle se trouve dans le répertoire principal.
#
# Compilation:
#  Options de javac:
#   -d : répertoire dans lequel sont générés les .class compilés
#   -sourcepath : répertoire dans lequel sont cherchés les .java
#   -classpath : répertoire dans lequel sont cherchées les classes compilées (.class et .jar)

PROJECTDIR = .
SRCDIR = src
BINDIR = bin
DOCDIR = documentation
# Find all Java files
JAVA_FILES := $(shell find ./ -type f -name "*.java")

PACKAGE_NAMES = app sql.ExemplesJava sql.Requetes

all: start

# Compile
compileJavaProject: $(JAVA_FILES)
	javac -d bin -classpath $(PROJECTDIR)/ojdbc6.jar $^

# Start the application
start: compileJavaProject run

run:
	java -classpath bin:$(PROJECTDIR)/ojdbc6.jar app/MainApp

javadoc:
	# javadoc -d $(DOCDIR) -classpath objbc6.jar -sourcepath src $(PACKAGE_NAMES)

clean:
	rm -rf bin/

cleanjavadoc:
	rm -rf $(DOCDIR)


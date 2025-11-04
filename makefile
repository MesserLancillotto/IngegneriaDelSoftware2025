clean:
	rm -rf bin/*

all:
	mvn compile
anew:
	mvn clean compile -U;

exec:
	java -cp "bin:lib/*" Server.Server

h2:
	java -cp lib/h2-*.jar org.h2.tools.Console
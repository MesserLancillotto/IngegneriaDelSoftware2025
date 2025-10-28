clean:
	rm -rf bin/*

all:
	mvn compile
	# mvn dependency:copy-dependencies -DoutputDirectory=lib

anew:
	mvn clean compile -U;

exec:
	java -cp "bin:lib/*" Server.Server

h2_debug:
	java -cp lib/h2-*.jar org.h2.tools.Console
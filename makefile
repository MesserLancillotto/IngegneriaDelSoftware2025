clean:
	rm -rf bin/*

all:
	mvn compile
	# mvn dependency:copy-dependencies -DoutputDirectory=lib

exec:
	java -cp "bin:lib/*" Server.Server
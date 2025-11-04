compile_server:
	javac -d bin src/Server/*.java
run_server:
	java -cp bin Server.Server
compile_client:
	javac -d bin src/Client/*.java
run_client:
	java -cp bin Client.Client
clean:
	rm -rf bin/*
exec:
	java -cp "bin:lib/*" Client.Client
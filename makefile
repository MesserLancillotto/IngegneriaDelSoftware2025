compile_server:
	javac -d bin src/Server/*.java
run_server:
	java -cp bin Server.Server
request_classes:
	javac -d bin src/RequestReply/*.java
	javac -d bin src/RequestReply/Request/*.java
compile_client:
	javac -d bin src/Client/*.java
run_client:
	java -cp bin Client.Client
clean:
	rm -rf bin/*
all:
	javac -d bin src/Server/*.java
	javac -d bin src/RequestReply/**/*.java
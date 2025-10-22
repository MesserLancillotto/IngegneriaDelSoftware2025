client:
	javac -d bin src/Client/*.java

server:
	javac -d bin src/Server/*.java

comunication_type:
	javac -d bin src/RequestReply/ComunicationType/*.java

user_role_title:
	javac -d bin src/RequestReply/UserRoleTitle/*.java

request:
	# dipendenze
	javac -d bin src/RequestReply/ComunicationType/*.java
	javac -d bin src/RequestReply/UserRoleTitle/*.java
	javac -d bin src/RequestReply/Request/RequestType.java
	# classe principale
	javac -d bin -cp bin src/RequestReply/Request/*.java

reply:
	javac -d bin -cp bin src/RequestReply/Reply/*.java

request_reply:
	javac -d bin src/RequestReply/**/*.java

clean:
	rm -rf bin/*

all:
	javac -d bin src/**/*.java
	javac -d bin src/**/**/*.java
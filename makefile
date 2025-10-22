client:
	rm -rf bin/Client
	javac -d bin src/Client/*.java

server:
	rm -rf bin/Server
	javac -d bin src/Server/*.java

comunication_type:
	rm -rf bin/RequestReply/ComunicationType
	javac -d bin src/RequestReply/ComunicationType/*.java

user_role_title:
	rm -rf bin/RequestReply/UserRoleTitle
	javac -d bin src/RequestReply/UserRoleTitle/*.java

request:
	rm -rf bin/RequestReply/Request
	# dipendenze
	javac -d bin src/RequestReply/ComunicationType/*.java
	javac -d bin src/RequestReply/UserRoleTitle/*.java
	javac -d bin src/RequestReply/Request/RequestType.java
	# classe principale
	javac -d bin -cp bin src/RequestReply/Request/*.java

reply:
	rm -rf bin/Reply
	javac -d bin -cp bin src/RequestReply/Reply/*.java

request_reply:
	rm -rf bin/RequestReply
	javac -d bin src/RequestReply/**/*.java

clean:
	rm -rf bin/*

all:
	rm -rf bin/*
	javac -d bin src/**/*.java
	javac -d bin src/**/**/*.java

install_h2:
	wget -P lib/ https://repo1.maven.org/maven2/com/h2database/h2/2.2.224/h2-2.2.224.jar

admin_db:
	javac -cp "lib/h2-2.2.224.jar" -d bin/ src/Server/Engine/Engine.java 
	java -cp "bin:lib/h2-2.2.224.jar" Server.Engine.Engine
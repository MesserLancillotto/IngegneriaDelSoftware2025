# IngegneriaDelSoftware2025
## Compilare ed eseguire il progetto
### Lato server
```
make compile_server 
make run_server
```
### Lato client
NOTA: il client non deve essere usato da se, difatti è sprovvisto di main!
La classe Client deve essere chiamata da qualche parente che necessita di invocare le API del server! 
```
make compile_client
```

## Compilare i diagrammi UML indicativi del progetto
Per compilare gli UseCasesUML, installare un renderer per mermaid, su ArchLinux 
```
sudo pacman -S mermaid-cli
```
o su Apple
```
brew install mermaid-cli
```
o su Apple usando node
```
brew install node
npm install -g @mermaid-js/mermaid-cli
```
ed eseguire
```
./absolute_compiler.sh
```
usare il parametro `-h` o `--help` per ulteriori informazioni.
Altrimenti copiare ed incollare su [mermaid.live](https://mermaid.live)

@echo off

java -DENV=sub -classpath lib/*;target/classes/. jade.Boot -container -host localhost -port 1111 -agents "ControllerAgent:asofth.prototype.agent.ControllerAgent"
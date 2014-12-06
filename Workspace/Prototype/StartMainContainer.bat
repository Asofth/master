@echo off

java -DENV=main -classpath lib/*;target/classes/. jade.Boot -host localhost -port 1111 -gui -services jade.core.mobility.AgentMobilityService;jade.core.event.NotificationService;jade.core.replication.AgentReplicationService -agents "MainControllerAgent:asofth.prototype.agent.ControllerAgent"
@echo off

java -DENV=sub -classpath lib/*;target/classes/. jade.Boot -container -host localhost -port 1111 -gui -services jade.core.mobility.AgentMobilityService;jade.core.event.NotificationService;jade.core.replication.AgentReplicationService -agents "ControllerAgent:asofth.prototype.agent.ControllerAgent"
COMPOSE_FILE=docker-compose.yaml

up:
	docker-compose -f $(COMPOSE_FILE) up

down:
	docker-compose -f $(COMPOSE_FILE) down

restart: down up

logs:
	docker-compose -f $(COMPOSE_FILE) logs -f

status:
	docker-compose -f $(COMPOSE_FILE) ps

# Löscht Container und Volumes vollständig
fclean:
	docker-compose -f $(COMPOSE_FILE) down -v

info:
	@ echo "up \t \tdocker-compose -f $(COMPOSE_FILE) up"
	@ echo "down \t \tdocker-compose -f $(COMPOSE_FILE) down"
	@ echo "restart \tdocker-compose -f $(COMPOSE_FILE) down && docker-compose -f $(COMPOSE_FILE) up"
	@ echo "logs \t \tdocker-compose -f $(COMPOSE_FILE) logs -f"
	@ echo "status \t\tdocker-compose -f $(COMPOSE_FILE) ps"
	@ echo "fclean \t\tdocker-compose -f $(COMPOSE_FILE) down -v"

.PHONY: up down restart logs status fclean info
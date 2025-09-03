COMPOSE_FILE=docker-compose.yaml
FRONTEND_DIR=frontend/ContactSyncVue
BACKEND_DIR=backend/src/main
BACKEND_STATIC=$(BACKEND_DIR)/resources/static
BACKEND_JAR=build/libs/ContactSync-1.0-SNAPSHOT.jar
JAR=build/libs/app.jar

# --- FRONTEND ---

# Install dependencies (once)
frontend-install:
	@echo "Installing frontend dependencies..."
	@cd $(FRONTEND_DIR) && npm install

# Build frontend and copy to Spring Boot static folder
frontend-build: frontend-install
	@echo "Building frontend..."
	@cd $(FRONTEND_DIR) && npm run build

# Run frontend dev server
frontend-dev:
	@echo "Starting frontend dev server..."
	@cd $(FRONTEND_DIR) && npm run dev

# --- BACKEND / SPRING BOOT ---

# Build backend with frontend
build: frontend-build
	@echo "Building Spring Boot backend..."


# --- DOCKER COMPOSE ---

up: compose-up frontend-dev

compose-up:
	docker-compose -f $(COMPOSE_FILE) up -d # detached otherwise npm run dev can't execute

down:
	docker-compose -f $(COMPOSE_FILE) down

re: fclean build up

# Full clean (containers + volumes + frontend dist)
fclean:
	docker-compose -f $(COMPOSE_FILE) down -v




# make only backend
backend: compose-up
	gradle bootRun



logs:
	@docker-compose -f $(COMPOSE_FILE) logs -f

status:
	@docker-compose -f $(COMPOSE_FILE) ps


# Info/help
info:
	@echo "Targets:"
	@echo "  up \t\t Start docker-compose"
	@echo "  down \t\t Stop docker-compose"
	@echo "  restart \t Restart everything"
	@echo "  logs \t\t Follow logs"
	@echo "  status \t Show container status"
	@echo "  fclean \t Clean everything"
	@echo "  frontend-dev \t Run frontend dev server"

.PHONY: frontend-install frontend-build frontend-dev build up down restart re logs status fclean info

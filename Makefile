
default:
	@echo "Available make targets:"
	@echo "  ${GREEN}up${NC}             : Build and start web backend + frontend in detached mode"
	@echo "  ${GREEN}backend-up${NC}     : Run standalone backend + DB, stops on exit"
	@echo "  ${YELLOW}build${NC}          : Build web backend + frontend images"
	@echo "  ${YELLOW}backend-build${NC}  : Build standalone backend + DB images"
	@echo "  ${RED}down${NC}           : Stop all containers"
	@echo "  ${RED}backend-down${NC}   : Stop standalone backend + DB"
	@echo "  ${CYAN}fclean${NC}         : Stop all containers and remove volumes"
	@echo "  ${CYAN}backend-fclean${NC} : Stop standalone backend + DB and remove volumes"
	@echo "  logs            : Follow logs of all containers"

up: build
	docker-compose --profile web -f docker-compose.yaml up -d

down:
	docker-compose down

fclean:
	docker-compose down -v

build:
	docker-compose --profile web build

# profile standalone: without frontend
backend-build:
	docker-compose --profile standalone build backend-standalone db

# --no-deps: ignores dependent services -> frontend
# --abort-on-container-exit --remove-orphans: stop and remove all containers
backend-up: backend-build
	docker-compose --profile standalone up --no-deps --abort-on-container-exit --remove-orphans backend-standalone db

backend-down:
	docker-compose --profile standalone down

backend-fclean:
	docker-compose --profile standalone down -v

logs:
	@docker-compose logs -f

# ANSI color codes
GREEN  := \033[0;32m
RED    := \033[0;31m
YELLOW := \033[0;33m
CYAN   := \033[0;36m
NC     := \033[0m # No Color

.PHONY: up down fclean build backend-build backend-up backend-down backend-fclean logs default
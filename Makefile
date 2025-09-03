
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
	MODE=web docker compose down --remove-orphans || true
	MODE=web docker compose --profile web up

down:
	docker compose down

re: fclean-all up

fclean-all:
	MODE=web docker compose down -v --remove-orphans || true
	docker ps -aq | xargs -r docker rm -f
	docker network prune -f
	docker volume prune -f


fclean:
	MODE=web docker compose down -v  --remove-orphans
	MODE=web docker network prune -f

build:
	MODE=web docker compose --profile web build

# profile standalone: without frontend
backend-build:
	MODE=standalone docker compose --profile standalone build backend db

# --no-deps: ignores dependent services -> frontend
# --abort-on-container-exit --remove-orphans: stop and remove all containers
backend-up: backend-build
	MODE=standalone docker compose --profile standalone up --no-deps --abort-on-container-exit --remove-orphans backend db

#	docker compose --profile standalone up --no-deps --abort-on-container-exit --remove-orphans backend db


backend-down:
	MODE=standalone docker compose --profile standalone down

backend-fclean:
	MODE=standalone docker compose --profile standalone down -v


logs:
	@docker compose logs -f

GREEN  := \033[0;32m
RED    := \033[0;31m
YELLOW := \033[0;33m
CYAN   := \033[0;36m
NC     := \033[0m

.PHONY: up down fclean build backend-build backend-up backend-down backend-fclean logs default
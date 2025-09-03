
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


.PHONY: up down fclean build backend-build backend-up backend-down backend-fclean logs
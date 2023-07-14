# vacancy-collector

## Локальный запуск приложения

Запустить инфраструктурные сервисы с помощью docker-compose из каталога .run/docker-compose.yaml
```shell
docker-compose -p smart4it -f .run/docker-compose-local.yaml up
```

Создать БД
```shell
create database smart4it_db;
```

Запустить приложение с профилем local

Для отображения доступных endpoint запустить приложение и перейти по url:
http://host:port//swagger-ui.html (host - имя хоста, port - порт на котором поднято приложение)
# vacancy-collector

## Запуск приложения

Создать контейнер с БД
```shell
docker run --name smart4it_db -e POSTGRES_PASSWORD=postgres -d -p 5432:5432 postgres:15.2-alpine3.17
```

Создать БД
```shell
create database smart4it_db;
```

Запустить приложение с профилем local

Для отображения доступных endpoint запустить приложение и перейти по url:
http://host:port//swagger-ui.html (host - имя хоста, port - порт на котором поднято приложение)
## Backend для итогового проекта по БД

### Development:
Для запуска бэкенда вместе с dev-контейнером с базой используется <code>org.springframework.boot:spring-boot-docker-compose</code>
<br>

Однако, если есть необходимость изменить property <code>spring.docker.enabled</code>, то для ручного управления
контейнерами можно использовать команды:
<ul>
    <li>
        Запуск бэкенда через docker compose:
<pre>
docker compose -f ./backend/deploy/docker-compose.yaml up -d
</pre>
    </li>
    <li>
        Остановка бэкенда через docker compose:
<pre>
docker compose -f ./backend/deploy/docker-compose.yaml down 
</pre>
    </li>
</ul>

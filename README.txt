Location Microservice

Технические требования
Требования к API:
    1. GET /location - Получить все List<Location>
    2. GET /location?name={name} - Получить Location по name
    3. POST /location - Добавить новый Location
    4. PUT /location?name={name} - Изменить Location по name
    5. DELETE /location?name={name} - Удалить Location по name
    6. GET /location/weather?name={name} - Получить погоду для Location по name

    Location:
    class Location{
      Double longitude;
      Double latitude;
      String name;
    }

    Требования к названиям классов:
    1. Model:
        1.1. {Корневой пакет}.location.model.Weather.class
        1.2. {Корневой пакет}.location.model.Location.class
    2. Controller:
        2.1. {Корневой пакет}.location.controller.LocationController.class
    3. Config:
        3.1. {Корневой пакет}.location.config.LocationConfig.class
    4. Repository:
        4.1. {Корневой пакет}.location.repository.LocationRepository.class
    5. Main:
        5.1. {Корневой пакет}.location.LocationApplication.class

## Как проверить работу

### 1. Запуск сервиса
```bash
mvn spring-boot:run (через терминал)



2. Примеры запросов
    2.1. Получить все локации
    GET http://localhost:8081/location

    Ответ:
    [
        {
            "id": 1,
            "name": "Moscow",
            "latitude": 55.7558,
            "longitude": 37.6173
        },
        {
            "id": 2,
            "name": "London",
            "latitude": 51.5074,
            "longitude": -0.1278
        }
    ]

    2.2. Получить локацию по имени
    GET http://localhost:8081/location?name=Moscow

    Ответ:
    {
        "id": 1,
        "name": "Moscow",
        "latitude": 55.7558,
        "longitude": 37.6173
    }
    2.3. Добавить новую локацию
    POST http://localhost:8081/location
    Content-Type: application/json

    {
        "name": "Paris",
        "latitude": 48.8566,
        "longitude": 2.3522
    }

    Ответ:
    {
        "id": 3,
        "name": "Paris",
        "latitude": 48.8566,
        "longitude": 2.3522
    }

    2.4. Изменить локацию по имени
    PUT http://localhost:8081/location?name=Paris
    Content-Type: application/json

    {
        "latitude": 48.86,
        "longitude": 2.35
    }

    Ответ:
    {
        "id": 3,
        "name": "Paris",
        "latitude": 48.86,
        "longitude": 2.35
    }

    2.5. Удалить локацию по имени
    DELETE http://localhost:8081/location?name=Paris

    Ответ:
    Location 'Moscow' deleted

    2.6. Получить погоду для локации
    GET http://localhost:8081/location/weather?name=Moscow

    Ответ:
    {
        "temp": 15.5,
        "feels_like": 13.2,
        "temp_min": 14.0,
        "temp_max": 17.0,
        "pressure": 1012,
        "humidity": 65,
        "sea_level": 1012,
        "grnd_level": 1008
    }
# Микросервисы: Location, Person, Weather

## Сервис Location (порт 8081)

### Методы:

#### Получить все локации
GET http://localhost:8081/location

Copy

#### Получить локацию по имени
GET http://localhost:8081/location?name={name}

Copy

#### Добавить новую локацию
POST http://localhost:8081/location
Тело запроса (JSON):
{
"name": "Berlin",
"latitude": 52.52,
"longitude": 13.405
}

Copy

#### Обновить локацию
PUT http://localhost:8081/location?name={name}
Тело запроса (JSON):
{
"latitude": 52.53,
"longitude": 13.41
}

Copy

#### Удалить локацию
DELETE http://localhost:8081/location?name={name}

Copy

#### Получить погоду для локации
GET http://localhost:8081/location/weather?name={name}

Copy

## Сервис Person (порт 8083)

### Методы:

#### Получить всех пользователей
GET http://localhost:8083/person

Copy

#### Получить пользователя по ID
GET http://localhost:8083/person/{id}

Copy

#### Добавить нового пользователя
POST http://localhost:8083/person
Тело запроса (JSON):
{
"firstname": "Anna",
"surname": "Panina",
"lastname": "Artemovna",
"birthday": "2006-04-20",
"location": "Berlin"
}

Copy

#### Обновить пользователя
PUT http://localhost:8083/person/{id}
Тело запроса (JSON):
{
"firstname": "Anna Updated"
}

Copy

#### Удалить пользователя
DELETE http://localhost:8083/person/{id}

Copy

#### Получить погоду для пользователя
GET http://localhost:8083/person/{id}/weather

Copy

## Сервис Weather (порт 8082)

### Методы:

#### Получить данные о погоде
GET http://localhost:8082/weather?lat={latitude}&lon={longitude}

Copy

## Настройки Eureka
Eureka Server: http://localhost:8761

Copy

## Консоли H2
Location DB: http://localhost:8081/h2-console
Person DB: http://localhost:8083/h2-console
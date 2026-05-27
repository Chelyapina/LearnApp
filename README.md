# LearnApp

Приложение для изучения иностранных слов с принципом "0 кликов до обучения" - пользователь открыл приложение и сразу начал учить слова.

## Функционал

### 1. Авторизация и регистрация
- При первом вводе имени и пароля выполняется автоматическая регистрация пользователя.
- При последующих попытках входа требуется ввод корректного пароля.
- Обработка ошибок валидации и сетевых ошибок реализована через диалоговые окна и подписи под текстовыми полями.
- Авторизация использует JWT-токен, который хранится в `EncryptedSharedPreferences`. При истечении срока действия токен обновляется.

### 2. Обучение
Каждый день пользователь получает два списка слов в формате карточек: для изучения и для повторения.

- Переключение между колодами осуществляется через верхнюю навигационную панель.
- Тип текущей колоды отображается в виде бейджа в левом верхнем углу карточки: «Учим» / «Повторяем».
- Перевод отображается при нажатии на карточку (переворот).
- Отметка о результате: галочка - «вспомнил», крестик - «не вспомнил».
- Для слов из колоды повторения на карточке приводятся примеры использования, сгенерированные бэкендом с использованием ИИ.
- После первой загрузки обучение в рамках дневного лимита доступно в офлайн-режиме без потери прогресса (сохранение через `SharedPreferences`).

### 3. Статистика
Пользователь может отслеживать прогресс изучения слов. Раздел открывается по нажатию на иконку графика на главном экране.

Содержимое раздела:
- График изученных слов за текущий год (переключение года осуществляется стрелками слева и справа от значения года).
- Список дней и количество выученных слов за каждый день выбранного месяца. Месяц выбирается нажатием на номер месяца под столбцом графика.

### 4. Настройки
В меню доступен подраздел «Настройки», где пользователь может изменить параметры обучения и пароль.

- Лимит слов для изучения и повторения настраивается с помощью слайдеров.
- Смена пароля требует ввода нового пароля и его подтверждения.
- Любое изменение настроений является критической операцией и требует подтверждения текущего пароля.

### 5. Кастомные словари
Слова в приложении заданы на сервере, добавление собственных слов в текущей версии недоступно. Однако существующие слова можно группировать в словари.

Словари делятся на два типа:
- **Системные** - категория, к которой слово относится в системе (например, слово *yellow* входит в системный словарь *Colors*). Отображают только те слова, которые пользователь уже встречал в процессе обучения. Поддерживают только просмотр.
- **Кастомные** - создаются пользователем. Могут содержать любые слова из базы. Доступно создание (название, описание, язык - в текущей версии только английский) и удаление - категорий, а также добавление и удаление слов.

## Технологический стек

- **Архитектура**: вертикальная многомодульность + Clean Architecture + MVVM
- **DI**: Dagger 2
- **Сеть**: Retrofit 2 + OkHttp + kotlinx-serialization
- **Навигация**: Navigation Compose
- **Асинхронность**: Coroutines
- **Сериализация**: kotlinx-serialization-json
- **Безопасность**: androidx.security-crypto

## Экраны приложения

### 1. Авторизация и регистрация
<div align="center">
  <img  width="200" alt="Screenshot_20260526_002020" src="https://github.com/user-attachments/assets/326df5e9-ac84-4d9d-ae30-4371b0f5994b" />
  <img width="200" alt="Screenshot_20260526_002107" src="https://github.com/user-attachments/assets/876ba963-7b82-4c25-ac1b-36a5875e89f8" />
  <img width="200" alt="Screenshot_20260526_010645" src="https://github.com/user-attachments/assets/40b88e71-0543-4af9-a488-ed1f8636c4ea" />
  <br/>
</div>

### 2. Обучение
<div align="center">
  <img width="200" alt="Screenshot_20260526_002212" src="https://github.com/user-attachments/assets/fb4380fd-074d-4e1d-ab93-0956337ec889" />
  <img width="200" alt="Screenshot_20260526_002218" src="https://github.com/user-attachments/assets/07edf212-6d8a-45e7-9a25-4703db2e758a" />
  <img width="200" alt="Screenshot_20260526_002245" src="https://github.com/user-attachments/assets/60624cd7-8920-406c-ac5d-a0a2f3d84619" />
  <img width="200" alt="Screenshot_20260526_002229" src="https://github.com/user-attachments/assets/be2a89a1-6407-4ebf-8fb2-489812ec9aba" />
  <br/>
</div>

### 3. Статистика
<div align="center">
  <img width="200" alt="Screenshot_20260526_002316" src="https://github.com/user-attachments/assets/35164e60-6428-4526-b6f5-946c64588521" />
  <img width="200" alt="Screenshot_20260526_002325" src="https://github.com/user-attachments/assets/89fe23fa-c51c-42be-841c-673018094649" />
  <br/>
</div>

### 4. Настройки
<div align="center">
  <img width="200" alt="Screenshot_20260526_002419" src="https://github.com/user-attachments/assets/08ec9fdf-b014-4fd5-86f4-98bee90ab69a" />
  <img width="200" alt="Screenshot_20260526_010145" src="https://github.com/user-attachments/assets/4251d9c1-bede-4954-8c36-4f7334a65aa6" />
  <br/>
</div>

### 5. Кастомные словари
<div align="center">
  <img width="200" alt="Screenshot_20260526_002937" src="https://github.com/user-attachments/assets/cb928ddd-507d-45c8-9b02-39fbfb19c05c" />
  <img width="200" alt="Screenshot_20260526_005314" src="https://github.com/user-attachments/assets/04dcc53c-0cec-4d37-860c-1384ec9a82e3" />
  <img width="200" alt="Screenshot_20260526_005341" src="https://github.com/user-attachments/assets/0ee2cda8-93ce-4b62-ae20-c205aae1c5ed" />
  <img width="200" alt="Screenshot_20260526_005529" src="https://github.com/user-attachments/assets/edd1eb7a-6f4c-49fa-953d-5a96d0800963" />
  <br/>
</div>

## Видео-демонстрация работы приложения
https://github.com/user-attachments/assets/b45a30e9-cd5b-4e8f-bbf3-49e671660b22



## Структура модулей

Приложение содержит 15 модулей:
- :app
- :core:security
- :core:storage
- :core:di
- :core:navigation
- :core:network
- :core:models
- :shared:designsystem
- :shared:validation
- :feature:splash
- :feature:settings
- :feature:statistics
- :feature:dictionary
- :feature:authorization
- :feature:deck

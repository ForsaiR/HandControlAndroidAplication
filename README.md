# HandControlAndroidAplication
Activity Main - содержит 2 кнопки, которые на данный момент просто переносят на главный экран
Navigation - содержит меню навигации (разбил по следующему принципу)
+ Настройки: Отображение текущего состояния протеза + уровень заряда; Отображение миографика; Экран настроек протеза (выбор режима работы + отключение модулей)
+ Редактор жестов: Создание/Изменение жеста;
+ Исполнение: Выполнение жеста по нажатию + голосовое;
+ Выбор протеза: Выбор протеза из списка (в варианте с подключение к серверу);

## Разрешения
+ На кнопки подключения навешено условие, проверяющее разрешения на подключение к интеренету или блютуз
+ В SettingsFragment закомментирован код для проверки разрешения на сохранение файлов
+ В ExecutionFragment закомментирован код для проверки разрешения на запись голоса

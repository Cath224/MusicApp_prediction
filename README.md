# MusicApp_prediction

MusicApp_prediction - это сервис предсказаний, направлен на построение эффективной рекомендательной системы для музыкального сервиса "Eum-Ag".

## Описание

Сервис предсказаний для музыкального приложения использует комплексный подход, сочетая различные методы анализа данных и исследования поведения пользователей. Это позволяет создавать точные и персонализированные рекомендации, увеличивая вовлеченность пользователей и улучшая их опыт.

## Применяемый метод построения рекомендательной системы

### Источники данных

Для создания предсказательных моделей и аналитики используются следующие данные:

- **Данные о взаимодействии с пользователем**: Воспроизведенные треки, поисковые запросы, создание плейлистов, история прослушиваний и показатели вовлеченности пользователей.
- **Отзывы пользователей**: Положительные (лайки) или отрицательные (дизлайки) отзывы на песни, альбомы или плейлисты помогают уточнить рекомендации и оценить удовлетворенность и предпочтения пользователей.

### Метод фильтрации

Для построения рекомендательной системы используется метод коллаборативной фильтрации с применением алгоритма k-ближайших соседей (KNN). Этот алгоритм является непараметрическим классификатором с контролируемым обучением, основанным на предположении, что похожие точки находятся рядом друг с другом. В качестве функции оценки расстояния в KNN используется количество песен, которые соответствуют вкусам различных пользователей.

#### Алгоритм работы KNN

1. Выбирается пользователь А.
2. Случайным образом выбираются 1000 других пользователей.
3. Анализируются последние песни, которым пользователь А поставил лайки, а также последние 100 песен из его истории прослушиваний.
4. Производится сверка на наличие похожих композиций у пользователя А и у 1000 других пользователей. В случае выявления схожих композиций к сумме расстояния добавляется либо 1, либо 0,5 (для лайкнутых песен и песен из истории прослушиваний соответственно).
5. Отбираются 50 пользователей с наибольшим сходством композиций с пользователем А. Затем исследуются случайные композиции этих пользователей, которые автоматически добавляются в плейлист пользователя А, формируя рекомендательный плейлист.

package temporary;

import entity.DoctorSpeciality;
import entity.olddb.DoctorOldDB;
import entity.olddb.PatientOldDB;
import entity.olddb.AppointmentRecordOldDB;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class DBAutocomplete {
    String lastNames =
            """
            Смирнов,
            Иванов,
            Кузнецов,
            Соколов,
            Попов,
            Лебедев,
            Козлов,
            Новиков,
            Морозов,
            Петров,
            Волков,
            Соловьёв,
            Васильев,
            Зайцев,
            Павлов,
            Семёнов,
            Голубев,
            Виноградов,
            Богданов,
            Воробьёв,
            Фёдоров,
            Михайлов,
            Беляев,
            Тарасов,
            Белов,
            Комаров,
            Орлов,
            Киселёв,
            Макаров,
            Андреев,
            Ковалёв,
            Ильин,
            Гусев,
            Титов,
            Кузьмин,
            Кудрявцев,
            Баранов,
            Куликов,
            Алексеев,
            Степанов,
            Яковлев,
            Сорокин,
            Сергеев,
            Романов,
            Захаров,
            Борисов,
            Королёв,
            Герасимов,
            Пономарёв,
            Григорьев,
            Лазарев,
            Медведев,
            Ершов,
            Никитин,
            Соболев,
            Рябов,
            Поляков,
            Цветков,
            Данилов,
            Жуков,
            Фролов,
            Журавлёв,
            Николаев,
            Крылов,
            Максимов,
            Сидоров,
            Осипов,
            Белоусов,
            Федотов,
            Дорофеев,
            Егоров,
            Матвеев,
            Бобров,
            Дмитриев,
            Калинин,
            Анисимов,
            Петухов,
            Антонов,
            Тимофеев,
            Никифоров,
            Веселов,
            Филиппов,
            Марков,
            Большаков,
            Суханов,
            Миронов,
            Ширяев,
            Александров,
            Коновалов,
            Шестаков,
            Казаков,
            Ефимов,
            Денисов,
            Громов,
            Фомин,
            Давыдов,
            Мельников,
            Щербаков,
            Блинов,
            Колесников,
            Карпов,
            Афанасьев,
            Власов,
            Маслов,
            Исаков,
            Тихонов,
            Аксёнов,
            Гаврилов,
            Родионов,
            Котов,
            Горбунов,
            Кудряшов,
            Быков,
            Зуев,
            Владимиров,
            Артемьев,
            Гурьев,
            Зиновьев,
            Гришин,
            Кононов,
            Дементьев,
            Ситников,
            Симонов,
            Мишин,
            Фадеев,
            Комиссаров,
            Мамонтов,
            Носов,
            Гуляев,
            Шаров,
            Устинов,
            Вишняков,
            Евсеев,
            Лаврентьев,
            Брагин,
            Константинов,
            Корнилов,
            Авдеев,
            Зыков,
            Бирюков,
            Шарапов,
            Никонов,
            Щукин,
            Дьячков,
            Одинцов,
            Сазонов,
            Якушев,
            Красильников,
            Гордеев,
            Самойлов,
            Князев,
            Беспалов,
            Уваров,
            Шашков,
            Бобылёв,
            Доронин,
            Белозёров,
            Рожков,
            Самсонов,
            Мясников,
            Лихачёв,
            Буров,
            Сысоев,
            Фомичёв,
            Русаков,
            Стрелков,
            Гущин,
            Тетерин,
            Колобов,
            Субботин,
            Фокин,
            Блохин,
            Селиверстов,
            Пестов,
            Кондратьев,
            Силин,
            Меркушев,
            Лыткин,
            Туров
            """;
    String lastNamesForArray = lastNames.replaceAll("[\n\r]", "");
    String[] lastNamesArray = lastNamesForArray.split(",");
    String names = """
            Авдей
            ;Авксентий
            ;Агапит
            ;Агафон
            ;Акакий
            ;Александр
            ;Алексей
            ;Альберт
            ;Альвиан
            ;Анатолий
            ;Андрей
            ;Аникита
            ;Антон
            ;Антонин
            ;Анфим
            ;Аристарх
            ;Аркадий
            ;Арсений
            ;Артём
            ;Артемий
            ;Артур
            ;Архипп
            ;Афанасий
            ;Богдан
            ;Борис
            ;Вавила
            ;Вадим
            ;Валентин
            ;Валерий
            ;Валерьян
            ;Варлам
            ;Варсонофий
            ;Варфоломей
            ;Василий
            ;Венедикт
            ;Вениамин
            ;Викентий
            ;Виктор
            ;Виссарион
            ;Виталий
            ;Владимир
            ;Владислав
            ;Владлен
            ;Влас
            ;Всеволод
            ;Вячеслав
            ;Гавриил
            ;Галактион
            ;Геласий
            ;Геннадий
            ;Георгий
            ;Герасим
            ;Герман
            ;Германн
            ;Глеб
            ;Гордей
            ;Григорий
            ;Данакт
            ;Демьян
            ;Денис
            ;Дмитрий
            ;Добрыня
            ;Дорофей
            ;Евгений
            ;Евграф
            ;Евдоким
            ;Евсей
            ;Евстафий
            ;Егор
            ;Емельян
            ;Еремей
            ;Ермолай
            ;Ерофей
            ;Ефим
            ;Ефрем
            ;Ждан
            ;Зиновий
            ;Иакинф
            ;Иван
            ;Игнатий
            ;Игорь
            ;Изот
            ;Илья
            ;Иннокентий
            ;Ириней
            ;Исидор
            ;Иулиан
            ;Лукий
            ;Лукьян
            ;Магистриан
            ;Макар
            ;Максим
            ;Мамонт
            ;Марк
            ;Мартын
            ;Матвей
            ;Мелентий
            ;Мина
            ;Мирон
            ;Мирослав
            ;Митрофан
            ;Михаил
            ;Мстислав
            ;Назар
            ;Нестор
            ;Никандр
            ;Никанор
            ;Никита
            ;Никифор
            ;Никодим
            ;Николай
            ;Никон
            ;Олег
            ;Онисим
            ;Онуфрий
            ;Павел
            ;Паисий
            ;Панкратий
            ;Пантелеймон
            ;Парфений
            ;Пафнутий
            ;Пахомий
            ;Пётр
            ;Платон
            ;Поликарп
            ;Порфирий
            ;Потап
            ;Прокопий
            ;Протасий
            ;Прохор
            ;Разумник
            ;Родион
            ;Родослав
            ;Роман
            ;Ростислав
            ;Руслан
            ;Савва
            ;Савелий
            ;Самуил
            ;Святополк
            ;Святослав
            ;Севастьян
            ;Семён
            ;Серафим
            ;Сергей
            ;Сила
            ;Сильвестр
            ;Созон
            ;Софрон
            ;Спиридон
            ;Станислав
            ;Степан
            ;Тарас
            ;Тимофей
            ;Тимур
            ;Тит
            ;Тихон
            ;Трифон
            ;Трофим
            ;Фаддей
            ;Фёдор
            ;Федосей
            ;Федот
            ;Феликс
            ;Филипп
            ;Фирс
            ;Фока
            ;Фома
            ;Фотий
            ;Фрол
            ;Харитон
            ;Хрисанф
            ;Христофор
            """;

    String namesForArray = names.replaceAll("[\n\r]", "");
    String[] firstNamesArray = namesForArray.split(";");

    String middleNames = """
            Александрович 13 букв
            Алексеевич 10 букв
            Анатольевич 11 букв
            Андреевич 9 букв
            Антонович 9 букв
            Аркадьевич 10 букв
            Артемович 9 букв
            Бедросович 10 букв
            Богданович 10 букв
            Борисович 9 букв
            Валентинович 12 букв
            Валерьевич 10 букв
            Васильевич 10 букв
            Викторович 10 букв
            Витальевич 10 букв
            Владимирович 12 букв
            Владиславович 13 букв
            Вольфович 9 букв
            Вячеславович 12 букв
            Геннадиевич 11 букв
            Георгиевич 10 букв
            Григорьевич 11 букв
            Данилович 9 букв
            Денисович 9 букв
            Дмитриевич 10 букв
            Евгеньевич 10 букв
            Егорович 8 букв
            Ефимович 8 букв
            Иванович 8 букв
            Иваныч 6 букв
            Игнатьевич 10 букв
            Игоревич 8 букв
            Ильич 5 букв
            Иосифович 9 букв
            Исаакович 9 букв
            Кириллович 10 букв
            Константинович 14 букв
            Леонидович 10 букв
            Львович 7 букв
            Максимович 10 букв
            Матвеевич 9 букв
            Михайлович 10 букв
            Николаевич 10 букв
            Олегович 8 букв
            Павлович 8 букв
            Палыч 5 букв
            Петрович 8 букв
            Платонович 10 букв
            Робертович 10 букв
            Романович 9 букв
            Саныч 5 букв
            Северинович 11 букв
            Семенович 9 букв
            Сергеевич 9 букв
            Станиславович 13 букв
            Степанович 10 букв
            Тарасович 10 букв
            Тимофеевич 10 букв
            Федорович 10 букв
            Феликсович 10 букв
            Филиппович 10 букв
            Эдуардович 10 букв
            Юрьевич 10 букв
            Яковлевич 10 букв
            Ярославович 10 букв
            """;
    String middleNamesForArray = middleNames.replaceAll("[\n\r]", "");
    String[] middleNamesArray = middleNamesForArray.split("[01-9]+[\\s][букв]+");

    String illness = """
            Абсцесс простаты
            Аденома простаты
            Аденомиоз
            Алкогольный гепатит
            Аллергический конъюнктивит
            Альбинизм
            Анкилозирующий спондилит
            Аортальная регургитация (аортальная недостаточность)
            Артериит Такаясу
            Артериовенозная фистула
            Артрит, вызванный кристаллами кальция пирофосфата (псевдоподагра)
            Атеросклероз
            Атопический дерматит (экзема)
            Атриовентрикулярная блокада
            Аутоиммунный миозит
            Бактериальный вагиноз
            Баланопостит
            Бессимптомный лимфоцитарный тиреоидит (послеродовый тиреоидит)
            Блефарит
            Блефароспазм
            Болезнь Аддисона
            Болезнь Бехчета
            Болезнь Крона
            Болезнь Паркинсона
            Болезнь Пейрони
            Болезнь Фрайберга
            Бородавки
            Буллезная кератопатия
            Буньон
            Бурсит
            Бурсит ахиллова сухожилия
            Варикозное расширение вен
            Вирилизация (андрогенитальный синдром)
            Витилиго
            Возрастная дегенерация желтого пятна (ВДЖП)
            Воспаление глазницы
            Воспалительное заболевание органов малого таза (ВЗОМТ)
            Галакторея
            Гастрит
            Гастрит атрофический
            Гастрит поверхностный
            Гастрит эрозивный
            Гастроэзофагиальная рефлюксная болезнь (ГЭРБ)
            Гастроэнтерит
            Геморрагический васкулит (пурпура Шёнлейна-Геноха)
            Гигантизм и акромегалия
            Гигантоклеточный артериит (болезнь Хортона)
            Гиперальдостеронизм
            Гипертензивная ретинопатия
            Гипертиреоз (тиреотоксикоз)
            Гипертрофическая кардиомиопатия
            Гиполипидемия
            Гипопитуитаризм
            Гипотиреоз (миксидема)
            Глаукома
            Гломерулонефрит
            Головные боли напряжения
            Гортанные контактные язвы
            Гранулема кольцевидная
            Гранулематоз с полиангиитом (гранулематоз Вегенера)
            Дакриоцистит
            Дерматомикоз стоп
            Дерматофитозы (стригущий лишай, дерматофития)
            Диабетическая ретинопатия
            Дивертикулез толстой кишки
            Дилатационная кардиомиопатия
            Дислипидемия (гиперлипидемия)
            Дисфункция синусового узла
            Доброкачественные опухоли яичников
            Жировой гепатоз (стеатогепатоз)
            Заболевания нервных сплетений
            Заглоточный абсцесс
            Задержка мочи
            Закупорка наружного слухового прохода
            Закупорка центральных артерий сетчатки и их ветвей
            Закупорка центральных вен сетчатки и их ветвей
            Интерстициальный кератит
            Интертриго
            Инфекционный конъюнктивит
            Инфекционный эндокардит
            Ихтиоз
            Ишемическая болезнь сердца (ИБС)
            Ишемическая оптическая нейропатия
            Камни в желчном пузыре (желчекаменная болезнь)
            Камни в мочевыводящих путях (уролитиаз)
            Каналикулит
            Кандидоз (молочница)
            Катаракта
            Кератит, вызванный вирусом простого герпеса
            Кератоконус
            Кератомаляция
            Кисты бартолиновых желез
            Кисты Бейкера (подколенные кисты)
            Контагиозный моллюск
            Контактный дерматит
            Контрактура Дюпюитрена
            Красный волосистый питириаз
            Красный плоский лишай
            Ларингит
            Легочная регургитация
            Лимфатический отек
            Лямблиоз
            Мастоидит
            Медикаментозная сыпь
            Мелазма
            Миастения гравис
            Микроскопическая полиангиопатия
            Миокардит
            Миомы шейки матки
            Митральная регургитация (митральная недостаточность)
            Митральный стеноз
            Мозоли и мозолистые наросты
            Молочница влагалища (кандидоз)
            Нарушение кровоснабжения спинного мозга (инфаркт спинного мозга)
            Нарушения сердечного ритма (аритмии)
            Наследственные заболевания зрительного нерва
            Невралгия тройничного нерва
            Неврит зрительного нерва
            Неинфекционный эндокардит
            Нейрогенный мочевой пузырь
            Непереносимость лактозы
            Непроходимость мочевыводящих путей (обструктивная уропатия)
            Несахарный почечный диабет
            Нуммулярный дерматит
            Облитерирующий тромбоангиит (болезнь Бюргера)
            Ожирение
            Окклюзионные заболевания периферических артерий
            Опоясывающий герпес с поражением глаз
            Орхит
            Остеоартрит
            Остеонекроз
            Острый перикардит
            Отек зрительного нерва
            Отит наружный (инфекция ушного канала)
            Отит острый
            Отит серозный средний
            Отит хронический гнойный
            Отит хронический наружный (дерматит слухового прохода)
            Отслоение сетчатки
            Панкреатит
            Паралич Белла
            Пароксизмальная наджелудочковая тахикардия
            Пемфигоид слизистых оболочек глаз
            Перекручивание яичка
            Периоральный дерматит
            Периферический язвенный кератит
            Перихондрит
            Перфорация барабанной перепонки
            Пигментный ретинит
            Пиелонефрит (инфекция почек)
            Поверхностный точечный кератит
            Повышенное артериальное давление (гипертензия)
            Подагра
            Подострый тиреоидит
            Поликистозная болезнь почек
            Полиневропатия
            Полипы шейки матки
            Поражение нервов стопы (неврома Мортона)
            Предсердная экстрасистолия
            Пресептальная флегмона
            Проктит
            Пролапс митрального клапана
            Простатит
            Простой хронический лишай (нейродермит)
            Псориаз
            Псориатический артрит
            Разноцветный лишай
            Рассеянный склероз
            Реактивный артрит
            Ревматическая полимиалгия
            Ревматоидный артрит
            Регургитация на трехстворчатом клапане (недостаточность трехстворчатого клапана)
            Рестриктивная кардиомиопатия
            Рефракционные нарушения
            Рецидивирующий полихондрит
            Ринит (насморк)
            Ринит аллергический
            Ринит атрофический
            Розацеа (розовая угревая сыпь)
            Розовый лишай
            Сахарный диабет
            Себорейный дерматит
            Симпатическая офтальмия
            Синдром Вольфа-Паркинсона-Уайта
            Синдром Гийена-Барре
            Синдром Жильбера
            Синдром Когана
            Синдром Кушинга
            Синдром поликистозных яичников (СПКЯ)
            Синдром пустого турецкого седла
            Синдром раздраженного кишечника
            Синдром Рейно
            Синдром Шегрена
            Синусит
            Системная красная волчанка
            Системный склероз (склеродермия)
            Склерит
            Смешанное заболевание соединительной ткани
            Сосудистые звездочки (идиопатические телеангиэктазии)
            Спинальные мышечные атрофии (СМА)
            Стеноз аорты
            Стеноз клапана легочной артерии
            Стеноз трехстворчатого клапана
            Стенокардия
            Стригущий лишай волосистой части головы
            Стриктура уретры
            Сухой кератоконъюнктивит
            Сухость кожи (ксеродермия)
            Тендинит и теносиновит
            Тетрада Фалло
            Тиреоидит Хашимото
            Токсическая амблиопия
            Тонзиллит
            Тонзиллярный целлюлит и тозиллярный абсцесс
            Трахома
            Трихиаз
            Тромбоз глубоких вен
            Тромбоз поверхностных вен
            Тубулоинтерстициальный нефрит
            Увеит
            Угревая сыпь
            Узелковый полиартериит
            Узловая эритема
            Уретрит
            Фарингит
            Феохромоцитома
            Фибрилляция (мерцание) и трепетание предсердий
            Фибрилляция желудочков
            Фиброзно-кистозные изменения в молочной железе
            Фиброзно-мышечная дисплазия
            Фибромиома матки
            Фимоз и парафимоз
            Флегмона глазницы
            Фликтенулезный кератоконъюнктивит
            Халазион и ячмень (гордеолум)
            Холецистит
            Хроническая болезнь почек
            Хроническая венозная недостаточность и постфлебитический синдром
            Хроническая воспалительная демиелинизирующая полиневропатия
            Хронический перикардит
            Целиакия
            Центральный несахарный диабет
            Цервицит
            Цирроз печени
            Цистит
            Цистит интерстициальный
            Эзофагит
            Эндометриоз
            Эндофтальмит
            Энтропион и эктропион
            Эозинофильный фасциит
            Эпиглоттит
            Эпидидимит и орхоэпидидимит
            Эпиретинальная мембрана
            Эписклерит
            Эутиреоидный зоб
            Язва двенадцатиперстной кишки
            Язва желудка
            Язва роговицы
            Язвенный колит
            """;
    String [] illnessArray = illness.split("[\n\r]");

    String medications = """
            А-Пнеумон А-Флумон Абизол Абрикотаб Авамис Авастин Авирол Аводарт Авопрост Аврора Мариславна Адаптовит Адаптол-БТ Адваграф Адвантан Адек Аденик Аденозин Абакавир Абактал Абипренолы Абиратерон-М Абиратерон-Ника Абитаксел Абитэль Аблокасил АбнобаВИСКУМ Фраксини Абомин Аваксим Авандия Авелокс Авиа-Море Авиомарин Авиранц Аводель Авокомб Аволам АВС-Спектрум Агапурин Агарикус Агастацин Аген Агистам Аглиократ Агнес Агрепид Агри Адалат Адалат Адалимаб Адаптол Адасель Магний Адельфан-Эзидрекс Аденозинтрифосфат Аденокор В12 Анкерманн Вагалин Вагестрол Вагилак Вагилак Вазолекс Актив Вазомаг Вазонит Вазостенон Валацикловир Валган Валериана Вагиквик Вагинорм-С Вагифем Ваготил Ваден Вазапростан Вазелин Вазобрал Вазопрен Вайтек Женьшень Живица Живокост Жабник Железо Желтушник Эквин Л-карнитин Л-Лизин Лабазник Лайснер Лайтслим Лакрипос Лаксигал Лаксикон Лаксимед Лактазар Лактиале Лактинет Лактобактерии Лактобациллин Лактобекс Лактобекс Лактогон Лактолакс Лактоструктум Л-карнитин Ладисан Ладнексит Ладпулин Лазея Лазикс Лазоксол Лайон Лактобактерин Таваник Тагансорбент Тадалис Тайверб Гвоздика Мангостин Тами-грипп Тамифлю Тамоксифен-Эбеве Тамсулозин Тамсулозин-ЛФ Танакан Тантум Таптиком Тапузим Тардиферон Тардокс Т-Федрин Таденан Тазепам Тазоцин Тазробида Тайзомед Тайлед Минт Тайлолфен Железо+Метафолин Таксофит Тактивин Таллитон Талцеф Тальго Тамоксифен Тамсол Тамсулон-ФС Мабтера Мавирет Мавит Магвит Магне-В6 Магнекард Магнекор Магнелэнд Магнерот Магнес Максамин-Форте Максигра Максиколд Маалукол Маброн Маверекс Магнагель Магневист Магневит Магнезиум Синерджи Магнелек Магнетик Магнефар Макмирор Максиварт Максиган Максидез Максидекс Максиколд Максиколд Максилонг-С Максима Ревитал Макситопир Навобан Наглазим Назалонг Назалфен Називин Назол Назонекс Назорин
            """;
    String [] medicationsArray = medications.split("[ ]");

    public ArrayList<PatientOldDB> createPatientList(){
        ArrayList<PatientOldDB> patients = new ArrayList<PatientOldDB>();
        for (int i = 0; i < 100; i++){
            PatientOldDB patient = PatientOldDB.builder()
                    .lastName(lastNamesArray[returnRandomPositiveNumber(lastNamesArray.length)])
                    .firstName(firstNamesArray[returnRandomPositiveNumber(firstNamesArray.length)])
                    .middleName(middleNamesArray[returnRandomPositiveNumber(middleNamesArray.length)])
                    .birthDate(LocalDate.of((returnRandomPositiveNumber(1930, 2022)), returnRandomPositiveNumber(1,13), returnRandomPositiveNumber(1, 32)))
                    .build();
            patients.add(patient);
        }
        return patients;
    }

    public ArrayList<DoctorOldDB> createDoctorsList(){
        ArrayList doctors = new ArrayList<DoctorOldDB>();
        for (int i = 0; i < 10; i++){
            DoctorOldDB doctor = DoctorOldDB.builder()
                    .lastName(lastNamesArray[i])
                    .firstName(firstNamesArray[i])
                    .middleName(middleNamesArray[i])
                    .speciality(returnSpeciality(i))
                    .build();
            doctors.add(doctor);
        }
        return doctors;
    }

    public ArrayList<AppointmentRecordOldDB> createReceptionResultList(ArrayList<PatientOldDB> patientsList, ArrayList<DoctorOldDB> doctorsList){
        ArrayList<AppointmentRecordOldDB> receptionResults = new ArrayList<>();
        for (int i = 0; i < medicationsArray.length; i++){
            AppointmentRecordOldDB receptionResult = AppointmentRecordOldDB.builder()
                    .patient(patientsList.get(returnRandomPositiveNumber(patientsList.size())))
                    .doctor(doctorsList.get(returnRandomPositiveNumber(doctorsList.size())))
                    .receptionDate(LocalDate.of((returnRandomPositiveNumber(2000, 2022)), returnRandomPositiveNumber(1,13), returnRandomPositiveNumber(1, 31)))
                    .healthComplaints(illnessArray[i])
                    .doctorsRecommendation(medicationsArray[i])
                    .build();
            receptionResults.add(receptionResult);
        }
        return receptionResults;
    }

    private DoctorSpeciality returnSpeciality(int i){
        if (i == 0 || i == 6)
            return DoctorSpeciality.NEUROLOGIST;
        if (i == 1)
            return DoctorSpeciality.SURGEON;
        if (i == 2)
            return DoctorSpeciality.OPTOMETRIST;
        if (i == 3)
            return DoctorSpeciality.OPTOMETRIST;
        if (i == 4)
            return DoctorSpeciality.DENTIST;
        if (i > 4)
            return DoctorSpeciality.GENERAL_DOCTOR;

        return DoctorSpeciality.GENERAL_DOCTOR;
    }

    private int returnRandomPositiveNumber(int maxLimit){
        Random random = new Random();
        return random.nextInt(maxLimit);
    }

    private int returnRandomPositiveNumber(int minLimit, int maxLimit){
        return (int) (minLimit + Math.random()*(maxLimit - minLimit));
    }




}
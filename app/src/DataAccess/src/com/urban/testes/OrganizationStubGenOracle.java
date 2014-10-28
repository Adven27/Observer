package com.urban.testes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.urban.entity.ContactType;

public class OrganizationStubGenOracle {
    private static ArrayList<OrganizationStub> orgs = new ArrayList<OrganizationStub>();
    private static ArrayList<CategoryStub> categories = new ArrayList<CategoryStub>();

    private static HashMap<OrganizationStub, Integer> orgMap = new HashMap<OrganizationStub, Integer>();

    public static void main(String args[]) {
        fillOrganizationData();

        System.out.printf(
                "insert into contacttype (id, name) "
                + "values (1, 'PHONE');%n");
        System.out.printf(
                "insert into contacttype (id, name) "
                + "values (2, 'EMAIL');%n");
        System.out.printf(
                "insert into contacttype (id, name) "
                + "values (3, 'WEB');%n");
        System.out.printf(
                "insert into contacttype (id, name) "
                + "values (4, 'SKYPE');%n%n");


        int i = 0;
        int j = 0;
        for (OrganizationStub org : orgs) {
            System.out.printf(
                    "insert into organization (id, logo, name, description) "
                    + "values (%d, null, '%s', '%s');%n", i, org.name, org.desc);
            orgMap.put(org, i);

            System.out.printf(
                    "insert into place (id, lat, alt, description) "
                    + "values (%d, %s, %s, '%s');%n", i, org.place.lat, org.place.alt, "");

            System.out.printf(
                    "insert into organization_place (organization, place) "
                    + "values (%d, %d);%n", i, i);

            System.out.printf(
                    "insert into address (id, street, house, letter, floor, flat, place) "
                    + "values (%d, '%s', %d, '%s', %d, %d, %d);%n", i, org.place.address.street, org.place.address.house, org.place.address.letter, org.place.address.floor, org.place.address.flat, i);

            /*System.out.printf(
                    "insert into place_address (place, address) "
                    + "values (%d, %d);%n", i, i);*/

            Iterator<ContactStub> iterator = org.contacts.iterator();
            while (iterator.hasNext()) {
                ContactStub contract = (ContactStub)iterator.next();
                System.out.printf(
                        "insert into contact (id, contact, contacttype) "
                        + "values (%d, '%s', %d);%n", j, contract.info, contract.type.ordinal()+1);

                System.out.printf(
                        "insert into organization_contact (organization, contact) "
                        + "values (%d, %d);%n", i, j);

                j++;
            }

            System.out.println();

            i++;
        }

        int positionId = 0;
        int categoryId = 0;
        for (CategoryStub category : categories) {
            Iterator<PositionStub> positionIterator = category.positions.iterator();

            System.out.printf(
                    "insert into category (id, name, parent, \"order\") "
                    + "values (%d, '%s', null, %d);%n", categoryId, category.name, categoryId);

            while (positionIterator.hasNext()) {
                PositionStub position = (PositionStub)positionIterator.next();
                System.out.printf(
                        "insert into position (id, name, organization) "
                        + "values (%d, '%s', %d);%n", positionId, position.name, orgMap.get(position.organization));

                System.out.printf(
                        "insert into category_position (position, category) "
                        + "values (%d, %d);%n", positionId, categoryId);

                positionId++;
            }
            categoryId++;
        }

        System.out.println();

    }

    private static void fillOrganizationData() {


        CategoryStub categoryFood = new CategoryStub("Еда", 0);
        categories.add(categoryFood);
        CategoryStub categorySport = new CategoryStub("Спорт", 1);
        categories.add(categorySport);
        CategoryStub categoryDrink = new CategoryStub("Бары", 2);
        categories.add(categoryDrink);

        AddressStub address = new AddressStub("Зосимовская", 107, ' ', 1, 0);
        PlaceStub place = new PlaceStub(39.888486, 59.20806, address);
        OrganizationStub organization = new OrganizationStub("Green Cafe", "Бизнес-ланч в Green Cafe. Самые приятные минуты рабочего дня - это бизнес-ланч в Green Cafe. Разнообразное и вкусное меню, приятная музыка и оптимальные цены.", place);
        organization.addContact(ContactType.EMAIL, "Green@wologda.ru");
        organization.addContact(ContactType.PHONE, "750675");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Чернышевского", 149, ' ', 1, 0);
        place = new PlaceStub(39.895188, 59.249726, address);
        organization = new OrganizationStub("Баранка кафе", "Кафе «Баранка» - отличное место для того, чтобы поделиться с друзьями впечатлениями или узнать последние новости. Беспроводной интернет всегда к вашим услугам.", place);
        organization.addContact(ContactType.PHONE, "+7(8172)");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Герцена", 56, ' ', 1, 0);
        place = new PlaceStub(39.897272, 59.209769, address);
        organization = new OrganizationStub("Бильярдный клуб и кафе «Гладиатор»", "Приглашаем по четвергам на программу с 21:00 (запись на участие в конкурсах заранее), живая музыка с 15:00-18:00 со среды по пятницу. Можно попробовать: Пицца “Сырная” Пицца “Острая Американа” Пицца “Чикконе” Пицца “Традиционная” И конечно сыграть в бильярд.", place);
        organization.addContact(ContactType.PHONE, "562671");
        organization.addContact(ContactType.PHONE, "562277");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);
        categorySport.addByOrganization(organization);

        address = new AddressStub("Батюшкова", 11, ' ', 1, 0);
        place = new PlaceStub(39.883779, 59.2197, address);
        organization = new OrganizationStub("Бургер кинг", "Американская компания, Сеть ресторанов быстрого питания Бургер кинг Вологда. Штаб-квартира — в Майами, штат Флорида.", place);
        organization.addContact(ContactType.PHONE, "210321");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Лермонтова", 15, ' ', 1, 0);
        place = new PlaceStub(39.893032, 59.221422, address);
        organization = new OrganizationStub("Городское кафе Авеню", " Мы работаем круглосуточно. Для Вас русская, европейская, итальянская, японская кухня", place);
        organization.addContact(ContactType.EMAIL, "info@aveny.ru");
        organization.addContact(ContactType.PHONE, "503573");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Орлова", 6, ' ', 1, 0);
        place = new PlaceStub(39.887247, 59.223945, address);
        organization = new OrganizationStub("Кафе \"Зачет\"", "Здесь всегда вкусные обеды, горячая выпечка и очень приемлемые цены!", place);
        organization.addContact(ContactType.EMAIL, "mail@example.com");
        organization.addContact(ContactType.PHONE, "212141");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Проспект Победы", 8, ' ', 1, 0);
        place = new PlaceStub(39.887947, 59.221781, address);
        organization = new OrganizationStub("Кафе \"Парижанка\"", "Кафе \"Парижанка\" - уютное место для самого приятного: побаловать себя разнообразными сладостями, ароматным кофе или просто пообедать в спокойной обстановке. В самом центре Вологды Вы найдете тихий уголок Парижа.", place);
        organization.addContact(ContactType.EMAIL, "PManager@kluchik-vologda.ru");
        organization.addContact(ContactType.PHONE, "720456");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Чехова", 51, ' ', 1, 0);
        place = new PlaceStub(39.890508, 59.209, address);
        organization = new OrganizationStub("Кафе Гудок", "Городское кафе Гудок город Вологда. Кол-во: 60 мест Средний счет без алкагольных напитков: 350р. Бизнес обеды с 12-00 до 14-00", place);
        organization.addContact(ContactType.PHONE, "758956");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Мира", 9, ' ', 1, 0);
        place = new PlaceStub(39.889852, 59.220879, address);
        organization = new OrganizationStub("Кафе Каменный мост", "Ежели вас завлекает неторопливая богемная атмосфера европейской кофейни 30-х, ежели вы мечтаете о временах, когда писатели приходили в кафе испить чашечку кофе и, наслаждаясь его горьковатым запахом, создать новейшую главу собственного романа.", place);
        organization.addContact(ContactType.PHONE, "723542");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Набережная 6 Армии", 143, ' ', 1, 0);
        place = new PlaceStub(39.90144, 59.223683, address);
        organization = new OrganizationStub("Кафе Красный мост", "В цокольном этаже галереи находится кафе «Красный Мост». Его главными достоинствами являются: - прекрасный интерьер, сочетающий ретро детали и современный стиль хай-те.", place);
        organization.addContact(ContactType.EMAIL, "redbridge@yandex.ru");
        organization.addContact(ContactType.PHONE, "547930");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Лермонтова", 4, ' ', 1, 0);
        place = new PlaceStub(39.892223, 59.221528, address);
        organization = new OrganizationStub("Кафе Лукоморье", "160 посадочных мест, кухня русская, грузинская, украинская", place);
        organization.addContact(ContactType.PHONE, "769655");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Благовещенская", 44, ' ', 1, 0);
        place = new PlaceStub(39.877419, 59.221781, address);
        organization = new OrganizationStub("Кафе \"Архитектор\"", "", place);
        organization.addContact(ContactType.PHONE, "757437");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Мира", 17, 'а', 1, 0);
        place = new PlaceStub(39.886609, 59.219078, address);
        organization = new OrganizationStub("Кафе \"Ёрш\"", "Приглашаем Вас посетить наше уютное летнее кафе, где все располагает к приятному и непринужденному отдыху! Мы позаботились, чтоб каждый из вас нашел для себя то, что он ищет! Великолепная кухня: японская, итальянская, европейская, восточная.", place);
        organization.addContact(ContactType.PHONE, "563884");
        organization.addContact(ContactType.PHONE, "505026");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Мира", 8, ' ', 1, 0);
        place = new PlaceStub(39.889654, 59.221763, address);
        organization = new OrganizationStub("Кафе \"Мак Дак\"", "Калоритные краски, залитые золотыми солнечными лучами помещения, современная и комфортная обстановка – все это создает запоминающуюся атмосферу праздничка в счастливой сказочной стране.", place);
        organization.addContact(ContactType.EMAIL, "hotel@spasskaya.ru");
        organization.addContact(ContactType.PHONE, "787349");
        organization.addContact(ContactType.PHONE, "787841");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Орлова", 4, 'а', 1, 0);
        place = new PlaceStub(39.888819, 59.223554, address);
        organization = new OrganizationStub("Пивной бар Килт", "\"Килт\" является одним из лучших пивных баров нашего города. Выполненный в стиле средневекового шотландского замка, он переносит нас во времена доблестных и отважных рыцарей, милых дам и красочных мифов. Каждый элемент пивного бара выполнен в ключе тех времен.", place);
        organization.addContact(ContactType.PHONE, "265590");
        orgs.add(organization);
        categoryDrink.addByOrganization(organization);

        address = new AddressStub("Пошехонское шоссе", 22, ' ', 1, 0);
        place = new PlaceStub(39.859246, 59.197233, address);
        organization = new OrganizationStub("Кофе.com", "Издревле кофейня была местом, где решались деловые и торговые вопросы. Вот только теперь встречи проходят на бегу, а переговоры ведутся через Интернет.", place);
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Герцена", 20, ' ', 1, 0);
        place = new PlaceStub(39.890651, 59.213528, address);
        organization = new OrganizationStub("Кофейня \"Золотой ключик\"", "Уютное местечко в каждом \"оГороде\", где можно попробовать множество вкусных десертов, большое количество сортов кофе, чая, коктейлей и мороженого! В холодный, ветренный вечер Вас согреет Глинтвейн и чашечка горячего шоколада.", place);
        organization.addContact(ContactType.EMAIL, "mkulkova@kluchik-vologda.ru");
        organization.addContact(ContactType.PHONE, "752768");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Октябрьская", 38, ' ', 1, 0);
        place = new PlaceStub(39.875811, 59.220662, address);
        organization = new OrganizationStub("Кофейня-булочная Крендель", "Узнали тайные рецептуры и добавили сыра, сварили полубожественный кофе, походили из угла в угол!", place);
        organization.addContact(ContactType.PHONE, "501361");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Козленская", 33, ' ', 1, 0);
        place = new PlaceStub(39.893715, 59.215499, address);
        organization = new OrganizationStub("Пиццерия \"Жар Пицца\"", "Настоящая Итальянская пицца.", place);
        organization.addContact(ContactType.PHONE, "721066");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Пушкинская", 43, ' ', 1, 0);
        place = new PlaceStub(39.885845, 59.215099, address);
        organization = new OrganizationStub("Спорт кафе \"Динамо\"", "Спорт кафе \"Динамо\". Трансляция спортивных мероприятий.", place);
        organization.addContact(ContactType.PHONE, "721450");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Мира", 82, ' ', 1, 0);
        place = new PlaceStub(39.880204, 59.211617, address);
        organization = new OrganizationStub("ШТОФ", "Притягательное место для любителей (и профессионалов отменного пива), веселых коктейлей, вкусной еды и хороших компаний.", place);
        organization.addContact(ContactType.PHONE, "570410");
        orgs.add(organization);
        categoryDrink.addByOrganization(organization);

        address = new AddressStub("Октябрьская", 38, ' ', 1, 0);
        place = new PlaceStub(39.875811, 59.220662, address);
        organization = new OrganizationStub("АРС кафе", "74 посадочных места, летнее кафе на 12 столиков, русская и европейская кухня", place);
        organization.addContact(ContactType.PHONE, "720575");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Залинейная", 22, ' ', 1, 0);
        place = new PlaceStub(39.843678, 59.237255, address);
        organization = new OrganizationStub("PARADISE CLUB", "«Мы не следуем тенденциям — мы их создаем!» Party Club X.O.открыл свои двери и перевернул представление о проведении ночного досуга, подняв культуру отдыха на качественно новый уровень. Всё это время PARTY CLUB X.O. находится в эпицентре клубной жизни Вологды.", place);
        organization.addContact(ContactType.EMAIL, "davologda@rambler.ru");
        organization.addContact(ContactType.PHONE, "783737");
        orgs.add(organization);
        categoryDrink.addByOrganization(organization);

        address = new AddressStub("Проспект Победы", 61, ' ', 1, 0);
        place = new PlaceStub(39.873547, 59.224033, address);
        organization = new OrganizationStub("Ванкувер", "Ночью все это место превращается в клубную феерию с царством потрясающе кристального звука, волшебного света.", place);
        organization.addContact(ContactType.PHONE, "720771");
        organization.addContact(ContactType.PHONE, "89115339260");
        organization.addContact(ContactType.PHONE, "89115288808");
        orgs.add(organization);
        categoryDrink.addByOrganization(organization);

        address = new AddressStub("Ветошкина", 19, ' ', 1, 0);
        place = new PlaceStub(39.891011, 59.211782, address);
        organization = new OrganizationStub("Харди-Гарди", "Гостеприимное место без лишнего пафоса и суеты. Для вас блюда русской, европейской и восточной кухни.", place);
        organization.addContact(ContactType.EMAIL, "752757");
        organization.addContact(ContactType.PHONE, "info@hurdy-gurdyclub.ru");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Пугачёва", 26, ' ', 1, 0);
        place = new PlaceStub(39.925605, 59.211073, address);
        organization = new OrganizationStub("IRRIS", "Рок-бар.", place);
        organization.addContact(ContactType.PHONE, "+79115032350");
        orgs.add(organization);


        address = new AddressStub("Проспект Победы", 10, ' ', 2, 0);
        place = new PlaceStub(39.887687, 59.221836, address);
        organization = new OrganizationStub("Клуб-ресторан «Винтаж»", "Новейший развлекательный клуб-ресторан «Винтаж» – одно из немногих мест нашего городка, где можно не попросту вкусно поужинать и пообщаться с друзьями в душевной обстановке, да и приятно провести время в компании любимых музыкальных исполнителей, окунуться в атмосферу британского музыкального клуба.", place);
        organization.addContact(ContactType.EMAIL, "");
        organization.addContact(ContactType.PHONE, "");
        orgs.add(organization);
        categoryDrink.addByOrganization(organization);

        address = new AddressStub("Октябрьская", 25, ' ', 1, 0);
        place = new PlaceStub(39.872371, 59.219709, address);
        organization = new OrganizationStub("ТНТ", "Ночной клуб \"ТНТ\" входит в состав гостинично-развлекательного комплекса \"Спасский\" и расположен в центре исторической части Вологды.", place);
        organization.addContact(ContactType.PHONE, "795056");
        orgs.add(organization);
        categoryDrink.addByOrganization(organization);

        address = new AddressStub("Судоремонтная", 2, 'а', 1, 0);
        place = new PlaceStub(39.92397, 59.209493, address);
        organization = new OrganizationStub("Огни сухоны", "Вологодский РЦ \"Огни Сухоны\" основан в 2005 году, является, пожалуй, лучшим местом Вологды в стиле диско 80-х. С самого начала своего основания и по сей день основу дискотеки составляют ретро хиты 80-х и 2000-х годов.", place);
        organization.addContact(ContactType.PHONE, "76 79 18");
        organization.addContact(ContactType.PHONE, "76 78 24");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Советский проспект", 45, ' ', 1, 0);
        place = new PlaceStub(39.904917, 59.212114, address);
        organization = new OrganizationStub("Поплавок", "лотская кухня и низкие цены; три зала для отдыха дискозал на 250 мест бар, ретрозал на 100 мест и караоке-бар на 50 мест \"живая\" музыка, веселительная программа и концерты, ди-джеи и дискотека, лазерное шоу комнаты отдыха", place);
        organization.addContact(ContactType.PHONE, "767346");
        organization.addContact(ContactType.PHONE, "767178");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Ленинградская", 85, ' ', 3, 0);
        place = new PlaceStub(39.843894, 59.207729, address);
        organization = new OrganizationStub("Боулинг Ключ", "Шесть дорожек настоящего американского боулинга фирмы Brunswick вместе с оригинальным космическим дизайном определили новый эталон активного отдыха, как для спортсменов, так и для любителей этой игры.", place);
        organization.addContact(ContactType.EMAIL, "bowling@bowlingkluch.ru");
        organization.addContact(ContactType.PHONE, "537848");
        orgs.add(organization);
        categorySport.addByOrganization(organization);

        address = new AddressStub("Чехова", 27, ' ', 1, 0);
        place = new PlaceStub(39.884875, 59.212529, address);
        organization = new OrganizationStub("Пластилин, Фитнес клуб", "Сеть клубов «Пластилин» - мир свободы, ярких красок и прекрасных результатов! Это лучшее соотношение цены и качества, широкий выбор услуг в различных ценовых категориях, самое новое профессиональное оборудование и, конечно же, профессионализм сотрудников: от менеджеров до инструкторов.", place);
        organization.addContact(ContactType.EMAIL, "fitnessplastilin@mail.ru");
        organization.addContact(ContactType.PHONE, "78 77 78");
        organization.addContact(ContactType.PHONE, "71 44 11");
        orgs.add(organization);
        categorySport.addByOrganization(organization);

        address = new AddressStub("Ярославская", 7, ' ', 1, 0);
        place = new PlaceStub(39.849239, 59.206618, address);
        organization = new OrganizationStub("Клуб бокса ТАЙФУН.", "Постоянно проводит набор юношей и девушек от 10 лет, а так же взрослых в секцию БОКСА.", place);
        organization.addContact(ContactType.PHONE, "539159");
        orgs.add(organization);
        categorySport.addByOrganization(organization);

        address = new AddressStub("Петина", 10, ' ', 1, 0);
        place = new PlaceStub(39.8472, 59.215702, address);
        organization = new OrganizationStub("Фитнес клуб ПАРНАС", "ПАРНАС - фитнес клуб, задающий качественно новый подход к пониманию фитнеса и себя в фитнесе. Клубная карта ПАРНАСА - система свободного выбора физкультурно-оздоровительных услуг.", place);
        organization.addContact(ContactType.EMAIL, "club-parnas@mail.ru");
        organization.addContact(ContactType.PHONE, "52 22 52");
        orgs.add(organization);
        categorySport.addByOrganization(organization);

        address = new AddressStub("Предтеченская", 57, 'а', 1, 0);
        place = new PlaceStub(39.88925, 59.213335, address);
        organization = new OrganizationStub("Бассейн Динамо", " дорожек по 30 метров, глубина от 1.0 до 12.2 м. Продолжительность сеанса - 45 мининут.", place);
        organization.addContact(ContactType.PHONE, "720553");
        orgs.add(organization);
        categorySport.addByOrganization(organization);

        address = new AddressStub("Кремлевская площадь", 10, ' ', 1, 0);
        place = new PlaceStub(39.884794, 59.223425, address);
        organization = new OrganizationStub("Вологодская областная картинная галерея", "Вологодская областная картинная галерея была организована в 1952 году на базе художественного отдела Вологодского областного краеведческого музея.", place);
        organization.addContact(ContactType.EMAIL, "vologda-gallery@yandex.ru");
        organization.addContact(ContactType.PHONE, "721433");
        organization.addContact(ContactType.PHONE, "721228");
        orgs.add(organization);

        address = new AddressStub("Октябрьская", 25, ' ', 1, 0);
        place = new PlaceStub(39.872371, 59.219709, address);
        organization = new OrganizationStub("Гостиница \"Спасская\"", "ГОСТИНИЦА \"Спасская\" существует с 1990 года. В Вологодской области она занимает лидирующие позиции в сфере обслуживания. Одной из основных задач предприятие считает оказание широкого комплекса услуг европейского уровня: повышенная комфортность проживания, питания и проведения досуга.", place);
        organization.addContact(ContactType.EMAIL, "info@spasskaya.ru");
        organization.addContact(ContactType.PHONE, "720145");
        organization.addContact(ContactType.PHONE, "723069");
        orgs.add(organization);

        address = new AddressStub("Советский проспект", 6, ' ', 1, 0);
        place = new PlaceStub(39.893095, 59.218148, address);
        organization = new OrganizationStub("Гостиница \"Золотой Якорь\"", "Гостиница \"Золотой Якорь\" расположена в центре города Вологды на Советском проспекте, рядом с Администрацией города Вологды, пл. Революций, пл. Возрождения и кинотеатром Ленком. Номерной фонд 30 мест, в гостинице имеется кафе, ресторан, парикмахерская.", place);
        organization.addContact(ContactType.PHONE, "726241");
        orgs.add(organization);

        address = new AddressStub("Герцена", 27, ' ', 1, 0);
        place = new PlaceStub(39.889996, 59.215117, address);
        organization = new OrganizationStub("Гостиница «Атриум»", "Гостиница «Атриум» - современный , комфортабельный новый отель Вологды, открывшийся в конце 2008 года. Проект строительства гостиницы реализован по инициативе Правительства Вологодской области и Клуба Делового общения г.Вологды.", place);
        organization.addContact(ContactType.EMAIL, "hotel@atriumvologda.ru");
        organization.addContact(ContactType.PHONE, "787825");
        orgs.add(organization);

        address = new AddressStub("Мира", 92, ' ', 1, 0);
        place = new PlaceStub(39.880743, 59.210276, address);
        organization = new OrganizationStub("Гостиница «Вологда»", "Является одной из ведущих в городе Вологде. Расположение гостиницы очень удобно. Она построена в центральной, исторической части областного центра и в то же время в 200 метрах от авто- и железнодорожного вокзалов. Поблизости – остановки городского транспорта.", place);
        organization.addContact(ContactType.PHONE, "729057");
        organization.addContact(ContactType.PHONE, "560409");
        organization.addContact(ContactType.PHONE, "723079");
        orgs.add(organization);

        address = new AddressStub("Ленинградская", 89, ' ', 1, 0);
        place = new PlaceStub(39.838989, 59.203725, address);
        organization = new OrganizationStub("Дом культуры подшипникового завода (ДК ПЗ)", "Здание дома культуры вологодского подшипникового завода находится на одной из наиболее оживленных улиц города - улице ленинградской дом номер 89. Здание имеет три этажа , на которых расположены различные торговые площади и даже парикмахерская .В здании проводятся как дискотеки так и концерты.", place);
        organization.addContact(ContactType.PHONE, "534233");
        organization.addContact(ContactType.PHONE, "519099");
        orgs.add(organization);

        address = new AddressStub("Кремлевская площадь", 2, ' ', 1, 0);
        place = new PlaceStub(39.884471,59.222942, address);
        organization = new OrganizationStub("Трактир Ёлки-Палки", "Торговая марка «Ёлки-Палки» уже второе десятилетие является брендом №1 в сегменте русской национальной кухни, символом высокого качества и здорового питания. Отражая культурные особенности России, она хорошо узнаваема и любима сотнями тысяч людей.", place);
        organization.addContact(ContactType.EMAIL, "info@elkipalki.ru");
        organization.addContact(ContactType.PHONE, "724774");
        orgs.add(organization);

        address = new AddressStub("Октябрьская", 25, ' ', 1, 0);
        place = new PlaceStub(39.872371, 59.219709, address);
        organization = new OrganizationStub("Пивной бар \"Бочка\"", "Небольшой зал вместимостью 30 мест с традиционным интерьером в стиле баварской пивной. Здесь всегда большой выбор свежего разливного охлажденного пива. Элитные сорта представлены в баночной и бутылочной упаковке.", place);
        organization.addContact(ContactType.EMAIL, "hotel@spasskaya.ru");
        organization.addContact(ContactType.PHONE, "787565 (доб.2170");
        orgs.add(organization);
        categoryDrink.addByOrganization(organization);

        address = new AddressStub("Ленинградская", 85, ' ', 1, 0);
        place = new PlaceStub(39.843894, 59.207729, address);
        organization = new OrganizationStub("Ресторан Пиноккио", "тальянский ресторан «Пиноккио» – собственного рода мозаика итальянской жизни. Гармоничное сочетание разных форм – от лассического ресторана до пиццерии, от бара до кондитерской – дозволяет нашим гостям получить полное представление о многогранном кулинарном искусстве Италии.", place);
        organization.addContact(ContactType.PHONE, "537845");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Батюшкова", 11, ' ', 1, 0);
        place = new PlaceStub(39.883779, 59.2197, address);
        organization = new OrganizationStub("Ресторан Арбат", "Торгово-развлекательный комплекс Арбатъ. Русская, европейская, японская кухня Организация и проведение праздников, банкетов, фуршетов, ...", place);
        organization.addContact(ContactType.EMAIL, "mea35@mail.ru");
        organization.addContact(ContactType.PHONE, "210321");
        organization.addContact(ContactType.PHONE, "89216018180");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Проспект победы", 6, ' ', 1, 0);
        place = new PlaceStub(39.888495, 59.221675, address);
        organization = new OrganizationStub("Ресторан Север", "Один из наистарейших ресторанов городка – «Север» – сейчас встречает собственных гостей современными интерьерами и великолепной кухней. Комфортно расположенный в самом историческом центре Вологды.", place);
        organization.addContact(ContactType.PHONE, "722600");
        organization.addContact(ContactType.PHONE, "721692");
        organization.addContact(ContactType.PHONE, "факс - 722600");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Проспект победы", 13, ' ', 1, 0);
        place = new PlaceStub(39.886519, 59.221224, address);
        organization = new OrganizationStub("Ресторан Семь вечеров", "Так приятно, когда важные события жизни проходят в красивой обстановке за аппетитным и изобильным столом. Уютный и изысканный банкетный зал идеально подходит для душевных семейных и корпоративных мероприятий, семинаров и презентаций, переговоров и деловых застолий.", place);
        organization.addContact(ContactType.EMAIL, "info@semvecherov.ru");
        organization.addContact(ContactType.PHONE, "728282");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Пречистенская Набережная", 44, 'а', 1, 0);
        place = new PlaceStub(39.906992, 59.2197, address);
        organization = new OrganizationStub("Спортивно-концертный комплекс СПЕКТР", "Муниципальное унитарное предприятие \"Спортивно-концертный комплекс \"Спектр\".", place);
        organization.addContact(ContactType.EMAIL, "spektr@vologda.ru");
        organization.addContact(ContactType.PHONE, "762075");
        orgs.add(organization);
        categorySport.addByOrganization(organization);

        address = new AddressStub("Лермонтова", 23, ' ', 1, 0);
        place = new PlaceStub(39.895188, 59.219364, address);
        organization = new OrganizationStub("Ресторан «АНГЛИТЕР»", "40 посадочных мест, европейская кухня, летняя веранда.", place);
        organization.addContact(ContactType.EMAIL, "");
        organization.addContact(ContactType.PHONE, "721782");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("Лермонтова", 19, ' ', 1, 0);
        place = new PlaceStub(39.893912, 59.220188, address);
        organization = new OrganizationStub("«Киото»", "Ресторан японской кухни.", place);
        organization.addContact(ContactType.EMAIL, "info@kyoto-vologda.ru");
        organization.addContact(ContactType.PHONE, "720627");
        orgs.add(organization);
        categoryFood.addByOrganization(organization);

        address = new AddressStub("М. Ульяновой", 13, ' ', 1, 0);
        place = new PlaceStub(39.887283, 59.217977, address);
        organization = new OrganizationStub("Фитнес клуб «Мегаполис»", "Самый функциональный тренажерный зал в Вологодской области с лучшим в мире оборудованием ведущих мировых брендов", place);
        organization.addContact(ContactType.PHONE, "707555");
        organization.addContact(ContactType.PHONE, "702888");
        orgs.add(organization);
        categorySport.addByOrganization(organization);
    }

    private static class OrganizationStub {
        String name;
        String desc;
        PlaceStub place;
        Set<ContactStub> contacts = new HashSet<ContactStub>(2);

        public OrganizationStub(String name, String desc, PlaceStub place) {
            this.name = name;
            this.desc = desc;
            this.place = place;
        }

        public void addContact(ContactType type, String info) {
            contacts.add(new ContactStub(type, info));
        }
    }

    private static class ContactStub {
        ContactType type;
        String info;

        public ContactStub(ContactType type, String info) {
            this.type = type;
            this.info = info;
        }


    }

    private static class PlaceStub {
        double lat, alt;
        AddressStub address;

        public PlaceStub(double lat, double alt, AddressStub address) {
            this.lat = lat;
            this.alt = alt;
            this.address = address;
        }
    }

    private static class AddressStub {

        String street;
        int house;
        char letter;
        int floor;
        int flat;

        public AddressStub(String street, int house, char letter, int floor, int flat) {
            this.street = street;
            this.house = house;
            this.letter = letter;
            this.floor = floor;
            this.flat = flat;
        }
    }

    private static class CategoryStub {
        Set<PositionStub> positions = new HashSet<PositionStub>();

        String name;
        int order;

        public CategoryStub(String name, int order) {
            this.name = name;
            this.order = order;
        }

        public void addPosition(PositionStub position) {
            positions.add(position);
        }

        public void addByOrganization(OrganizationStub organization) {
            positions.add(new PositionStub(organization.name, organization));
        }
    }

    private static class PositionStub {
        String name;
        OrganizationStub organization;

        public PositionStub(String name, OrganizationStub organization) {
            this.name = name;
            this.organization = organization;
        }
    }



}

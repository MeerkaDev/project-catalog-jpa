package meerkated.meerkated_jpa;

import jakarta.persistence.*;
import meerkated.meerkated_jpa.entity.Category;
import meerkated.meerkated_jpa.entity.Product;
import meerkated.meerkated_jpa.entity.SpecCategory;
import meerkated.meerkated_jpa.entity.ValueSpec;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class JpaApplication {
    
    public static void main(String[] args) throws Exception {
        // Maven - отдельная программа выполняющая функции системы
        // автоматизации сборки, автоматизируется процесс компиляции и упаковки
        // ее результата в целостный артефакт (конечная точка сборки), в
        // качестве артефакта может выступать как JAR (executable), так и
        // WAR (архив веб приложения написанного на java для развертки на
        // веб сервере) файл, получить собранный артефакт можно используя
        // стандартные средства сборки предоставляемые средой IDEA либо любой
        // другой, происходит привязка к среде - не рекомендуется, не удобно
        // когда над проектом работает несколько разработчиков на разных средах,
        // то есть отсутствует единый инструмент для сборки проекта, решают
        // эту проблему внешние системы автоматизации сборки такие как Maven и
        // Gradle которые не привязаны к среде разработки (при их использовании
        // можно произвести сборку вообще без среды разработки).
        // При разработке проекта могут потребоваться возможности которых
        // нет в стандартной библиотеке JDK, в таких случаях требуется
        // подключение внешней библиотеки реализующей недостающие возможности,
        // такая библиотека в контексте разрабатываемого проекта будет
        // называться зависимостью. Maven позволяет автоматизировать работу
        // по управлению зависимостями, он умеет их скачивать из репозиториев
        // хранения, включать скачанные зависимости в сборку и так далее.
        // У Maven есть центральный репозиторий зависимостей по адресу
        // `mvnrepository.com` (Maven Central Repository), в данном репозитории
        // можно найти любую популярную Java библиотеку.
        
        // В стандартной библиотеке JDK есть модуль под названием JDBC
        // позволяющий работать с реляционными базами данных при условии что
        // для базы данных реализован драйвер для взаимодействия. Драйвером
        // называется программная прослойка связывающая между собой язык
        // программирования и сервер базы данных.
        
        // `Java Application` -> `PostgreSQL Java Driver` -> `PostgreSQL Server`.
        // `C++ Application` -> `PostgreSQL C++ Driver` -> `PostgreSQL Server`.
        // ... (актуально для любого языка программирования).
        
        // Драйвер для работы с той или иной базой данных не входит в
        // стандартную библиотеку JDK, то есть если мы хотим выстроить
        // подключение к PostgreSQL необходимо подключить к проекту
        // соответствующий драйвер в качестве зависимости.
        
        // Для подключения к серверу БД PostgreSQL через JDBC
        // необходимо сформировать строку подключения по формату:
        // jdbc:postgresql://<хост>:<порт>/<база данных>
        // * `хост` - номер виртуального сетевого интерфейса к которому привязан
        // сервер базы данных.
        // * `порт` - номер виртуального сетевого интерфейса сервер базы данных.
        // * `база данных` - название базы данных на сервере к которой
        // формируется подключение.

//        String jdbcUrl = "jdbc:postgresql://localhost:5432/catalog";
//        String username = "postgres";
//        String password = "postgres";
//        Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
//
//        String sqlQuery = "select c.name cat_name, p.name, p.price from products p" +
//            " join categories c on c.id = p.category_id";
//        PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
//
//        ResultSet resultSet = preparedStatement.executeQuery();

//        while (resultSet.next()) {
//            System.out.println(resultSet.getLong("id"));
//            System.out.println(resultSet.getString("name"));
//        }
//        // resultSet.next(); // переключается на следующую запись возвращает boolean true если перешел
//        /*System.out.println(resultSet.getString("name"));
//
//        resultSet.next();
//        System.out.println(resultSet.getString("name"));*/
        
        // Вывести информацию о каждом товаре (название категории, название товара, стоимость товара)
        /*while (resultSet.next()) {
            System.out.println(resultSet.getString(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3));
        }*/
        
        // Java EE/Jakarta EE - набор стандартов (спецификаций) описывающие
        // подходы и инструменты упрощающие разработку крупных корпоративных
        // приложений на Java (в основном веб-приложений). Одним из модулей
        // описанных в Java EE/Jakarta EE является JPA, данный модуль описывает
        // взаимодействие между Java приложением и реляционной базой данных
        // по принципу ORM (Object Relational Mapping). ORM - система
        // объектно-реляционного сопоставления которая является прослойкой между
        // данными в табличном виде и в виде объектов, вся суть ORM сводится к
        // преобразованию данных в табличный вид и из табличного вида в объект.
        // JPA является всего лишь стандартом описывающим понятия, объекты и
        // методы используемые для взаимодействия по принципу ORM, то есть
        // JPA это не конкретная реализация (перечень классов) которую можно
        //  использовать, а лишь руководство к реализации (стандарт, интерфейс).
        // Класс используемый для репрезентации записей таблицы в контексте
        // стандарта JPA называется сущностью.
        
        // table humans         -> ORM -> class Human
        // id serial8           -> ORM -> Long id
        // first_name varchar   -> ORM -> String firstName
        // birthdate date       -> ORM -> LocalDate birthdate
        
        // Библиотек реализующих стандарт JPA довольно много:
        // 1) Hibernate;
        // 2) EclipseLink;
        // 3) ...;
        
        // Hibernate является внешней библиотекой не входящей в стандартную
        // библиотеку JDK. Использование возможностей Hibernate требует его
        // подключения к проекту в качестве зависимости, найти можно на
        // центральном репозитории Maven (`mvnrepository.com`) под названием
        // `hibernate-core`.
        
        // наименования базовых пакетов
        // `javax` - до восьмой
        // `jakarta` - после восьмой
        
        // В JPA предусмотрен файл конфигурации под название `persistence.xml`
        // предназначенный для определения конфигурационных блоков
        // (persistence unit-ы) с параметрами подключения и другими в том числе.
        // Файл конфигурации `persistence.xml` должен находиться внутри папки
        // META-INF которая в свою очередь должны быть включена в итоговую
        // сборку.
        
        // `EntityManagerFactory` - главный объект в формате JPA через который
        // выстраивается подключение. Объект `EntityManagerFactory`
        // используется для получения более легковесных объектов EntityManager.
        
        // `EntityManager` - объект предоставляющий методы для работы с
        // сущностями и выполнения запросов к базе данных. Каждый объект
        // EntityManager имеет локальный кэш в котором хранятся привязанные к
        // нему сущности, объекты одного EntityManager не могут использоваться
        // в комбинации с объектами другого EntityManager.
        
        // `entityManager.find(Class<T> class, Object id) : T` - возвращает
        // сущность заданного типа T по заданному значению первичного ключа из
        // параметра id при условии, что такая сущность существует, если же нет
        // то возвращает null.
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("main");
        
        EntityManager manager = factory.createEntityManager();
    /*
        // select c.* from categories c where c.id = 2
        Category category = manager.find(Category.class, 50);
        if (category != null) {
            System.out.println(category.getName());
        } else {
            System.out.println("Can't find category with such ID");
        }*/
        
        // Найти товар по айди и вывести название категории, название товара
        // и его стоимость.
    
        /*
        Product product = manager.find(Product.class, 1);
        if (product != null) {
            System.out.println("Категория - " + product.getCategory().getName());
            System.out.println("Наименование - " + product.getName());
            System.out.println("Стоимость - " + product.getPrice());
        } else {
            System.out.println("Can't find product with such ID");
        }
        */
        
        /*
        Category category = manager.find(Category.class, 1);
        if (category != null) {
            List<Product> products = category.getProducts();
            
            for (Product p : products) {
                System.out.println(p.getName());
            }
        } else {
            System.out.println("Can't find category with such ID");
        }
        */
        
        // В стандарте JPA в контексте работы с сущностями можно производить
        // операции на изменение (создание новой сущности, изменение и удаление
        // имеющейся), данные операции всегда должны выполняться в контексте
        // транзакции. Транзакцией называется группа запросов отправляемая
        // на выполнение к серверу базы данных по принципу все или ничего
        // (либо будут выполнены все запросы входящие в транзакцию либо ни один
        // из них), выполнение запросов в контексте транзакции гарантируют
        // целостность данных.

//        try {
//            manager.getTransaction().begin();
        
        // Создание и редактирование тут
            /*
            Category category = new Category();
            category.setName("Мебель");
            manager.persist(category);
            */
        
        // Изменение объекта через setter, persist необязательно
            /*
            Category category = manager.find(Category.class, 3);
            category.setName("New name");
            */
        
        // Удаление
            /*
            Category category = manager.find(Category.class, 3);
            manager.remove(category);
            */

//            manager.getTransaction().commit();
//        } catch (Exception e) {
//            manager.getTransaction().rollback();
//            throw new RuntimeException(e);
//        }
//
//        manager.close();
//
//        factory.close();
        
        // В стандарте JPA есть возможность выполнения запросов написанных
        // на языке JPQL, запросы могут быть как на выборку, так и на изменение
        // информации. JPQL представляет собой язык похожий на классический SQL,
        // но ориентированный на работу с сущностями. Для выполнения JPQL
        // запроса в стандарте JPA предусмотрены объекты Query (используется
        // для изменения данных в таблицах) и TypeQuery<T> (используется для
        // запросов на выборку данных), где T - тип результата.
        
        // JPQL -> ORM -> PostgreSQL Dialect -> PostgreSQL Server.
        // JPQL -> ORM -> MySQL Dialect -> MySQL Server.
        // ...
        
        // SQL -> select p.* from products p
        // SQL -> select p from Product p -> Product.class
    
        // SQL -> select p.name from products p
        // SQL -> select p.name from Product p -> String.class
        
        // SQL -> select p.* from products p where p.price between ? and ?
        // SQL -> select p from Product p where p.price between ? and ? -> Product.class
        
        // SQL -> select count(p.id) from products p where p.category_id = ?
        // JPQL -> select count(p.id) from Product p where p.category.id = ? -> Long.class
        
        // SQL -> update products set price = price * 1.1 where category_id = ?
        // JPQL -> update Product p set p.price = p.price * 1.1 where p.category.id = ?
    
        /*
        TypedQuery<Category> categoriesQuery = manager.createQuery(
            "select c from Category c", Category.class
        );
        List<Category> categories = categoriesQuery.getResultList();
        
        for (Category category : categories) {
            System.out.println(category.getName());
        }
        */
        
        /*
        int minPrice = 100_000;
        int maxPrice = 250_000;
        */
        /*TypedQuery<Product> productTypedQuery = manager.createQuery(
            "select p from Product p where p.price between " +  minPrice + " and " + maxPrice, Product.class
        );*/
        /*TypedQuery<Product> productTypedQuery = manager.createQuery(
            "select p from Product p where p.price between ?1 and ?2", Product.class
        );
        productTypedQuery.setParameter(1, minPrice);
        productTypedQuery.setParameter(2, maxPrice);
        
        List<Product> products = productTypedQuery.getResultList();
        
        for (Product product : products) {
            System.out.println(product.getName());
        }*/
        /*Product product = manager.find(Product.class, 9);
        List<SpecCategory> specCategories = product.getCategory().getSpecCategories();
        System.out.println("Product name: " + product.getName());
        TypedQuery<ValueSpec> valueSpecTypedQuery = manager.createQuery(
            "select v from ValueSpec v where v.product = ?1 and " +
                "v.specCategory = ?2",ValueSpec.class
        );
        
        for (SpecCategory specCategory : specCategories) {
            System.out.println(specCategory.getName());
    
            valueSpecTypedQuery.setParameter(1, product);
            valueSpecTypedQuery.setParameter(2, specCategory);
            
            List<ValueSpec> valueSpecList = valueSpecTypedQuery.getResultList();
    
            for (ValueSpec valueSpec : valueSpecList) {
                System.out.println(valueSpec.getId() + ", ");
            }
        }*/
        
        try{
            manager.getTransaction().begin();
            /*Query query = manager.createQuery(
                "update Product p set p.price = p.price * 2 where p.category.id = ?1"
            );*/
            
            Query query = manager.createQuery(
                "delete from Product p where p.category.id = 2"
            );
    
            query.setParameter(1, 2);
            query.executeUpdate();
            
            manager.getTransaction().commit();
        } catch (Exception e) {
            manager.getTransaction().rollback();
            throw new RuntimeException(e);
        }
        manager.close();
        factory.close();
    }
}

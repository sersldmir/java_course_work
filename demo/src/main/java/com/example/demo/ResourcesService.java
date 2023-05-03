package com.example.demo;

import java.util.List;

import com.example.demo.security.UserInfo;
import com.example.demo.security.UserInfoRepository;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Класс ResourcesService предоставляет методы для управления ресурсами и поставщиками, а также
 * аутентификацию пользователей и функции поиска.
 * 
 * Аннотация `@Service` используется для указания того, что класс ResourcesService является служебным
 * компонентом в приложении Spring. Она позволяет Spring
 * автоматически обнаруживать и настраивать класс как сервисный компонент, делая его доступным для
 * использования во всем приложении.
 */
@Service
public class ResourcesService {
    
    /** `@Autowired` — это аннотация в Spring, позволяющая автоматически внедрять зависимости. В данном
    * случае он внедряет экземпляр ResourceRepository в класс ResourcesService, позволяя службе получать
    * доступ и использовать методы, предоставляемые репозиторием.
    */
    @Autowired
    private ResourceRepository repoRes;

    /** `@Autowired` — это аннотация в Spring, позволяющая автоматически внедрять зависимости. В данном
    * случае он внедряет экземпляр SupplierRepository в класс ResourcesService, позволяя службе получать
    * доступ и использовать методы, предоставляемые репозиторием.
    */
    @Autowired
    private SupplierRepository repoSup;

    /** `@Autowired` — это аннотация в Spring, позволяющая автоматически внедрять зависимости. В этом случае
    * он внедряет экземпляр UserInfoRepository в класс ResourcesService, позволяя службе получать доступ и
    * использовать методы, предоставляемые репозиторием. Обычно это используется для доступа и управления
    * данными, хранящимися в базе данных.
    */
    @Autowired
    private UserInfoRepository userRepo;

    /** `@Autowired` используется для автоматического внедрения экземпляра интерфейса `PasswordEncoder` в
    * класс `ResourcesService`. Интерфейс `PasswordEncoder` используется для кодирования паролей для
    * безопасного хранения и сравнения. Это позволяет классу `ResourcesService` использовать
    * `PasswordEncoder` для кодирования паролей перед их сохранением в базе данных, а также для сравнения
    * закодированных паролей с пользовательским вводом во время аутентификации.
    */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Эта функция возвращает список ресурсов либо путем поиска по ключевому слову, либо возвращая все
     * ресурсы, если ключевое слово не указано.
     * 
     * @param keyword Параметр ключевого слова — это строка, которая используется для поиска ресурсов в
     * репозитории repoRes. Если ключевое слово не пустое, вызывается метод поиска репозитория с ключевым
     * словом в качестве параметра и возвращаются результаты. Если ключевое слово равно null, все ресурсы
     * возвращаются путем вызова метода findAll.
     * 
     * @return Возвращается список объектов ресурсов. Если указано непустое ключевое слово, метод вернет
     * отфильтрованный список ресурсов, соответствующих этому ключевому слову. В противном случае он вернет
     * все ресурсы в хранилище.
     */
    public List<Resource> listAllRes(String keyword) {
        if (keyword != null) {
            return repoRes.search(keyword);
        }
        return repoRes.findAll();
    }



    /**
     * Эта функция Java возвращает список всех поставщиков или отфильтрованный список на основе ключевого
     * слова.
     * 
     * @param keyword Параметр ключевого слова — это строка, которая используется для поиска поставщиков в
     * репозитории. Если ключевое слово не равно null, метод вызовет метод поиска объекта repoSup и вернет
     * список поставщиков, соответствующих ключевому слову. Если ключевое слово равно null, метод вернет
     * список
     * 
     * @return Метод listAllSup возвращает список объектов Supplier. Если параметр `keyword` не равен нулю,
     * он возвращает список объектов `Supplier`, которые соответствуют ключевому слову, используя метод
     * `search` объекта `repoSup`. Если параметр `keyword` равен нулю, он возвращает все объекты
     * `Supplier`, используя метод `findAll` из `repoSup.
     */
    public List<Supplier> listAllSup(String keyword) {
        if (keyword != null) {
            return repoSup.search(keyword);
        }
        return repoSup.findAll();
    }


    /**
     * Функция возвращает список всех поставщиков с помощью метода findAll из репозитория repoSup.
     * 
     * @return Список всех поставщиков из репозитория.
     */
    public List<Supplier> listAllSups() {
        return repoSup.findAll();
    }

    /** `private String resKeywordName` объявляет частную переменную экземпляра `resKeywordName` типа
    * `String` в классе `ResourcesService`. Эта переменная используется для хранения имени ключевого
    * слова, используемого в методе `listByResCriteria` для поиска ресурсов. Доступ к нему можно
    * получить через метод получения `getResKeywordName()`.
    */
    private String resKeywordName;


    /** `private String supKeywordName` объявляет частную переменную экземпляра `supKeywordName` типа
    * `String` в классе `ResourcesService`. Эта переменная используется для хранения имени ключевого
    * слова, используемого в методе `listBySupCriteria` для поиска поставщиков. Доступ к нему можно
    * получить через геттер-метод getSupKeywordName().
    */
    private String supKeywordName;


    /** `private String resKeyword` объявляет частную переменную экземпляра `resKeyword` типа `String` в
    * классе `ResourcesService`. Эта переменная используется для хранения ключевого слова,
    * используемого в методе `listByResCriteria` для поиска ресурсов. Доступ к нему можно получить
    * через геттер-метод getResKeyword().
    */
    private String resKeyword;


    /** `private String supKeyword` объявляет частную переменную экземпляра `supKeyword` типа `String` в
    * классе `ResourcesService`. Эта переменная используется для хранения ключевого слова,
    * используемого в методе `listBySupCriteria` для поиска поставщиков. Доступ к нему можно получить
    * через геттер-метод getSupKeyword().
    */
    private String supKeyword;

    /**
     * Эта функция возвращает значение переменной «resKeywordName».
     * 
     * @return Метод возвращает значение переменной `resKeywordName`, которая является строкой.
     */
    public String getResKeywordName() {
        return resKeywordName;
    }

    /**
     * Функция возвращает значение переменной «resKeyword».
     * 
     * @return Метод `getResKeyword()` возвращает значение `String`, которое является значением переменной
     * `resKeyword`.
     */
    public String getResKeyword() {
        return resKeyword;
    }

    /**
     * Эта функция возвращает имя ключевого слова «супер» в Java.
     * 
     * @return Метод возвращает значение переменной `supKeywordName`, которая является строкой.
     */
    public String getSupKeywordName() {
        return supKeywordName;
    }


    /**
     * Эта функция возвращает значение переменной «supKeyword» в виде строки.
     * 
     * @return Метод `getSupKeyword()` возвращает переменную `String` с именем `supKeyword`.
     */
    public String getSupKeyword() {
        return supKeyword;
    }

    /**
     * Функция принимает различные ключевые слова для поиска и возвращает список ресурсов на основе
     * критериев ключевых слов.
     * 
     * @param keywordName Строка, представляющая имя ресурса для поиска.
     * @param keywordType Ключевое слово, используемое для поиска ресурсов по их типу.
     * @param keywordQuantity Ключевое слово, используемое для поиска ресурсов по количеству.
     * @param keywordCost Это строковый параметр, используемый в качестве ключевого слова для поиска
     * ресурсов по стоимости. Он используется в методе для вызова метода «searchByCost» из репозитория
     * «repoRes» для получения списка ресурсов, соответствующих заданному ключевому слову стоимости.
     * @param keywordAcdate Этот параметр используется для поиска ресурсов по дате их получения. Это
     * строка, представляющая дату в определенном формате.
     * @param keywordSupplier Строковый параметр, используемый для поиска ресурсов по поставщику.
     * Используется в методе searchBySupplier объекта repoRes.
     * 
     * @return Список ресурсов на основе предоставленных критериев поиска. Если критерии поиска не указаны,
     * возвращаются все ресурсы.
     */
    public List<Resource> listByResCriteria(
            String keywordName,
            String keywordType,
            String keywordQuantity,
            String keywordCost,
            String keywordAcdate,
            String keywordSupplier){
                
        if (!StringUtil.isNullOrEmpty(keywordName)){
            this.resKeywordName = "keywordName";
            this.resKeyword = keywordName;
            return repoRes.searchByName(keywordName);
        }
        else if (!StringUtil.isNullOrEmpty(keywordType)){
            this.resKeywordName = "keywordType";
            this.resKeyword = keywordType;
            return repoRes.searchByType(keywordType);
        }
        else if (!StringUtil.isNullOrEmpty(keywordQuantity)){
            this.resKeywordName = "keywordShipmentCity";
            this.resKeyword = keywordQuantity;
            return repoRes.searchByQuantity(keywordQuantity);
        }
        else if (!StringUtil.isNullOrEmpty(keywordCost)){
            this.resKeywordName = "keywordShipmentDate";
            this.resKeyword = keywordCost;
            return repoRes.searchByCost(keywordCost);
        }
        else if (!StringUtil.isNullOrEmpty(keywordAcdate)){
            this.resKeywordName = "keywordAcDate";
            this.resKeyword = keywordAcdate;
            return repoRes.searchByAcdate(keywordAcdate);
        }
        else if (!StringUtil.isNullOrEmpty(keywordSupplier)){
            this.resKeywordName = "keywordSupplier";
            this.resKeyword = keywordSupplier;
            return repoRes.searchBySupplier(keywordSupplier);
        }
        else return repoRes.findAll();
    }

    /**
     * Функция возвращает список поставщиков на основе различных критериев поиска, таких как имя, телефон,
     * адрес электронной почты и идентификатор.
     * 
     * @param keywordId Строка, представляющая идентификатор поставщика для поиска.
     * @param keywordName Строка, используемая для поиска поставщиков по имени.
     * @param keywordPhone Строка, представляющая номер телефона для поиска в списке поставщиков.
     * @param keywordEmail Строковый параметр, используемый для поиска поставщиков по их адресу электронной
     * почты.
     * 
     * @return Список объектов поставщиков на основе предоставленных критериев поиска. Если критерии не
     * указаны, возвращаются все объекты Supplier.
     */
    public List<Supplier> listBySupCriteria(
            String keywordId,
            String keywordName,
            String keywordPhone,
            String keywordEmail){
        if (!StringUtil.isNullOrEmpty(keywordName)){
            this.supKeywordName = "keywordName";
            this.supKeyword = keywordName;
            return repoSup.searchByName(keywordName);
        }
        else if (!StringUtil.isNullOrEmpty(keywordPhone)){
            this.supKeywordName = "keywordDate";
            this.supKeyword = keywordPhone;
            return repoSup.searchByPhone(keywordPhone);
        }
        else if (!StringUtil.isNullOrEmpty(keywordEmail)){
            this.supKeywordName = "keywordAuthor";
            this.supKeyword = keywordEmail;
            return repoSup.searchByEmail(keywordEmail);
        }
        else if (!StringUtil.isNullOrEmpty(keywordId)){
            this.supKeywordName = "keywordId";
            this.supKeyword = keywordId;
            return repoSup.searchById(keywordId);
        }
        else return repoSup.findAll();
    }

    /**
     * Эта функция добавляет пользователя в систему, кодируя его пароль и сохраняя его информацию в
     * пользовательском репозитории.
     * 
     * @param userInfo UserInfo — это объект, который содержит информацию о пользователе, такую как его имя
     * пользователя, пароль, адрес электронной почты и другие важные сведения. Этот метод принимает объект
     * UserInfo в качестве параметра для добавления пользователя в систему.
     * 
     * @return Метод возвращает строковое сообщение «Пользователь добавлен в систему!» после добавления
     * нового пользователя в систему.
     */
    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepo.save(userInfo);
        return "User added to system!";
    }

    /**
     * Эта функция сохраняет объект ресурса, используя репозиторий.
     * 
     * @param res Параметр «res» — это объект типа «Ресурс», который передается в качестве аргумента
     * методу «saveRes». Этот объект сохраняется в репозиторий с помощью объекта «repoRes».
     */
    public void saveRes(Resource res) {
        repoRes.save(res);
    }

    /**
     * Эта функция сохраняет объект поставщика, используя репозиторий.
     * 
     * @param sup Параметр «sup» — это объект класса «Supplier», который содержит информацию о поставщике.
     * Этот метод сохраняет объект поставщика в хранилище с помощью объекта «repoSup».
     */
    public void saveSup(Supplier sup) {
        repoSup.save(sup);
    }

    /**
     * Эта функция извлекает ресурс из репозитория по его идентификатору.
     * 
     * @param id Параметр «id» — это тип данных Long, представляющий уникальный идентификатор ресурса,
     * извлекаемого из репозитория.
     * 
     * @return Метод getRes возвращает объект Resource с указанным идентификатором. Он извлекает объект
     * «Ресурс» из репозитория с помощью метода «findById» и возвращает его с помощью метода «get».
     */
    public Resource getRes(Long id) {
        return repoRes.findById(id).get();
    }

    /**
     * Эта функция извлекает объект поставщика из репозитория на основе его идентификатора.
     * 
     * @param id Параметр "id" представляет собой тип данных Long, представляющий уникальный идентификатор
     * поставщика. Он используется для извлечения объекта поставщика из репозитория путем вызова метода
     * findById().
     * 
     * @return Метод getSup возвращает объект Supplier с указанным идентификатором, полученным из
     * репозитория repoSup.
     */
    public Supplier getSup(Long id) {
        return repoSup.findById(id).get();
    }


    /**
     * Функция удаляет ресурс из репозитория на основе его идентификатора.
     * 
     * @param id Параметр «id» представляет собой тип данных Long, представляющий уникальный идентификатор
     * ресурса, который необходимо удалить из базы данных. Метод «deleteRes» принимает этот параметр и
     * использует его для удаления соответствующего ресурса из репозитория.
     */
    public void deleteRes(Long id) {
        repoRes.deleteById(id);
    }

    /**
     * Функция удаляет поставщика из репозитория по его идентификатору.
     * 
     * @param id Параметр "id" представляет собой тип данных Long, представляющий собой уникальный
     * идентификатор поставщика, который необходимо удалить из базы данных. Метод «deleteSup» принимает
     * этот параметр и использует его для удаления соответствующей записи поставщика из базы данных с
     * помощью метода «deleteById», предоставляемого репозиторием.
     */
    public void deleteSup(Long id) {
        repoSup.deleteById(id);
    }

    /**
     * Эта функция возвращает список строк, представляющих поставщиков, извлеченных из репозитория.
     * 
     * @return Список строк, содержащих имена поставщиков, извлеченных из репозитория.
     */
    public List<String> getResSupplier(){
        return repoRes.extractSupplier();
    }

    /**
     * Эта функция возвращает список имен поставщиков на основе поиска по ключевому слову или всех имен
     * поставщиков, если ключевое слово не указано.
     * 
     * @param keywordSup Параметр keywordSup представляет собой строку, представляющую ключевое слово,
     * используемое для поиска имен поставщиков. Если параметр не нулевой или пустой, метод будет искать
     * имена поставщиков, соответствующие ключевому слову. В противном случае он вернет список всех
     * доступных имен поставщиков.
     * 
     * @return Метод getSupplierNames возвращает список объектов String. Содержимое списка зависит от
     * входного параметра `keywordSup`. Если `keywordSup` не является нулевым или пустым, метод ищет имена
     * поставщиков, которые соответствуют ключевому слову, и возвращает список этих имен. Если `keywordSup`
     * имеет значение null или пусто, метод возвращает список всех имен поставщиков.
     */
    public List<String> getSupplierNames(String keywordSup){
        if (!StringUtil.isNullOrEmpty(keywordSup)){
            this.resKeywordName = "keywordSup";
            this.resKeyword = keywordSup;
            return repoRes.searchSupplierNames(keywordSup);
        }
        else{
            return repoRes.extractSupplier();
        }
    }

}

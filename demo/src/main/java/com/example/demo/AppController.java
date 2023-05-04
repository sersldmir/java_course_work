package com.example.demo;

import java.util.List;

import com.example.demo.security.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Это класс контроллера, который обрабатывает HTTP-запросы и ответы для веб-приложения управления
 * ресурсами, с методами аутентификации пользователей, управления ресурсами и поставщиками, а также
 * поиска/фильтрации ресурсов и поставщиков. Аннотация `@Controller` указывает, что этот класс является контроллером Spring MVC,
 * а `@EnableMethodSecurity(prePostEnabled = true, proxyTargetClass = true)` включает безопасность на
 * уровне метода с использованием Spring Security. Это означает, что методы в этом классе могут быть
 * аннотированы с помощью `@PreAuthorize` или `@PostAuthorize`, чтобы указать правила управления
 * доступом для этих методов.
 */
@Controller
@EnableMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class AppController {

    /** `@Autowired` — это аннотация Spring, которая внедряет экземпляр класса `ResourcesService` в класс
    * `AppController`. Это позволяет AppController использовать методы и функции, предоставляемые классом
    * ResourcesService.
    */
    @Autowired
    private ResourcesService service;

    /**
     * Это функция, которая возвращает «about_page», когда к конечной точке «/about» обращаются через
     * запрос GET.
     * 
     * @return Метод возвращает строку «about_page» - название соответсвующей html-страницы, которая будет загружена.
     */
    @GetMapping("/about")
    public String about(){
        return "about_page";
    }
    
    /**
     * Эта функция возвращает страницу входа.
     * 
     * @return Метод возвращает строку «login_page» - название соответсвующей html-страницы, которая будет загружена.
     */
    @GetMapping("/login_page")
    public String login(){
        return "login_page";
    }

    /**
     * Эта функция устанавливает имя пользователя текущего пользователя в качестве атрибута сеанса и
     * перенаправляет на домашнюю страницу после отправки формы входа.
     * 
     * @param username Параметр username — это строка, которая передается в качестве параметра запроса
     * методу loginSubmit. Он используется для идентификации пользователя, пытающегося войти в систему.
     * @param session Параметр `session` является объектом класса `HttpSession`, который представляет сеанс
     * пользователя и позволяет сохранять и извлекать данные по нескольким запросам от одного и того же
     * клиента. В этом методе объект сеанса используется для хранения имени пользователя, вошедшего в
     * систему, в качестве атрибута, который
     * 
     * @return Метод возвращает строку «redirect:/», которая является перенаправлением на домашнюю
     * страницу.
     */
    @PostMapping("/login_page")
    public String loginSubmit(@RequestParam String username, HttpSession session){

        final String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        session.setAttribute("username", currentUser);
        return "redirect:/";
    }

    /**
     * Эта функция добавляет в систему нового пользователя и устанавливает атрибуты сеанса для его
     * имени и ролей.
     * 
     * @param userInfo Объект типа UserInfo, который передается как атрибут модели в метод контроллера.
     * @param name Параметр «имя» — это строка, которая передается в качестве параметра запроса в
     * HTTP-запросе POST в конечную точку «/reg». Он используется для установки атрибута «имя пользователя»
     * в объекте HttpSession.
     * @param roles Параметр «roles» — это строка, представляющая роли, назначенные добавляемому
     * пользователю. Он передается в качестве параметра запроса HTTP POST в конечную точку «/reg». Роли
     * можно использовать для определения уровня доступа пользователя и разрешений в приложении.
     * @param session Параметр `session` является объектом класса `HttpSession`, который представляет
     * пользовательский сеанс и позволяет сохранять и извлекать данные по нескольким запросам от одного и
     * того же клиента. В этом фрагменте кода объект «session» используется для хранения атрибутов «name» и
     * «roles».
     * 
     * @return Метод возвращает строку «redirect:/», которая используется для перенаправления пользователя
     * на домашнюю страницу после добавления нового пользователя и настройки атрибутов сеанса.
     */
    @PostMapping("/reg")
    public String addNewUser(@ModelAttribute UserInfo userInfo, @RequestParam String name, @RequestParam String roles, HttpSession session) {

        service.addUser(userInfo);
        session.setAttribute("username", name);
        session.setAttribute("roles", roles);
        return "redirect:/";
    }

    /**
     * Эта функция выводит текущего пользователя из системы и возвращает страницу регистрации.
     * 
     * @param request HttpServletRequest — это интерфейс, предоставляющий методы для получения информации о
     * HTTP-запросе. Он содержит информацию о запросе, такую как URI запроса, заголовки, параметры и другие
     * сведения.
     * @param response Параметр ответа — это объект класса HttpServletResponse, который используется для
     * отправки ответа обратно клиенту после обработки запроса. Он содержит методы для установки заголовков
     * ответа, записи содержимого ответа и установки кода состояния ответа. В этом конкретном фрагменте
     * кода параметр ответа не используется для
     * 
     * @return Метод возвращает строку «reg_page» - название соответсвующей html-страницы, которая будет загружена.
     */
    @GetMapping("/reg")
    public String register(HttpServletRequest request, HttpServletResponse response) {

        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "reg_page";
    }

    /**
     * Эта функция добавляет нового пользователя с ролью администратора и устанавливает атрибуты
     * сеанса для имени пользователя и ролей.
     * 
     * @param userInfo Объект типа UserInfo, содержащий информацию о добавляемом пользователе.
     * @param name Параметр name имеет тип String и используется для передачи имени пользователя,
     * добавляемого в качестве администратора. Получается из параметров запроса с помощью аннотации
     * `RequestParam`.
     * @param roles Параметр «roles» — это строка, содержащая роли, назначенные добавляемому пользователю.
     * В данном случае он используется для назначения пользователю полномочий "ROLE_ADMIN". Это делается с
     * помощью аннотации Spring Security `@PreAuthorize`, которая проверяет, есть ли у текущего
     * пользователя «
     * @param session Параметр сеанса — это объект класса HttpSession в Java. Он представляет сеанс
     * пользователя и позволяет хранить и извлекать данные, связанные с этим сеансом. В этом фрагменте кода
     * объект сеанса используется для установки атрибутов «имя пользователя» и «роли», которые можно
     * использовать для идентификации
     * 
     * @return Метод возвращает строку "redirect:/". Это используется для перенаправления пользователя на
     * домашнюю страницу после добавления нового пользователя с ролью администратора.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/reg_admin")
    public String addNewUserAdmin(@ModelAttribute UserInfo userInfo, @RequestParam String name, @RequestParam String roles, HttpSession session) {

        service.addUser(userInfo);
        session.setAttribute("username", name);
        session.setAttribute("roles", roles);
        return "redirect:/";
    }

    /**
     * Эта функция возвращает страницу регистрации для администраторов и требует авторизации от
     * пользователей с полномочиями "ROLE_ADMIN".
     * 
     * @return Метод возвращает строку «reg_page_admin» - название соответсвующей html-страницы, которая будет загружена.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/reg_admin")
    public String registerAdmin() {
        return "reg_page_admin";
    }

    /**
     * Это функция, которая возвращает представление для домашней страницы, которая включает в себя
     * список ресурсов и их поставщиков, а также позволяет выполнять поиск по ключевому слову.
     * 
     * @param model Модель — это интерфейс в Spring MVC, который обеспечивает способ передачи данных между
     * контроллером и представлением. Это позволяет контроллеру добавлять в модель атрибуты, к которым
     * затем может обращаться представление для отображения данных.
     * @param keyword Строковый параметр, используемый для поиска ресурсов по ключевому слову. Он
     * передается как параметр запроса в URL-адресе.
     * 
     * @return Метод возвращает строку "index" - название соответсвующей html-страницы, которая будет загружена.
     */
    @RequestMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword){

        List<Resource> listRes = service.listAllRes(keyword);
        List<String> listSups = service.getResSupplier();
        model.addAttribute("listRes", listRes);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listSups", listSups);
        return "index";
    }

    /**
     * Эта функция возвращает представление для страницы поставщика со списком поставщиков и функцией
     * поиска.
     * 
     * @param model Модель — это интерфейс в Spring MVC, который обеспечивает способ передачи данных между
     * контроллером и представлением. Это позволяет контроллеру добавлять в модель атрибуты, к которым
     * затем может обращаться представление для отображения данных.
     * @param keyword Параметр ключевого слова — это строка, которая используется для фильтрации списка
     * поставщиков, возвращаемого методом службы. Он передается в качестве параметра в метод и используется
     * для поиска поставщиков, имя или другие атрибуты которых содержат ключевое слово. Затем
     * отфильтрованный список поставщиков добавляется в модель и возвращается
     * 
     * @return Метод возвращает строку «sup», которая является именем представления, которое будет
     * отображаться платформой Spring MVC.
     */
    @RequestMapping("/sup")
    public String viewSupPage(Model model, @Param("keyword") String keyword) {

        List<Supplier> listSup = service.listAllSup(keyword);
        model.addAttribute("listSup", listSup);
        model.addAttribute("keyword", keyword);
        return "sup";
    }

    /**
     * Функция ищет ресурсы на основе различных критериев и возвращает список соответствующих ресурсов для
     * отображения на странице индекса.
     * 
     * @param model Модель — это интерфейс в Spring MVC, который обеспечивает способ передачи данных между
     * контроллером и представлением. Это позволяет контроллеру добавлять в модель атрибуты, к которым
     * затем может обращаться представление для отображения данных.
     * @param keywordName строка, представляющая ключевое слово для поиска в имени ресурса
     * @param keywordType Строковый параметр, используемый в качестве ключевого слова для поиска типа
     * ресурса. Он передается в качестве параметра в метод listByResCriteria() класса службы для получения
     * списка ресурсов, соответствующих указанному типу.
     * @param keywordQuantity Строковый параметр, используемый в качестве ключевого слова для поиска
     * ресурсов по их количеству. Он передается в качестве параметра в метод listByResCriteria объекта
     * службы.
     * @param keywordCost Строковый параметр, используемый в качестве ключевого слова для поиска ресурсов
     * по их стоимости.
     * @param keywordAcdate Этот параметр представляет собой строку, представляющую ключевое слово для
     * поиска ресурсов по дате получения. Он используется в методе для фильтрации списка ресурсов на основе
     * даты приобретения.
     * @param keywordSupplier Строковый параметр, используемый для поиска ресурсов по имени/названии поставщика.
     * 
     * @return Метод возвращает строку "index" - название соответсвующей html-страницы, которая будет загружена.
     */
    @RequestMapping("/findRes")
    public String searchRes(Model model, @Param("keywordName") String keywordName,
                              @Param("keywordType") String keywordType,
                              @Param("keywordQuantity") String keywordQuantity,
                              @Param("keywordCost") String keywordCost,
                              @Param("keywordAcdate") String keywordAcdate,
                              @Param("keywordSupplier") String keywordSupplier){

        List<Resource> listResByCriteria = service.listByResCriteria(keywordName, keywordType,
                keywordQuantity, keywordCost, keywordAcdate, keywordSupplier);
        model.addAttribute("listRes", listResByCriteria);
        model.addAttribute(service.getResKeywordName(),service.getResKeyword());
        List<String> listSups = service.getSupplierNames(keywordSupplier);
        model.addAttribute("listSups", listSups);
        return "index";
    }

    /**
     * Это функция, которая ищет поставщиков на основе определенных критериев и возвращает список
     * подходящих поставщиков.
     * 
     * @param model Модель — это интерфейс в Spring MVC, который обеспечивает способ передачи данных между
     * контроллером и представлением. Это позволяет контроллеру добавлять в модель атрибуты, к которым
     * затем может обращаться представление для отображения данных.
     * @param keywordName Строковый параметр, представляющий ключевое слово, используемое для поиска
     * поставщиков по имени.
     * @param keywordPhone Строковая переменная, содержащая ключевое слово, используемое для поиска
     * поставщиков по номеру телефона.
     * @param keywordEmail Строковый параметр, используемый в качестве ключевого слова для поиска
     * поставщиков по их адресу электронной почты.
     * @param keywordId Строковый параметр, представляющий ключевое слово для поиска поставщика по его
     * идентификатору.
     * 
     * @return Метод возвращает строку «sup».
     */
    @RequestMapping("/findSup")
    public String searchSup(Model model, @Param("keywordName") String keywordName,
                               @Param("keywordPhone") String keywordPhone,
                               @Param("keywordEmail") String keywordEmail,
                               @Param("keywordId") String keywordId){

        List<Supplier> listSupByCriteria = service.listBySupCriteria(keywordId, keywordName, keywordPhone, keywordEmail);
        model.addAttribute("listSup", listSupByCriteria);
        model.addAttribute(service.getSupKeywordName(), service.getSupKeyword());
        return "sup";
    }

    /**
     * Эта функция Java показывает форму для создания нового ресурса и передает в представление модель с
     * объектом ресурса и списком поставщиков.
     * 
     * @param model Модель — это интерфейс в Spring MVC, который обеспечивает способ передачи данных между
     * контроллером и представлением. Это позволяет контроллеру добавлять в модель атрибуты, к которым
     * затем может обращаться представление для отображения данных. В этом случае модель используется для
     * передачи нового объекта Resource
     * 
     * @return Метод возвращает строку «new_res», которая является именем представления, которое будет
     * отображаться платформой Spring MVC.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/newRes")
    public String showNewResForm(Model model){

        Resource resource = new Resource();
        model.addAttribute("resource", resource);
        model.addAttribute("suppliers", service.listAllSups());
        return "new_res";
    }

    /**
     * Эта функция показывает форму для создания нового поставщика и требует от пользователя наличия
     * полномочий "ROLE_ADMIN".
     * 
     * @param model Модель — это интерфейс в Spring MVC, который обеспечивает способ передачи данных между
     * контроллером и представлением. Это позволяет контроллеру добавлять в модель атрибуты, к которым
     * затем может обращаться представление для отображения данных. В этом случае метод showNewSupForm
     * добавляет новый поставщик.
     * 
     * @return Метод возвращает строку «new_sup» - название соответсвующей html-страницы, которая будет загружена.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/newSup")
    public String showNewSupForm(Model model){

        Supplier supplier = new Supplier();
        model.addAttribute("supplier", supplier);
        return "new_sup";
    }

    /**
     * Эта функция сохраняет ресурс с помощью POST-запроса и перенаправляет на домашнюю страницу.
     * 
     * @param resource Параметр «ресурс» — это объект класса Resource, снабженный аннотацией
     * `ModelAttribute`. Он используется для привязки данных формы, отправленных в запросе HTTP POST, к
     * объекту Resource. Затем объект Resource передается в качестве параметра методу service.saveRes() для
     * сохранения ресурса.
     * 
     * @return Метод возвращает строку «redirect:/», которая представляет собой инструкцию перенаправления
     * на корневой URL-адрес приложения.
     */
    @RequestMapping(value = "/saveRes", method = RequestMethod.POST)
    public String saveRes(@ModelAttribute("resource") Resource resource){

        service.saveRes(resource);
        return "redirect:/";
    }

    /**
     * Эта функция сохраняет объект поставщика с помощью запроса POST и перенаправляет на страницу
     * поставщика.
     * 
     * @param supplier Параметр «поставщик» — это объект класса «Поставщик», снабженный аннотацией
     * `@ModelAttribute`. Этот объект используется для захвата данных, отправленных через форму в
     * POST-запросе, и передачи их методу «saveSup» объекта «service».
     * 
     * @return Метод возвращает строку "redirect:/sup". Это инструкция перенаправления в браузер для
     * перенаправления на URL-адрес «/sup» после сохранения объекта поставщика.
     */
    @RequestMapping(value = "/saveSup", method = RequestMethod.POST)
    public String saveSup(@ModelAttribute("supplier") Supplier supplier){
        service.saveSup(supplier);
        return "redirect:/sup";
    }

    /**
     * Эта функция Java показывает форму редактирования ресурса для пользователя с правами администратора и
     * заполняет ее информацией о выбранном ресурсе и списком поставщиков.
     * 
     * @param id Параметр id — это переменная типа Long, которая передается как переменная пути в
     * URL-адресе. Он представляет собой идентификатор ресурса, который необходимо отредактировать.
     * 
     * @return Возвращается объект ModelAndView, который содержит имя представления «edit_res» и два
     * добавленных к нему объекта: «ресурс» и «поставщики». Объект «ресурс» содержит объект «Ресурс»,
     * полученный из службы с использованием заданного идентификатора, а объект «поставщики» содержит
     * список всех поставщиков, полученных из службы.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/editRes/{resid}")
    public ModelAndView showEditResForm(@PathVariable(name = "resid") Long id){

        ModelAndView mav = new ModelAndView("edit_res");
        Resource resource = service.getRes(id);
        mav.addObject("resource", resource);
        mav.addObject("suppliers", service.listAllSups());
        return mav;
    }

    /**
     * Эта функция показывает форму редактирования для поставщика с определенным идентификатором, но
     * только если у пользователя есть полномочия "ROLE_ADMIN".
     * 
     * @param id Параметр id — это переменная типа Long, которая передается как переменная пути в
     * URL-адресе. Он представляет собой уникальный идентификатор поставщика, который необходимо
     * отредактировать.
     * 
     * @return Возвращается объект ModelAndView, который содержит имя представления "edit_sup" и объект
     * Supplier с указанным идентификатором в качестве атрибута.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/editSup/{supid}")
    public ModelAndView showEditSupForm(@PathVariable(name = "supid") Long id){

        ModelAndView mav = new ModelAndView("edit_sup");
        Supplier supplier = service.getSup(id);
        mav.addObject("supplier", supplier);
        return mav;
    }

    /**
     * Эта функция Java удаляет ресурс с определенным идентификатором, если у пользователя есть полномочия
     * ROLE_ADMIN, и перенаправляет на домашнюю страницу.
     * 
     * @param id Параметр id представляет собой переменную типа Long, представляющую идентификатор ресурса,
     * который необходимо удалить. Он получается из URL-пути с помощью аннотации `@PathVariable`.
     * 
     * @return Метод возвращает строку «redirect:/», которая представляет собой инструкцию перенаправления
     * на корневой URL-адрес приложения.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/deleteRes/{resid}")
    public String deleteRes(@PathVariable(name = "resid") Long id){

        service.deleteRes(id);
        return "redirect:/";
    }

    /**
     * Эта функция Java удаляет поставщика с определенным идентификатором, если у пользователя есть
     * полномочия ROLE_ADMIN, и перенаправляет на страницу поставщика.
     * 
     * @param id Параметр id — это переменная типа Long, которая передается как переменная пути в
     * URL-адресе. Он представляет собой идентификатор поставщика, который необходимо удалить. Метод
     * снабжен аннотацией @RequestMapping для сопоставления URL-адреса «/deleteSup/{supid}» с этим методом.
     * Аннотация @PreAuthorize
     * 
     * @return Метод возвращает строку "redirect:/sup", которая представляет собой инструкцию перенаправления
     * на страницу с поставщиками
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/deleteSup/{supid}")
    public String deleteSup(@PathVariable(name = "supid") Long id){

        service.deleteSup(id);
        return "redirect:/sup";
    }

}

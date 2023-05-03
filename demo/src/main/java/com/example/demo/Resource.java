package com.example.demo;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Это класс, представляющий сущность ресурса с различными атрибутами и отношением «многие к
 * одному» с сущностью поставщика.
 * `@Entity` — это аннотация JPA, которая помечает класс как постоянную сущность, что означает, что он
 * будет сопоставлен с таблицей базы данных. `@Table(name = "resources")` указывает имя таблицы базы
 * данных, с которой будет сопоставлен этот объект.
 */
@Entity
@Table(name = "resources")
public class Resource {
    
    /** `@Column(name = "resid")` — это аннотация, указывающая сопоставление поля объекта с соответствующим
    * столбцом в таблице базы данных. В этом случае он сопоставляет поле «resid» со столбцом с именем
    * «resid» в таблице «resources». Строка «private Long resid» объявляет закрытое поле «resid» типа
    * «Long» в классе «Resource».
    */
    @Column(name = "resid")
    private Long resid;

    /** `@Column(name = "name")` — это аннотация, которая сопоставляет поле `name` в классе Java со
    * столбцом с именем `name` в соответствующей таблице базы данных. Это означает, что когда
    * экземпляр этого класса сохраняется в базе данных, значение поля «имя» будет сохранено в столбце
    * «имя» таблицы «ресурсы».  `private String name` объявляет частное поле с именем `name` типа `String` в классе `Resource`.
    * Это поле представляет имя ресурса и сопоставляется со столбцом «имя» в соответствующей таблице
    * базы данных.
    */
    @Column(name = "name")
    private String name;

    /** `@Column(name = "type")` — это аннотация, которая сопоставляет поле `type` в классе Java со
    * столбцом с именем `type` в соответствующей таблице базы данных. Это означает, что когда
    * экземпляр этого класса сохраняется в базе данных, значение поля «тип» будет сохранено в столбце
    * «тип» таблицы «ресурсы». `private String type` объявляет частное поле с именем `type` типа `String` в классе `Resource`.
    * Это поле представляет тип ресурса и сопоставляется со столбцом «тип» в соответствующей таблице
    * базы данных.
    */
    @Column(name = "type")
    private String type;

    /** `@Column(name = «quantity»)` — это аннотация, которая сопоставляет поле «количество» в классе Java
    * со столбцом с именем «количество» в соответствующей таблице базы данных. Это означает, что когда
    * экземпляр этого класса сохраняется в базе данных, значение поля «количество» будет сохранено в
    * столбце «количество» таблицы «ресурсы». Строка `private intquantity` объявляет частное поле с
    * именем `quantity` типа `int` в классе `Resource`. Это поле представляет количество ресурса и
    * сопоставляется со столбцом «количество» в соответствующей таблице базы данных.
    */
    @Column(name = "quantity")
    private int quantity;

    /** `@Column(name = "cost")` — это аннотация, которая сопоставляет поле `cost` в классе `Resource`
    * со столбцом с именем `cost` в соответствующей таблице базы данных. Это означает, что когда
    * экземпляр этого класса сохраняется в базе данных, значение поля «стоимость» будет сохранено в
    * столбце «стоимость» в таблице «ресурсы». Строка `private int cost` объявляет частное поле с
    * именем `cost` типа `int` в классе `Resource`. Это поле представляет стоимость ресурса и
    * сопоставляется со столбцом «стоимость» в соответствующей таблице базы данных.
    */
    @Column(name = "cost")
    private int cost;

    /** `@Column(name = "acdate")` — это аннотация, которая сопоставляет поле `acdate` со столбцом с
    * именем `acdate` в соответствующей таблице базы данных.`private String acdate` объявляет частное поле с именем `acdate` типа `String` в классе
    * `Resource`. Аннотация
    * `@DateTimeFormat` используется для указания формата строки даты.
    */
    @Column(name = "acdate")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private String acdate;

    /** Этот блок кода определяет объявляет частное поле с именем `supplier` типа `Long` в классе
    * `Resource`. Это поле представляет связь между сущностью «Ресурс» и сущностью «Поставщик», где
    * «Ресурс» может иметь одного «Поставщика». Он снабжен аннотацией `@ManyToOne`, чтобы указать
    * отношение «многие к одному» с сущностью «Поставщик», а `@JoinColumn` используется для указания
    * столбца внешнего ключа в таблице «Ресурс», который ссылается на столбец первичного ключа в
    * таблица «Поставщик». Для атрибута `cascade` установлено значение `CascadeType.ALL`, чтобы
    * включить каскадирование всех операций (например, сохранение, слияние, удаление) для связанного
    * объекта `Supplier`. Атрибут `fetch` установлен в `FetchType.LAZY`, чтобы указать, что объект
    * `Supplier` должен загружаться лениво при доступе. Аннотация `OnDelete` используется для указания
    * действия, которое должно быть выполнено при удалении объекта `Supplier`, а `@Fetch` используется
    * для указания стратегии выборки для ассоциации.
    */
    @Column(name = "supplier")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "supplier", referencedColumnName = "supid",
    insertable = false, updatable = false)
    @Fetch(FetchMode.JOIN)
    private Long supplier;

    /** Конструктор по умолчанию для класса `Resource`. Он помечен
    // как «защищенный», чтобы предотвратить доступ к нему за пределами класса или его подклассов. Этот
    // конструктор используется для создания нового экземпляра класса Resource без каких-либо
    // аргументов. Обычно он используется такими фреймворками, как Hibernate, для создания экземпляров
    // класса при извлечении данных из базы данных.
    */
    protected Resource() {
    }

    
    /**
     * Функция возвращает значение поля «resid», которое генерируется автоматически с использованием
     * стратегии IDENTITY. Аннотации `@Id` и `@GeneratedValue` указывают, что это свойство является первичным ключом сущности,
     * и его значение будет автоматически сгенерировано базой данных.
     *
     * @return Идентификатор ресурса типа Long
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getResid() {
        return resid;
    }

    /**
     * Функция устанавливает значение переменной Long с именем «resid» в текущем объекте.
     * 
     * @param resid Идентификатор ресурса типа Long
     */
    public void setResid(Long resid) {
        this.resid = resid;
    }

    /**
     * Функция возвращает название ресурса.
     * 
     * @return Значение переменной name в виде строки.
     */
    public String getName() {
        return name;
    }

    /**
     * Функция устанавливает имя объекта.
     * 
     * @param name Параметр типа String, представляющий имя объекта.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Функция возвращает тип объекта в виде строки.
     * 
     * @return Тип ресурса в виде строки. 
     */
    public String getType() {
        return type;
    }

    /**
     * Функция устанавливает тип ресурса.
     * 
     * @param type Тип ресурса в виде строки.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Функция возвращает количество ресурса.
     * 
     * @return Количество ресурсов в виде целого числа.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Функция задает количество ресрусов.
     * 
     * @param quantity Целочисленное значение, представляющее количество ресурса.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Функция возвращает значение стоимости.
     * 
     * @return Целочисленное значение, которое является значением стоимости ресурса.
     */
    public int getCost() {
        return cost;
    }

    /**
     * Функция устанавливает стоимость ресурса.
     * 
     * @param cost Целочисленное значение, представляющее стоимость ресурса.
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Функция возвращает дату покупки ресурса в виде строки.
     * 
     * @return Дата покупки ресурса в виде строки.
     */
    public String getAcdate() {
        return acdate;
    }

    /**
     * Функция устанавливает дату покупки ресурса.
     * 
     * @param acdate Переменная типа String, представляющая дату покупки ресурса.
     */
    public void setAcdate(String acdate) {
        this.acdate = acdate;
    }

    /**
     * Функция возвращает идентификатор поставщика.
     * 
     * @return Идентификатор поставщика типа `Long`.
     */
    public Long getSupplier() {
        return supplier;
    }

    /**
     * Это метод, который устанавливает идентификатор поставщика.
     * 
     * @param supplier Уникальный идентификатор поставщика типа `Long`
     */
    public void setSupplier(Long supplier) {
        this.supplier = supplier;
    }
}

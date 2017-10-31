pull-up-constructor-body:php

###

1.ru. Создайте конструктор в суперклассе.
1.en. Create a constructor in a superclass.
1.uk. Створіть конструктор в суперкласі.

2.ru. Извлеките общий код конструкторов каждого подкласса в конструктор суперкласса. Перед этим действием стоит попробовать поместить как можно больше общего кода в начало конструктора.
2.en. Extract the common code from the beginning of the constructor of each subclass to the superclass constructor. Before doing so, try to move as much common code as possible to the beginning of the constructor.
2.uk. Витягніть загальний код конструкторів кожного підкласу в конструктор суперкласу. Перед цією дією варто спробувати помістити якомога більше загального коду в початок конструктора.

3.ru. Поместите вызов конструктора суперкласса первой строкой в конструкторах подклассов.
3.en. Place the call for the superclass constructor in the first line in the subclass constructors.
3.uk. Розташуйте виклик конструктора суперкласу першим рядком в конструкторах підкласів.



###

```
class Employee {
  // ...
  protected $name;
  protected $id;
}
   
class Manager extends Employee {
  // ...
  private $grade;
  public function __construct($name, $id, $grade) {
    $this->name = $name;
    $this->id = $id;
    $this->grade = $grade;
  }
}
```

###

```
class Employee {
  // ...
  protected $name;
  protected $id;
  protected function __construct($name, $id) {
    $this->name = $name;
    $this->id = $id;
  }
}
   
class Manager extends Employee {
  // ...
  private $grade;
  public function __construct($name, $id, $grade) {
    parent::__construct($name, $id);
    $this->grade = $grade;
  }
}
```

###

Set step 1

#|ru| Рассмотрим классы менеджера и служащего. В данном примере класс <code>Employee</code> не имеет конструктора, а его поля заполняются в классе <code>Manager</code>, который затем используется в программе.
#|en| Let's look at <i>Pull Up Constructor Body</i> using manager and employee classes. In this case, <code>Employee</code> does not have any constructor and its fields are filled in the <code>Manager</code> class, which is actually used in the program.
#|uk| Розглянемо класи менеджера і службовця. В даному прикладі клас <code>Employee</code> не має конструктора, а його поля заповнюються в класі <code>Manager</code>, який потім використовується в програмі.

#|ru| Таким образом, если мы захотим создать ещё один подкласс <code>Employee</code>, нам придётся дублировать часть конструктора <code>Manager</code>, чтобы инициализировать поля класса <code>Employee</code>.
#|en| So if we want to create another <code>Employee</code> subclass, we must duplicate parts of the <code>Manager</code> constructor in order to initialize the <code>Employee</code> fields.
#|uk| Таким чином, якщо ми захочемо створити ще один підклас <code>Employee</code>, нам доведеться дублювати частину конструктора <code>Manager</code>, щоб ініціалізувати поля класса <code>Employee</code>.

#|ru| Вместо этого мы можем поднять часть тела конструктора <code>Manager</code> в его суперкласс.
#|en| Instead, we can pull up part of the body of the <code>Manager</code> constructor to its superclass.
#|uk| Замість цього ми можемо підняти частину тіла конструктора <code>Manager</code> в його суперклас.

Go to the end of "Employee"

#|ru| Определим конструктор в классе <code>Employee</code> и сделаем его защищённым. Это даст понять другим программистам, что его нужно вызывать в подклассах.
#|en| Let's define the constructor in the <code>Employee</code> class and make it protected. That will work as default implementation and let subclasses call it inside their own constructors.
#|uk| Визначимо конструктор в класі <code>Employee</code> і зробимо його захищеним. Це дасть зрозуміти іншим програмістам, що його потрібно викликати в підкласах.

Print:
```

  protected function __construct() {
  }
```

Set step 2

Select "$name" in "Manager"
+Select "$id" in "Manager"
+Select "$this->name = $name;" in "Manager"
+Select "$this->id = $id;" in "Manager"

#|ru| Перенесём код инициализации полей суперкласса в созданный конструктор.
#|en| Move the code to initialize the fields in the superclass to the created constructor.
#|uk| Перенесемо код ініціалізації полів суперкласу в створений конструктор.

Go to parameters of "protected function __construct" in "Employee"

Print "$name, $id"

Wait 500ms

Select in "Manager":
```
    $this->name = $name;
    $this->id = $id;

```

Remove selected

Wait 500ms

Go to start of "protected function __construct" in "Employee"

Print:
```

    $this->name = $name;
    $this->id = $id;
```

Set step 3

Select name of "public function __construct" in "Employee"

#|ru| После того как новый конструктор стал доступен, его можно вызвать из конструктора <code>Manager</code>.
#|en| At this point, the new constructor can be called inside <code>Manager</code> constructor as <code>super</code>.
#|uk| Після того як новий конструктор став доступний, його можна викликати з конструктора <code>Manager</code>.

Go to start of "public function __construct" in "Manager"

Print:
```

    parent::__construct($name, $id);
```

#C|ru| Запускаем финальное тестирование.
#S Отлично, все работает!

#C|en| Let's start the final testing.
#S Wonderful, it's all working!

#C|uk| Запускаємо фінальне тестування.
#S Супер, все працює.

Set final step

#|ru|Q На этом рефакторинг можно считать оконченным. В завершение, можете посмотреть разницу между старым и новым кодом.
#|en|Q The refactoring is complete! You can compare the old and new code if you like.
#|uk|Q На цьому рефакторинг можна вважати закінченим. На завершення, можете подивитися різницю між старим та новим кодом.
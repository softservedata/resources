<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Edit User Profile</title>
<!-- <link rel="stylesheet" href="resource/css/register.css" type="text/css" media="screen"> -->
</head>
<body>
<!-- <img src="resource/img/wheat.jpg"> -->
<div id="register_container">
    <h4 id="pageID">Зареєструвати нового користувача</h4>
      <div id="contact">
<!--         <fieldset id="personalInfo"> -->
         <form id="register_form" name="register" method="post" action="/registrator/register">
             <p>
                <label for="login">Логін:</label>
                <input name="login" type="text" class="text" id="login" tabindex="100" />
             </p>
             <p>
                <label for="password">Пароль:</label>
                <input name="password" type="password" class="text" id="password" tabindex="100" />
             </p>
             <p>
                <label for="confirm_password">Підтвердити пароль:</label>
                <input name="confirm_password" type="password" class="text" id="confirm_password" tabindex="100" />
             </p>
            <p>
              <label for="first_name">Ім"я</label>
              <input name="firstName" type="text" class="text" id="first_name" tabindex="100" />
            </p>
            <p>
              <label for="last_name">Прізвище</label>
              <input name="lastName" type="text" class="text" id="last_name" tabindex="100" />
            </p>
            <p>
              <label for="middle_name">По батькові</label>
              <input name="middleName" type="text" class="text" id="middle_name" tabindex="100" />
            </p>
            <p>
              <label for="email">Електронна адреса</label>
              <input name="email" type="text" class="text" id="email" tabindex="100" />
            </p>
            <p>
              <label for="phone">Мобільний телефон</label>
              <input name="phone" type="text" class="text" id="phone" tabindex="100" />
            </p>
            <p>
              <label for="address">Адреса</label>
              <input name="address" type="text" class="text" id="address" tabindex="100" />
            </p>
            <p>
              <label for="date_of_birth">Дата народження (у форматі дд-мм-рррр)</label>
              <input name="date_of_birth" type="text" class="text" id="date_of_birth" tabindex="100" />
            </p>
            <p>
              <label for="passport_seria">Паспорт (серія)</label>
              <input name="passport_seria" type="text" class="text" id="passport" tabindex="100" />
            </p>
             <p>
              <label for="passport_number">Паспорт (номер)</label>
              <input name="passport_number" type="text" class="text" id="passport_number" tabindex="100" />
            </p>
            <p>
               <label for="published_by_data">Паспорт виданий (ким і коли)</label>
               <input name="published_by_data" type="text" class="text" id="published_by_data" tabindex="100" />
             </p>
            <input type="submit" name="submit" value="Надіслати">
            <input type="reset" name="cancel" value="Скасувати">
        </form>
<!--         </fieldset> -->
        </div>
        </div>
</body>
</html>
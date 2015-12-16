<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Registrator</title>
</head>
<body>
   <div class="languages">
       <a href="?lang=ua">UA</a>| <a href="?lang=en">ENG</a>| <a
           href="?lang=ru">RUS</a>
   </div>
   <h1>
       Додати новий тип ресурсу
   </h1>
   <form method="POST" action="add-resource-types" name="newRT">
       <table>
           <tr>
               <td>Назва: </td>
               <td><input type="text" name="typeName"></td>
           </tr> 
           <tr>
               <td>Опис лінійного параметру: </td>
               <td><input type="text" name="description"></td>
           </tr>
           <tr>
               <td>Одиниці вимірювання: </td>
               <td><input type="text" name="unitName"></td>
           </tr>
            <tr>
               <td>Опис дискретного параметру: </td>
               <td><input type="text" name="description"></td>
           </tr>
           <tr>
               <td>Одиниці вимірювання: </td>
               <td><input type="text" name="unitName"></td>
           </tr> 
                
       </table>
    <div class="button"> <input type="submit" value="Add"> </div>
   </form>


</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 17.11.2020
  Time: 16:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="dialog-window-for-filters">
    <h3>Расширенный фильтр</h3>
    <div id="search-by-gender">
        <label for="gender-option">Пол:</label>
        <select name="gender" id="gender-option">
            <option value="any-gender" selected>Любой</option>
            <option value="male">Мужской</option>
            <option value="female">Женский</option>
        </select>
    </div>
    <div id="search-by-age">
        <label for="from_age">Возраст:</label>
        <input name="from_age" type="number" id="from_age" placeholder="От">
        <input name="to_age" type="number" id="to_age" placeholder="До">
    </div>
    <div id="search-by-city">
        <label for="city-option">Город:</label>
        <select name="city" id="city-option">
            <option value="any-city" selected>Любой</option>
            <option value="Уфа">Уфа</option>
            <option value="Казань">Казань</option>
        </select>
    </div>

    <button id="submit-search-user-by-criteria">Искать</button>

</div>

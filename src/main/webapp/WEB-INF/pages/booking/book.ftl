<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book the event</title>
</head>
<body>
<a href="/">Home</a>
<form method="post">

    <p>Event: ${eventSchedule.getEvent().getName()}</p>
    <p>Date: ${eventSchedule.getStartDateTime()}</p>
    <p>Auditorium: ${eventSchedule.getAuditorium().getName()}</p>
    <p>Price: ${price?string.currency} (basic)</p>
    <p>Seats:
        <select name="seat">
        <#list seats as seat, vip >
            <option <#if !availableSeats?seq_contains(seat) >disabled</#if>
                    value="${seat}">
                ${seat}<#if vip > (VIP)</#if>
            </option>
        </#list>
        </select>
    </p>
    <p>For user:
        <select name="user">
        <#list users as user >
            <option value="${user.getId()}">${user.getFirstName()} ${user.getLastName()}</option>
        </#list>
        </select>
    </p>
    <button type="submit">Book!</button>

</form>


</body>
</html>
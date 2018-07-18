<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Tickets</title>
</head>
<body>
<a href="/">Home</a>
<h3>Tickets for <a href="/user/view/${user.getId()}">${user.getFirstName()} ${user.getLastName()}</a></h3>
<table class="datatable">
    <tr>
        <th>Event</th>
        <th>Date and Time</th>
        <th>Auditorium</th>
        <th>Seat</th>
        <th>Price</th>
    </tr>
    <#list tickets as ticket >
        <tr>
            <td>${ticket.getEventSchedule().getEvent().getName()}</td>
            <td>${ticket.getEventSchedule().getStartDateTime()}</td>
            <td>${ticket.getEventSchedule().getAuditorium().getName()}</td>
            <td>${ticket.getSeat()}</td>
            <td>${ticket.getPrice()?string.currency}</td>
        </tr>
    </#list>
</table>
</body>
</html>
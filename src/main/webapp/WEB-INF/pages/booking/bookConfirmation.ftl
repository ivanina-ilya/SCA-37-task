<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking Conformation</title>
</head>
<body>
<h5>Are you sure want to book this event?</h5>
<p>Event: ${ticket.getEventSchedule().getEvent().getName()}</p>
<p>Date: ${ticket.getEventSchedule().getStartDateTime()}</p>
<p>Auditorium: ${ticket.getEventSchedule().getAuditorium().getName()}</p>
<p>Seat: ${ticket.getSeat()}</p>
<p>For user: ${ticket.getUser().getFirstName()} ${ticket.getUser().getLastName()} (${ticket.getUser().getEmail()})</p>
<p>Price: ${ticket.getPrice()?string.currency}  (your discount is ${discount.getPercent()}%)</p>
<form action="/booking/${ticket.getEventSchedule().getId()}" method="post">
    <button type="submit">Cancel</button>
</form>
<form action="/user/tickets/${ticket.getUser().getId()}" method="post">
    <input type="hidden" name="preBookTicketId" value="${ticket.getId()}" />
    <button type="submit">Book now!</button>
</form>
</body>
</html>
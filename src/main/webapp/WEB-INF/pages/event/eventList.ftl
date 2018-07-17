<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Event List</title>
</head>
<body>

<table class="datatable">
    <tr>
        <th>Event</th>
        <th>Scheduling</th>
    </tr>
    <#list model["evenModelList"] as key, value >
        <tr>
            <td>${key.getName()}</td>
            <td>
                <table class="scheduletable">
                    <tr>
                        <th>Auditorium</th>
                        <th>Date</th>
                        <th>Booking</th>
                    </tr>
                    <#list value as scheduling >
                        <tr>
                            <td>${scheduling.getAuditorium().getName()}</td>
                            <td>${scheduling.getStartDateTime()}</td>
                            <td><a href="/booking/${scheduling.getId()}">Got to book</a></td>
                        </tr>
                    </#list>
                </table>
            </td>
        </tr>
    <#else >
        <tr><td>No results</td><td></td></tr>
    </#list>
</table>

</body>
</html>
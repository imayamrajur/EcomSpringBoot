<%@ page pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
 
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
      google.charts.load('current', {'packages':['corechart']});
      google.charts.setOnLoadCallback(drawChart);

      function drawChart() {
        var data = google.visualization.arrayToDataTable([
          ['Customer', 'Revenue'],
          <c:forEach var = "e" items = "${data}">
          		["${e[0]}",  ${e[2]}],
          </c:forEach>
        ]);

        var options = {
          title: 'Revenue By Customer',
          curveType: 'function',
          legend: { position: 'bottom' }
        };

        var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));
        chart.draw(data, options);
      }
    </script>
    
  <div id="curve_chart" style="width: 900px!important; height: 500px!important;"></div>

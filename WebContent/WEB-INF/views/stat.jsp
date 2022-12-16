<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  <title>Tennis Stats</title>
  <script type="text/javascript" src="<c:url value="/webjars/jquery/1.9.1/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/index.js"/>"></script>
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>"/> 
  <script type="text/javascript">
	$(document).on("keydown", function(e){
		processUserSelectionData('LOGGER_FORM_KEYPRESS',e.which);
	});
  </script>  
  
</head>
<body onload="processTennisProcedures('READ_MATCH_FOR_STATS',null)">
<form:form name="tennis_stat_form" autocomplete="off">
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
  <div class="container">
	<div class="row">
	 <div class="col-md-12 offset-md-12">
       <span class="anchor"></span>
         <div class="card card-outline-secondary">
          <div class="card-body">
			<div class="row">
			 <div class="col-lg-4">
			    <h6>Tennis Stats</h6>
 				<table class="table table-bordered table-responsive">
				  <thead>
				    <tr>
				      <th scope="col"></th>
				      <th scope="col">Stats</th>
				    </tr>
				  </thead>
				  <tbody>
				    <tr>
				      <td>
					    <label>Ace</label>
					  </td>
				      <td>
					    <input id="ace_increment_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="ace_txt" type="text" style="width:25%" value="0"></input>
					    <input id="ace_decrement_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
				    </tr>
				  </tbody>
				</table> 
			  </div>
            </div>
          </div>
         </div>
       </div>
    </div>
  </div>
 </div>
 <input type="hidden" id="stat_player_id" name="stat_player_id" value="${stat_player_id}"></input>
</form:form>
</body>
</html>
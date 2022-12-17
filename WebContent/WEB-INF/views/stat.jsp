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
				      <th scope="col">${session_match.homeFirstPlayer.full_name}</th>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <th scope="col">${session_match.homeSecondPlayer.full_name}</th>
					  </c:if>
				      <th scope="col">${session_match.awayFirstPlayer.full_name}</th>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <th scope="col">${session_match.awaySecondPlayer.full_name}</th>
					  </c:if>
				    </tr>
				  </thead>
				  <tbody>
				    <tr>
				      <td>
					    <label>Ace</label>
					  </td>
				      <td>
					    <input id="ace_increment_home_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="ace_home_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="ace_decrement_home_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="ace_increment_home_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="ace_home_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="ace_decrement_home_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
				      <td>
					    <input id="ace_increment_away_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="ace_away_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="ace_decrement_away_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="ace_increment_away_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="ace_away_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="ace_decrement_away_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
				    </tr>
				    <tr>
				      <td>
					    <label>1st Serve Point</label>
					  </td>
				      <td>
					    <input id="firstServePoint_increment_home_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="firstServePoint_home_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="firstServePoint_decrement_home_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="firstServePoint_increment_home_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="firstServePoint_home_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="firstServePoint_decrement_home_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
				      <td>
					    <input id="firstServePoint_increment_away_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="firstServePoint_away_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="firstServePoint_decrement_away_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="firstServePoint_increment_away_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="firstServePoint_away_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="firstServePoint_decrement_away_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
				    </tr>
				    <tr>
				      <td>
					    <label>Forehand Winner</label>
					  </td>
				      <td>
					    <input id="forehandWinner_increment_home_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="forehandWinner_home_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="forehandWinner_decrement_home_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="forehandWinner_increment_home_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="forehandWinner_home_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="forehandWinner_decrement_home_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
				      <td>
					    <input id="forehandWinner_increment_away_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="forehandWinner_away_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="forehandWinner_decrement_away_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="forehandWinner_increment_away_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="forehandWinner_away_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="forehandWinner_decrement_away_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
				    </tr>
				    <tr>
				      <td>
					    <label>Back Hand Winner</label>
					  </td>
				      <td>
					    <input id="backhandWinner_increment_home_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="backhandWinner_home_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="backhandWinner_decrement_home_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="backhandWinner_increment_home_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="backhandWinner_home_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="backhandWinner_decrement_home_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
				      <td>
					    <input id="backhandWinner_increment_away_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="backhandWinner_away_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="backhandWinner_decrement_away_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="backhandWinner_increment_away_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="backhandWinner_away_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="backhandWinner_decrement_away_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
				    </tr>
				    <tr>
				      <td>
					    <label>Serve Winner</label>
					  </td>
				      <td>
					    <input id="serveWinner_increment_home_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="serveWinner_home_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="serveWinner_decrement_home_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="serveWinner_increment_home_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="serveWinner_home_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="serveWinner_decrement_home_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
				      <td>
					    <input id="serveWinner_increment_away_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="serveWinner_away_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="serveWinner_decrement_away_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="serveWinner_increment_away_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="serveWinner_away_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="serveWinner_decrement_away_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
				    </tr>
				    <tr>
				      <td>
					    <label>Double Fault</label>
					  </td>
				      <td>
					    <input id="doubleFault_increment_home_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="doubleFault_home_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="doubleFault_decrement_home_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="doubleFault_increment_home_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="doubleFault_home_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="doubleFault_decrement_home_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
				      <td>
					    <input id="doubleFault_increment_away_first_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
					    <input id="doubleFault_away_first_txt" type="text" style="width:25%" value="0"></input>
					    <input id="doubleFault_decrement_away_first_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
					  </td>
					  <c:if test = "${session_match.matchType == 'doubles'}">
					      <td>
						    <input id="doubleFault_increment_away_second_btn" type="button" onclick="processUserSelection(this)" value="+"></input>
						    <input id="doubleFault_away_second_txt" type="text" style="width:25%" value="0"></input>
						    <input id="doubleFault_decrement_away_second_btn" type="button" onclick="processUserSelection(this)" value="-"></input>
						  </td>
					  </c:if>
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
</form:form>
</body>
</html>
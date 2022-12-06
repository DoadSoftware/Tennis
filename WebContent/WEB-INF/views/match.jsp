<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
  <sec:csrfMetaTags/>
  <meta charset="utf-8" name="viewport" content="width=device-width, initial-scale=1">
  <title>Tennis</title>
  <script type="text/javascript" src="<c:url value="/webjars/jquery/1.9.1/jquery.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/webjars/bootstrap/3.3.6/js/bootstrap.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/javascript/index.js"/>"></script>
  <link rel="stylesheet" href="<c:url value="/webjars/bootstrap/3.3.6/css/bootstrap.min.css"/>"/>
  <script type="text/javascript">
	$(document).on("keydown", function(e){
		if (e.which >= 112 && e.which <= 123) { // Suppress default behaviour of F1 to F12
			e.preventDefault();
		}
		processUserSelectionData('LOGGER_FORM_KEYPRESS',e.which);
	});
  </script>  
</head>
<body onload="afterPageLoad('MATCH');">
<form:form name="tennis_form" autocomplete="off" action="match" method="POST" enctype="multipart/form-data">
<div class="content py-5" style="background-color: #EAE8FF; color: #2E008B">
  <div class="container">
	<div class="row">
	 <div class="col-md-8 offset-md-2">
       <span class="anchor"></span>
         <div class="card card-outline-secondary">
           <div class="card-header">
			  <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
	          </div>
           </div>
          <div class="card-body">
          <div id="select_graphic_options_div" style="display:none;">
			  </div>
			  <div class="panel-group" id="match_configuration">
			    <div class="panel panel-default">
			      <div class="panel-heading">
			        <h5 class="panel-title">
			          <a data-toggle="collapse" data-parent="#match_configuration" href="#load_setup_match">Configuration</a>
			        </h5>
			      </div>
			      <div id="load_setup_match" class="panel-collapse collapse">
					<div class="panel-body">
					    <div class="col-sm-4 col-md-4">
						    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
						  		name="setup_match_btn" id="setup_match_btn" onclick="processUserSelection(this)">
						  		<i class="fas fa-tools"></i> Setup Match</button>
						 </div>
					    <div class="col-sm-8 col-md-8">
						    <label for="select_tennis_matches" class="col-sm-5 col-form-label text-left">Select Tennis Match</label>
						      <select id="select_tennis_matches" name="select_tennis_matches" 
						      		class="browser-default custom-select custom-select-sm">
									<c:forEach items = "${match_files}" var = "match">
							          <option value="${match.name}">${match.name}</option>
									</c:forEach>
						      </select>
						    <button style="background-color:#2E008B;color:#FEFEFE;" class="btn btn-sm" type="button"
						  		name="load_match_btn" id="load_match_btn" onclick="processUserSelection(this)">
						  		<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true" style="display:none"></span>
						  		<i class="fas fa-download"></i> Load Match</button>
					    </div>
				    </div>
			      </div>
			    </div>
			  </div> 
		    <div class="form-group row row-bottom-margin ml-2" style="margin-bottom:5px;">
			  <div id="select_event_div" style="display:none;"></div>
			  <div id="tennis_div" style="display:none;"></div>
           </div>
          </div>
         </div>
       </div>
    </div>
  </div>
 </div>
 <input type="hidden" id="selected_player_id" name="selected_player_id"></input>
 <input type="hidden" name="selectedBroadcaster" id="selectedBroadcaster" value="${session_selected_broadcaster}"/>
</form:form>
</body>
</html>